package com.hotelbooking.dao;

import com.hotelbooking.model.Customer;
import com.hotelbooking.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing {@link Customer} entities.
 * <p>
 * Provides CRUD operations (Create, Read, Update, Delete) for the
 * {@code customers} table. Each method uses JDBC with prepared
 * statements to ensure SQL safety and efficient resource management.
 * </p>
 *
 * <p><b>Database Table:</b> {@code customers}</p>
 * <p><b>Columns:</b> id, name, email, phone</p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Retrieve all customers</li>
 *   <li>Find customers by ID</li>
 *   <li>Create new customers</li>
 *   <li>Update existing customers</li>
 *   <li>Delete customers</li>
 * </ul>
 *
 * @author  
 * @version 1.0
 * @since 2025-11-01
 */
public class CustomerDAO {

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of {@link Customer} objects representing all customers in the system.
     * @throws Exception if a database error occurs.
     */
    public List<Customer> getAll() throws Exception {
        String sql = "SELECT * FROM customers";
        try (Connection c = DBConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(sql)) {

            List<Customer> list = new ArrayList<>();
            while (rs.next()) {
                Customer u = new Customer();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                list.add(u);
            }
            return list;
        }
    }

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The {@link Customer} object if found, otherwise {@code null}.
     * @throws Exception if a database access error occurs.
     */
    public Customer getById(int id) throws Exception {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer u = new Customer();
                    u.setId(rs.getInt("id"));
                    u.setName(rs.getString("name"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("phone"));
                    return u;
                }
                return null;
            }
        }
    }

    /**
     * Creates a new customer record in the database.
     *
     * @param u The {@link Customer} object containing the customer details.
     * @return The auto-generated ID of the created customer, or {@code -1} if creation fails.
     * @throws Exception if a database error occurs during insertion.
     */
    public int create(Customer u) throws Exception {
        String sql = "INSERT INTO customers(name, email, phone) VALUES(?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhone());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
            return -1;
        }
    }

    /**
     * Updates an existing customer's details.
     *
     * @param u The {@link Customer} object containing updated data.
     * @return {@code true} if the update was successful, {@code false} otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean update(Customer u) throws Exception {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhone());
            ps.setInt(4, u.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a customer record from the database.
     *
     * @param id The ID of the customer to delete.
     * @return {@code true} if the deletion was successful, {@code false} otherwise.
     * @throws Exception if a database access error occurs.
     */
    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
