package service;

import model.Showtime;
import model.ShowtimeDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeDetailService {
    private Connection connection;

    public ShowtimeDetailService(Connection connection) {
        this.connection = connection;
    }

    public List<ShowtimeDetail> getAllShowtimeDetails() {
        List<ShowtimeDetail> showtimeDetails = new ArrayList<>();
        String query = "SELECT * FROM showtime_details";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ShowtimeDetail showtimeDetail = new ShowtimeDetail();
                showtimeDetail.setDetailId(rs.getInt("detail_id"));
                showtimeDetail.setShowtimeId(rs.getInt("showtime_id"));
                showtimeDetail.setComplexId(rs.getInt("complex_id"));
                showtimeDetail.setAvailableDate(rs.getDate("available_date"));
                showtimeDetail.setPrice(rs.getDouble("price"));
                showtimeDetails.add(showtimeDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimeDetails;
    }

    public List<ShowtimeDetail> getShowtimeDetailsByShowtimes(List<Showtime> showtimes) {
        List<ShowtimeDetail> showtimeDetails = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM showtime_details WHERE showtime_id IN (");
        
        for (int i = 0; i < showtimes.size(); i++) {
            queryBuilder.append("?");
            if (i < showtimes.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");
        
        String query = queryBuilder.toString();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < showtimes.size(); i++) {
                stmt.setInt(i + 1, showtimes.get(i).getId());
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ShowtimeDetail showtimeDetail = new ShowtimeDetail();
                    showtimeDetail.setDetailId(rs.getInt("detail_id"));
                    showtimeDetail.setShowtimeId(rs.getInt("showtime_id"));
                    showtimeDetail.setComplexId(rs.getInt("complex_id"));
                    showtimeDetail.setAvailableDate(rs.getDate("available_date"));
                    showtimeDetail.setPrice(rs.getDouble("price"));
                    showtimeDetails.add(showtimeDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimeDetails;
    }

    public boolean addShowtimeDetail(ShowtimeDetail showtimeDetail) {
        String query = "INSERT INTO showtime_details (showtime_id, complex_id, available_date, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, showtimeDetail.getShowtimeId());
            stmt.setInt(2, showtimeDetail.getComplexId());
            stmt.setDate(3, showtimeDetail.getAvailableDate());
            stmt.setDouble(4, showtimeDetail.getPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateShowtimeDetail(ShowtimeDetail showtimeDetail) {
        String query = "UPDATE showtime_details SET complex_id = ?, available_date = ?, price = ? WHERE detail_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, showtimeDetail.getComplexId());
            stmt.setDate(2, showtimeDetail.getAvailableDate());
            stmt.setDouble(3, showtimeDetail.getPrice());
            stmt.setInt(4, showtimeDetail.getDetailId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteShowtimeDetail(int detailId) {
        String query = "DELETE FROM showtime_details WHERE detail_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, detailId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
