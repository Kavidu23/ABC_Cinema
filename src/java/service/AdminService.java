package service;

import java.sql.*;
import util.DatabaseConnectionHelper;

public class AdminService {

    // Method to check if an admin exists by username and password
    public boolean authenticateAdmin(String username, String password) {
        String query = "SELECT COUNT(*) FROM admin WHERE username = ? AND password = ?";
        
        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            // If count is greater than 0, the admin exists with correct credentials
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get the details of an admin by username
    public String getAdminDetails(String username) {
        String query = "SELECT username FROM admin WHERE username = ?";
        String adminDetails = null;

        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            // If the admin exists, retrieve the details
            if (rs.next()) {
                adminDetails = rs.getString("username");  // You can add more details if needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminDetails;
    }
}
