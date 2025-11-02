package com.hotelbooking.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

/**
 * Utility class for managing database connections in the Hotel Booking System.
 *
 * <p>This class provides a centralized way to obtain a connection to the MySQL database.
 * It supports both default hardcoded configuration and optional configuration from
 * a `db.properties` file located in the application's classpath (e.g., under
 * `src/main/resources`).</p>
 *
 * <p>Usage example:
 * <pre>{@code
 * try (Connection conn = DBConnection.getConnection()) {
 *     // Use the connection
 * }
 * }</pre>
 * </p>
 *
 * <p><strong>Default configuration:</strong><br>
 * URL: jdbc:mysql://localhost:3306/hotel_db?useSSL=false&serverTimezone=UTC<br>
 * USER: root<br>
 * PASSWORD: (empty string)</p>
 *
 * <p>If a <code>db.properties</code> file exists, the following keys can override defaults:
 * <ul>
 *   <li><code>db.url</code></li>
 *   <li><code>db.user</code></li>
 *   <li><code>db.password</code></li>
 * </ul>
 * </p>
 *
 * <p>This class uses the MySQL Connector/J driver (<code>com.mysql.cj.jdbc.Driver</code>).</p>
 *
 * @author  
 * @version 1.0
 */
public class DBConnection {

    /** Default JDBC URL to connect to the hotel database. */
    private static String URL = "jdbc:mysql://localhost:3306/hotel_db?useSSL=false&serverTimezone=UTC";

    /** Default MySQL username. */
    private static String USER = "root";

    /** Default MySQL password (empty by default). */
    private static String PASSWORD = "";

    /**
     * Static initialization block.
     *
     * <p>Attempts to load database connection settings from a properties file named
     * <code>db.properties</code> located in the application's classpath.
     * If the file is not found or fails to load, the default values are used instead.</p>
     *
     * Example of a valid <code>db.properties</code> file:
     * <pre>
     * db.url=jdbc:mysql://localhost:3306/hotel_db?useSSL=false&serverTimezone=UTC
     * db.user=root
     * db.password=1234
     * </pre>
     */
    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                URL = p.getProperty("db.url", URL);
                USER = p.getProperty("db.user", USER);
                PASSWORD = p.getProperty("db.password", PASSWORD); // ✅ corrected key
            }
        } catch (Exception e) {
            // If properties cannot be loaded, defaults are used.
            // It's common to silently fail here since defaults are safe.
        }
    }

    /**
     * Obtains a connection to the MySQL database.
     *
     * <p>This method loads the MySQL JDBC driver and creates a new {@link Connection}
     * object using the configured URL, username, and password.</p>
     *
     * @return a live {@link Connection} object ready for SQL queries
     * @throws Exception if the JDBC driver cannot be loaded or the connection fails
     */
    public static Connection getConnection() throws Exception {
        // Load the MySQL JDBC driver explicitly
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Return a new database connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
