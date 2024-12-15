package service;

import model.Showtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class ShowtimeService {
    private Connection connection;

    public ShowtimeService(Connection connection) {
        this.connection = connection;
    }

    // Add a new showtime
    public void addShowtime(Showtime showtime) throws Exception {
        String sql = "INSERT INTO showtime (movie_id, duration, format, showtime, language) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, showtime.getMovieId());
            ps.setInt(2, showtime.getDuration());
            ps.setString(3, showtime.getFormat());
            ps.setTimestamp(4, showtime.getShowtime());
            ps.setString(5, showtime.getLanguage());

            ps.executeUpdate();
        }
    }

    // Update an existing showtime
    public void updateShowtime(Showtime showtime) throws Exception {
        String sql = "UPDATE showtime SET movie_id = ?, duration = ?, format = ?, showtime = ?, language = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, showtime.getMovieId());
            ps.setInt(2, showtime.getDuration());
            ps.setString(3, showtime.getFormat());
            ps.setTimestamp(4, showtime.getShowtime());
            ps.setString(5, showtime.getLanguage());
            ps.setInt(6, showtime.getId());

            ps.executeUpdate();
        }
    }

    // Get all showtimes
    public List<Showtime> getAllShowtimes() throws Exception {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT * FROM showtime";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Showtime showtime = new Showtime();
                showtime.setId(rs.getInt("id"));
                showtime.setMovieId(rs.getInt("movie_id"));
                showtime.setDuration(rs.getInt("duration"));
                showtime.setFormat(rs.getString("format"));
                showtime.setShowtime(rs.getTimestamp("showtime"));
                showtime.setLanguage(rs.getString("language"));

                showtimes.add(showtime);
            }
        }

        return showtimes;
    }

    // Delete a showtime
    public void deleteShowtime(int id) throws Exception {
        String sql = "DELETE FROM showtime WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    
    
    // Get a showtime by its ID
public Showtime getShowtimeById(int id) throws Exception {
    Showtime showtime = null;
    String sql = "SELECT * FROM showtime WHERE id = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            showtime = new Showtime();
            showtime.setId(rs.getInt("id"));
            showtime.setMovieId(rs.getInt("movie_id"));
            showtime.setDuration(rs.getInt("duration"));
            showtime.setFormat(rs.getString("format"));
            showtime.setShowtime(rs.getTimestamp("showtime"));
            showtime.setLanguage(rs.getString("language"));
        }
    }

    return showtime;
}


    public List<Showtime> getShowtimesByMovieId(int movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        String query = "SELECT * FROM showtime WHERE movie_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Showtime showtime = new Showtime();
                showtime.setId(rs.getInt("id"));
                showtime.setMovieId(rs.getInt("movie_id"));
                showtime.setDuration(rs.getInt("duration"));
                showtime.setFormat(rs.getString("format"));
                showtime.setShowtime(rs.getTimestamp("showtime"));
                showtime.setLanguage(rs.getString("language"));
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimes;
    }



}

