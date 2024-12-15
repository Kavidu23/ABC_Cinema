package controller;

import service.BannerService;
import model.Banner;
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

@WebServlet(name = "BannerServlet", urlPatterns = {"/BannerServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)  // 50MB
public class BannerServlet extends HttpServlet {

    private BannerService bannerService;
    private static final String UPLOAD_DIR = "Uploads"; // Relative upload folder

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnectionHelper.getConnection(); // Get DB connection
            bannerService = new BannerService(connection); // Initialize service
        } catch (Exception e) {
            throw new ServletException("Error initializing BannerService", e);
        }
    }

    private String getUploadDirectory() {
        return getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            handleAddBanner(request, response);
        } else if ("update".equals(action)) {
            handleUpdateBanner(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteBanner(request, response);
        } else {
            listBanners(response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response); // Delegate to POST for uniform handling
        List<Banner> banners = bannerService.getAllBanners();
        request.setAttribute("banners", banners);  // Pass the banners to the JSP
        request.getRequestDispatcher("admin.jsp").forward(request, response); // Forward to the JSP page
    }

    private void handleAddBanner(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String trailer = request.getParameter("trailer");
        Part filePart = request.getPart("bannerImage");
        String movieName = request.getParameter("bannerName");  // Get movie name from the request
        String fileName = uploadFile(filePart);

        if (fileName != null) {
            Banner banner = new Banner(0, trailer, fileName, movieName); // Include movie name in the banner object
            if (bannerService.insertBanner(banner)) {
                response.getWriter().println("<script>alert('Banner added successfully!');window.location.href = 'admin.jsp';</script>");
            } else {
                response.getWriter().println("<script>alert('Error adding banner!');window.location.href = 'admin.jsp'</script>");
            }
        } else {
            response.getWriter().println("<script>alert('Error: No file uploaded!');window.location.href = 'admin.jsp';</script>");
        }
    }

    private void handleUpdateBanner(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String trailer = request.getParameter("trailer");
        String movieName = request.getParameter("movieName");  // Get movie name from the request
        Part filePart = request.getPart("bannerImage");
        String fileName = uploadFile(filePart);

        Banner existingBanner = bannerService.getBannerById(id);
        if (existingBanner != null) {
            if (fileName == null) {
                fileName = existingBanner.getBannerImage(); // Retain existing image
            } else {
                deleteFile(existingBanner.getBannerImage()); // Delete old image
            }

            Banner updatedBanner = new Banner(id, trailer, fileName, movieName); // Include movie name in the updated banner object
            if (bannerService.updateBanner(updatedBanner)) {
                response.getWriter().println("<script>alert('Banner updated successfully!');window.location.href = 'admin.jsp';</script>");
            } else {
                response.getWriter().println("<script>alert('Error updating banner!');window.location.href = 'admin.jsp'</script>");
            }
        } else {
            response.getWriter().println("<script>alert('Error: Banner not found!');window.location.href = 'admin.jsp'</script>");
        }
    }

    private void handleDeleteBanner(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Banner banner = bannerService.getBannerById(id);

        if (banner != null && bannerService.deleteBanner(id)) {
            deleteFile(banner.getBannerImage()); // Remove associated file
            response.getWriter().println("<script>alert('Banner deleted successfully!');window.location.href = 'admin.jsp';</script>");
        } else {
            response.getWriter().println("<script>alert('Error deleting banner!');window.location.href = 'admin.jsp'</script>");
        }
    }

    private void listBanners(HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerService.getAllBanners();
        response.getWriter().println("<h3>All Banners:</h3>");
        response.getWriter().println("<ul>");
        for (Banner banner : banners) {
            response.getWriter().println("<li>Id: " + banner.getId() + ", Trailer: " + banner.getTrailer() +
                                         ", Movie: " + banner.getMovieName() + ", Image: <img src='Uploads/" + banner.getBannerImage() + "' width='100'></li>");
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
        return "BannerServlet handles CRUD operations for movie banners.";
    }
}
