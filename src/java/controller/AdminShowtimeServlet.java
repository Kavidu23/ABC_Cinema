package controller;

import model.Showtime;
import service.ShowtimeService;
import util.DatabaseConnectionHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet("/AdminShowtimeServlet")
public class AdminShowtimeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            ShowtimeService showtimeService = new ShowtimeService(connection);

            if ("add".equals(action)) {
                // Add Showtime
                Showtime showtime = new Showtime();
                showtime.setMovieId(Integer.parseInt(request.getParameter("movieId")));
                showtime.setDuration(Integer.parseInt(request.getParameter("duration")));
                showtime.setFormat(request.getParameter("format"));
                showtime.setLanguage(request.getParameter("language"));

                LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("showtime"));
                showtime.setShowtime(Timestamp.valueOf(localDateTime));

                showtimeService.addShowtime(showtime);
                response.getWriter().write("<script>alert('Showtime added successfully!'); window.location.href='admint.jsp';</script>");

            } else if ("update".equals(action)) {
                // Update Showtime
                Showtime showtime = new Showtime();
                showtime.setId(Integer.parseInt(request.getParameter("id")));
                showtime.setMovieId(Integer.parseInt(request.getParameter("movieId")));
                showtime.setDuration(Integer.parseInt(request.getParameter("duration")));
                showtime.setFormat(request.getParameter("format"));
                showtime.setLanguage(request.getParameter("language"));

                LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("showtime"));
                showtime.setShowtime(Timestamp.valueOf(localDateTime));

                showtimeService.updateShowtime(showtime);
                response.getWriter().write("<script>alert('Showtime updated successfully!'); window.location.href='admint.jsp';</script>");

            } else if ("delete".equals(action)) {
                // Delete Showtime
                int id = Integer.parseInt(request.getParameter("id"));
                showtimeService.deleteShowtime(id);
                response.getWriter().write("<script>alert('Showtime deleted successfully!'); window.location.href='admint.jsp';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('An error occurred. Please try again.'); window.location.href='admint.jsp';</script>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            ShowtimeService showtimeService = new ShowtimeService(connection);

            if ("edit".equals(action)) {
                // Edit Showtime - Fetch the showtime by ID
                int id = Integer.parseInt(request.getParameter("id"));
                Showtime showtime = showtimeService.getShowtimeById(id);

                // Set the showtime in the request to forward to the JSP for editing
                if (showtime != null) {
                    request.setAttribute("showtime", showtime);
                    request.getRequestDispatcher("/admin_showtime_edit.jsp").forward(request, response);
                } else {
                    response.getWriter().write("<script>alert('Showtime not found.'); window.location.href='admint.jsp';</script>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('An error occurred. Please try again.'); window.location.href='admint.jsp';</script>");
        }
    }
}
