package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.NotificationDAO;
import com.hotelbooking.model.Notification;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.List;

/**
 * {@code NotificationServlet} manages CRUD operations for user and system notifications.
 * <p>
 * This servlet provides endpoints to:
 * <ul>
 *     <li><b>GET</b> — Retrieve notifications for a specific user or all system-wide notifications.</li>
 *     <li><b>POST</b> — Create a new notification (via JSON body).</li>
 *     <li><b>PUT</b> — Mark a notification as read using its ID.</li>
 *     <li><b>DELETE</b> — Delete a notification by ID.</li>
 * </ul>
 * <p>
 * All responses are in JSON format, and database operations are handled via {@link NotificationDAO}.
 *
 * <h3>Example Endpoints:</h3>
 * <ul>
 *     <li>GET /notifications — retrieves all system and user notifications</li>
 *     <li>GET /notifications?userId=15 — retrieves notifications for user #15</li>
 *     <li>POST /notifications — creates a new notification (JSON body)</li>
 *     <li>PUT /notifications?id=20 — marks notification #20 as read</li>
 *     <li>DELETE /notifications?id=20 — deletes notification #20</li>
 * </ul>
 */
public class NotificationServlet extends HttpServlet {

    /** DAO for notification-related database operations. */
    private final NotificationDAO dao = new NotificationDAO();

    /** Jackson ObjectMapper for JSON serialization and deserialization. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests to retrieve notifications.
     * <p>
     * If a {@code userId} query parameter is provided, returns notifications for that specific user.
     * Otherwise, returns all notifications (including system-wide ones).
     *
     * @param req the HTTP request, optionally containing a {@code userId} parameter
     * @param res the HTTP response containing a JSON list of {@link Notification} objects
     * @throws IOException if an I/O error occurs during reading or writing
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        try {
            String uid = req.getParameter("userId");
            Integer userId = uid == null ? null : Integer.valueOf(uid);
            List<Notification> list = dao.getNotificationsForUser(userId);
            res.getWriter().write(mapper.writeValueAsString(list));
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP POST requests to create a new notification.
     * <p>
     * Expects a JSON body containing notification details. The response returns the generated
     * notification ID in JSON format.
     *
     * Example JSON body:
     * <pre>
     * {
     *   "userId": 15,
     *   "message": "Your booking has been confirmed",
     *   "type": "BOOKING_CONFIRMATION",
     *   "read": false
     * }
     * </pre>
     *
     * @param req the HTTP request containing the JSON representation of a {@link Notification}
     * @param res the HTTP response containing the created notification ID in JSON
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Notification n = mapper.readValue(req.getReader(), Notification.class);
            int id = dao.createNotification(n);
            res.setContentType("application/json");
            res.getWriter().write("{\"id\":" + id + "}");
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP PUT requests to mark a notification as read.
     * <p>
     * Requires an {@code id} query parameter identifying the notification to update.
     * Returns a success message if updated, or an error message if not found.
     *
     * Example request:
     * <pre>
     * PUT /notifications?id=10
     * </pre>
     *
     * @param req the HTTP request containing the {@code id} parameter
     * @param res the HTTP response indicating success or error in JSON
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            String idParam = req.getParameter("id");
            res.setContentType("application/json");
            if (idParam == null) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write("{\"error\":\"id required\"}");
                return;
            }
            int id = Integer.parseInt(idParam);
            boolean ok = dao.markAsRead(id);
            if (ok) {
                res.getWriter().write("{\"status\":\"marked\"}");
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("{\"error\":\"Not found\"}");
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles HTTP DELETE requests to remove a notification by ID.
     * <p>
     * Requires an {@code id} query parameter specifying the notification to delete.
     * Returns a success message if deleted, or a 404 error if not found.
     *
     * Example request:
     * <pre>
     * DELETE /notifications?id=12
     * </pre>
     *
     * @param req the HTTP request containing the {@code id} parameter
     * @param res the HTTP response indicating deletion status in JSON
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            res.setContentType("application/json");
            int id = Integer.parseInt(req.getParameter("id"));
            boolean ok = dao.deleteNotification(id);
            if (ok) {
                res.getWriter().write("{\"status\":\"deleted\"}");
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("{\"error\":\"Not found\"}");
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
