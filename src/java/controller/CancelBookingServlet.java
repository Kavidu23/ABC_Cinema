package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.SeatSelectionService;
import util.DatabaseConnectionHelper;

@WebServlet(name = "CancelBookingServlet", urlPatterns = {"/CancelBookingServlet"})
public class CancelBookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Retrieve selected seats from the request parameter
            String selectedSeatsStr = request.getParameter("seat");
            if (selectedSeatsStr != null && !selectedSeatsStr.isEmpty()) {
                String[] selectedSeats = selectedSeatsStr.replace("'", "").split(",");
                
                // Delete selected seats from the database
                try {
                    SeatSelectionService seatSelectionService = new SeatSelectionService();
                    for (String seat : selectedSeats) {
                        // Assuming nowShowingId and showtime are also needed, retrieve them from the session
           
    
                        seatSelectionService.deleteSeatSelection(selectedSeatsStr);
                    }
                } catch (Exception ex) {
                    
                }
            }

            // Invalidate the session completely
            session.invalidate();
        }

        // Redirect to the dashboard or login page
        response.sendRedirect("dashboard.jsp");
    }
}
