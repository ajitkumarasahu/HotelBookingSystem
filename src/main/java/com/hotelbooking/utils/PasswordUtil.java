package com.hotelbooking.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for hashing and verifying user passwords in the Hotel Booking System.
 *
 * <p>This class provides methods to hash passwords using the SHA-256 algorithm and verify
 * them during authentication. It includes a simple static salt for demonstration purposes.</p>
 *
 * <p><b>⚠️ Important Security Note:</b><br>
 * While SHA-256 is secure, using a fixed salt is not ideal for production. 
 * For real-world applications, use a unique salt per user (e.g., generated via {@link SecureRandom})
 * and consider using a stronger, adaptive hashing algorithm such as
 * <code>PBKDF2WithHmacSHA256</code>, <code>bcrypt</code>, or <code>Argon2</code>.</p>
 *
 * <p><b>Example usage:</b>
 * <pre>{@code
 * String hash = PasswordUtil.hashPassword("myPassword123");
 * boolean isMatch = PasswordUtil.verifyPassword("myPassword123", hash);
 * }</pre></p>
 *
 * @author  
 * @version 1.0
 */
public class PasswordUtil {

    /** 
     * A fixed salt value used for hashing. 
     * <p>This is for demonstration only. In production, store a unique salt per user.</p>
     */
    private static final byte[] STATIC_SALT = "HotelBookingSalt".getBytes();

    /**
     * Hashes a plain-text password using SHA-256 and Base64 encoding.
     *
     * <p>The hashing process includes:
     * <ul>
     *   <li>Combining the password with a static salt.</li>
     *   <li>Applying the SHA-256 hash algorithm.</li>
     *   <li>Encoding the hash as a Base64 string for storage.</li>
     * </ul></p>
     *
     * @param password the raw password to hash
     * @return a Base64-encoded SHA-256 hash of the password
     * @throws Exception if the hashing algorithm is unavailable
     */
    public static String hashPassword(String password) throws Exception {
        // Create a MessageDigest instance for SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Apply the salt before hashing
        md.update(STATIC_SALT);

        // Perform the hash
        byte[] hashed = md.digest(password.getBytes("UTF-8"));

        // Return Base64-encoded hash
        return Base64.getEncoder().encodeToString(hashed);
    }

    /**
     * Verifies if a provided password matches a stored hash.
     *
     * <p>This method hashes the input password using the same salt and algorithm,
     * then compares it to the stored hash.</p>
     *
     * @param password the plain-text password to verify
     * @param storedHash the previously stored hashed password
     * @return {@code true} if the passwords match; {@code false} otherwise
     * @throws Exception if hashing fails
     */
    public static boolean verifyPassword(String password, String storedHash) throws Exception {
        String newHash = hashPassword(password);
        return newHash.equals(storedHash);
    }

    /**
     * (Optional) Generates a random salt using a secure random number generator.
     *
     * <p>This method is not used in the current implementation but can be integrated
     * if unique salts per user are desired.</p>
     *
     * @param length the number of random bytes to generate
     * @return a Base64-encoded random salt
     */
    public static String generateRandomSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
