package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.BookingHistoryDAO;
import com.hotelbooking.model.BookingHistory;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.List;

/**
 * {@code BookingHistoryServlet} provides a REST endpoint for retrieving
 * the booking history of a specific customer.
 * <p>
 * This servlet exposes a simple GET API that returns a list of past
 * bookings (both completed and canceled) associated with a customer ID.
 * All responses are provided in JSON format.
 *
 * <h3>Supported Endpoint:</h3>
 * <ul>
 *     <li><b>GET /api/history?customerId=1</b> — Retrieve booking history for a specific customer.</li>
 * </ul>
 *
 * <h3>Example Request:</h3>
 * <pre>
 * GET /api/history?customerId=1
 * </pre>
 *
 * <h3>Example JSON Response:</h3>
 * <pre>
 * [
 *   {
 *     "bookingId": 12,
 *     "roomId": 4,
 *     "checkIn": "2025-10-01",
 *     "checkOut": "2025-10-05",
 *     "status": "COMPLETED"
 *   },
 *   {
 *     "bookingId": 9,
 *     "roomId": 2,
 *     "checkIn": "2025-09-20",
 *     "checkOut": "2025-09-22",
 *     "status": "CANCELLED"
 *   }
 * ]
 * </pre>
 */
public class BookingHistoryServlet extends HttpServlet {

    /** Data access object for booking history operations. */
    private final BookingHistoryDAO dao = new BookingHistoryDAO();

    /** ObjectMapper for converting between Java objects and JSON. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests to fetch the booking history for a customer.
     * <p>
     * Requires a {@code customerId} query parameter.
     * Returns HTTP 400 if the parameter is missing,
     * and 500 if any unexpected error occurs.
     *
     * @param req the HTTP request containing {@code customerId} as a parameter
     * @param res the HTTP response containing a JSON array of booking history entries
     * @throws IOException if an I/O error occurs while reading or writing data
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        String param = req.getParameter("customerId");

        if (param == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"error\":\"customerId required\"}");
            return;
        }

        try {
            int customerId = Integer.parseInt(param);
            List<BookingHistory> list = dao.getHistoryByCustomer(customerId);
            res.getWriter().write(mapper.writeValueAsString(list));
        } catch (NumberFormatException nfe) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"error\":\"customerId must be a valid integer\"}");
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
