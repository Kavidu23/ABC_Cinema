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
import java.util.concurrent.TimeUnit;

@WebServlet(name = "LoginConfirmPageThings", urlPatterns = {"/LoginConfirmPageThings"})
public class LoginConfirmPageThings extends HttpServlet {

    private final UserService userService = new UserService();
    private int count;

    @Override
    public void init() {
        count = 0;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (count >= 3) {
            request.setAttribute("errorMessage", "Login Attempts Exceeded. Please wait 15 seconds.");
            request.setAttribute("disabled", true); // Disable the button
            request.getRequestDispatcher("loginc.jsp").forward(request, response);

            // Start the 15-second countdown in a separate thread
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(15);
                    count = 0; // Reset count after the wait period
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
            return;
        }

        // Retrieve form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate user credentials
        User user = userService.validateUser(email, password);

        if (user != null) {
            // If the user is authenticated, create a session
            HttpSession session = request.getSession();
            session.setAttribute("userName", user.getName());  // Store the user's name in session
            session.setAttribute("userEmail", user.getEmail()); // Store email in session (if needed)

            // Redirect to confirmation page
            response.sendRedirect("confirm.jsp");
        } else {
            // If authentication fails, show error message and redirect to login page
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("loginc.jsp").forward(request, response);
            count++;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to login page
        response.sendRedirect("loginc.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles user login";
    }
}
