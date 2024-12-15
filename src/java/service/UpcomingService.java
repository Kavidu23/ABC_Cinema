package service;

import model.Upcoming;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpcomingService {

    private Connection connection;

    public UpcomingService(Connection connection) {
        this.connection = connection;
    }

    // Create - Insert a new upcoming movie
    public boolean insertUpcoming(Upcoming upcoming) {
        String query = "INSERT INTO upcoming (movie_name, trailer, movie_image) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, upcoming.getMovieName());
            stmt.setString(2, upcoming.getTrailerLink());  // 'trailer' field mapped here
            stmt.setString(3, upcoming.getMovieImage());   // 'movie_image' field mapped here
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read - Get all upcoming movies
    public List<Upcoming> getAllUpcoming() {
        List<Upcoming> upcomings = new ArrayList<>();
        String query = "SELECT * FROM upcoming";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                upcomings.add(new Upcoming(
                        rs.getInt("id"),
                        rs.getString("movie_name"),
                        rs.getString("trailer"),
                        rs.getString("movie_image")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upcomings;
    }

    // Update - Update an upcoming movie
    public boolean updateUpcoming(Upcoming upcoming) {
        String query = "UPDATE upcoming SET movie_name = ?, trailer = ?, movie_image = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, upcoming.getMovieName());
            stmt.setString(2, upcoming.getTrailerLink());  // 'trailer' field updated
            stmt.setString(3, upcoming.getMovieImage());   // 'movie_image' field updated
            stmt.setInt(4, upcoming.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete - Delete an upcoming movie
    public boolean deleteUpcoming(int id) {
        String query = "DELETE FROM upcoming WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a specific upcoming movie by ID
    public Upcoming getUpcomingById(int id) {
        String query = "SELECT * FROM upcoming WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Upcoming(
                            rs.getInt("id"),
                            rs.getString("movie_name"),
                            rs.getString("movie_image"),
                            rs.getString("trailer")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
