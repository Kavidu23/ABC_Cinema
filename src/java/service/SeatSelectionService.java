package service;

import model.SeatSelection;
import util.DatabaseConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionService {

    // Method to insert seat selections
    public void insertSeatSelections(List<SeatSelection> seatSelections) throws SQLException {
        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            for (SeatSelection seatSelection : seatSelections) {
                insertSeatSelection(connection, seatSelection);
            }
        }
    }

    // Helper method to insert a single seat selection
    private void insertSeatSelection(Connection connection, SeatSelection seatSelection) throws SQLException {
        String query = "INSERT INTO seat_selections (now_showing_id, showtime, seat_number) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, seatSelection.getNowShowingId());
            stmt.setString(2, seatSelection.getShowtime());
            stmt.setString(3, seatSelection.getSeatNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            throw new SQLException("Error inserting seat selection", e); // Rethrow the exception with additional context
        }
    }

    // Method to retrieve seat selections by movie ID and showtime
    public List<SeatSelection> getSeatSelections(int nowShowingId, String showtime) throws SQLException {
        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            return retrieveSeatSelections(connection, nowShowingId, showtime);
        }
    }

    // Helper method to retrieve seat selections
    private List<SeatSelection> retrieveSeatSelections(Connection connection, int nowShowingId, String showtime) throws SQLException {
        List<SeatSelection> seatSelections = new ArrayList<>();
        String query = "SELECT * FROM seat_selections WHERE now_showing_id = ? AND showtime = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nowShowingId);
            stmt.setString(2, showtime);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SeatSelection seatSelection = new SeatSelection(
                    rs.getInt("now_showing_id"),
                    rs.getString("showtime"),
                    rs.getString("seat_number")
                );
                seatSelection.setSelectionId(rs.getInt("selection_id"));
                seatSelections.add(seatSelection);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            throw new SQLException("Error retrieving seat selections", e); // Rethrow the exception with additional context
        }
        return seatSelections;
    }

    // Method to delete seat selection
    public void deleteSeatSelection(String seat) throws SQLException {
        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            String query = "DELETE FROM seat_selections WHERE seat_number = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, seat);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // Log the exception
                throw new SQLException("Error deleting seat selection", e); // Rethrow the exception with additional context
            }
        }
    }
}
