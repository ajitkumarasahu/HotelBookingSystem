package com.hotelbooking.model;

/**
 * Represents a customer review for a specific room in the hotel booking system.
 * <p>
 * A {@code Review} contains information about the customer who submitted it,
 * the room being reviewed, the numerical rating, an optional comment, and
 * the timestamp of when the review was created.
 * </p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     Review review = new Review();
 *     review.setRoomId(101);
 *     review.setCustomerId(5);
 *     review.setRating(4);
 *     review.setComment("Clean and comfortable stay.");
 *     review.setCreatedAt("2025-11-01 15:30:00");
 * </pre>
 *
 * @author  
 * @version 1.0
 */
public class Review {

    /** The unique identifier for this review (primary key). */
    private int id;

    /** The ID of the room being reviewed. */
    private int roomId;

    /** The ID of the customer who submitted the review. */
    private int customerId;

    /** The numerical rating given by the customer, ranging from 1 (lowest) to 5 (highest). */
    private int rating;

    /** The customer's comment about their stay. */
    private String comment;

    /** The timestamp of when the review was created, formatted as {@code yyyy-MM-dd HH:mm:ss}. */
    private String createdAt;

    /**
     * Default no-argument constructor.
     * <p>Creates an empty {@code Review} object.</p>
     */
    public Review() {}

    /**
     * Gets the unique identifier of this review.
     *
     * @return the review ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this review.
     *
     * @param id the review ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the room being reviewed.
     *
     * @return the room ID
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Sets the ID of the room being reviewed.
     *
     * @param roomId the room ID to set
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the ID of the customer who submitted this review.
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer who submitted this review.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the rating given by the customer.
     *
     * @return the rating value (1–5)
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating given by the customer.
     * <p>Expected range is between 1 (lowest) and 5 (highest).</p>
     *
     * @param rating the rating to set
     * @throws IllegalArgumentException if the rating is not between 1 and 5
     */
    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
        this.rating = rating;
    }

    /**
     * Gets the customer's comment for the review.
     *
     * @return the comment text
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the customer's comment for the review.
     *
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the timestamp when this review was created.
     *
     * @return the creation date and time as a string (format: yyyy-MM-dd HH:mm:ss)
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when this review was created.
     *
     * @param createdAt the timestamp to set (format: yyyy-MM-dd HH:mm:ss)
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
