package com.hotelbooking.dao;

import com.hotelbooking.model.Room;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for managing {@link Room} entities.
 * <p>
 * Provides CRUD operations (Create, Read, Update, Delete) for the {@code rooms} table
 * in the database. Uses {@link DBConnection} to establish database connections.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Retrieve all rooms</li>
 *   <li>Retrieve a room by its ID</li>
 *   <li>Add new rooms</li>
 *   <li>Update existing room details</li>
 *   <li>Delete rooms</li>
 *   <li>Support future functionality for room availability checks</li>
 * </ul>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class RoomDAO {

    /**
     * Retrieves all rooms from the database.
     *
     * @return A list of {@link Room} objects representing all rooms in the system.
     * @throws Exception if a database connection or query error occurs.
     */
    public List<Room> getAll() throws Exception {
        String sql = "SELECT * FROM rooms";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            List<Room> list = new ArrayList<>();
            while (rs.next()) {
                Room r = new Room();
                r.setId(rs.getInt("id"));
                r.setRoomNo(rs.getInt("room_no"));
                r.setRoomType(rs.getString("room_type"));
                r.setPrice(rs.getDouble("price"));
                r.setStatus(rs.getString("status"));
                list.add(r);
            }
            return list;
        }
    }

    /**
     * Retrieves a room by its unique ID.
     *
     * @param id The room ID to search for.
     * @return The corresponding {@link Room} object if found, otherwise {@code null}.
     * @throws Exception if a database error occurs.
     */
    public Room getById(int id) throws Exception {
        String sql = "SELECT * FROM rooms WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Room r = new Room();
                    r.setId(rs.getInt("id"));
                    r.setRoomNo(rs.getInt("room_no"));
                    r.setRoomType(rs.getString("room_type"));
                    r.setPrice(rs.getDouble("price"));
                    r.setStatus(rs.getString("status"));
                    return r;
                }
                return null;
            }
        }
    }

    /**
     * Creates a new room record in the database.
     *
     * @param r The {@link Room} object containing room details.
     * @return The auto-generated ID of the new room, or {@code -1} if creation failed.
     * @throws Exception if a database error occurs.
     */
    public int create(Room r) throws Exception {
        String sql = "INSERT INTO rooms(room_no, room_type, price, status) VALUES(?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, r.getRoomNo());
            ps.setString(2, r.getRoomType());
            ps.setDouble(3, r.getPrice());
            ps.setString(4, r.getStatus());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Updates an existing room record in the database.
     *
     * @param r The {@link Room} object with updated data.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean update(Room r) throws Exception {
        String sql = "UPDATE rooms SET room_no=?, room_type=?, price=?, status=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, r.getRoomNo());
            ps.setString(2, r.getRoomType());
            ps.setDouble(3, r.getPrice());
            ps.setString(4, r.getStatus());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a room from the database by its ID.
     *
     * @param id The ID of the room to delete.
     * @return {@code true} if deletion was successful, {@code false} otherwise.
     * @throws Exception if a database error occurs.
     */
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Placeholder for future implementation of room creation logic.
     * <p>
     * This method is not yet implemented and will throw an exception when called.
     * </p>
     *
     * @param room The {@link Room} object to be added.
     * @return The generated room ID when implemented.
     * @throws UnsupportedOperationException Always thrown until implemented.
     */
    public int addRoom(Room room) {
        throw new UnsupportedOperationException("Unimplemented method 'addRoom'");
    }

    /**
     * Placeholder for future implementation of fetching available rooms.
     * <p>
     * Intended to return rooms available between the specified check-in and check-out dates.
     * </p>
     *
     * @param checkIn  The desired check-in date.
     * @param checkOut The desired check-out date.
     * @return A list of available {@link Room} objects when implemented.
     * @throws UnsupportedOperationException Always thrown until implemented.
     */
    public List<Room> getAvailableRooms(Date checkIn, Date checkOut) {
        throw new UnsupportedOperationException("Unimplemented method 'getAvailableRooms'");
    }
}
