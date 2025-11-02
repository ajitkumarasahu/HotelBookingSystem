package com.hotelbooking.service;

import com.hotelbooking.dao.UserDAO;
import com.hotelbooking.model.User;
import com.hotelbooking.utils.PasswordUtil;

import java.util.List;
import java.util.regex.Pattern;

/**
 * {@code UserService} provides business logic for managing user accounts
 * including registration, login, retrieval, update, and deletion.
 * <p>
 * This service layer performs validation, enforces business rules,
 * and delegates persistence operations to {@link UserDAO}.
 * Passwords are securely hashed before being stored in the database
 * using {@link PasswordUtil}.
 *
 * <h3>Responsibilities:</h3>
 * <ul>
 *     <li>Validate user registration details (name, email, password).</li>
 *     <li>Hash and store passwords securely.</li>
 *     <li>Authenticate users via email and password.</li>
 *     <li>Provide CRUD operations for user management.</li>
 * </ul>
 *
 * <h3>Typical Usage:</h3>
 * <pre>
 * UserService service = new UserService();
 * User newUser = new User("John Doe", "john@example.com", "mypassword");
 * service.registerUser(newUser);
 * </pre>
 *
 * <p><b>Note:</b> Validation failures and business rule violations
 * throw {@code BusinessException} (a custom checked exception).</p>
 */
public class UserService {

    /** Data access object for user-related database operations. */
    private final UserDAO userDAO = new UserDAO();

    /** Email validation pattern ensuring a simple but valid structure (e.g. "user@domain.com"). */
    private final Pattern emailPattern = Pattern.compile("^\\S+@\\S+\\.\\S+$");

    /**
     * Registers a new user after validating and hashing the password.
     * <p>
     * This method validates:
     * <ul>
     *     <li>Name must be at least 2 characters.</li>
     *     <li>Email must match a valid pattern.</li>
     *     <li>Password must be at least 6 characters.</li>
     * </ul>
     * The password is hashed using {@link PasswordUtil#hashPassword(String)} before being stored.
     * If the user role is not specified, it defaults to {@code "CUSTOMER"}.
     *
     * @param user the {@link User} object to be registered
     * @throws BusinessException if validation fails or user is null
     * @throws Exception if any DAO/database operation fails
     */
    public void registerUser(User user) throws Exception {
        if (user == null)
            throw new BusinessException("User is null");

        if (user.getName() == null || user.getName().trim().length() < 2)
            throw new BusinessException("Name must be at least 2 characters");

        if (user.getEmail() == null || !emailPattern.matcher(user.getEmail()).matches())
            throw new BusinessException("Invalid email");

        if (user.getPassword() == null || user.getPassword().length() < 6)
            throw new BusinessException("Password must be at least 6 characters");

        // Hash password before saving
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));

        // Default role assignment
        if (user.getRole() == null)
            user.setRole("CUSTOMER");

        userDAO.register(user);
    }

    /**
     * Authenticates a user based on provided email and password.
     * <p>
     * The {@link UserDAO#login(String, String)} method is expected to handle
     * verification against stored (hashed) passwords.
     *
     * @param email       the user's email address
     * @param rawPassword the user's plain-text password
     * @return the authenticated {@link User} object if credentials are valid
     * @throws BusinessException if email or password is missing or invalid
     * @throws Exception if database access fails
     */
    public User loginUser(String email, String rawPassword) throws Exception {
        if (email == null || rawPassword == null)
            throw new BusinessException("Email and password required");

        User matched = userDAO.login(email, rawPassword); // assumes DAO verifies hashed passwords

        if (matched == null)
            throw new BusinessException("Invalid credentials");

        return matched;
    }

    /**
     * Retrieves all users from the system.
     *
     * @return a {@link List} of all {@link User} records
     * @throws Exception if database retrieval fails
     */
    public List<User> getAllUsers() throws Exception {
        return userDAO.getAllUsers();
    }

    /**
     * Updates user information in the database.
     * <p>
     * This method validates that the user object and ID are valid before delegating
     * to the DAO layer. Additional validation (e.g., email/role) can be added if required.
     *
     * @param user the updated {@link User} object
     * @throws BusinessException if the user or ID is invalid
     * @throws Exception if database update fails
     */
    public void updateUser(User user) throws Exception {
        if (user == null || user.getId() <= 0)
            throw new BusinessException("Invalid user id");

        userDAO.updateUser(user);
    }

    /**
     * Deletes a user by their unique ID.
     *
     * @param id the user ID
     * @throws BusinessException if the ID is invalid (≤ 0)
     * @throws Exception if deletion fails or database error occurs
     */
    public void deleteUser(int id) throws Exception {
        if (id <= 0)
            throw new BusinessException("Invalid user id");

        userDAO.deleteUser(id);
    }
}
