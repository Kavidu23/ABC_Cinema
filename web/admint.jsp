<%@ page import="model.Showtime" %>
<%@ page import="service.ShowtimeService" %>
<%@ page import="util.DatabaseConnectionHelper" %>
<%@ page import="model.NowShowing" %>
<%@ page import="service.NowShowingService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Timestamp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Showtime Management</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="admin.css">
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
        <h1>Add New Showtime</h1>
        <navbar class="navbar">
            <a href="admin.jsp"><h3>Movie Management</h3></a>
            <a href="admint.jsp"><h3>Time Management</h3></a>
            <a href="adminComplexShowtime.jsp"><h3>Complex & Show</h3></a>
            <a href="adminfeed.jsp"><h3>Feedback</h3></a>
        </navbar>
        <h2>Time Management</h2>
        <form action="AdminShowtimeServlet?action=add" method="post">
            <label for="movieId">Select Movie:</label>
            <select id="movieId" name="movieId" required>
                <option value="" disabled selected>Select a movie</option>
                <%
                    Connection connection = null;
                    try {
                        connection = DatabaseConnectionHelper.getConnection();
                        NowShowingService nowShowingService = new NowShowingService(connection);
                        List<NowShowing> nowShowingList = nowShowingService.getAllNowShowing();

                        if (nowShowingList != null) {
                            for (NowShowing movie : nowShowingList) {
                %>
                <option value="<%= movie.getId() %>"><%= movie.getMovieName() %></option>
                <%
                            }
                        } else {
                %>
                <option value="" disabled>No movies available</option>
                <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                %>
            </select>
            
            <label for="duration">Duration (minutes):</label>
            <input type="number" id="duration" name="duration" required>

            <label for="format">Format:</label>
            <select id="format" name="format" required>
                <option value="2D">2D</option>
                <option value="3D">3D</option>
            </select>

            <label for="showtime">Showtime:</label>
            <input type="datetime-local" id="showtime" name="showtime" required>

            <label for="language">Language:</label>
            <input type="text" id="language" name="language" required>

            <button type="submit" class="btn">Add Showtime</button>
        </form>

        <h3>Current Showtimes</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Movie ID</th>
                    <th>Duration</th>
                    <th>Format</th>
                    <th>Showtime</th>
                    <th>Language</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    connection = null;
                    try {
                        connection = DatabaseConnectionHelper.getConnection();
                        ShowtimeService showtimeService = new ShowtimeService(connection);
                        List<Showtime> showtimeList = showtimeService.getAllShowtimes();

                        if (showtimeList != null && !showtimeList.isEmpty()) {
                            for (Showtime showtime : showtimeList) {
                %>
                <tr>
                    <td><%= showtime.getId() %></td>
                    <td><%= showtime.getMovieId() %></td>
                    <td>
                        <input type="number" name="duration" form="editForm<%= showtime.getId() %>" value="<%= showtime.getDuration() %>">
                    </td>
                    <td>
                        <select name="format" form="editForm<%= showtime.getId() %>">
                            <option value="2D" <%= "2D".equals(showtime.getFormat()) ? "selected" : "" %>>2D</option>
                            <option value="3D" <%= "3D".equals(showtime.getFormat()) ? "selected" : "" %>>3D</option>
                        </select>
                    </td>
                    <td>
                        <input type="datetime-local" name="showtime" form="editForm<%= showtime.getId() %>" value="<%= showtime.getShowtime().toString().replace(' ', 'T') %>">
                    </td>
                    <td>
                        <input type="text" name="language" form="editForm<%= showtime.getId() %>" value="<%= showtime.getLanguage() %>">
                    </td>
                    <td>
                        <form id="editForm<%= showtime.getId() %>" action="AdminShowtimeServlet?action=update" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= showtime.getId() %>">
                            <input type="hidden" name="movieId" value="<%= showtime.getMovieId() %>">
                            <button type="submit" class="btn">Update</button>
                        </form>
                        <form action="AdminShowtimeServlet?action=delete" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= showtime.getId() %>">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                            }
                        } else {
                %>
                <tr>
                    <td colspan="7">No showtimes available.</td>
                </tr>
                <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
