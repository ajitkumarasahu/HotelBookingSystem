package com.hotelbooking.model;

/**
 * Represents a record of a completed or historical hotel booking.
 * <p>
 * The {@code BookingHistory} class stores detailed information about a past booking,
 * including customer details, room information, check-in and check-out dates,
 * and payment details. It is typically used for viewing or generating reports
 * of previous reservations.
 * </p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     BookingHistory history = new BookingHistory();
 *     history.setBookingId(5001);
 *     history.setRoomId(101);
 *     history.setRoomNumber("A-203");
 *     history.setRoomType("Deluxe");
 *     history.setCustomerId(12);
 *     history.setCustomerName("John Smith");
 *     history.setCheckIn("2025-10-20");
 *     history.setCheckOut("2025-10-25");
 *     history.setPaymentAmount(750.0);
 *     history.setPaymentStatus("Paid");
 * </pre>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *     <li>Displaying past bookings in customer or admin dashboards</li>
 *     <li>Generating reports or invoices</li>
 *     <li>Auditing booking and payment history</li>
 * </ul>
 * 
 * @author  
 * @version 1.0
 */
public class BookingHistory {

    /** The unique identifier of the booking. */
    private int bookingId;

    /** The ID of the room associated with the booking. */
    private int roomId;

    /** The room number assigned for the booking. */
    private String roomNumber;

    /** The type of the room (e.g., Deluxe, Suite, Standard). */
    private String roomType;

    /** The unique identifier of the customer who made the booking. */
    private int customerId;

    /** The full name of the customer who made the booking. */
    private String customerName;

    /** The check-in date for the booking (format: yyyy-MM-dd). */
    private String checkIn;

    /** The check-out date for the booking (format: yyyy-MM-dd). */
    private String checkOut;

    /** The total payment amount for the booking. */
    private double paymentAmount;

    /** The current status of the payment (e.g., Paid, Pending, Refunded). */
    private String paymentStatus;

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    /**
     * Gets the unique identifier of the booking.
     *
     * @return the booking ID
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets the unique identifier of the booking.
     *
     * @param bookingId the booking ID to set
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the ID of the room associated with the booking.
     *
     * @return the room ID
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Sets the ID of the room associated with the booking.
     *
     * @param roomId the room ID to set
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the room number assigned for the booking.
     *
     * @return the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number assigned for the booking.
     *
     * @param roomNumber the room number to set
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the type of the room.
     *
     * @return the room type
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Sets the type of the room.
     *
     * @param roomType the room type to set (e.g., "Deluxe", "Suite")
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
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
     * Gets the name of the customer who made the booking.
     *
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer who made the booking.
     *
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the check-in date of the booking.
     *
     * @return the check-in date (format: yyyy-MM-dd)
     */
    public String getCheckIn() {
        return checkIn;
    }

    /**
     * Sets the check-in date of the booking.
     *
     * @param checkIn the check-in date to set (format: yyyy-MM-dd)
     */
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Gets the check-out date of the booking.
     *
     * @return the check-out date (format: yyyy-MM-dd)
     */
    public String getCheckOut() {
        return checkOut;
    }

    /**
     * Sets the check-out date of the booking.
     *
     * @param checkOut the check-out date to set (format: yyyy-MM-dd)
     */
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * Gets the total payment amount for the booking.
     *
     * @return the payment amount
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the total payment amount for the booking.
     *
     * @param paymentAmount the payment amount to set (must be non-negative)
     * @throws IllegalArgumentException if the payment amount is negative
     */
    public void setPaymentAmount(double paymentAmount) {
        if (paymentAmount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        this.paymentAmount = paymentAmount;
    }

    /**
     * Gets the payment status of the booking.
     *
     * @return the payment status
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the booking.
     *
     * @param paymentStatus the payment status to set (e.g., "Paid", "Pending", "Refunded")
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
