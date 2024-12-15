package service;

import model.Showtime;
import model.ShowtimeDetail;
import model.NowShowing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private Connection connection;

    public BookService(Connection connection) {
        this.connection = connection;
    }

    public NowShowing getNowShowingById(int nowShowingId) {
        NowShowing nowShowing = null;
        String query = "SELECT * FROM now_showing WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, nowShowingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nowShowing = new NowShowing(
                    rs.getInt("id"),
                    rs.getString("movie_name"),
                    rs.getString("movie_image"),
                    rs.getString("trailer")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nowShowing;
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

    public List<ShowtimeDetail> getShowtimeDetailsByShowtimes(List<Showtime> showtimes) {
        List<ShowtimeDetail> showtimeDetails = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT sd.*, c.name AS complex_name FROM showtime_details sd INNER JOIN complexes c ON sd.complex_id = c.complex_id WHERE sd.showtime_id IN (");
        
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
                    showtimeDetail.setComplexName(rs.getString("complex_name"));
                    showtimeDetails.add(showtimeDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showtimeDetails;
    }
}
