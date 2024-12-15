/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.sql.Connection;
import java.sql.SQLException;
import util.DatabaseConnectionHelper;

/**
 * Class to test database connection.
 * @author Kashmika
 */
public class DB {

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
