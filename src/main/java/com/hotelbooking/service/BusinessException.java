package com.hotelbooking.service;

/**
 * {@code BusinessException} represents a domain-level or validation-related error
 * that occurs when business rules are violated during service-layer processing.
 * <p>
 * Unlike low-level exceptions such as {@link java.sql.SQLException} or
 * {@link java.io.IOException}, this exception is used to signal that an operation
 * failed due to invalid input, logical inconsistencies, or unmet business constraints.
 * <p>
 * Examples include:
 * <ul>
 *     <li>Attempting to create a booking with missing required fields.</li>
 *     <li>Providing an invalid payment amount or non-existent room ID.</li>
 *     <li>Trying to delete an entity that does not exist.</li>
 * </ul>
 * <p>
 * Controllers or servlets typically catch this exception to return user-friendly
 * error messages (e.g., HTTP 400 Bad Request), while other exceptions might
 * correspond to internal server errors (HTTP 500).
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * if (room == null) {
 *     throw new BusinessException("Room information is required");
 * }
 * </pre>
 *
 * @see com.hotelbooking.service.UserService
 * @see com.hotelbooking.service.RoomService
 * @see com.hotelbooking.service.PaymentService
 */
public class BusinessException extends Exception {

    /**
     * Constructs a new {@code BusinessException} with the specified detail message.
     *
     * @param message a descriptive message explaining the reason for the exception
     */
    public BusinessException(String message) {
        super(message);
    }
}

