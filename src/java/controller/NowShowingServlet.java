package controller;

import model.NowShowing;
import service.NowShowingService;
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

@WebServlet(name = "NowShowingServlet", urlPatterns = {"/NowShowingServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)  // 50MB
public class NowShowingServlet extends HttpServlet {

    private NowShowingService nowShowingService;
    private static final String UPLOAD_DIR = "Uploads"; // Relative upload folder

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnectionHelper.getConnection(); // Get DB connection
            this.nowShowingService = new NowShowingService(connection); // Initialize service
        } catch (Exception e) {
            throw new ServletException("Error initializing NowShowingService", e);
        }
    }

    private String getUploadDirectory() {
        return getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            handleAddNowShowing(request, response);
        } else if ("update".equals(action)) {
            handleUpdateNowShowing(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteNowShowing(request, response);
        } else {
            listNowShowingMovies(response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Delegate to POST for uniform handling
    }

    private void handleAddNowShowing(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String movieName = request.getParameter("movieName");
        String movieTrailer = request.getParameter("movieTrailer"); // Get the movieTrailer parameter
        Part filePart = request.getPart("movieImage");
        String fileName = uploadFile(filePart);

        if (fileName != null) {
            NowShowing newMovie = new NowShowing(0, movieName, fileName, movieTrailer); // Add trailer URL
            if (nowShowingService.insertNowShowing(newMovie)) {
                response.getWriter().println("<script>alert('Now showing movie added successfully!'); window.location.href = 'admin.jsp';</script>");
            } else {
                response.getWriter().println("<script>alert('Error adding movie'); window.location.href = 'admin.jsp';</script>");
            }
        } else {
            response.getWriter().println("<script>alert('Error: No file uploaded!');window.location.href = 'admin.jsp';</script>");
        }
    }

    private void handleUpdateNowShowing(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String movieName = request.getParameter("movieName");
        String movieTrailer = request.getParameter("movieTrailer"); // Get the movieTrailer parameter
        Part filePart = request.getPart("movieImage");
        String fileName = uploadFile(filePart);

        NowShowing existingMovie = nowShowingService.getNowShowingById(id);
        if (existingMovie != null) {
            if (fileName == null) {
                fileName = existingMovie.getMovieImage(); // Retain existing image
            } else {
                deleteFile(existingMovie.getMovieImage()); // Delete old image
            }

            NowShowing updatedMovie = new NowShowing(id, movieName, fileName, movieTrailer); // Add trailer URL
            if (nowShowingService.updateNowShowing(updatedMovie)) {
                response.getWriter().println("<script>alert('Now showing movie updated successfully!'); window.location.href = 'admin.jsp';</script>");
            } else {
                response.getWriter().println("<script>alert('Error updating movie'); window.location.href = 'admin.jsp';</script>");
            }
        } else {
            response.getWriter().println("<script>alert('Error: Movie not found!');window.location.href = 'admin.jsp'</script>");
        }
    }

    private void handleDeleteNowShowing(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        NowShowing movie = nowShowingService.getNowShowingById(id);

        if (movie != null && nowShowingService.deleteNowShowing(id)) {
            deleteFile(movie.getMovieImage()); // Remove associated file
            response.getWriter().println("<script>alert('Now showing movie deleted successfully!'); window.location.href = 'admin.jsp';</script>");
        } else {
            response.getWriter().println("<script>alert('Error deleting movie');window.location.href = 'admin.jsp'</script>");
        }
    }

    private void listNowShowingMovies(HttpServletResponse response) throws IOException {
        response.getWriter().println("<h2>Now Showing Movies</h2>");
        response.getWriter().println("<ul>");
        List<NowShowing> movies = nowShowingService.getAllNowShowing();
        for (NowShowing movie : movies) {
            response.getWriter().println("<li>" + movie.getMovieName() + " - <img src='Uploads/" + movie.getMovieImage() + "' width='100'></li>");
            response.getWriter().println("<a href='NowShowingServlet?action=edit&id=" + movie.getId() + "'>Edit</a> | ");
            response.getWriter().println("<a href='NowShowingServlet?action=delete&id=" + movie.getId() + "'>Delete</a>");
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
        return "Now Showing Movies Servlet";
    }
}
