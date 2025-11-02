package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.RoomDAO;
import com.hotelbooking.model.Room;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * {@code RoomServlet} handles all HTTP requests related to room management
 * in the Hotel Booking System.
 *
 * <p>This servlet exposes a RESTful-style API for performing CRUD operations
 * (Create, Read, Update, Delete) on {@link Room} entities via JSON requests
 * and responses.</p>
 *
 * <p>Supported endpoints:</p>
 * <ul>
 *   <li><b>GET /api/rooms</b> → Retrieve all rooms</li>
 *   <li><b>GET /api/rooms?id={id}</b> → Retrieve room by ID</li>
 *   <li><b>POST /api/rooms</b> → Create a new room</li>
 *   <li><b>PUT /api/rooms</b> → Update an existing room</li>
 *   <li><b>DELETE /api/rooms?id={id}</b> → Delete a room by ID</li>
 * </ul>
 *
 * <p>All responses are in JSON format and follow the pattern:
 * <pre>
 * {
 *   "status": "created" | "updated" | "deleted",
 *   "error": "Room not found" | "Database error"
 * }
 * </pre></p>
 *
 * <p>Example JSON for POST/PUT:</p>
 * <pre>
 * {
 *   "id": 1,
 *   "type": "Deluxe Suite",
 *   "price": 250.0,
 *   "available": true
 * }
 * </pre>
 *
 * @author  
 * @version 1.0
 */
public class RoomServlet extends HttpServlet {

    /** Data Access Object for performing database operations on Room entities. */
    private final RoomDAO dao = new RoomDAO();

    /** ObjectMapper from Jackson library for JSON serialization/deserialization. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests.
     *
     * <p>If an 'id' parameter is provided, fetches a single room by ID.
     * Otherwise, returns a list of all available rooms.</p>
     *
     * @param req  the {@link HttpServletRequest} object
     * @param resp the {@link HttpServletResponse} object
     * @throws IOException if writing to the response fails
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        try {
            if (idParam != null) {
                // Retrieve room by ID
                int id = Integer.parseInt(idParam);
                Room room = dao.getById(id);

                if (room == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Room not found\"}");
                } else {
                    resp.getWriter().write(mapper.writeValueAsString(room));
                }
            } else {
                // Retrieve all rooms
                List<Room> rooms = dao.getAll();
                resp.getWriter().write(mapper.writeValueAsString(rooms));
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid room ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * <p>Creates a new {@link Room} record in the database using JSON input from the request body.</p>
     *
     * @param req  the {@link HttpServletRequest} object containing JSON room data
     * @param resp the {@link HttpServletResponse} object
     * @throws IOException if reading or writing JSON fails
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            // Parse the JSON body into a Room object
            Room room = mapper.readValue(req.getReader(), Room.class);

            // Persist to database and return created ID
            int id = dao.create(room);
            resp.getWriter().write("{\"status\":\"created\",\"id\":" + id + "}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP PUT requests.
     *
     * <p>Updates an existing {@link Room} in the database. 
     * The request body should contain the complete room object in JSON format.</p>
     *
     * @param req  the {@link HttpServletRequest} object
     * @param resp the {@link HttpServletResponse} object
     * @throws IOException if JSON parsing or database operation fails
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            Room room = mapper.readValue(req.getReader(), Room.class);
            boolean updated = dao.update(room);

            if (updated) {
                resp.getWriter().write("{\"status\":\"updated\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Room not found\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP DELETE requests.
     *
     * <p>Deletes a {@link Room} from the database based on the provided room ID.</p>
     *
     * @param req  the {@link HttpServletRequest} containing the 'id' parameter
     * @param resp the {@link HttpServletResponse} used to return the result
     * @throws IOException if response writing fails
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String idParam = req.getParameter("id");

            if (idParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"id parameter is required\"}");
                return;
            }

            int id = Integer.parseInt(idParam);
            boolean deleted = dao.delete(id);

            if (deleted) {
                resp.getWriter().write("{\"status\":\"deleted\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Room not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid room ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
