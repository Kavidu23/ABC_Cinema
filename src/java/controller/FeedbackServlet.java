package controller;

import model.Feedback;
import service.FeedbackService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/submitFeedback")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data from the request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String feedbackText = request.getParameter("feedback");
        int rating = Integer.parseInt(request.getParameter("rating"));

        // Create a new Feedback object
        Feedback feedback = new Feedback(name, email, feedbackText, rating);

        // Call the FeedbackService to save feedback in the database
        FeedbackService feedbackService = new FeedbackService();
        boolean success = feedbackService.addFeedback(feedback);

        // Redirect user to a confirmation page (or show a message)
        if (success) {
            response.sendRedirect("thankyou.jsp");  // Redirect to a thank you page
        } else {
            response.sendRedirect("error.jsp");  // Redirect to an error page if feedback submission failed
        }
    }
}
