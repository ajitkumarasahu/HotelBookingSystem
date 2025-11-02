package com.hotelbooking.service;

import com.hotelbooking.dao.BookingHistoryDAO;
import com.hotelbooking.model.BookingHistory;

import java.util.List;

/**
 * {@code BookingHistoryService} provides business logic for retrieving
 * a customer's past hotel booking records.
 * <p>
 * This service acts as a layer between servlets/controllers and the {@link BookingHistoryDAO},
 * ensuring that all input parameters are validated and data access is safely handled.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Fetch complete booking history for a given customer.</li>
 *     <li>Validate customer identifiers before data retrieval.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * BookingHistoryService service = new BookingHistoryService();
 * List&lt;BookingHistory&gt; history = service.getHistoryForCustomer(12);
 * </pre>
 *
 * @see com.hotelbooking.dao.BookingHistoryDAO
 * @see com.hotelbooking.model.BookingHistory
 */
public class BookingHistoryService {

    /** Data Access Object for booking history operations. */
    private final BookingHistoryDAO dao = new BookingHistoryDAO();

    /**
     * Retrieves all historical bookings for a specific customer.
     * <p>
     * Validation:
     * <ul>
     *     <li>Customer ID must be a positive integer.</li>
     * </ul>
     *
     * @param customerId the unique identifier of the customer
     * @return a list of {@link BookingHistory} records associated with the customer
     * @throws BusinessException if the {@code customerId} is invalid
     * @throws Exception if a DAO or database error occurs
     */
    public List<BookingHistory> getHistoryForCustomer(int customerId) throws Exception {
        if (customerId <= 0)
            throw new BusinessException("Invalid customer id");
        return dao.getHistoryByCustomer(customerId);
    }
}
