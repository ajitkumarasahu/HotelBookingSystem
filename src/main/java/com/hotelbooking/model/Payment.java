package com.hotelbooking.model;

/**
 * Represents a payment made for a hotel booking.
 * <p>
 * The {@code Payment} class stores information about a specific transaction,
 * including the booking it is associated with, the payment amount, method, and status.
 * It can be extended to include additional fields like refund details or timestamps
 * depending on system requirements.
 * </p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     Payment payment = new Payment();
 *     payment.setBookingId(2001);
 *     payment.setAmount(350.75);
 *     payment.setMethod("Credit Card");
 *     payment.setStatus("Completed");
 * </pre>
 * 
 * <p><b>Possible values:</b></p>
 * <ul>
 *     <li><b>method:</b> "Credit Card", "Debit Card", "PayPal", "Cash", "Bank Transfer"</li>
 *     <li><b>status:</b> "Pending", "Completed", "Failed", "Refunded"</li>
 * </ul>
 * 
 * @author  
 * @version 1.0
 */
public class Payment {

    /** The unique identifier for this payment record (primary key). */
    private int id;

    /** The ID of the booking associated with this payment. */
    private int bookingId;

    /** The total amount paid for the booking. */
    private double amount;

    /** The method used to make the payment (e.g., Credit Card, Cash, PayPal). */
    private String method;

    /** The current status of the payment (e.g., Pending, Completed, Failed, Refunded). */
    private String status;

    /**
     * Default no-argument constructor.
     * <p>Creates an empty {@code Payment} object.</p>
     */
    public Payment() {}

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    /**
     * Gets the unique identifier of the payment.
     *
     * @return the payment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the payment.
     *
     * @param id the payment ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the associated booking.
     *
     * @return the booking ID
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Sets the ID of the associated booking.
     *
     * @param bookingId the booking ID to set
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the total amount of the payment.
     *
     * @return the payment amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the total amount of the payment.
     *
     * @param amount the amount to set (must be non-negative)
     * @throws IllegalArgumentException if the amount is negative
     */
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Payment amount cannot be negative.");
        }
        this.amount = amount;
    }

    /**
     * Gets the method used to make the payment.
     *
     * @return the payment method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method used to make the payment.
     *
     * @param method the payment method to set (e.g., "Credit Card", "Cash")
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the current status of the payment.
     *
     * @return the payment status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the payment.
     *
     * @param status the payment status to set (e.g., "Pending", "Completed", "Failed")
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
