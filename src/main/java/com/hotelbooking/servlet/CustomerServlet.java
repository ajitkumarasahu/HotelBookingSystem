package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.CustomerDAO;
import com.hotelbooking.model.Customer;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;

/**
 * {@code CustomerServlet} provides REST-style endpoints to manage hotel customers.
 * <p>
 * This servlet supports the following operations:
 * <ul>
 *     <li><b>GET</b> – Retrieve all customers or a single customer by ID.</li>
 *     <li><b>POST</b> – Create a new customer record.</li>
 *     <li><b>PUT</b> – Update an existing customer record.</li>
 *     <li><b>DELETE</b> – Remove a customer by ID.</li>
 * </ul>
 * <p>
 * All responses are returned in JSON format. This servlet interacts with the {@link CustomerDAO}
 * for database operations and uses the Jackson {@link ObjectMapper} for JSON handling.
 *
 * <h3>Example Endpoints:</h3>
 * <ul>
 *     <li>GET /customers — retrieves all customers</li>
 *     <li>GET /customers?id=3 — retrieves a specific customer by ID</li>
 *     <li>POST /customers — creates a new customer (JSON body)</li>
 *     <li>PUT /customers — updates an existing customer (JSON body)</li>
 *     <li>DELETE /customers?id=3 — deletes a customer by ID</li>
 * </ul>
 */
public class CustomerServlet extends HttpServlet {

    /** Data Access Object for customer-related database operations. */
    private final CustomerDAO dao = new CustomerDAO();

    /** Jackson ObjectMapper for serializing and deserializing JSON data. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests to retrieve customer information.
     * <p>
     * If an {@code id} query parameter is provided, retrieves a single customer by ID.
     * Otherwise, returns a list of all customers.
     *
     * @param req  the HTTP request, optionally containing an {@code id} parameter
     * @param resp the HTTP response containing JSON data (single or list of customers)
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");
        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Customer c = dao.getById(id);
                if (c == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Customer not found\"}");
                } else {
                    resp.getWriter().write(mapper.writeValueAsString(c));
                }
            } else {
                List<Customer> list = dao.getAll();
                resp.getWriter().write(mapper.writeValueAsString(list));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP POST requests to create a new customer record.
     * <p>
     * Expects a JSON body containing customer data. Returns the generated customer ID on success.
     *
     * Example JSON body:
     * <pre>
     * {
     *   "name": "John Doe",
     *   "email": "john.doe@example.com",
     *   "phone": "1234567890",
     *   "address": "123 Main Street"
     * }
     * </pre>
     *
     * @param req  the HTTP request containing the JSON representation of a {@link Customer}
     * @param resp the HTTP response containing the created customer ID in JSON
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Customer c = mapper.readValue(req.getReader(), Customer.class);
            int id = dao.create(c);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"created\",\"id\":" + id + "}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP PUT requests to update an existing customer record.
     * <p>
     * Expects a JSON body containing the updated {@link Customer} data (including the ID).
     * Returns a success message if updated, or a 404 error if the record was not found.
     *
     * Example JSON body:
     * <pre>
     * {
     *   "id": 3,
     *   "name": "Jane Smith",
     *   "email": "jane.smith@example.com",
     *   "phone": "9876543210",
     *   "address": "456 Elm Avenue"
     * }
     * </pre>
     *
     * @param req  the HTTP request containing the updated customer JSON data
     * @param resp the HTTP response indicating success or failure
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Customer c = mapper.readValue(req.getReader(), Customer.class);
            boolean ok = dao.update(c);
            resp.setContentType("application/json");
            if (ok) {
                resp.getWriter().write("{\"status\":\"updated\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Customer not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP DELETE requests to delete a customer by ID.
     * <p>
     * Requires an {@code id} query parameter (e.g., {@code /customers?id=5}).
     * Returns a confirmation message if the deletion succeeds, or a 404 error if not found.
     *
     * @param req  the HTTP request containing the {@code id} parameter
     * @param resp the HTTP response indicating deletion status in JSON
     * @throws IOException if an input or output error occurs
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
                resp.getWriter().write("{\"error\":\"Customer not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
