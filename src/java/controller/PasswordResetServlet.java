package controller;

import service.UserService;
import util.DatabaseConnectionHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

@WebServlet(name = "PasswordResetServlet", urlPatterns = {"/reset-password"})
public class PasswordResetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("Email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Input validation
        if (email == null || email.isEmpty() ||
            newPassword == null || newPassword.isEmpty() ||
            confirmPassword == null || confirmPassword.isEmpty()) {
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
            return;
        }

        UserService userService = new UserService();
        String hashedPassword = userService.hashPassword(newPassword);

        String query = "UPDATE user SET password = ? WHERE email = ?";

        try (Connection connection = DatabaseConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, email);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
               response.getWriter().write("<script>alert('Password updated successfully. Please log in.'); window.location.href = 'login.jsp';</script>");
            } else {
                request.setAttribute("errorMessage", "User with the provided email does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        }

       
    }
}
