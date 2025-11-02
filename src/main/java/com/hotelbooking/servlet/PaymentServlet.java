package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.PaymentDAO;
import com.hotelbooking.model.Payment;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * {@code PaymentServlet} manages CRUD operations for hotel booking payments.
 * <p>
 * This servlet exposes RESTful endpoints for managing payment records.
 * All responses are returned in JSON format, and the servlet interacts with the
 * {@link PaymentDAO} class to perform database operations.
 *
 * <h3>Supported Endpoints:</h3>
 * <ul>
 *     <li><b>GET /payments</b> — Retrieve all payments or a single payment by ID.</li>
 *     <li><b>POST /payments</b> — Create a new payment record.</li>
 *     <li><b>PUT /payments</b> — Update an existing payment record.</li>
 *     <li><b>DELETE /payments</b> — Delete a payment by ID.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * GET /payments?id=12
 * POST /payments
 * {
 *   "bookingId": 45,
 *   "amount": 299.99,
 *   "method": "Credit Card",
 *   "status": "Completed"
 * }
 * </pre>
 *
 * <p>All methods return JSON responses and use standard HTTP status codes.</p>
 */
public class PaymentServlet extends HttpServlet {

    /** Data Access Object for payment database operations. */
    private final PaymentDAO dao = new PaymentDAO();

    /** Jackson ObjectMapper for JSON serialization/deserialization. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests to retrieve payment records.
     * <p>
     * If the {@code id} query parameter is provided, returns the corresponding payment record.
     * Otherwise, returns a list of all payments.
     *
     * @param req  the HTTP request, may contain the {@code id} parameter
     * @param resp the HTTP response containing payment data in JSON format
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");
        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Payment p = dao.getById(id);
                if (p == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Payment not found\"}");
                } else {
                    resp.getWriter().write(mapper.writeValueAsString(p));
                }
            } else {
                List<Payment> list = dao.getAll();
                resp.getWriter().write(mapper.writeValueAsString(list));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP POST requests to create a new payment record.
     * <p>
     * Expects a JSON body representing a {@link Payment} object. Returns a confirmation message
     * along with the generated payment ID.
     *
     * Example JSON body:
     * <pre>
     * {
     *   "bookingId": 45,
     *   "amount": 299.99,
     *   "method": "Credit Card",
     *   "status": "Completed"
     * }
     * </pre>
     *
     * @param req  the HTTP request containing the payment JSON data
     * @param resp the HTTP response with the created payment ID in JSON
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Payment p = mapper.readValue(req.getReader(), Payment.class);
            int id = dao.create(p);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"created\",\"id\":" + id + "}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP PUT requests to update an existing payment record.
     * <p>
     * Expects a JSON body containing the full {@link Payment} object, including its ID.
     * If the record exists, it is updated; otherwise, a 404 response is returned.
     *
     * Example JSON body:
     * <pre>
     * {
     *   "id": 12,
     *   "bookingId": 45,
     *   "amount": 350.00,
     *   "method": "Debit Card",
     *   "status": "Updated"
     * }
     * </pre>
     *
     * @param req  the HTTP request containing the updated payment JSON
     * @param resp the HTTP response indicating success or failure
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Payment p = mapper.readValue(req.getReader(), Payment.class);
            boolean ok = dao.update(p);
            resp.setContentType("application/json");
            if (ok) {
                resp.getWriter().write("{\"status\":\"updated\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Payment not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP DELETE requests to remove a payment record by ID.
     * <p>
     * Requires an {@code id} query parameter (e.g., {@code ?id=5}).
     * Returns a success message if the payment was deleted, or a 404 error if not found.
     *
     * @param req  the HTTP request containing the {@code id} parameter
     * @param resp the HTTP response with JSON indicating result
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String idParam = req.getParameter("id");
            resp.setContentType("application/json");

            if (idParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"id required\"}");
                return;
            }

            int id = Integer.parseInt(idParam);
            boolean ok = dao.delete(id);

            if (ok) {
                resp.getWriter().write("{\"status\":\"deleted\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Payment not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
