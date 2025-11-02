package com.hotelbooking.dao;

import com.hotelbooking.model.Review;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Review} entities.
 * <p>
 * Provides CRUD operations related to the {@code reviews} table in the database,
 * such as adding new reviews, retrieving reviews by room, fetching all reviews,
 * and deleting existing reviews.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Insert new customer reviews into the database</li>
 *   <li>Retrieve reviews for a specific room</li>
 *   <li>Retrieve all reviews</li>
 *   <li>Delete reviews by ID</li>
 * </ul>
 *
 * <p><b>Note:</b> This class uses {@link DBConnection} for database connections and
 * automatically maps query results to {@link Review} objects.</p>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class ReviewDAO {

    /**
     * Inserts a new review record into the database.
     * <p>
     * The review includes information such as room ID, customer ID, rating, and comment.
     * The method returns the auto-generated primary key of the newly inserted review.
     * </p>
     *
     * @param r The {@link Review} object containing the review details.
     * @return The auto-generated ID of the new review, or {@code -1} if insertion fails.
     * @throws Exception if a database error occurs during execution.
     */
    public int addReview(Review r) throws Exception {
        String sql = "INSERT INTO reviews(room_id, customer_id, rating, comment) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, r.getRoomId());
            ps.setInt(2, r.getCustomerId());
            ps.setInt(3, r.getRating());
            ps.setString(4, r.getComment());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Retrieves all reviews associated with a specific room.
     * <p>
     * The reviews are ordered by their creation timestamp in descending order (newest first).
     * </p>
     *
     * @param roomId The ID of the room for which to retrieve reviews.
     * @return A list of {@link Review} objects belonging to the specified room.
     *         Returns an empty list if no reviews are found.
     * @throws Exception if a database error occurs during retrieval.
     */
    public List<Review> getReviewsByRoom(int roomId) throws Exception {
        String sql = "SELECT * FROM reviews WHERE room_id = ? ORDER BY created_at DESC";
        List<Review> out = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = mapRow(rs);
                    out.add(r);
                }
            }
        }
        return out;
    }

    /**
     * Retrieves all reviews from the database.
     * <p>
     * This method is typically used for administrative purposes
     * (e.g., monitoring feedback across all rooms).
     * </p>
     *
     * @return A list of all {@link Review} records, sorted by creation time (newest first).
     * @throws Exception if a database error occurs.
     */
    public List<Review> getAllReviews() throws Exception {
        String sql = "SELECT * FROM reviews ORDER BY created_at DESC";
        List<Review> out = new ArrayList<>();

        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            while (rs.next()) {
                out.add(mapRow(rs));
            }
        }
        return out;
    }

    /**
     * Deletes a review from the database by its ID.
     *
     * @param id The ID of the review to delete.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     * @throws Exception if a database error occurs during deletion.
     */
    public boolean deleteReview(int id) throws Exception {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Maps a single row from a {@link ResultSet} to a {@link Review} object.
     * <p>
     * This method is a helper used internally to convert database records into
     * usable Java objects.
     * </p>
     *
     * @param rs The {@link ResultSet} pointing to the current row.
     * @return A {@link Review} object containing the mapped data.
     * @throws SQLException if an error occurs while accessing the result set.
     */
    private Review mapRow(ResultSet rs) throws SQLException {
        Review r = new Review();
        r.setId(rs.getInt("id"));
        r.setRoomId(rs.getInt("room_id"));
        r.setCustomerId(rs.getInt("customer_id"));
        r.setRating(rs.getInt("rating"));
        r.setComment(rs.getString("comment"));

        Timestamp ts = rs.getTimestamp("created_at");
        r.setCreatedAt(ts != null ? ts.toString() : null);

        return r;
    }
}
