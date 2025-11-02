package com.hotelbooking.dao;

import com.hotelbooking.model.Payment;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Payment} entities.
 * <p>
 * This class provides CRUD operations (Create, Read, Update, Delete)
 * for interacting with the {@code payments} table in the database.
 * It uses {@link DBConnection} for establishing database connections.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Insert new payment records</li>
 *   <li>Retrieve payment details by ID</li>
 *   <li>Retrieve all payments</li>
 *   <li>Update existing payment details</li>
 *   <li>Delete payments by ID</li>
 * </ul>
 *
 * <p><b>Note:</b> Additional methods such as refunds and specialized
 * payment queries are marked as unimplemented placeholders for future expansion.</p>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class PaymentDAO {

    /**
     * Retrieves all payment records from the database.
     *
     * @return A list of all {@link Payment} objects.
     * @throws Exception if a database connection or query error occurs.
     */
    public List<Payment> getAll() throws Exception {
        String sql = "SELECT * FROM payments";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            List<Payment> list = new ArrayList<>();
            while (rs.next()) {
                Payment p = new Payment();
                p.setId(rs.getInt("id"));
                p.setBookingId(rs.getInt("booking_id"));
                p.setAmount(rs.getDouble("amount"));
                p.setMethod(rs.getString("method"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
            return list;
        }
    }

    /**
     * Retrieves a payment record by its ID.
     *
     * @param id The unique ID of the payment to retrieve.
     * @return The {@link Payment} object if found, otherwise {@code null}.
     * @throws Exception if a database error occurs.
     */
    public Payment getById(int id) throws Exception {
        String sql = "SELECT * FROM payments WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Payment p = new Payment();
                    p.setId(rs.getInt("id"));
                    p.setBookingId(rs.getInt("booking_id"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setMethod(rs.getString("method"));
                    p.setStatus(rs.getString("status"));
                    return p;
                }
                return null;
            }
        }
    }

    /**
     * Inserts a new payment record into the database.
     *
     * @param p The {@link Payment} object containing payment details.
     * @return The auto-generated ID of the inserted payment, or {@code -1} if insertion fails.
     * @throws Exception if a database error occurs.
     */
    public int create(Payment p) throws Exception {
        String sql = "INSERT INTO payments(booking_id, amount, method, status) VALUES(?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getBookingId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getMethod());
            ps.setString(4, p.getStatus());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Updates an existing payment record in the database.
     *
     * @param p The {@link Payment} object containing updated payment data.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean update(Payment p) throws Exception {
        String sql = "UPDATE payments SET booking_id=?, amount=?, method=?, status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, p.getBookingId());
            ps.setDouble(2, p.getAmount());
            ps.setString(3, p.getMethod());
            ps.setString(4, p.getStatus());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a payment record from the database.
     *
     * @param id The ID of the payment to delete.
     * @return {@code true} if deletion was successful, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM payments WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Placeholder for future implementation of a payment creation method.
     * <p>
     * This method will be responsible for handling more complex payment logic,
     * such as integrating with external payment gateways or transaction logs.
     * </p>
     *
     * @param p The {@link Payment} object to be created.
     * @return The generated payment ID when implemented.
     * @throws UnsupportedOperationException Always thrown until implemented.
     */
    public int createPayment(Payment p) {
        throw new UnsupportedOperationException("Unimplemented method 'createPayment'");
    }

    /**
     * Placeholder for future implementation to retrieve all payments.
     * <p>
     * This method might support filtering or sorting in future enhancements.
     * </p>
     *
     * @return A list of {@link Payment} objects when implemented.
     * @throws UnsupportedOperationException Always thrown until implemented.
     */
    public List<Payment> getAllPayments() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllPayments'");
    }

    /**
     * Placeholder for future implementation of a refund process.
     * <p>
     * Intended to add a refund record or update payment status in case of
     * partial or full refunds.
     * </p>
     *
     * @param paymentId The ID of the payment being refunded.
     * @param amount    The refund amount.
     * @throws UnsupportedOperationException Always thrown until implemented.
     */
    public void addRefund(int paymentId, double amount) {
        throw new UnsupportedOperationException("Unimplemented method 'addRefund'");
    }
}
