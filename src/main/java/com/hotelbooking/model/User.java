package com.hotelbooking.model;

/**
 * Represents a user within the hotel booking system.
 * <p>
 * This class stores user-related data such as ID, name, email,
 * password (hashed), and role. It can represent different types of users
 * such as customers, administrators, or hotel staff depending on the assigned role.
 * </p>
 * 
 * <p><b>Example:</b></p>
 * <pre>
 *     User user = new User(1, "John Doe", "john@example.com", "hashedPassword", "customer");
 * </pre>
 * 
 * @author  
 * @version 1.0
 */
public class User {
    /** The unique identifier for the user. */
    private int id;
    
    /** The user's full name. */
    private String name;
    
    /** The user's email address, used for login and communication. */
    private String email;
    
    /** The user's hashed password for authentication. */
    private String password;
    
    /** The user's role (e.g., "admin", "customer", "staff"). */
    private String role;

    /**
     * Default no-argument constructor.
     * <p>Creates an empty {@code User} object.</p>
     */
    public User() {}

    /**
     * Constructs a {@code User} object with all fields initialized.
     *
     * @param id        the unique ID of the user
     * @param name      the full name of the user
     * @param email     the email address of the user
     * @param password  the hashed password of the user
     * @param role      the role assigned to the user
     */
    public User(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the user's unique ID.
     *
     * @return the user's ID
     */
    public int getId() { 
        return id; 
    }

    /**
     * Sets the user's unique ID.
     *
     * @param id the ID to set
     */
    public void setId(int id) { 
        this.id = id; 
    }

    /**
     * Gets the user's full name.
     *
     * @return the user's name
     */
    public String getName() { 
        return name; 
    }

    /**
     * Sets the user's full name.
     *
     * @param name the name to set
     */
    public void setName(String name) { 
        this.name = name; 
    }

    /**
     * Gets the user's email address.
     *
     * @return the user's email
     */
    public String getEmail() { 
        return email; 
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set
     */
    public void setEmail(String email) { 
        this.email = email; 
    }

    /**
     * Gets the user's hashed password.
     *
     * @return the user's hashed password
     */
    public String getPassword() { 
        return password; 
    }

    /**
     * Sets the user's hashed password.
     *
     * @param password the hashed password to set
     */
    public void setPassword(String password) { 
        this.password = password; 
    }

    /**
     * Gets the user's role in the system.
     *
     * @return the user's role
     */
    public String getRole() { 
        return role; 
    }

    /**
     * Sets the user's role in the system.
     *
     * @param role the role to set (e.g., "admin", "customer", "staff")
     */
    public void setRole(String role) { 
        this.role = role; 
    }
}
