package controller;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpSession;

@WebServlet(name = "EmailVerificationServlet", urlPatterns = {"/verify-email"})
public class EmailVerificationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        // Input validation
        if (email == null || email.isEmpty()) {
            request.setAttribute("errorMessage", "Email is required.");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            return;
        }

        UserService userService = new UserService();
        boolean emailExists = userService.checkEmailExists(email);

        if (emailExists) {
            // Redirect to password reset page or any next step
            HttpSession session= request.getSession();
            session.setAttribute("Email",email);
            
            request.setAttribute("successMessage", "Email found. Proceed to the next step.");
            request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
        } else {
            // Show an error message on the login page
            request.setAttribute("errorMessage", "The email you entered is not registered.");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
    }
}
