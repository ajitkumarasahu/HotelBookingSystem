package com.hotelbooking.dao;

import com.hotelbooking.model.Notification;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Notification} entities.
 * <p>
 * Handles all database operations related to user notifications, including
 * creation, retrieval, marking as read, and deletion. Notifications may
 * optionally be associated with a specific user or booking.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Insert new notifications (system-wide or user-specific)</li>
 *   <li>Retrieve notifications for a given user</li>
 *   <li>Mark notifications as read</li>
 *   <li>Delete notifications</li>
 * </ul>
 *
 * <p><b>Database Table:</b> {@code notifications}</p>
 * <p><b>Columns:</b> id, user_id, booking_id, title, message, is_read, created_at</p>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class NotificationDAO {

    /**
     * Creates a new notification record in the database.
     * <p>
     * If the notification is intended for all users (a global notification),
     * the {@code userId} field in the {@link Notification} object can be {@code null}.
     * </p>
     *
     * @param n The {@link Notification} object containing the notification details.
     * @return The auto-generated ID of the inserted notification, or {@code -1} if creation fails.
     * @throws Exception if a database error occurs during insertion.
     */
    public int createNotification(Notification n) throws Exception {
        String sql = "INSERT INTO notifications(user_id, booking_id, title, message) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (n.getUserId() == null) ps.setNull(1, Types.INTEGER);
            else ps.setInt(1, n.getUserId());

            if (n.getBookingId() == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, n.getBookingId());

            ps.setString(3, n.getTitle());
            ps.setString(4, n.getMessage());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Retrieves all notifications for a given user.
     * <p>
     * If {@code userId} is {@code null}, retrieves all notifications in the system.
     * Otherwise, retrieves both user-specific and global (user_id is NULL) notifications.
     * </p>
     *
     * @param userId The ID of the user, or {@code null} to fetch all notifications.
     * @return A list of {@link Notification} objects sorted by creation date (newest first).
     * @throws Exception if a database error occurs.
     */
    public List<Notification> getNotificationsForUser(Integer userId) throws Exception {
        String sql;
        if (userId == null) {
            sql = "SELECT * FROM notifications ORDER BY created_at DESC";
        } else {
            sql = "SELECT * FROM notifications WHERE user_id = ? OR user_id IS NULL ORDER BY created_at DESC";
        }

        List<Notification> out = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (userId != null) ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapRow(rs));
            }
        }
        return out;
    }

    /**
     * Marks a notification as read.
     *
     * @param id The ID of the notification to mark as read.
     * @return {@code true} if the notification was successfully updated, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean markAsRead(int id) throws Exception {
        String sql = "UPDATE notifications SET is_read=1 WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param id The ID of the notification to delete.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean deleteNotification(int id) throws Exception {
        String sql = "DELETE FROM notifications WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Helper method for mapping a single database row to a {@link Notification} object.
     * <p>
     * Used internally by query methods to convert {@link ResultSet} data into Java objects.
     * </p>
     *
     * @param rs The {@link ResultSet} positioned at the current row.
     * @return A populated {@link Notification} object.
     * @throws SQLException if an SQL access error occurs.
     */
    private Notification mapRow(ResultSet rs) throws SQLException {
        Notification n = new Notification();
        n.setId(rs.getInt("id"));

        int uid = rs.getInt("user_id");
        n.setUserId(rs.wasNull() ? null : uid);

        int bid = rs.getInt("booking_id");
        n.setBookingId(rs.wasNull() ? null : bid);

        n.setTitle(rs.getString("title"));
        n.setMessage(rs.getString("message"));
        n.setRead(rs.getInt("is_read") == 1);

        Timestamp ts = rs.getTimestamp("created_at");
        n.setCreatedAt(ts != null ? ts.toString() : null);

        return n;
    }

    /**
     * Fetches notifications for a user.
     * <p>
     * This method is a placeholder stub for future implementations (e.g., integrating caching or push notifications).
     * </p>
     *
     * @param userId The ID of the user for whom notifications are being fetched.
     * @return A list of {@link Notification} objects (currently returns an empty list).
     */
    public List<Notification> getNotifications(int userId) {
        System.out.println("Fetching notifications for user: " + userId);
        return new ArrayList<>();
    }
}
