package com.hotelbooking.dao;

import com.hotelbooking.model.User;
import com.hotelbooking.utils.DBConnection;
import com.hotelbooking.utils.PasswordUtil;

import java.sql.*;
import java.util.*;

/**
 * Data Access Object (DAO) class for managing {@link User} entities.
 * <p>
 * This class provides CRUD operations (Create, Read, Update, Delete)
 * for the {@code users} table in the database.
 * It uses the {@link DBConnection} utility to establish connections and
 * {@link PasswordUtil} for secure password hashing and verification.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Register new users</li>
 *   <li>Authenticate users (login)</li>
 *   <li>Retrieve user information</li>
 *   <li>Update user details</li>
 *   <li>Delete users</li>
 * </ul>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class UserDAO {

    /**
     * Registers a new user in the database.
     * The password is securely hashed before being stored.
     *
     * @param user The {@link User} object containing user details.
     * @throws Exception if a database error occurs or password hashing fails.
     */
    public void register(User user) throws Exception {
        String sql = "INSERT INTO users(name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(4, user.getRole());
            ps.executeUpdate();
        }
    }

    /**
     * Authenticates a user by email and password.
     * <p>
     * The method retrieves the stored password hash from the database and verifies it
     * against the provided password using {@link PasswordUtil#verifyPassword(String, String)}.
     * </p>
     *
     * @param email    The email address of the user.
     * @param password The plain text password entered by the user.
     * @return A {@link User} object if authentication is successful, or {@code null} if invalid.
     * @throws Exception if a database error occurs.
     */
    public User login(String email, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");

                if (PasswordUtil.verifyPassword(password, storedHash)) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        null, // Password not returned for security
                        rs.getString("role")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     * <p>
     * Passwords are not included in the returned {@link User} objects for security reasons.
     * </p>
     *
     * @return A list of all {@link User} objects in the database.
     * @throws Exception if a database error occurs.
     */
    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email, role FROM users";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        null,
                        rs.getString("role")
                ));
            }
        }
        return users;
    }

    /**
     * Updates an existing user's details in the database.
     * <p>
     * Only the name, email, and role fields are updated. The password remains unchanged.
     * </p>
     *
     * @param user The {@link User} object containing updated information.
     * @throws Exception if a database error occurs.
     */
    public void updateUser(User user) throws Exception {
        String sql = "UPDATE users SET name=?, email=?, role=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id The ID of the user to delete.
     * @throws Exception if a database error occurs.
     */
    public void deleteUser(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
