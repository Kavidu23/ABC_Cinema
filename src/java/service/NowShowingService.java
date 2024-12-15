package service;

import model.NowShowing;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NowShowingService {

    private Connection connection;

    public NowShowingService(Connection connection) {
        this.connection = connection;
    }

    // Create - Insert a new now showing movie
    public boolean insertNowShowing(NowShowing nowShowing) {
        String query = "INSERT INTO now_showing (movie_name, movie_image, trailer) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nowShowing.getMovieName());
            stmt.setString(2, nowShowing.getMovieImage());
            stmt.setString(3, nowShowing.getTrailer()); // Set the trailer URL
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read - Get all now showing movies
    public List<NowShowing> getAllNowShowing() {
        List<NowShowing> nowShowings = new ArrayList<>();
        String query = "SELECT * FROM now_showing";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                nowShowings.add(new NowShowing(rs.getInt("id"), rs.getString("movie_name"), 
                                               rs.getString("movie_image"), rs.getString("trailer"))); // Include trailer
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nowShowings;
    }

    // Read - Get a specific now showing movie by ID
    public NowShowing getNowShowingById(int id) {
        String query = "SELECT * FROM now_showing WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NowShowing(rs.getInt("id"), rs.getString("movie_name"), 
                                          rs.getString("movie_image"), rs.getString("trailer")); // Include trailer
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update - Update a now showing movie
    public boolean updateNowShowing(NowShowing nowShowing) {
        String query = "UPDATE now_showing SET movie_name = ?, movie_image = ?, trailer = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nowShowing.getMovieName());
            stmt.setString(2, nowShowing.getMovieImage());
            stmt.setString(3, nowShowing.getTrailer()); // Set the trailer URL
            stmt.setInt(4, nowShowing.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete - Delete a now showing movie
    public boolean deleteNowShowing(int id) {
        String query = "DELETE FROM now_showing WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // Search for movies by name
    public List<NowShowing> searchMovies(String query) {
    List<NowShowing> nowShowings = new ArrayList<>();
    String sql = "SELECT * FROM now_showing WHERE movie_name LIKE ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, query + "%"); // Matches movies starting with the query
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nowShowings.add(new NowShowing(
                        rs.getInt("id"),
                        rs.getString("movie_name"),
                        rs.getString("movie_image"),
                        rs.getString("trailer")
                ));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return nowShowings;
}


    // Method to get a specific now showing movie by its name
    public NowShowing getNowShowingByName(String movieName) {
    String query = "SELECT * FROM now_showing WHERE movie_name = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, movieName);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new NowShowing(
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
    return null; // Return null if no movie is found with the specified name
}



}


