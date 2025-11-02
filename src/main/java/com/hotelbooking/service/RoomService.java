package com.hotelbooking.service;

import com.hotelbooking.dao.RoomDAO;
import com.hotelbooking.model.Room;

import java.util.List;

/**
 * {@code RoomService} provides business logic and validation for managing hotel room data.
 * <p>
 * This service acts as an intermediary between servlet/controllers and the {@link RoomDAO},
 * ensuring that all operations involving rooms are validated before being passed to the
 * persistence layer.
 * <p>
 * The service supports CRUD operations and room availability checks for booking purposes.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Validate room data before creation or update.</li>
 *     <li>Fetch all rooms or a specific room by ID.</li>
 *     <li>Update or delete room records.</li>
 *     <li>Retrieve available rooms between given dates.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * RoomService service = new RoomService();
 * Room room = new Room();
 * room.setRoomNo(101);
 * room.setType("Deluxe");
 * room.setPrice(120.0);
 * int id = service.addRoom(room);
 * </pre>
 */
public class RoomService {

    /** Data access object used for interacting with the rooms database. */
    private final RoomDAO roomDAO = new RoomDAO();

    /**
     * Adds a new room to the database after performing basic validation.
     * <p>
     * This method checks that:
     * <ul>
     *     <li>The {@link Room} object is not null.</li>
     *     <li>The room number is a positive integer.</li>
     *     <li>The room price is non-negative.</li>
     * </ul>
     *
     * @param room the {@link Room} object to be added
     * @return the generated room ID
     * @throws BusinessException if the room data is invalid
     * @throws Exception if any database or DAO error occurs
     */
    public int addRoom(Room room) throws Exception {
        if (room == null)
            throw new BusinessException("Room required");
        if (room.getRoomNo() <= 0)
            throw new BusinessException("Invalid room number");
        if (room.getPrice() < 0)
            throw new BusinessException("Invalid price");

        return roomDAO.addRoom(room);
    }

    /**
     * Retrieves all rooms from the database.
     *
     * @return a {@link List} of all {@link Room} records
     * @throws Exception if database retrieval fails
     */
    public List<Room> listRooms() throws Exception {
        return roomDAO.getAll();
    }

    /**
     * Finds a room by its unique ID.
     *
     * @param id the room ID
     * @return the {@link Room} object if found, or {@code null} if not
     * @throws Exception if database access fails
     */
    public Room findById(int id) throws Exception {
        return roomDAO.getById(id);
    }

    /**
     * Updates an existing room record.
     * <p>
     * This method validates that the provided {@link Room} object is not null and that
     * it has a valid ID before passing it to the DAO for persistence.
     *
     * @param room the updated {@link Room} object
     * @throws BusinessException if the room is null or has an invalid ID
     * @throws Exception if a database update error occurs
     */
    public void updateRoom(Room room) throws Exception {
        if (room == null || room.getId() <= 0)
            throw new BusinessException("Invalid room");

        roomDAO.update(room);
    }

    /**
     * Deletes a room by its ID.
     * <p>
     * No validation is performed beyond delegation to the DAO. If the room
     * does not exist, the DAO layer should handle the result appropriately.
     *
     * @param id the room ID to delete
     * @throws Exception if database deletion fails
     */
    public void deleteRoom(int id) throws Exception {
        roomDAO.delete(id);
    }

    /**
     * Retrieves a list of available rooms for the given check-in and check-out dates.
     * <p>
     * This is typically used by the booking process to find rooms that are not already
     * reserved during a specific period.
     *
     * @param checkIn  the check-in date (inclusive)
     * @param checkOut the check-out date (exclusive)
     * @return a {@link List} of available {@link Room} objects
     * @throws Exception if database access or query fails
     */
    public List<Room> findAvailableRooms(java.sql.Date checkIn, java.sql.Date checkOut) throws Exception {
        return roomDAO.getAvailableRooms(checkIn, checkOut);
    }
}
