package com.hotelbooking.service;

import com.hotelbooking.dao.BookingDAO;
import com.hotelbooking.model.Booking;

import java.sql.Date;
import java.util.List;

/**
 * {@code BookingService} provides business logic and validation for managing hotel room bookings.
 * <p>
 * This service layer validates booking data, checks room availability,
 * and delegates database operations to the {@link BookingDAO}.
 * It ensures that bookings respect logical constraints such as valid date ranges
 * and available room inventory.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Retrieve bookings (all or by ID).</li>
 *     <li>Create bookings with validation and availability checks.</li>
 *     <li>Update existing bookings after validation.</li>
 *     <li>Cancel bookings safely.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * BookingService service = new BookingService();
 * Booking b = new Booking();
 * b.setCustomerId(10);
 * b.setRoomId(5);
 * b.setCheckIn("2025-12-01");
 * b.setCheckOut("2025-12-05");
 * int id = service.createBooking(b);
 * </pre>
 *
 * @see com.hotelbooking.dao.BookingDAO
 * @see com.hotelbooking.model.Booking
 */
public class BookingService {

    /** Data Access Object responsible for booking persistence. */
    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * Retrieves all bookings in the system.
     *
     * @return a list of {@link Booking} objects
     * @throws Exception if a database access error occurs
     */
    public List<Booking> getAllBookings() throws Exception {
        return bookingDAO.getAll();
    }

    /**
     * Retrieves a single booking by its unique identifier.
     *
     * @param id the ID of the booking
     * @return the corresponding {@link Booking}, or {@code null} if not found
     * @throws Exception if a database access error occurs
     */
    public Booking getBookingById(int id) throws Exception {
        return bookingDAO.getById(id);
    }

    /**
     * Creates a new booking after validating the input and checking room availability.
     * <p>
     * Business validation rules:
     * <ul>
     *     <li>Check-in and Check-out dates must not be {@code null}.</li>
     *     <li>The selected room must be available for the desired date range.</li>
     * </ul>
     * This method converts date strings to {@link java.sql.Date} before validation.
     *
     * @param booking the {@link Booking} object containing customer and room details
     * @return the generated booking ID
     * @throws BusinessException if validation fails (invalid dates or unavailable room)
     * @throws Exception if a DAO or database error occurs
     */
    public int createBooking(Booking booking) throws Exception {
        if (booking.getCheckIn() == null || booking.getCheckOut() == null) {
            throw new BusinessException("Check-in and Check-out dates are required.");
        }

        Date desiredIn = Date.valueOf(booking.getCheckIn());
        Date desiredOut = Date.valueOf(booking.getCheckOut());

        boolean available = bookingDAO.isRoomAvailable(booking.getRoomId(), desiredIn, desiredOut);
        if (!available)
            throw new BusinessException("Room is not available for the selected dates.");

        return bookingDAO.create(booking);
    }

    /**
     * Updates an existing booking record.
     * <p>
     * Validation:
     * <ul>
     *     <li>Booking ID must be greater than zero.</li>
     * </ul>
     *
     * @param booking the {@link Booking} object with updated details
     * @return {@code true} if the update was successful; {@code false} otherwise
     * @throws BusinessException if the booking ID is invalid
     * @throws Exception if a DAO or database error occurs
     */
    public boolean updateBooking(Booking booking) throws Exception {
        if (booking.getId() <= 0)
            throw new BusinessException("Invalid booking ID.");
        return bookingDAO.update(booking);
    }

    /**
     * Cancels (deletes) an existing booking.
     * <p>
     * Validation:
     * <ul>
     *     <li>The booking ID must be valid (greater than zero).</li>
     * </ul>
     *
     * @param id the ID of the booking to cancel
     * @return {@code true} if cancellation was successful; {@code false} otherwise
     * @throws BusinessException if the booking ID is invalid
     * @throws Exception if a DAO or database error occurs
     */
    public boolean cancelBooking(int id) throws Exception {
        if (id <= 0)
            throw new BusinessException("Invalid booking ID.");
        return bookingDAO.delete(id);
    }
}
