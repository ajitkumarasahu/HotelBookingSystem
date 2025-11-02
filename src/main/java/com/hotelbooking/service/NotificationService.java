package com.hotelbooking.service;

import com.hotelbooking.dao.NotificationDAO;
import com.hotelbooking.model.Notification;

import java.util.List;

/**
 * {@code NotificationService} manages the creation, retrieval, and lifecycle
 * of system and user notifications within the hotel booking system.
 * <p>
 * It provides validation and business logic for notification creation, marking
 * notifications as read, and deletion. This service delegates persistence operations
 * to the {@link NotificationDAO}.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Validate and create new notifications for users or system events.</li>
 *     <li>Fetch notifications for a specific user or all notifications (if userId is null).</li>
 *     <li>Mark notifications as read when acknowledged by the user.</li>
 *     <li>Delete notifications from the system when they are no longer needed.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * NotificationService service = new NotificationService();
 * Notification n = new Notification();
 * n.setUserId(5);
 * n.setTitle("Booking Confirmed");
 * n.setMessage("Your room booking #123 has been confirmed.");
 * int id = service.createNotification(n);
 * </pre>
 *
 * @see com.hotelbooking.model.Notification
 * @see com.hotelbooking.dao.NotificationDAO
 */
public class NotificationService {

    /** Data Access Object responsible for managing notification persistence. */
    private final NotificationDAO dao = new NotificationDAO();

    /**
     * Creates a new notification after validating required fields.
     * <p>
     * This method ensures:
     * <ul>
     *     <li>The {@link Notification} object is not {@code null}.</li>
     *     <li>The notification title is not empty.</li>
     *     <li>The notification message is not empty.</li>
     * </ul>
     * Once validated, the notification is persisted through {@link NotificationDAO#createNotification(Notification)}.
     *
     * @param n the {@link Notification} object containing notification details
     * @return the generated notification ID
     * @throws BusinessException if required fields are missing or invalid
     * @throws Exception if a database or DAO error occurs
     */
    public int createNotification(Notification n) throws Exception {
        if (n == null)
            throw new BusinessException("Notification required");
        if (n.getTitle() == null || n.getTitle().trim().isEmpty())
            throw new BusinessException("Title required");
        if (n.getMessage() == null || n.getMessage().trim().isEmpty())
            throw new BusinessException("Message required");
        return dao.createNotification(n);
    }

    /**
     * Retrieves a list of notifications for a specific user.
     * <p>
     * If {@code userId} is {@code null}, this method may return system-wide notifications
     * or all notifications depending on DAO implementation.
     *
     * @param userId the user ID whose notifications should be retrieved, or {@code null} for all
     * @return a list of {@link Notification} objects
     * @throws Exception if a database access error occurs
     */
    public List<Notification> getNotificationsForUser(Integer userId) throws Exception {
        return dao.getNotificationsForUser(userId);
    }

    /**
     * Marks a specific notification as read.
     * <p>
     * If the notification ID does not exist, a {@link BusinessException} is thrown.
     *
     * @param id the ID of the notification to mark as read
     * @throws BusinessException if the notification does not exist
     * @throws Exception if a database or DAO error occurs
     */
    public void markAsRead(int id) throws Exception {
        if (!dao.markAsRead(id))
            throw new BusinessException("Notification not found");
    }

    /**
     * Deletes a notification by its ID.
     * <p>
     * If the notification does not exist, a {@link BusinessException} is thrown.
     *
     * @param id the ID of the notification to delete
     * @throws BusinessException if the notification cannot be found or deleted
     * @throws Exception if a database or DAO error occurs
     */
    public void deleteNotification(int id) throws Exception {
        if (!dao.deleteNotification(id))
            throw new BusinessException("Notification not found");
    }
}
