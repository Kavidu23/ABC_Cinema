package controller;

import model.NowShowing;
import model.Showtime;
import model.ShowtimeDetail;
import service.BookService;
import util.DatabaseConnectionHelper;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BookServlet handles fetching movie details and showtime details based on movie ID.
 */
@WebServlet(name = "BookServlet", urlPatterns = {"/book"})
public class BookServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnectionHelper.getConnection();
            this.bookService = new BookService(connection);
        } catch (Exception e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("movieId")); // Get the movie ID from the URL

        // Fetch the movie details
        NowShowing movieDetails = bookService.getNowShowingById(id);

        if (movieDetails != null) {
            request.setAttribute("movieDetails", movieDetails);

            // Fetch showtimes and showtime details
            List<Showtime> showtimes = bookService.getShowtimesByMovieId(id);
            List<ShowtimeDetail> showtimeDetails = bookService.getShowtimeDetailsByShowtimes(showtimes);

            // Pass the showtimes and showtime details to the JSP
            request.setAttribute("showtimes", showtimes);
            request.setAttribute("showtimeDetails", showtimeDetails);

            // Forward to JSP
            request.getRequestDispatcher("book.jsp").forward(request, response);
        } else {
            response.getWriter().println("<script>alert('Movie not found!'); window.location.href = 'dashboard.jsp';</script>");
        }
    }
}
