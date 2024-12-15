package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author kavin
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class Logout extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the current session (don't create a new one if it doesn't exist)
        HttpSession session = request.getSession(false);

        // If the session exists, invalidate it
        if (session != null) {
            session.invalidate(); // Ends the session
        }
        
        


        // Redirect the user to the login page or home page
        response.sendRedirect("login.jsp");
         
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}