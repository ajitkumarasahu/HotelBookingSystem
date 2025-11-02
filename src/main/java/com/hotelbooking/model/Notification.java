package com.hotelbooking.model;

/**
 * Represents a notification sent to a user or related to a booking
 * in the hotel booking system.
 * <p>
 * A {@code Notification} may inform users about booking confirmations,
 * cancellations, payment updates, reminders, or other system events.
 * Notifications can be user-specific or general (when {@code userId} is null).
 * </p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     Notification notification = new Notification();
 *     notification.setUserId(15);
 *     notification.setBookingId(2001);
 *     notification.setTitle("Booking Confirmed");
 *     notification.setMessage("Your booking for Room 101 has been confirmed.");
 *     notification.setRead(false);
 *     notification.setCreatedAt("2025-11-01 10:45:00");
 * </pre>
 *
 * <p><b>Possible Use Cases:</b></p>
 * <ul>
 *     <li>Booking confirmation or cancellation messages</li>
 *     <li>Payment status updates</li>
 *     <li>Promotional or informational alerts</li>
 * </ul>
 *
 * @author  
 * @version 1.0
 */
public class Notification {

    /** The unique identifier for the notification (primary key). */
    private int id;

    /** The ID of the user associated with the notification (nullable). */
    private Integer userId;

    /** The ID of the booking related to the notification (nullable). */
    private Integer bookingId;

    /** The title or subject of the notification. */
    private String title;

    /** The detailed message content of the notification. */
    private String message;

    /** Indicates whether the notification has been read by the user. */
    private boolean isRead;

    /** The timestamp when the notification was created, formatted as {@code yyyy-MM-dd HH:mm:ss}. */
    private String createdAt;

    /**
     * Default no-argument constructor.
     * <p>Creates an empty {@code Notification} object.</p>
     */
    public Notification() {}

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    /**
     * Gets the unique identifier of the notification.
     *
     * @return the notification ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the notification.
     *
     * @param id the notification ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user associated with this notification.
     * <p>This field can be {@code null} for system-wide notifications.</p>
     *
     * @return the user ID, or {@code null} if not applicable
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user associated with this notification.
     *
     * @param userId the user ID to set, or {@code null} for system-wide notifications
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the booking related to this notification.
     * <p>This field can be {@code null} if the notification is not booking-specific.</p>
     *
     * @return the booking ID, or {@code null} if not applicable
     */
    public Integer getBookingId() {
        return bookingId;
    }

    /**
     * Sets the ID of the booking related to this notification.
     *
     * @param bookingId the booking ID to set, or {@code null} if not applicable
     */
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the title or subject of the notification.
     *
     * @return the notification title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title or subject of the notification.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the detailed message content of the notification.
     *
     * @return the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the detailed message content of the notification.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks whether the notification has been read by the user.
     *
     * @return {@code true} if the notification has been read, {@code false} otherwise
     */
    public boolean isRead() {
        return isRead;
    }

    /**
     * Sets the read status of the notification.
     *
     * @param read {@code true} if the notification has been read, {@code false} otherwise
     */
    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * Gets the creation timestamp of the notification.
     *
     * @return the creation date and time as a string (format: yyyy-MM-dd HH:mm:ss)
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp of the notification.
     *
     * @param createdAt the timestamp to set (format: yyyy-MM-dd HH:mm:ss)
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
