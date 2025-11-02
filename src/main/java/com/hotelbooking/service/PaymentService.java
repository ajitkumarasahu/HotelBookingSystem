package com.hotelbooking.service;

import com.hotelbooking.dao.PaymentDAO;
import com.hotelbooking.dao.BookingDAO;
import com.hotelbooking.model.Payment;
import com.hotelbooking.model.Booking;

import java.util.List;

/**
 * {@code PaymentService} provides business logic for managing payment operations
 * related to hotel bookings. It performs validation of payment details and ensures
 * that all transactions are associated with valid bookings.
 * <p>
 * This service encapsulates business rules such as ensuring payment amounts are valid,
 * verifying booking existence before payment creation, and enforcing refund constraints.
 * It delegates persistence operations to the {@link PaymentDAO} and {@link BookingDAO}.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Create and validate new payment records for existing bookings.</li>
 *     <li>Retrieve all payment records for administrative or reporting use.</li>
 *     <li>Process refunds with validation against original payment amounts.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * PaymentService service = new PaymentService();
 * Payment payment = new Payment();
 * payment.setBookingId(10);
 * payment.setAmount(250.00);
 * payment.setMethod("Credit Card");
 * int paymentId = service.createPayment(payment);
 * </pre>
 *
 * @see com.hotelbooking.model.Payment
 * @see com.hotelbooking.dao.PaymentDAO
 * @see com.hotelbooking.model.Booking
 */
public class PaymentService {

    /** Data Access Object for interacting with the payment persistence layer. */
    private final PaymentDAO paymentDAO = new PaymentDAO();

    /** Data Access Object for validating booking references and relationships. */
    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * Creates a new payment record after performing business validation.
     * <p>
     * This method ensures that:
     * <ul>
     *     <li>The payment object is not {@code null}.</li>
     *     <li>The associated booking exists and has a valid ID.</li>
     *     <li>The payment amount is greater than zero.</li>
     * </ul>
     * Once validated, the payment is persisted using {@link PaymentDAO#createPayment(Payment)}.
     *
     * @param p the {@link Payment} object containing transaction details
     * @return the generated payment ID
     * @throws BusinessException if validation fails (e.g., invalid booking, missing amount)
     * @throws Exception if a database or DAO error occurs
     */
    public int createPayment(Payment p) throws Exception {
        if (p == null)
            throw new BusinessException("Payment required");
        if (p.getBookingId() <= 0)
            throw new BusinessException("Booking required");
        Booking b = bookingDAO.getById(p.getBookingId());
        if (b == null)
            throw new BusinessException("Booking not found");
        if (p.getAmount() <= 0)
            throw new BusinessException("Invalid amount");
        return paymentDAO.createPayment(p);
    }

    /**
     * Retrieves all payments stored in the system.
     *
     * @return a list of {@link Payment} objects
     * @throws Exception if a database access error occurs
     */
    public List<Payment> listPayments() throws Exception {
        return paymentDAO.getAllPayments();
    }

    /**
     * Processes a refund transaction for a specific payment.
     * <p>
     * Business rules enforced:
     * <ul>
     *     <li>The specified payment must exist.</li>
     *     <li>The refund amount must be positive and not exceed the original payment amount.</li>
     * </ul>
     * If these checks pass, the refund is recorded in the system.
     *
     * @param paymentId the ID of the payment being refunded
     * @param amount the amount to refund
     * @throws BusinessException if validation fails (invalid payment or refund amount)
     * @throws Exception if a database or DAO error occurs
     */
    public void refundPayment(int paymentId, double amount) throws Exception {
        Payment p = paymentDAO.getById(paymentId);
        if (p == null)
            throw new BusinessException("Payment not found");
        if (amount <= 0 || amount > p.getAmount())
            throw new BusinessException("Invalid refund amount");
        paymentDAO.addRefund(paymentId, amount);
    }
}
