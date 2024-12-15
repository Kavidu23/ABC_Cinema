<%@page import="model.NowShowing"%>
<%@page import="java.time.temporal.ChronoUnit"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="service.NowShowingService"%>
<%@page import="util.DatabaseConnectionHelper"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking Confirmation</title>
    <link rel="stylesheet" href="confirm.css">
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
    <!-- External CSS Libraries -->
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
    <% 
    // Retrieve the user name from the session
    String userName = (String) session.getAttribute("userName");
    String userEmail = (String) session.getAttribute("userEmail");

    // Retrieve details from session or request
    String movieIdStr = (String) session.getAttribute("movieId");
    if (movieIdStr == null) {
        movieIdStr = request.getParameter("movieId");
    }

    String showtime = (String) session.getAttribute("showtime");
    if (showtime == null) {
        showtime = request.getParameter("showtime");
    }

    String selectedSeats = (String) session.getAttribute("selectedSeats");
    if (selectedSeats == null) {
        selectedSeats = request.getParameter("selectedSeats");
    }

    String location = (String) session.getAttribute("location");
    if (location == null) {
        location = request.getParameter("location");
    }

    String availableDate = (String) session.getAttribute("availableDate");
    if (availableDate == null) {
        availableDate = request.getParameter("availableDate");
    }

    String language = (String) session.getAttribute("language");
    if (language == null) {
        language = request.getParameter("language");
    }

    String format = (String) session.getAttribute("format");
    if (format == null) {
        format = request.getParameter("format");
    }

    // Handle unauthenticated users
    if (userName == null) {
        session.setAttribute("movieId", movieIdStr);
        session.setAttribute("showtime", showtime);
        session.setAttribute("selectedSeats", selectedSeats);
        session.setAttribute("location", location);
        session.setAttribute("availableDate", availableDate);
        session.setAttribute("language", language);
        session.setAttribute("format", format);

        session.setMaxInactiveInterval(5 * 60);
        response.sendRedirect("loginc.jsp?redirect=confirm.jsp&movieId=" + movieIdStr + "&showtime=" + showtime + "&selectedSeats=" + selectedSeats + "&location=" + location + "&availableDate=" + availableDate + "&language=" + language + "&format=" + format);
        return;
    }

    // Proceed if user is logged in
    int movieId = Integer.parseInt(movieIdStr);
    NowShowingService nowshowingService = new NowShowingService(DatabaseConnectionHelper.getConnection());
    NowShowing nowshowing = nowshowingService.getNowShowingById(movieId);
    %>

    <div class="container">
        <h3>Booking Confirmation</h3>
        <p class="subtitle">Please review your booking details below</p>

        <div class="details-container">
            <div class="movie-image">
                <img src="Resources/<%= nowshowing.getMovieImage() %>" alt="Movie Image" height="230px">
            </div>
            <div class="booking-details">
                <div class="detail">
                    <span class="label">Movie Name:</span>
                    <span class="value"><%= nowshowing.getMovieName() %></span>
                </div>
                <div class="detail">
                    <span class="label">Showtime:</span>
                    <span class="value"><%= URLDecoder.decode(showtime, "UTF-8") %></span>
                </div>
                <div class="detail">
                    <span class="label">Selected Seats:</span>
                    <span class="value"><%= URLDecoder.decode(selectedSeats, "UTF-8") %></span>
                </div>
                <div class="detail">
                    <span class="label">Location:</span>
                    <span class="value"><%= URLDecoder.decode(location, "UTF-8") %></span>
                </div>
                <div class="detail">
                    <span class="label">Available Date (Onward):</span>
                    <span class="value"><%= availableDate %></span>
                </div>
                <div class="detail">
                    <span class="label">Price per Adult Ticket:</span>
                    <span class="value">3000 LKR</span>
                </div>
                <div class="detail">
                    <span class="label">Price per Child Ticket:</span>
                    <span class="value">2000 LKR</span>
                </div>
                <div class="detail">
                    <span class="label">Language:</span>
                    <span class="value"><%= language %></span>
                </div>
                <div class="detail">
                    <span class="label">Format:</span>
                    <span class="value"><%= format %></span>
                </div>
            </div>
        </div>

        <div class="ticket-selection">
            <h4>Select Ticket Type for Each Seat</h4>
            <form id="ticketForm" method="post" action="PaymentHandleServlet">
                <% 
                String[] seatArray = URLDecoder.decode(selectedSeats, "UTF-8").split(",");
                for (String seat : seatArray) {
                %>
                <div class="ticket-detail">
                    <label for="ticketType_<%= seat %>">Seat <%= seat %>:</label>
                    <select required id="ticketType_<%= seat %>" name="ticketType_<%= seat %>">
                        <option value="adult">Adult - 3000 LKR</option>
                        <option value="child">Child - 2000 LKR</option>
                    </select>
                </div>
                <% } %>
                <br>
                <label>Select Date : </label>
                <%
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate availableDateParsed = LocalDate.parse(availableDate, formatter);
                LocalDate maxDate = availableDateParsed.plusDays(20);
                %>

                <input required type="date" id="currentMonth" name="selectedDate" min="<%= availableDate %>" max="<%= maxDate.toString() %>">
                <div class="actions-container">
                    <p class="subtitle">PROCEED TO PAYMENT</p>
                    <button type="submit" onclick="calculateTotal()">PAY WITH PayPal <i class='bx bxl-paypal'></i></button>
                </div>
                <!-- Hidden fields to pass details -->
                <input type="hidden" name="movieId" value="<%= movieIdStr %>">
                <input type="hidden" name="showtime" value="<%= showtime %>">
                <input type="hidden" name="selectedSeats" value="<%= selectedSeats %>">
                <input type="hidden" name="location" value="<%= location %>">
                <input type="hidden" name="availableDate" value="<%= availableDate %>">
                <input type="hidden" name="language" value="<%= language %>">
                <input type="hidden" name="format" value="<%= format %>">
            </form>
            
            <form id="cancel" action="CancelBookingServlet" method="get">
                <input type="hidden" name="seat" value="<%= selectedSeats %>">
                <button type="submit" onclick="cancelSessionAndRedirect()">CANCEL</button>
            </form> 
             
        </div>
    </div>

    <script src="confirm.js"></script>
    <script type="text/javascript">
    function cancelSessionAndRedirect() {
    // Invalidate session by making an AJAX call to a server-side endpoint to handle session cancellation
    fetch('/cancelSession', { method: 'POST' })
      .then(response => {
        if (response.ok) {
          // Redirect to seat selection page after session is canceled
          window.location.href = 'dashboard.jsp'; // Or any other page you want to redirect to
        } else {
          console.error('Session cancellation failed');
        }
      })
      .catch(error => {
        console.error('Error canceling session:', error);
      });
    }
    </script>

</body>
</html>