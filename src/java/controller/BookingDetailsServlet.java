package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * BookingDetailsServlet handles collecting and processing booking details from the form and redirects to seat selection.
 */
@WebServlet(name = "BookingDetailsServlet", urlPatterns = {"/submitBooking"})
public class BookingDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Collect details from the form
        String location = request.getParameter("location");
        String availableDate = request.getParameter("availableDate");
        String price = request.getParameter("price");
        String language = request.getParameter("language");
        String format = request.getParameter("format");
        String showtime = request.getParameter("showtime");

        // Collect details from the URL
        String type = request.getParameter("type");
        int movieId = Integer.parseInt(request.getParameter("movieId"));

        // Encode URL parameters
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8.toString());
        String encodedAvailableDate = URLEncoder.encode(availableDate, StandardCharsets.UTF_8.toString());
        String encodedPrice = URLEncoder.encode(price, StandardCharsets.UTF_8.toString());
        String encodedLanguage = URLEncoder.encode(language, StandardCharsets.UTF_8.toString());
        String encodedFormat = URLEncoder.encode(format, StandardCharsets.UTF_8.toString());
        String encodedShowtime = URLEncoder.encode(showtime, StandardCharsets.UTF_8.toString());
        String encodedType = URLEncoder.encode(type, StandardCharsets.UTF_8.toString());
        String encodedMovieId = URLEncoder.encode(String.valueOf(movieId), StandardCharsets.UTF_8.toString());

        // Redirect to seat.jsp with URL parameters
        response.sendRedirect("seat.jsp?location=" + encodedLocation 
            + "&availableDate=" + encodedAvailableDate
            + "&price=" + encodedPrice
            + "&language=" + encodedLanguage
            + "&format=" + encodedFormat
            + "&showtime=" + encodedShowtime
            + "&type=" + encodedType
            + "&movieId=" + encodedMovieId);
    }
}
