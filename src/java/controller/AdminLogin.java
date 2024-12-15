package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.AdminService;

@WebServlet(name = "AdminLogin", urlPatterns = {"/AdminLogin"})
public class AdminLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // No processing needed for GET requests
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the user inputs from the login form
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        // Initialize AdminService to authenticate the admin
        AdminService service = new AdminService();
        try {
            boolean check = service.authenticateAdmin(name, password);
            
            if (check) {
                // Create session and set user information
                HttpSession session = request.getSession();
                session.setAttribute("username", name);
                session.setAttribute("loginMessage", "Login successful!");
                
                // Redirect to admin page
                response.sendRedirect("admin.jsp");
            } else {
                // If authentication fails, set error message
                request.setAttribute("errorMessage", "Invalid Username or Password");
                request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Handle exceptions and forward to the login page with an error message
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("adminlogin.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin Login Servlet";
    }
}
