package com.hotelbooking.auth;  
// Declares that this class belongs to the 'auth' package inside 'com.hotelbooking'

import com.fasterxml.jackson.databind.ObjectMapper;  
// Imports Jackson's ObjectMapper to convert JSON <-> Java objects

import com.hotelbooking.dao.UserDAO;  
// Imports the Data Access Object (DAO) class for user-related database operations

import com.hotelbooking.model.User;  
// Imports the User model class that represents user data

import javax.servlet.http.*;  
// Imports HttpServlet and related classes for handling HTTP requests and responses

import java.io.IOException;  
// For handling input/output exceptions

import java.util.List;  
// Used to store and return lists of User objects


public class AuthServlet extends HttpServlet {
    // This servlet handles authentication-related operations: signup, login, and user management

    private final UserDAO userDAO = new UserDAO();
    // Creates an instance of UserDAO to interact with the database

    private final ObjectMapper mapper = new ObjectMapper();
    // ObjectMapper is used to convert between JSON strings and Java objects


    // Handles HTTP POST requests (used for Signup and Login)
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        // Sets the response type to JSON

        String path = req.getPathInfo();
        // Gets the specific sub-path (e.g., /signup or /login)

        try {
            if ("/signup".equals(path)) {
                // If the request path is '/signup', handle user registration

                User user = mapper.readValue(req.getInputStream(), User.class);
                // Converts the JSON request body into a User object

                userDAO.register(user);
                // Calls the DAO to register (insert) the user into the database

                res.getWriter().write("{\"status\":\"registered\"}");
                // Sends a JSON response confirming successful registration

            } else if ("/login".equals(path)) {
                // If the request path is '/login', handle user login

                User loginReq = mapper.readValue(req.getInputStream(), User.class);
                // Converts JSON request into a User object with email and password

                User loggedIn = userDAO.login(loginReq.getEmail(), loginReq.getPassword());
                // Checks the credentials against the database

                if (loggedIn != null) {
                    // If credentials are valid, send back user details as JSON
                    res.getWriter().write(mapper.writeValueAsString(loggedIn));
                } else {
                    // If login fails, send HTTP 401 Unauthorized
                    res.setStatus(401);
                    res.getWriter().write("{\"error\":\"Invalid credentials\"}");
                }
            }
        } catch (Exception e) {
            // Handles any unexpected exceptions
            res.setStatus(500);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


    // Handles HTTP GET requests (used to retrieve all users)
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        // Response will be in JSON format

        try {
            List<User> users = userDAO.getAllUsers();
            // Fetches a list of all users from the database

            res.getWriter().write(mapper.writeValueAsString(users));
            // Converts the user list to JSON and sends it in the response
        } catch (Exception e) {
            // Handles any errors
            res.setStatus(500);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


    // Handles HTTP PUT requests (used to update user details)
    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            User user = mapper.readValue(req.getInputStream(), User.class);
            // Reads the updated user details from JSON input

            userDAO.updateUser(user);
            // Updates user information in the database

            res.getWriter().write("{\"status\":\"updated\"}");
            // Sends success response
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


    // Handles HTTP DELETE requests (used to remove a user by ID)
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            // Extracts the user ID from the request parameters

            userDAO.deleteUser(id);
            // Deletes the user record from the database

            res.getWriter().write("{\"status\":\"deleted\"}");
            // Sends a success message
        } catch (Exception e) {
            res.setStatus(500);
            res.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
