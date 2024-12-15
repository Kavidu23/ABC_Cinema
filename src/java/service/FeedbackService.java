package service;

import model.Feedback;
import util.DatabaseConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService {

    // Method to add feedback
    public boolean addFeedback(Feedback feedback) {
        String query = "INSERT INTO feedback (name, email, feedback, rating) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, feedback.getName());
            preparedStatement.setString(2, feedback.getEmail());
            preparedStatement.setString(3, feedback.getFeedback());
            preparedStatement.setInt(4, feedback.getRating());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all feedback
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedback";

        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Feedback feedback = new Feedback(
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("feedback"),
                    resultSet.getInt("rating")
                );

                feedbackList.add(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }
}
