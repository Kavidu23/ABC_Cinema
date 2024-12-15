<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.SeatSelection" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet" %>
<%@ page import="util.DatabaseConnectionHelper" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seat Selection</title>
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="seat.css">
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
</head>
<body>
    <div class="container">
        <h3>Seat Selection</h3>
        <p class="subtitle">Choose your preferred seats for the movie</p>

        <div class="screen">Screen This Way</div>

        <form action="submitSeats" method="post" id="seatForm">
            <div class="seating">
                <%
                    // Retrieve the movieId and showtime from the request
                    String movieId = request.getParameter("movieId");
                    String showtime = request.getParameter("showtime");
                    List<SeatSelection> bookedSeats = new java.util.ArrayList<>();

                    // Establish database connection and retrieve booked seats
                    Connection connection = null;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {
                        connection = DatabaseConnectionHelper.getConnection();
                        String query = "SELECT seat_number FROM seat_selections WHERE now_showing_id = ? AND showtime = ?";
                        stmt = connection.prepareStatement(query);
                        stmt.setInt(1, Integer.parseInt(movieId)); // movieId
                        stmt.setString(2, showtime); // showtime
                        rs = stmt.executeQuery();

                        // Collect booked seats
                        while (rs.next()) {
                            String seat = rs.getString("seat_number");
                            SeatSelection seatSelection = new SeatSelection(Integer.parseInt(movieId), showtime, seat); // Use the constructor
                            bookedSeats.add(seatSelection);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (stmt != null) stmt.close();
                            if (connection != null) connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                %>

                <% 
                    // Render the seats dynamically
                    for (char row = 'A'; row <= 'F'; row++) { 
                %>
                    <div class="row"><span class="row-label"><%= row %></span>
                    <% 
                        for (int seatNumber = 1; seatNumber <= 5; seatNumber++) {
                            String seat = "" + row + seatNumber;
                            boolean isBooked = false;

                            // Check if the seat is booked
                            for (SeatSelection s : bookedSeats) {
                                if (s.getSeatNumber().equals(seat)) {
                                    isBooked = true;
                                    break;
                                }
                            }

                            // Set the seat class depending on whether it is booked or not
                            String seatClass = isBooked ? "seat unavailable" : "seat available";
                    %>
                        <div class="<%= seatClass %>" data-seat="<%= seat %>"></div>
                    <% } %>
                    </div>
                <% } %>
            </div>

            <div class="other">
                <!-- Hidden inputs to pass along the details -->
                <input type="hidden" name="movieId" value="<%= request.getParameter("movieId") %>">
                <input type="hidden" name="location" value="<%= request.getParameter("location") %>">
                <input type="hidden" name="availableDate" value="<%= request.getParameter("availableDate") %>">
                <input type="hidden" name="price" value="<%= request.getParameter("price") %>">
                <input type="hidden" name="language" value="<%= request.getParameter("language") %>">
                <input type="hidden" name="format" value="<%= request.getParameter("format") %>">
                <input type="hidden" name="showtime" value="<%= request.getParameter("showtime") %>">
                <input type="hidden" name="type" value="<%= request.getParameter("type") %>">
                <div id="selectedSeatsContainer"></div>
            </div>

            <div class="actions">
                <button id="proceed-btn" type="submit" disabled>Proceed</button>
            </div>
        </form>
    </div>

    <!-- Display the details -->
    <div class="other">
        <h4>Booking Details:</h4>
        <p>Location: <%= request.getParameter("location") %></p>
        <p>Available Date: <%= request.getParameter("availableDate") %></p>
        <p>Price: <%= request.getParameter("price") %> LKR</p>
        <p>Language: <%= request.getParameter("language") %></p>
        <p>Format: <%= request.getParameter("format") %></p>
        <p>Showtime: <%= request.getParameter("showtime") %></p>
    </div>

    <!-- Display error message if it exists -->
    <%
        String errorMessage = request.getParameter("errorMessage");
        if (errorMessage != null) {
    %>
        <script>
            alert("<%= errorMessage %>");
        </script>
    <%
        }
    %>

    <script>
        // Handle seat selection
        const seats = document.querySelectorAll(".seat.available");
        const proceedButton = document.getElementById("proceed-btn");
        const selectedSeatsContainer = document.getElementById("selectedSeatsContainer");

        seats.forEach(seat => {
            seat.addEventListener("click", () => {
                seat.classList.toggle("selected");

                // Update hidden inputs with selected seats
                const selectedSeats = document.querySelectorAll(".seat.selected");
                selectedSeatsContainer.innerHTML = "";
                selectedSeats.forEach(seat => {
                    const seatInput = document.createElement("input");
                    seatInput.type = "hidden";
                    seatInput.name = "selectedSeats";
                    seatInput.value = seat.getAttribute("data-seat");
                    selectedSeatsContainer.appendChild(seatInput);
                });

                // Enable "Proceed" button if at least one seat is selected
                proceedButton.disabled = selectedSeats.length === 0;
            });
        });
    </script>
</body>
</html>
