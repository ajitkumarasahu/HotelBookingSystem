package com.hotelbooking.model;

/**
 * Represents a hotel room in the hotel booking system.
 * <p>
 * This class contains details about a room such as its number, type, price,
 * availability status, and other identifying information.
 * It can be used to manage room data within the booking or inventory systems.
 * </p>
 *
 * <p><b>Note:</b> The class currently includes both {@code roomNo}/{@code roomType}
 * and {@code roomNumber}/{@code type} fields, which appear to represent similar data.
 * It is recommended to standardize on one naming convention to avoid confusion.</p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     Room room = new Room();
 *     room.setRoomNumber(101);
 *     room.setType("Deluxe");
 *     room.setPrice(150.0);
 *     room.setAvailable(true);
 * </pre>
 * 
 * @author  
 * @version 1.0
 */
public class Room {

    /** The unique identifier for the room (database primary key). */
    private int id;

    /** The room number (legacy field). */
    private int roomNo;

    /** The room type (legacy field, e.g., "Suite", "Standard"). */
    private String roomType;

    /** The price per night for this room. */
    private double price;

    /** The current status of the room (e.g., "Available", "Booked", "Maintenance"). */
    private String status;

    /** The room number (duplicate of roomNo, consider merging). */
    private int roomNumber;

    /** The room type (duplicate of roomType, consider merging). */
    private String type;

    /** Indicates whether the room is currently available for booking. */
    private boolean available;

    /**
     * Default no-argument constructor.
     * <p>Creates an empty {@code Room} object.</p>
     */
    public Room() {}

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    /**
     * Gets the unique identifier of the room.
     *
     * @return the room ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the room.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the room number (legacy field).
     *
     * @return the room number
     */
    public int getRoomNo() {
        return roomNo;
    }

    /**
     * Sets the room number (legacy field).
     *
     * @param roomNo the room number to set
     */
    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    /**
     * Gets the room type (legacy field).
     *
     * @return the room type
     */
    public String getRoomType() {
        return roomType;
    }

    /**
     * Sets the room type (legacy field).
     *
     * @param roomType the room type to set
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    /**
     * Gets the price per night for this room.
     *
     * @return the room price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per night for this room.
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the current status of the room.
     *
     * @return the room status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the room.
     *
     * @param status the room status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the room number (preferred field).
     *
     * @return the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number (preferred field).
     *
     * @param roomNumber the room number to set
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the room type (preferred field).
     *
     * @return the room type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the room type (preferred field).
     *
     * @param type the room type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Checks whether the room is available for booking.
     *
     * @return {@code true} if the room is available, {@code false} otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the room.
     *
     * @param available {@code true} if the room is available, {@code false} otherwise
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Returns a string representation of the room details.
     *
     * @return a formatted string with room information
     */
    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber +
               ", type=" + type +
               ", price=" + price +
               ", available=" + available + "]";
    }
}
