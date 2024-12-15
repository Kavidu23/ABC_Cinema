package controller;

import model.User;
import service.UserService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {

    private final UserService userService = new UserService(); // Instance of UserService

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Retrieve form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pnumber = request.getParameter("pnumber");
        String password = request.getParameter("password"); 

        // Create a user object
        User user = new User(name, email, pnumber, password); // Pass password to the User object

        // Add the user to the database
        boolean success = userService.addUser(user);

        // Redirect based on the result
        if (success) {
            // Create a session for the user
            HttpSession session = request.getSession(); // Create a session (or retrieve it if already exists)
            session.setAttribute("userName", name);    // Store user's name in the session
            session.setAttribute("userEmail", email); // Optionally, store email in the session

            // Redirect to confirmation page after successful sign-up
            response.sendRedirect("confirm.jsp"); // Redirect to the confirmation page or a success page
        } else {
            // Set an error message if the email already exists
            request.setAttribute("errorMessage", "The email " + email + " is already registered.");
            // Redirect to the signup page with the error message
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Sign-up Controller";
    }
}
