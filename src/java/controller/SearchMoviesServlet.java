package controller;

import com.google.gson.Gson;
import model.NowShowing;
import service.NowShowingService;
import util.DatabaseConnectionHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/SearchMoviesServlet")
public class SearchMoviesServlet extends HttpServlet {
    private NowShowingService nowShowingService;

    @Override
    public void init() throws ServletException {
        Connection connection = null;
        try {
            connection = DatabaseConnectionHelper.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(SearchMoviesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        nowShowingService = new NowShowingService(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        
        if (query != null && !query.isEmpty()) {
            // Search for movies based on the query
            List<NowShowing> movies = nowShowingService.searchMovies(query);

            // Convert list of movies to JSON and send the response
            String json = new Gson().toJson(movies);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } else {
            // If no query, redirect to the dashboard
            response.sendRedirect("dashboard.jsp");
        }
    }
}
