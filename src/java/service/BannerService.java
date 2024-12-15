package service;

import model.Banner;
import util.DatabaseConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BannerService {
    private final Connection connection;

    // Constructor accepting a Connection parameter
    public BannerService(Connection connection) {
        this.connection = connection;
    }

    // Insert a new banner, including the movie name (now as the last column)
    public boolean insertBanner(Banner banner) {
        String query = "INSERT INTO movie (trailer, banner_image, movie_name) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, banner.getTrailer());  // Insert trailer
            stmt.setString(2, banner.getBannerImage());  // Insert banner image
            stmt.setString(3, banner.getMovieName());  // Insert movie name
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all banners with the movie name
    public List<Banner> getAllBanners() {
        List<Banner> banners = new ArrayList<>();
        String query = "SELECT * FROM movie";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                banners.add(new Banner(
                    rs.getInt("id"),
                    rs.getString("trailer"),
                    rs.getString("banner_image"),
                    rs.getString("movie_name") // Get movie name
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return banners;
    }

    // Get a specific banner by its ID
    public Banner getBannerById(int id) {
        String query = "SELECT * FROM movie WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Banner(
                    rs.getInt("id"),
                    rs.getString("trailer"),
                    rs.getString("banner_image"),
                    rs.getString("movie_name") // Get movie name
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing banner's details, including movie name
    public boolean updateBanner(Banner banner) {
        String query = "UPDATE movie SET trailer = ?, banner_image = ?, movie_name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, banner.getTrailer());
            stmt.setString(2, banner.getBannerImage());
            stmt.setString(3, banner.getMovieName()); // Update movie name
            stmt.setInt(4, banner.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a banner by its ID
    public boolean deleteBanner(int id) {
        String query = "DELETE FROM movie WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
