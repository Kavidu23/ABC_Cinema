package controller;

import model.ShowtimeDetail;
import service.ShowtimeDetailService;
import util.DatabaseConnectionHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

@WebServlet("/ShowtimeDetailServlet")
public class ShowtimeDetailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try (Connection connection = DatabaseConnectionHelper.getConnection()) {
            ShowtimeDetailService showtimeDetailService = new ShowtimeDetailService(connection);

            if ("add".equals(action)) {
                // Add ShowtimeDetail
                ShowtimeDetail showtimeDetail = new ShowtimeDetail();
                showtimeDetail.setShowtimeId(Integer.parseInt(request.getParameter("showtimeId")));
                showtimeDetail.setComplexId(Integer.parseInt(request.getParameter("complexId")));
                showtimeDetail.setAvailableDate(Date.valueOf(request.getParameter("availableDate")));
                showtimeDetail.setPrice(Double.parseDouble(request.getParameter("price")));

                showtimeDetailService.addShowtimeDetail(showtimeDetail);
                response.getWriter().write("<script>alert('Showtime detail added successfully!'); window.location.href='adminComplexShowtime.jsp';</script>");

            } else if ("update".equals(action)) {
              // Update ShowtimeDetail
              ShowtimeDetail showtimeDetail = new ShowtimeDetail();
    
              showtimeDetail.setDetailId(Integer.parseInt(request.getParameter("detailId")));
              showtimeDetail.setComplexId(Integer.parseInt(request.getParameter("complexId")));
              showtimeDetail.setAvailableDate(Date.valueOf(request.getParameter("availableDate")));
              showtimeDetail.setPrice(Double.parseDouble(request.getParameter("price")));
    
              showtimeDetailService.updateShowtimeDetail(showtimeDetail);
              response.getWriter().write("<script>alert('Showtime detail updated successfully!'); window.location.href='adminComplexShowtime.jsp';</script>");
              }
               else if ("delete".equals(action)) {
                // Delete ShowtimeDetail
                int detailId = Integer.parseInt(request.getParameter("detailId"));
                showtimeDetailService.deleteShowtimeDetail(detailId);
                response.getWriter().write("<script>alert('Showtime detail deleted successfully!'); window.location.href='adminComplexShowtime.jsp';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('An error occurred. Please try again.'); window.location.href='adminComplexShowtime.jsp';</script>");
        }
    }
}
