package controller;

import model.SeatSelection;
import service.SeatSelectionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SeatSelectionServlet handles saving selected seats to the database and redirects to confirmation page.
 */
@WebServlet(name = "SeatSelectionServlet", urlPatterns = {"/submitSeats"})
public class SeatSelectionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieIdParam = request.getParameter("movieId");

        // Log all parameters for debugging
        System.out.println("movieId: " + movieIdParam);
        System.out.println("showtime: " + request.getParameter("showtime"));
        System.out.println("selectedSeats: " + java.util.Arrays.toString(request.getParameterValues("selectedSeats")));
        System.out.println("location: " + request.getParameter("location"));
        System.out.println("availableDate: " + request.getParameter("availableDate"));
        System.out.println("price: " + request.getParameter("price"));
        System.out.println("language: " + request.getParameter("language"));
        System.out.println("format: " + request.getParameter("format"));
        System.out.println("type: " + request.getParameter("type"));

        // Check if movieId parameter is null
        if (movieIdParam == null || movieIdParam.isEmpty()) {
            response.sendRedirect(buildRedirectURL(request, "Invalid movie ID. Please try again."));
            return;
        }

        // Collect details from the request
        int nowShowingId = Integer.parseInt(movieIdParam); // Directly parse movieId from the URL

        String showtime = request.getParameter("showtime");

        String[] selectedSeats = request.getParameterValues("selectedSeats");

        // Collect additional details from the request
        String location = request.getParameter("location");
        String availableDate = request.getParameter("availableDate");
        String price = request.getParameter("price");
        String language = request.getParameter("language");
        String format = request.getParameter("format");
        String type = request.getParameter("type");

        // Check if any seats are selected
        if (selectedSeats == null || selectedSeats.length == 0) {
            response.sendRedirect(buildRedirectURL(request, "No seats selected. Please select at least one seat."));
            return;
        }

        // Convert selected seats to SeatSelection objects
        List<SeatSelection> seatSelections = new ArrayList<>();
        for (String seat : selectedSeats) {
            seatSelections.add(new SeatSelection(nowShowingId, showtime, seat)); // Use showtime as string
        }

        // Save selected seats to the database
        try {
            SeatSelectionService seatSelectionService = new SeatSelectionService();
            seatSelectionService.insertSeatSelections(seatSelections);
            // Encode selected seats for URL
            StringBuilder seatsParam = new StringBuilder();
            for (String seat : selectedSeats) {
                if (seatsParam.length() > 0) seatsParam.append(",");
                seatsParam.append(URLEncoder.encode(seat, StandardCharsets.UTF_8.toString()));
            }

            // Redirect to confirmation page with details
            String redirectURL = String.format("confirm.jsp?movieId=%d&showtime=%s&selectedSeats=%s&location=%s&availableDate=%s&price=%s&language=%s&format=%s",
                    nowShowingId,
                    URLEncoder.encode(showtime, StandardCharsets.UTF_8.toString()),
                    seatsParam.toString(),
                    URLEncoder.encode(location, StandardCharsets.UTF_8.toString()),
                    URLEncoder.encode(availableDate, StandardCharsets.UTF_8.toString()),
                    URLEncoder.encode(price, StandardCharsets.UTF_8.toString()),
                    URLEncoder.encode(language, StandardCharsets.UTF_8.toString()),
                    URLEncoder.encode(format, StandardCharsets.UTF_8.toString())
            );

            response.sendRedirect(redirectURL);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error: " + e.getMessage()); // Log detailed error message
            response.sendRedirect(buildRedirectURL(request, "Error occurred while saving seat selections. Please try again."));
        }
    }

    private String buildRedirectURL(HttpServletRequest request, String errorMessage) throws UnsupportedEncodingException {
        return String.format("seat.jsp?errorMessage=%s&movieId=%s&location=%s&availableDate=%s&price=%s&language=%s&format=%s&showtime=%s&type=%s",
                URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString()),
                request.getParameter("movieId"),
                request.getParameter("location"),
                request.getParameter("availableDate"),
                request.getParameter("price"),
                request.getParameter("language"),
                request.getParameter("format"),
                request.getParameter("showtime"),
                request.getParameter("type"));
    }
}
