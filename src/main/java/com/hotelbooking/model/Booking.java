package com.hotelbooking.model;

/**
 * Represents a hotel room booking made by a customer.
 * <p>
 * The {@code Booking} class holds the core details of a hotel reservation,
 * including the customer ID, room ID, and check-in/check-out dates.
 * </p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     Booking booking = new Booking();
 *     booking.setId(101);
 *     booking.setCustomerId(15);
 *     booking.setRoomId(202);
 *     booking.setCheckIn("2025-11-10");
 *     booking.setCheckOut("2025-11-15");
 * </pre>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *     <li>Storing and retrieving booking details from the database</li>
 *     <li>Passing booking data between services or controllers</li>
 *     <li>Generating invoices, confirmations, or reports</li>
 * </ul>
 * 
 * @author  
 * @version 1.0
 */
public class Booking {

    /** The unique identifier for the booking. */
    private int id;

    /** The ID of the customer who made the booking. */
    private int customerId;

    /** The ID of the room associated with this booking. */
    private int roomId;

    /** The check-in date for the booking (format: YYYY-MM-DD). */
    private String checkIn;

    /** The check-out date for the booking (format: YYYY-MM-DD). */
    private String checkOut;

    /** 
     * Default no-argument constructor. 
     * Used for frameworks and libraries that require a default constructor (e.g., ORM tools).
     */
    public Booking() {}

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    /**
     * Gets the unique identifier of the booking.
     *
     * @return the booking ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the booking.
     *
     * @param id the booking ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the customer who made the booking.
     *
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer who made the booking.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the ID of the room associated with this booking.
     *
     * @return the room ID
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Sets the ID of the room associated with this booking.
     *
     * @param roomId the room ID to set
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the check-in date for the booking.
     *
     * @return the check-in date (format: YYYY-MM-DD)
     */
    public String getCheckIn() {
        return checkIn;
    }

    /**
     * Sets the check-in date for the booking.
     *
     * @param checkIn the check-in date to set (format: YYYY-MM-DD)
     */
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Gets the check-out date for the booking.
     *
     * @return the check-out date (format: YYYY-MM-DD)
     */
    public String getCheckOut() {
        return checkOut;
    }

    /**
     * Sets the check-out date for the booking.
     *
     * @param checkOut the check-out date to set (format: YYYY-MM-DD)
     */
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * Returns a string representation of the booking, useful for debugging or logging.
     *
     * @return a formatted string containing booking details
     */
    @Override
    public String toString() {
        return "Booking [id=" + id +
                ", customerId=" + customerId +
                ", roomId=" + roomId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut + "]";
    }
}
