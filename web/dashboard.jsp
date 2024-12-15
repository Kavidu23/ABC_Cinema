<%@page import="model.Upcoming"%>
<%@page import="service.UpcomingService"%>
<%@page import="model.NowShowing"%>
<%@page import="service.NowShowingService"%>
<%@page import="model.Banner"%>
<%@page import="java.util.List"%>
<%@page import="service.BannerService"%>
<%@page import="util.DatabaseConnectionHelper"%>
<% 
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@page import="model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome - ABC CINEMA</title>
    <!-- External CSS Libraries -->
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
    <link rel="stylesheet" href="index.css">
</head>
<body>
    <!-- Header -->
    <header>
        <img src="Resources/logo.png" alt="ABC Cinema Logo" height="50" width="100">
        <form id="search-form" action="SearchMoviesServlet" method="get" onsubmit="return false;">
          <div class="input-container">
          <i class="bx bx-search-alt-2"></i>
          <input type="text" id="search" name="search" placeholder="Search..." oninput="searchMovies()" autocomplete="off">
          <div id="search-results" class="search-results"></div> <!-- Div to display suggestions -->
          </div>
        </form>

        <i id="menu-icon" class="bx bx-menu"></i>
    </header>

    <div id="side-menu">
        <a href="javascript:void(0)" class="close-btn" onclick="document.getElementById('side-menu').style.width = '0'">×</a>
        <i class='bx bxs-user-circle'></i>

    <% 
    // Retrieve the user name from the session
    String userName = (String) session.getAttribute("userName");
    String userEmail = (String) session.getAttribute("userEmail");
    
    if (userName != null) { 
    %>
        <!-- If logged in, show the username and View Profile -->
        <span style="text-transform: uppercase;"><%= userName %></span>
        <a href="viewprofile.jsp">VIEW PROFILE</a>
    <% 
    } else {
    %>
        <!-- If not logged in, show Login and Signup -->
        <a href="login.jsp">LOGIN</a>
        <a href="signup.jsp">SIGN UP</a>
    <% 
    }
    %>


    </div>

    <!-- Carousel Section -->
    <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
    <div class="carousel-inner">
            <% 
            BannerService bannerService = new BannerService(DatabaseConnectionHelper.getConnection());
            List<Banner> banners = bannerService.getAllBanners();
            boolean firstItem = true; // To apply 'active' class only to the first item
            for (Banner banner : banners) {
            %>
            <div class="carousel-item <%= firstItem ? "active" : "" %>">
                <img src="Resources/<%= banner.getBannerImage() %>" class="d-block w-100" alt="<%= banner.getBannerImage() %>">
              <div class="overlayer">   
                  <a href="<%= banner.getTrailer() %>" target="_blank"><button id="bannerb">WATCH TRAILER</button></a>
              </div>
            </div>
            <%
                firstItem = false; // After the first item, set firstItem to false
            }
            %>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>


    <!-- Now Showing Section -->
    <section class="nowshowing">
    <p>NOW SHOWING</p>
    <div class="image-grid">
        <% 
        NowShowingService nowService = new NowShowingService(DatabaseConnectionHelper.getConnection());
        List<NowShowing> nowShowingMovies = nowService.getAllNowShowing(); // Corrected the variable name
        for (NowShowing movie : nowShowingMovies) {
        %>
        <div class="image-wrapper">
            <img src="Resources/<%= movie.getMovieImage() %>" alt="<%= movie.getMovieName() %>">
            <p><%=movie.getMovieName()%></p>
            <div class="overlay">
                <button id="book" onclick="location.href='book?type=nowshowing&movieId=<%= movie.getId() %>'">Book Tickets</button>
                <button id="watch">
                <a href="<%= movie.getTrailer() %>" target="_blank">Watch Trailer</a>
                </button>

            </div>
        </div>
        <% } %>     
    </div>       
    </section>


    <!-- Coming Soon Section -->
    <section class="upcoming">
    <p>COMING SOON</p>
    <div class="image-grid">
        <% 
        UpcomingService upcomingService = new UpcomingService(DatabaseConnectionHelper.getConnection());
        List<Upcoming> upcomingMovies = upcomingService.getAllUpcoming();
        for (Upcoming movie : upcomingMovies) { 
        %>
        <div class="image-wrapper">
            <img src="Resources/<%= movie.getMovieImage() %>" alt="<%= movie.getMovieName() %>">
            <p><%= movie.getMovieName() %></p>
            <div class="overlay">
                    <a href="<%= movie.getTrailerLink() %>" target="_blank">
                    <button>
                    Watch Trailer
                    </button>
                    </a>
            </div>
        </div>
        <% } %>
    </div>
    </section>


    <!-- Footer -->
    <footer>
        <div class="footer">
            <div class="links">
                <ul>
                    <li><a href="aboutus.html">ABOUT US</a></li>
                    <li><a href="faq.html">FAQ</a></li>
                    <li><a href="feedback.jsp">FEEDBACK</a></li>
                </ul>
            </div>
            <div class="links">
                <ul>
                    <li><a href="privacy.html">PRIVACY POLICY</a></li>
                    <li><a href="terms.jsp">TERMS & CONDITIONS</a></li>
                </ul>
                <div class="social-icons">
                    <a href="https://www.instagram.com" target="_blank" aria-label="Instagram">
                        <i class='bx bxl-instagram-alt'></i>
                    </a>
                    <a href="https://www.facebook.com" target="_blank" aria-label="Facebook">
                        <i class='bx bxl-facebook-circle'></i>
                    </a>
                    <a href="https://www.twitter.com" target="_blank" aria-label="Twitter">
                        <i class='bx bxl-twitter'></i>
                    </a>
                </div>
            </div>
            <p>Copyright © 2019 PVR Ltd. All Rights Reserved.</p>
        </div>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="toggle.js"></script>
</body>
</html>
