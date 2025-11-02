package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.BookingDAO;
import com.hotelbooking.dao.RoomDAO;
import com.hotelbooking.model.Booking;

import javax.servlet.http.*;
import java.io.*;
import java.sql.Date;
import java.util.*;

/**
 * {@code BookingServlet} handles all CRUD operations related to hotel room bookings.
 * <p>
 * This servlet exposes REST-style endpoints to manage booking data, including validation of room
 * availability and basic field checks. It communicates with {@link BookingDAO} for database operations
 * and {@link RoomDAO} to verify room existence and availability.
 * <p>
 * All requests and responses use JSON format.
 *
 * <h3>Supported Endpoints:</h3>
 * <ul>
 *     <li><b>GET /bookings</b> — Retrieve all bookings or one by ID.</li>
 *     <li><b>POST /bookings</b> — Create a new booking (with room/date validation).</li>
 *     <li><b>PUT /bookings</b> — Update an existing booking.</li>
 *     <li><b>DELETE /bookings</b> — Delete a booking by ID.</li>
 * </ul>
 *
 * <h3>Example JSON Request (POST):</h3>
 * <pre>
 * {
 *   "customerId": 2,
 *   "roomId": 5,
 *   "checkIn": "2025-11-01",
 *   "checkOut": "2025-11-05",
 *   "status": "CONFIRMED"
 * }
 * </pre>
 */
public class BookingServlet extends HttpServlet {

    /** Data access object for booking operations. */
    private final BookingDAO dao = new BookingDAO();

    /** DAO for validating room existence and availability. */
    private final RoomDAO roomDao = new RoomDAO();

    /** ObjectMapper for JSON parsing and serialization. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests to retrieve booking information.
     * <p>
     * If an {@code id} parameter is provided, returns a single booking record.
     * Otherwise, returns a list of all bookings.
     *
     * @param req  the HTTP request (optionally containing {@code id})
     * @param resp the HTTP response containing booking(s) in JSON format
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");
        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Booking b = dao.getById(id);
                if (b == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Booking not found\"}");
                } else {
                    resp.getWriter().write(mapper.writeValueAsString(b));
                }
            } else {
                List<Booking> list = dao.getAll();
                resp.getWriter().write(mapper.writeValueAsString(list));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP POST requests to create a new booking.
     * <p>
     * Validates required fields, ensures the room exists, and checks date availability.
     * Returns the newly created booking ID upon success.
     *
     * @param req  the HTTP request containing the booking JSON data
     * @param resp the HTTP response containing creation status or validation errors
     * @throws IOException if an I/O error occurs during parsing or writing
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Booking b = mapper.readValue(req.getReader(), Booking.class);

            // Required field validation
            if (b.getRoomId() == 0 || b.getCustomerId() == 0 ||
                b.getCheckIn() == null || b.getCheckOut() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"customerId, roomId, checkIn, checkOut required\"}");
                return;
            }

            // Validate room existence
            if (roomDao.getById(b.getRoomId()) == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Room does not exist\"}");
                return;
            }

            // Validate date range and room availability
            Date in = Date.valueOf(b.getCheckIn());
            Date out = Date.valueOf(b.getCheckOut());
            if (!dao.isRoomAvailable(b.getRoomId(), in, out)) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write("{\"error\":\"Room not available for the selected dates\"}");
                return;
            }

            int id = dao.create(b);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"status\":\"created\",\"id\":" + id + "}");
        } catch (IllegalArgumentException ia) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Bad date format. Use YYYY-MM-DD\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP PUT requests to update an existing booking record.
     * <p>
     * Expects a JSON body representing the full {@link Booking} object.
     * Returns a success message if updated, or 404 if the booking does not exist.
     *
     * @param req  the HTTP request containing updated booking data
     * @param resp the HTTP response indicating the update status
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Booking b = mapper.readValue(req.getReader(), Booking.class);
            boolean ok = dao.update(b);
            resp.setContentType("application/json");
            if (ok) {
                resp.getWriter().write("{\"status\":\"updated\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Booking not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP DELETE requests to remove a booking by ID.
     * <p>
     * Requires an {@code id} query parameter (e.g., {@code /bookings?id=10}).
     * Returns a JSON confirmation message if deleted, or a 404 error if not found.
     *
     * @param req  the HTTP request containing the {@code id} parameter
     * @param resp the HTTP response with JSON deletion status
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
                resp.getWriter().write("{\"error\":\"Booking not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
