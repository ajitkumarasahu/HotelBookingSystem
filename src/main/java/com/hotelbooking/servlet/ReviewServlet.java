package com.hotelbooking.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelbooking.dao.ReviewDAO;
import com.hotelbooking.model.Review;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.List;

/**
 * {@code ReviewServlet} handles HTTP requests related to hotel room reviews.
 * <p>
 * This servlet supports the following operations:
 * <ul>
 *     <li><b>GET</b> – Retrieve all reviews or reviews for a specific room.</li>
 *     <li><b>POST</b> – Add a new review (JSON in request body).</li>
 *     <li><b>DELETE</b> – Delete a review by its ID.</li>
 * </ul>
 * <p>
 * Responses are returned in JSON format. This servlet interacts with the {@link ReviewDAO}
 * data access object and uses the Jackson {@link ObjectMapper} for JSON serialization.
 *
 * Example endpoints:
 * <ul>
 *     <li>GET /reviews — retrieves all reviews</li>
 *     <li>GET /reviews?roomId=101 — retrieves reviews for room #101</li>
 *     <li>POST /reviews — creates a new review (JSON body)</li>
 *     <li>DELETE /reviews?id=5 — deletes review #5</li>
 * </ul>
 */
public class ReviewServlet extends HttpServlet {

    /** DAO for review database operations. */
    private final ReviewDAO dao = new ReviewDAO();

    /** Jackson ObjectMapper for JSON serialization and deserialization. */
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles GET requests to retrieve reviews.
     * <p>
     * If the {@code roomId} parameter is present, retrieves reviews for that specific room.
     * Otherwise, retrieves all reviews.
     *
     * @param req the HTTP request, optionally containing the {@code roomId} query parameter
     * @param res the HTTP response containing a JSON array of {@link Review} objects
     * @throws IOException if an I/O error occurs during the process
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        try {
            String roomId = req.getParameter("roomId");
            List<Review> list;

            if (roomId != null) {
                list = dao.getReviewsByRoom(Integer.parseInt(roomId));
            } else {
                list = dao.getAllReviews();
            }

            res.getWriter().write(mapper.writeValueAsString(list));
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles POST requests to create a new review.
     * <p>
     * Expects a JSON body representing a {@link Review} object. On success, returns
     * the generated review ID in JSON format.
     *
     * Example JSON body:
     * <pre>
     * {
     *   "roomId": 101,
     *   "userId": 15,
     *   "rating": 4,
     *   "comment": "Great experience!"
     * }
     * </pre>
     *
     * @param req the HTTP request containing JSON data for the new review
     * @param res the HTTP response containing the created review ID in JSON
     * @throws IOException if an I/O error occurs during the process
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            Review r = mapper.readValue(req.getReader(), Review.class);
            int id = dao.addReview(r);

            res.setContentType("application/json");
            res.getWriter().write("{\"id\":" + id + "}");
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Handles DELETE requests to remove a review by ID.
     * <p>
     * Requires an {@code id} query parameter (e.g., {@code ?id=10}).
     * Returns a confirmation message if deleted, or 404 if the review is not found.
     *
     * @param req the HTTP request containing the review {@code id} parameter
     * @param res the HTTP response indicating deletion status in JSON
     * @throws IOException if an I/O error occurs during the process
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean ok = dao.deleteReview(id);

            res.setContentType("application/json");
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
