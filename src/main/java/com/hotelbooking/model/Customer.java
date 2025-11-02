package com.hotelbooking.model;

/**
 * Represents a customer in the hotel booking system.
 * <p>
 * The {@code Customer} class stores information about a customer,
 * including their name, email, and phone number. Each customer is uniquely
 * identified by an {@code id}.
 * </p>
 *
 * <p><b>Example:</b></p>
 * <pre>
 *     Customer customer = new Customer();
 *     customer.setId(101);
 *     customer.setName("Alice Johnson");
 *     customer.setEmail("alice@example.com");
 *     customer.setPhone("+1-555-0123");
 * </pre>
 *
 * <p><b>Use Cases:</b></p>
 * <ul>
 *     <li>Managing customer information and contact details</li>
 *     <li>Linking customers to bookings, reviews, and payments</li>
 *     <li>Sending notifications or promotional offers</li>
 * </ul>
 * 
 * @author  
 * @version 1.0
 */
public class Customer {

    /** The unique identifier for the customer (primary key). */
    private int id;

    /** The full name of the customer. */
    private String name;

    /** The email address of the customer, used for communication and login. */
    private String email;

    /** The phone number of the customer for contact purposes. */
    private String phone;

    /**
     * Default no-argument constructor.
     * <p>Creates an empty {@code Customer} object.</p>
     */
    public Customer() {}

    // ---------------------------
    // Getters and Setters
    // ---------------------------

    /**
     * Gets the unique identifier of the customer.
     *
     * @return the customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the customer.
     *
     * @param id the customer ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the full name of the customer.
     *
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name of the customer.
     *
     * @param name the customer's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the customer.
     *
     * @return the customer's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     * <p>Should be a valid email format.</p>
     *
     * @param email the customer's email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the customer.
     *
     * @return the customer's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone the customer's phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
