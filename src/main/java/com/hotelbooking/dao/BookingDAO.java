package com.hotelbooking.dao;

import com.hotelbooking.model.Booking;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing {@link Booking} entities.
 * <p>
 * Provides complete CRUD operations and availability checks
 * for the {@code bookings} table in the hotel management system.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Retrieve booking records (single or all)</li>
 *   <li>Create, update, and delete bookings</li>
 *   <li>Check room availability for a given date range</li>
 * </ul>
 *
 * <p><b>Database Table:</b> {@code bookings}</p>
 * <p><b>Columns:</b> id, customer_id, room_id, check_in, check_out</p>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class BookingDAO {

    /**
     * Retrieves all bookings from the database.
     *
     * @return A list of all {@link Booking} records.
     * @throws Exception if a database error occurs.
     */
    public List<Booking> getAll() throws Exception {
        String sql = "SELECT * FROM bookings";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            List<Booking> list = new ArrayList<>();
            while (rs.next()) {
                Booking b = new Booking();
                b.setId(rs.getInt("id"));
                b.setCustomerId(rs.getInt("customer_id"));
                b.setRoomId(rs.getInt("room_id"));

                Date ci = rs.getDate("check_in");
                Date co = rs.getDate("check_out");
                b.setCheckIn(ci != null ? ci.toString() : null);
                b.setCheckOut(co != null ? co.toString() : null);

                list.add(b);
            }
            return list;
        }
    }

    /**
     * Retrieves a booking by its unique ID.
     *
     * @param id The booking ID.
     * @return The {@link Booking} object if found, or {@code null} if not.
     * @throws Exception if a database access error occurs.
     */
    public Booking getById(int id) throws Exception {
        String sql = "SELECT * FROM bookings WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Booking b = new Booking();
                    b.setId(rs.getInt("id"));
                    b.setCustomerId(rs.getInt("customer_id"));
                    b.setRoomId(rs.getInt("room_id"));

                    Date ci = rs.getDate("check_in");
                    Date co = rs.getDate("check_out");
                    b.setCheckIn(ci != null ? ci.toString() : null);
                    b.setCheckOut(co != null ? co.toString() : null);

                    return b;
                }
                return null;
            }
        }
    }

    /**
     * Creates a new booking record in the database.
     *
     * @param b The {@link Booking} object containing customer, room, and date information.
     * @return The auto-generated booking ID, or {@code -1} if the operation fails.
     * @throws Exception if a database error occurs.
     */
    public int create(Booking b) throws Exception {
        String sql = "INSERT INTO bookings(customer_id, room_id, check_in, check_out) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, b.getCustomerId());
            ps.setInt(2, b.getRoomId());
            ps.setDate(3, b.getCheckIn() != null ? Date.valueOf(b.getCheckIn()) : null);
            ps.setDate(4, b.getCheckOut() != null ? Date.valueOf(b.getCheckOut()) : null);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Updates an existing booking record.
     *
     * @param b The {@link Booking} object with updated details.
     * @return {@code true} if the update succeeded, {@code false} otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean update(Booking b) throws Exception {
        String sql = "UPDATE bookings SET customer_id=?, room_id=?, check_in=?, check_out=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, b.getCustomerId());
            ps.setInt(2, b.getRoomId());
            ps.setDate(3, b.getCheckIn() != null ? Date.valueOf(b.getCheckIn()) : null);
            ps.setDate(4, b.getCheckOut() != null ? Date.valueOf(b.getCheckOut()) : null);
            ps.setInt(5, b.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a booking record by its ID.
     *
     * @param id The booking ID to delete.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Checks whether a room is available for a specified date range.
     * <p>
     * This method determines availability by ensuring there are no existing bookings
     * that overlap with the desired check-in/check-out dates.
     * </p>
     *
     * <p><b>SQL Logic:</b></p>
     * <pre>
     * SELECT COUNT(*) FROM bookings
     * WHERE room_id = ?
     * AND NOT (check_out <= desiredIn OR check_in >= desiredOut)
     * </pre>
     * If the result count is {@code 0}, the room is available.
     *
     * @param roomId     The room ID to check.
     * @param desiredIn  The desired check-in date.
     * @param desiredOut The desired check-out date.
     * @return {@code true} if the room is available, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean isRoomAvailable(int roomId, java.sql.Date desiredIn, java.sql.Date desiredOut) throws Exception {
        String sql = "SELECT COUNT(*) FROM bookings WHERE room_id=? AND NOT (check_out <= ? OR check_in >= ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setDate(2, desiredIn);
            ps.setDate(3, desiredOut);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count == 0;
                }
                return true;
            }
        }
    }
}
