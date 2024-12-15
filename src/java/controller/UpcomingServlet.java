package controller;

import model.Upcoming;
import service.UpcomingService;
import util.DatabaseConnectionHelper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "UpcomingServlet", urlPatterns = {"/UpcomingServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)  // 50MB
public class UpcomingServlet extends HttpServlet {

    private UpcomingService upcomingService;
    private static final String UPLOAD_DIR = "Uploads"; // Relative upload folder

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnectionHelper.getConnection(); // Get DB connection
            this.upcomingService = new UpcomingService(connection); // Initialize service
        } catch (Exception e) {
            throw new ServletException("Error initializing UpcomingService", e);
        }
    }

    private String getUploadDirectory() {
        return getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (null == action) {
            listUpcomingMovies(response);
        } else switch (action) {
            case "add" -> handleAddUpcoming(request, response);
            case "update" -> handleUpdateUpcoming(request, response);
            case "delete" -> handleDeleteUpcoming(request, response);
            default -> listUpcomingMovies(response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Delegate to POST for uniform handling
    }

    private void handleAddUpcoming(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String movieName = request.getParameter("movieName");
        String trailerLink = request.getParameter("trailerLink");
        Part filePart = request.getPart("movieImage");
        String fileName = uploadFile(filePart);

        if (fileName != null) {
            Upcoming newMovie = new Upcoming(0, movieName, trailerLink, fileName);
            if (upcomingService.insertUpcoming(newMovie)) {
                response.getWriter().println("<script>alert('Upcoming movie added successfully!'); window.location.href = 'admin.jsp';</script>");
            } else {
                response.getWriter().println("<script>alert('Error adding movie'); window.location.href = 'admin.jsp';</script>");
            }
        } else {
            response.getWriter().println("<script>alert('Error: No file uploaded!');window.location.href = 'admin.jsp';</script>");
        }
    }

    private void handleUpdateUpcoming(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String movieName = request.getParameter("movieName");
        String trailerLink = request.getParameter("trailerLink");
        Part filePart = request.getPart("movieImage");
        String fileName = uploadFile(filePart);

        Upcoming existingMovie = upcomingService.getUpcomingById(id);
        if (existingMovie != null) {
            if (fileName == null) {
                fileName = existingMovie.getMovieImage(); // Retain existing image
            } else {
                deleteFile(existingMovie.getMovieImage()); // Delete old image
            }

            Upcoming updatedMovie = new Upcoming(id, movieName, trailerLink, fileName);
            if (upcomingService.updateUpcoming(updatedMovie)) {
                response.getWriter().println("<script>alert('Upcoming movie updated successfully!'); window.location.href = 'admin.jsp';</script>");
            } else {
                response.getWriter().println("<script>alert('Error updating movie'); window.location.href = 'admin.jsp';</script>");
            }
        } else {
            response.getWriter().println("<script>alert('Error: Movie not found!');window.location.href = 'admin.jsp'</script>");
        }
    }

    private void handleDeleteUpcoming(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Upcoming movie = upcomingService.getUpcomingById(id);

        if (movie != null && upcomingService.deleteUpcoming(id)) {
            deleteFile(movie.getMovieImage()); // Remove associated file
            response.getWriter().println("<script>alert('Upcoming movie deleted successfully!'); window.location.href = 'admin.jsp';</script>");
        } else {
            response.getWriter().println("<script>alert('Error deleting movie');window.location.href = 'admin.jsp'</script>");
        }
    }

    private void listUpcomingMovies(HttpServletResponse response) throws IOException {
        response.getWriter().println("<h2>Upcoming Movies</h2>");
        response.getWriter().println("<ul>");
        List<Upcoming> movies = upcomingService.getAllUpcoming();
        for (Upcoming movie : movies) {
            response.getWriter().println("<li>" + movie.getMovieName() + " - <img src='Uploads/" + movie.getMovieImage() + "' width='100'></li>");
            response.getWriter().println("<a href='UpcomingServlet?action=edit&id=" + movie.getId() + "'>Edit</a> | ");
            response.getWriter().println("<a href='UpcomingServlet?action=delete&id=" + movie.getId() + "'>Delete</a>");
        }
        response.getWriter().println("</ul>");
    }

    private String uploadFile(Part filePart) throws IOException {
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getUploadDirectory();
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath); // Save the file
            return fileName;
        }
        return null;
    }

    private void deleteFile(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            String filePath = getUploadDirectory() + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Upcoming Movies Servlet";
    }
}
