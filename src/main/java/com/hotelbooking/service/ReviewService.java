package com.hotelbooking.service;

import com.hotelbooking.dao.ReviewDAO;
import com.hotelbooking.model.Review;

import java.util.List;

/**
 * {@code ReviewService} provides business logic and validation for managing customer reviews
 * associated with hotel rooms.
 * <p>
 * This service layer ensures that all review-related operations, such as adding, fetching, and
 * deleting reviews, are properly validated before delegating to the {@link ReviewDAO}.
 * <p>
 * It enforces input validation rules (such as rating ranges) and converts DAO-level errors
 * into domain-specific {@link BusinessException}s for clean error propagation to servlets or controllers.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Validate and persist new customer reviews.</li>
 *     <li>Retrieve all reviews or reviews for a specific room.</li>
 *     <li>Delete reviews by ID.</li>
 * </ul>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 * ReviewService service = new ReviewService();
 * Review r = new Review();
 * r.setRoomId(101);
 * r.setCustomerId(5);
 * r.setRating(4);
 * r.setComment("Clean and comfortable!");
 * int id = service.addReview(r);
 * </pre>
 */
public class ReviewService {

    /** Data Access Object for interacting with the review persistence layer. */
    private final ReviewDAO reviewDAO = new ReviewDAO();

    /**
     * Adds a new review to the database after validation.
     * <p>
     * This method ensures:
     * <ul>
     *     <li>The {@link Review} object is not null.</li>
     *     <li>The rating value is between 1 and 5 (inclusive).</li>
     * </ul>
     *
     * @param r the {@link Review} object to be persisted
     * @return the generated review ID
     * @throws BusinessException if the review is invalid or has an out-of-range rating
     * @throws Exception if a database or DAO error occurs
     */
    public int addReview(Review r) throws Exception {
        if (r == null)
            throw new BusinessException("Review required");
        if (r.getRating() < 1 || r.getRating() > 5)
            throw new BusinessException("Rating must be between 1 and 5");
        return reviewDAO.addReview(r);
    }

    /**
     * Retrieves all reviews for a specific room.
     *
     * @param roomId the ID of the room whose reviews should be fetched
     * @return a list of {@link Review} objects associated with the given room
     * @throws Exception if a database access error occurs
     */
    public List<Review> getReviewsForRoom(int roomId) throws Exception {
        return reviewDAO.getReviewsByRoom(roomId);
    }

    /**
     * Retrieves all reviews in the system.
     *
     * @return a list of all {@link Review} records
     * @throws Exception if a database access error occurs
     */
    public List<Review> getAllReviews() throws Exception {
        return reviewDAO.getAllReviews();
    }

    /**
     * Deletes a review by its unique ID.
     * <p>
     * If the review cannot be deleted (for example, because it does not exist),
     * a {@link BusinessException} is thrown.
     *
     * @param id the ID of the review to delete
     * @throws BusinessException if the review cannot be found or deleted
     * @throws Exception if a database or DAO error occurs
     */
    public void deleteReview(int id) throws Exception {
        if (!reviewDAO.deleteReview(id))
            throw new BusinessException("Review delete failed or not found");
    }
}
