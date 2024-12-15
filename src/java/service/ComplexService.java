package service;

import model.Complex;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplexService {
    private Connection connection;

    public ComplexService(Connection connection) {
        this.connection = connection;
    }

    public List<Complex> getAllComplexes() {
        List<Complex> complexes = new ArrayList<>();
        String query = "SELECT * FROM complexes";

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Complex complex = new Complex();
                complex.setComplexId(rs.getInt("complex_id"));
                complex.setName(rs.getString("name"));
                complex.setLocation(rs.getString("location"));
                complexes.add(complex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complexes;
    }

    public boolean addComplex(Complex complex) {
        String query = "INSERT INTO complexes (name, location) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, complex.getName());
            stmt.setString(2, complex.getLocation());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateComplex(Complex complex) {
        String query = "UPDATE complexes SET name = ?, location = ? WHERE complex_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, complex.getName());
            stmt.setString(2, complex.getLocation());
            stmt.setInt(3, complex.getComplexId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteComplex(int complexId) {
        String query = "DELETE FROM complexes WHERE complex_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, complexId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Complex getComplexById(int complexId) {
        String query = "SELECT * FROM complexes WHERE complex_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, complexId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Complex complex = new Complex();
                complex.setComplexId(rs.getInt("complex_id"));
                complex.setName(rs.getString("name"));
                complex.setLocation(rs.getString("location"));
                return complex;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
