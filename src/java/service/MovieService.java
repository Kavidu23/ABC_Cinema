package service;

import model.movie;
import util.DatabaseConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private Connection connection;

    public MovieService(Connection connection) {
        this.connection = connection;
    }

    // Method to fetch now-showing movies
    public List<movie> getNowShowingMovies() {
        List<movie> movies = new ArrayList<>();
        String query = "SELECT id, movie_name, movie_image FROM now_showing";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movie movie = new movie();
                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("movie_name"));
                movie.setImage(rs.getString("movie_image"));
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Method to fetch upcoming movies
    public List<movie> getUpcomingMovies() {
        List<movie> movies = new ArrayList<>();
        String query = "SELECT id, movie_name, trailer, movie_image FROM upcoming";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movie movie = new movie();
                movie.setId(rs.getInt("id"));
                movie.setName(rs.getString("movie_name"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setImage(rs.getString("movie_image"));
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
