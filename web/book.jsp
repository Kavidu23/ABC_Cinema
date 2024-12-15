<%@ page import="model.NowShowing, model.ShowtimeDetail, java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book - ABC CINEMA</title>
    <!-- External CSS Libraries -->
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700&display=swap" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
    <link rel="stylesheet" href="book.css">
</head>
<body>
    <!-- Header -->
    <header>
        <img src="Resources/logo.png" alt="ABC Cinema Logo" height="50" width="100">
        <i id="menu-icon" class="bx bx-menu"></i>
    </header>

    <!-- Side Navigation Menu -->
    <div id="side-menu">
        <a href="javascript:void(0)" class="close-btn" onclick="document.getElementById('side-menu').style.width = '0'">×</a>
        <i class='bx bxs-user-circle'></i>
        <% 
        // Retrieve the user name from the session
        String userName = (String) session.getAttribute("userName");
        
        if (userName != null) { 
        %>
            <!-- If logged in, show the username and View Profile -->
            <span id="name"><%= userName %></span>
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

    <!-- Main Content -->
    <div class="mainimage">
        <% 
        NowShowing movie = (NowShowing) request.getAttribute("movieDetails");

        if (movie != null) {
        %>  
            <h1><%= movie.getMovieName() %></h1>
            <img src="Resources/<%= movie.getMovieImage() %>" alt="<%= movie.getMovieName() %>" height="370px" width="900px">
        <% 
        } else { 
        %>
            <h3 style="color: red; text-align: center;">Movie data not available!</h3>
        <% 
        } 
        %>
    </div>

    <hr>

    <!-- Showtime Details Section -->
    <section class="form1">
        <%
        List<ShowtimeDetail> showtimeDetails = (List<ShowtimeDetail>) request.getAttribute("showtimeDetails");
        
        if (showtimeDetails != null && !showtimeDetails.isEmpty()) {
            for (ShowtimeDetail showtimeDetail : showtimeDetails) {
        %>
                <p>LOCATION : <%= showtimeDetail.getComplexName() %><br>AVAILABLE DATE (UPWORD) : <%= showtimeDetail.getAvailableDate() %></p>
                <p>PRICE: <%= showtimeDetail.getPrice() %> LKR FOR ADULT AND 2000.0 LKR FOR CHILD<BR>LANGUAGE: ENGLISH<BR>FORMAT: 3D</p>
                <form action="submitBooking" method="post">
                    <input type="hidden" name="location" value="<%= showtimeDetail.getComplexName() %>">
                    <input type="hidden" name="availableDate" value="<%= showtimeDetail.getAvailableDate() %>">
                    <input type="hidden" name="price" value="<%= showtimeDetail.getPrice() %>">
                    <input type="hidden" name="language" value="ENGLISH">
                    <input type="hidden" name="format" value="3D">
                    <input type="hidden" name="type" value="<%= request.getParameter("type") %>">
                    <input type="hidden" name="movieId" value="<%= request.getParameter("movieId") %>">
                    <div class="next">
                    <button type="submit" class="btn btn-secondary" name="showtime" value="9:00 PM">9:00 PM</button>
                    </div>
                </form>
        <% 
            }
        } else { 
        %>
            <p>No showtimes available for the selected criteria.</p>
        <% 
        } 
        %>
    </section>
    
    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="toggle.js"></script>
    <script src="book.js"></script>
</body>
</html>
