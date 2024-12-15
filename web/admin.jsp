<%@page import="model.movie"%>
<%@page import="service.MovieService"%>
<%@page import="model.Upcoming"%>
<%@page import="service.UpcomingService"%>
<%@page import="model.NowShowing"%>
<%@page import="service.NowShowingService"%>
<%@page import="model.Banner"%>
<%@page import="java.util.List"%>
<%@page import="service.BannerService"%>
<%@page import="util.DatabaseConnectionHelper"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Movie Management</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="admin.css">
    <script src="admin.js" defer></script>
    
</head>
<body>
    <% 
    // Retrieve the user name from the session
    String userName = (String) session.getAttribute("username");
   
    if (!"admin".equals(userName)) { 
    %>
    <style>
        body{
            display: none;
        }
    </style>  
    <% 
    }
    %>
    
    <div class="container">
        <h1>Admin Panel</h1>
        <navbar class="navbar">
            <a href="admin.jsp"><h3>Movie Management</h3></a>
            <a href="admint.jsp"><h3>Time Management</h3></a>
            <a href="adminComplexShowtime.jsp"><h3>Complex & Show</h3></a>
            <a href="adminfeed.jsp"><h3>Feedback</h3></a>
        </navbar>
        <!-- Banner Section -->
        <section class="section">
            <h2>Banner Management</h2>
            <form action="BannerServlet?action=add" method="post" enctype="multipart/form-data">
                <label for="bannerTrailer">Trailer URL:</label>
                <input type="text" id="bannerTrailer" name="trailer" required><br>
                <label for="bannerImage">Banner Image:</label>
                <input type="file" id="bannerImage" name="bannerImage" required><br>
                <label for="bannerName">Banner Name:</label>
                <input type="text" id="bannerImage" name="bannerName" required><br>
                <button type="submit" class="btn">Add Banner</button>
            </form>

            <h3>Current Banners</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Trailer</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        BannerService bannerService = new BannerService(DatabaseConnectionHelper.getConnection());
                        List<Banner> banners = bannerService.getAllBanners();
                        for (Banner banner : banners) {
                    %>
                    <tr>
                        <td><%= banner.getId() %></td>
                        <td><a href="<%= banner.getTrailer() %>" target="_blank">Trailer</a></td>
                        <td>
                            <img src="Resources/<%= banner.getBannerImage() %>" alt="Banner <%= banner.getId() %>" width="100" height="60">
                        </td>
                        <td><%= banner.getMovieName() %></td>
                        <td>
                            
                            <form action="BannerServlet?action=update" enctype="multipart/form-data" method="post" style="display:inline;">
                                <label for="bannerTrailer">Trailer URL:</label>
                                <input type="text" id="bannerTrailer" name="trailer" required><br>
                                <label for="bannerImage">Banner Image:</label>
                                <input type="file" id="bannerImage" name="bannerImage" required><br>
                                <label for="bannerImage">Banner Name:</label>
                                <input type="text" id="bannerImage" name="bannerName" required><br>
                                <input type="hidden" name="id" value="<%= banner.getId() %>">
                                <button type="submit" class="btn update">Update</button>
                            </form>
                            <form action="BannerServlet?action=delete" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= banner.getId() %>">
                                <button type="submit" class="btn delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </section>

        <!-- Now Showing Section -->
        <section class="section">
            <h2>Now Showing</h2>
            <form action="NowShowingServlet?action=add" method="post" enctype="multipart/form-data">
                <label for="movieName">Movie Name:</label>
                <input type="text" id="movieName" name="movieName" required><br>
                <label for="movieImage">Movie Image:</label>
                <input type="file" id="movieImage" name="movieImage" required><br>
                <label for="movieTrailer">Movie Trailer:</label>
                <input type="text" id="movieName" name="movieTrailer" required><br>
                <button type="submit" class="btn">Add Now Showing</button>
            </form>

            <h3>Currently Showing Movies</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Trailer</th>
                        <th>Movie Name</th>
                        <th>Image</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        MovieService nowShowingService = new MovieService(DatabaseConnectionHelper.getConnection());
                        List<movie> nowShowingMovies = nowShowingService.getNowShowingMovies();
                        for (movie movie : nowShowingMovies) {
                    %>
                    <tr>
                        <td><%= movie.getId() %></td>
                        <td><a href="<%= movie.getTrailer() %>" target="_blank">Trailer</a></td>
                        <td><%= movie.getName() %></td>
                        
                        <td>
                            <img src="Resources/<%= movie.getImage() %>" alt="Movie <%= movie.getId() %>" width="100" height="60">
                        </td>
                        <td>
                            <form action="NowShowingServlet?action=update" enctype="multipart/form-data" method="post" style="display:inline;">
                                <label for="bannerTrailer">Movie Name</label>
                                <input type="text" id="bannerTrailer" name="movieName" required><br>
                                <label for="bannerImage">Movie Trailer:</label>
                                <input type="text" id="bannerTrailer" name="movieTrailer" required><br>
                                <label for="bannerImage">Banner Image:</label>
                                <input type="file" id="bannerImage" name="movieImage" required><br>
                                <input type="hidden" name="id" value="<%= movie.getId() %>">
                                <button type="submit" class="btn update">Update</button>
                            </form>
                            <form action="NowShowingServlet?action=delete"  method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= movie.getId() %>">
                                <button type="submit" class="btn delete">Delete</button>
                            </form>
                        </td>
                        
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </section>

        <!-- Upcoming Movies Section -->
        <section class="section">
            <h2>Upcoming Movies</h2>
            <form action="UpcomingServlet?action=add" method="post" enctype="multipart/form-data">
                <label for="upcomingName">Movie Name:</label>
                <input type="text" id="upcomingName" name="movieName" required><br>
                <label for="trailerLink">Trailer Link:</label>
                <input type="text" id="trailerLink" name="trailerLink" required><br>
                <label for="upcomingImage">Movie Image:</label>
                <input type="file" id="upcomingImage" name="movieImage" required><br>
                <button type="submit" class="btn">Add Upcoming Movie</button>
            </form>

            <h3>Upcoming Movies</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Trailer</th>
                        <th>Movie Name</th>
                        <th>Image</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<movie> upcomingMovies = nowShowingService.getUpcomingMovies();
                        for (movie movie : upcomingMovies) {
                    %>
                    <tr>
                        <td><%= movie.getId() %></td>
                         <td><a href="<%= movie.getTrailer() %>" target="_blank">Trailer</a></td>
                        <td><%= movie.getName() %></td>
                        <td>
                            <img src="Resources/<%= movie.getImage() %>" alt="Upcoming <%= movie.getId() %>" width="100" height="60">
                        </td>
                        <td>
                            <form action="UpcomingServlet?action=update" method="post" enctype="multipart/form-data" style="display:inline;">
                                <label for="bannerTrailer">Trailer URL:</label>
                                <input type="text" id="bannerTrailer" name="movieName" required><br>
                                <label for="bannerImage">Banner Image:</label>
                                <input type="text" id="bannerTrailer" name="movieTrailer" required><br>
                                <label for="bannerImage">Banner Image:</label>
                                <input type="file" id="bannerImage" name="movieImage" required><br>
                                <input type="hidden" name="id" value="<%= movie.getId() %>">
                                <button type="submit" class="btn update">Update</button>
                            </form>
                            <form action="UpcomingServlet?action=delete"  method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= movie.getId() %>">
                                <button type="submit" class="btn delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </section>
    </div>
</body>
</html>
