package service;

import model.User;
import util.DatabaseConnectionHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    // Hash password using SHA-256
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password: " + e.getMessage(), e);
        }
    }

    // Add user to the database with hashed password
    public boolean addUser(User user) {
        if (user == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User or password cannot be null");
        }

        String query = "INSERT INTO user (name, email, pnumber, password) VALUES (?, ?, ?, ?)";

        // Hash the user's password before saving it to the database
        String hashedPassword = hashPassword(user.getPassword());

        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPnumber());
            preparedStatement.setString(4, hashedPassword);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding user: " + e.getMessage(), e);
        }
    }

    // Authenticate user login with email and password
    public User authenticateUser(String email, String password) {
        User user = null;

        String query = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (storedPassword.equals(hashPassword(password))) {
                    user = new User(
                            String.valueOf(resultSet.getInt("id")),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("pnumber")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Validate user with email and hashed password
    public User validateUser(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        String hashedPassword = hashPassword(password);

        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("pnumber"),
                        null // Avoid returning the password
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Check if email exists in the database
    public boolean checkEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Return true if the count is greater than 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking email: " + e.getMessage(), e);
        }
        return false;
    }
}
