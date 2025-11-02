package com.hotelbooking.dao;

import com.hotelbooking.model.BookingHistory;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing booking history records.
 * <p>
 * This class retrieves historical booking data by joining related tables such as
 * {@code bookings}, {@code customers}, {@code rooms}, and {@code payments}.
 * It provides a detailed overview of each customer's past reservations, including
 * room details, check-in/check-out dates, and payment status.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Fetch booking history for a specific customer.</li>
 *   <li>Provide detailed records combining booking, room, customer, and payment information.</li>
 * </ul>
 *
 * <p><b>Database Tables Involved:</b></p>
 * <ul>
 *   <li>{@code bookings}</li>
 *   <li>{@code customers}</li>
 *   <li>{@code rooms}</li>
 *   <li>{@code payments}</li>
 * </ul>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class BookingHistoryDAO {

    /**
     * Retrieves the full booking history for a given customer.
     * <p>
     * This method performs a multi-table join across {@code bookings}, {@code customers},
     * {@code rooms}, and {@code payments} to produce a detailed history view for the specified
     * customer. Results are ordered by check-in date in descending order (latest first).
     * </p>
     *
     * <p><b>Example Output:</b></p>
     * <pre>
     * Booking ID | Room Number | Room Type | Check-In | Check-Out | Payment Amount | Payment Status
     * ----------------------------------------------------------------------------------------------
     *  101       | 201         | Deluxe    | 2025-10-20 | 2025-10-23 | 250.00 | Completed
     * </pre>
     *
     * @param customerId The unique ID of the customer whose booking history is to be fetched.
     * @return A list of {@link BookingHistory} objects containing detailed booking data.
     *         Returns an empty list if the customer has no booking history.
     * @throws Exception if a database access or SQL error occurs.
     */
    public List<BookingHistory> getHistoryByCustomer(int customerId) throws Exception {
        List<BookingHistory> list = new ArrayList<>();

        String sql = "SELECT b.id AS booking_id, b.room_id, r.room_number, r.type AS room_type, " +
                "b.customer_id, c.name AS customer_name, b.check_in, b.check_out, " +
                "p.amount AS payment_amount, p.status AS payment_status " +
                "FROM bookings b " +
                "JOIN customers c ON b.customer_id = c.id " +
                "JOIN rooms r ON b.room_id = r.id " +
                "LEFT JOIN payments p ON p.booking_id = b.id " +
                "WHERE b.customer_id = ? ORDER BY b.check_in DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookingHistory bh = new BookingHistory();
                    bh.setBookingId(rs.getInt("booking_id"));
                    bh.setRoomId(rs.getInt("room_id"));
                    bh.setRoomNumber(rs.getString("room_number"));
                    bh.setRoomType(rs.getString("room_type"));
                    bh.setCustomerId(rs.getInt("customer_id"));
                    bh.setCustomerName(rs.getString("customer_name"));
                    bh.setCheckIn(rs.getString("check_in"));
                    bh.setCheckOut(rs.getString("check_out"));
                    bh.setPaymentAmount(rs.getDouble("payment_amount"));
                    bh.setPaymentStatus(rs.getString("payment_status"));
                    list.add(bh);
                }
            }
        }
        return list;
    }

    /**
     * Placeholder method for fetching booking history by user ID.
     * <p>
     * This method currently prints a debug message and returns an empty list.
     * It is intended for future implementation that may involve custom filtering,
     * caching, or API-based history retrieval.
     * </p>
     *
     * @param userId The user ID for whom booking history will be fetched.
     * @return An empty list (unimplemented placeholder).
     */
    public List<BookingHistory> getBookingHistory(int userId) {
        System.out.println("Fetching booking history for user: " + userId);
        return new ArrayList<>();
    }
}
