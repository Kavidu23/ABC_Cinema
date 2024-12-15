<%@ page import="model.ShowtimeDetail" %>
<%@ page import="service.ShowtimeDetailService" %>
<%@ page import="model.Complex" %>
<%@ page import="service.ComplexService" %>
<%@ page import="model.Showtime" %>
<%@ page import="service.ShowtimeService" %>
<%@ page import="util.DatabaseConnectionHelper" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Showtime Detail Management</title>
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
        <h1>Add New Complex & Details</h1>
        <navbar class="navbar">
            <a href="admin.jsp"><h3>Movie Management</h3></a>
            <a href="admint.jsp"><h3>Time Management</h3></a>
            <a href="adminComplexShowtime.jsp"><h3>Complex & Show</h3></a>
            <a href="adminfeed.jsp"><h3>Feedback</h3></a>
        </navbar>
        <h2>Complex & Details Management</h2>
        <form action="ShowtimeDetailServlet?action=add" method="post">
            <label for="showtimeId">Select Showtime:</label>
            <select id="showtimeId" name="showtimeId" required>
                <option value="" disabled selected>Select a showtime</option>
                <%
                    Connection connection = null;
                    try {
                        connection = DatabaseConnectionHelper.getConnection();
                        ShowtimeService showtimeService = new ShowtimeService(connection);
                        List<Showtime> showtimeList = showtimeService.getAllShowtimes();

                        if (showtimeList != null) {
                            for (Showtime showtime : showtimeList) {
                %>
                <option value="<%= showtime.getId() %>">Showtime ID: <%= showtime.getId() %></option>
                <%
                            }
                        } else {
                %>
                <option value="" disabled>No showtimes available</option>
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

            <label for="complexId">Select Complex:</label>
            <select id="complexId" name="complexId" required>
                <option value="" disabled selected>Select a complex</option>
                <%
                    try {
                        connection = DatabaseConnectionHelper.getConnection();
                        ComplexService complexService = new ComplexService(connection);
                        List<Complex> complexList = complexService.getAllComplexes();

                        if (complexList != null) {
                            for (Complex complex : complexList) {
                %>
                <option value="<%= complex.getComplexId() %>"><%= complex.getName() %> - <%= complex.getLocation() %></option>
                <%
                            }
                        } else {
                %>
                <option value="" disabled>No complexes available</option>
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

            <label for="availableDate">Available Date:</label>
            <input type="date" id="availableDate" name="availableDate" required>

            <label for="price">Price:</label>
            <input type="number" step="0.01" id="price" name="price" required>

            <button type="submit" class="btn">Add Showtime Detail</button>
        </form>

        <h3>Current Showtime Details</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>Detail ID</th>
                    <th>Showtime ID</th>
                    <th>Complex</th>
                    <th>Available Date</th>
                    <th>Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    try {
                        connection = DatabaseConnectionHelper.getConnection();
                        ShowtimeDetailService showtimeDetailService = new ShowtimeDetailService(connection);
                        List<ShowtimeDetail> showtimeDetailList = showtimeDetailService.getAllShowtimeDetails();

                        if (showtimeDetailList != null && !showtimeDetailList.isEmpty()) {
                            for (ShowtimeDetail showtimeDetail : showtimeDetailList) {
                %>
                <tr>
                    <form id="updateForm<%= showtimeDetail.getDetailId() %>" action="ShowtimeDetailServlet?action=update" method="post">
                        <td><%= showtimeDetail.getDetailId() %></td>
                        <td><%= showtimeDetail.getShowtimeId() %></td>
                        <td>
                            <select name="complexId" required>
                                <option value="" disabled>Select a complex</option>
                                <%
                                    connection = DatabaseConnectionHelper.getConnection();
                                    ComplexService complexService = new ComplexService(connection);
                                    List<Complex> complexList = complexService.getAllComplexes();

                                    if (complexList != null) {
                                        for (Complex complex : complexList) {
                                %>
                                <option value="<%= complex.getComplexId() %>" <%= complex.getComplexId() == showtimeDetail.getComplexId() ? "selected" : "" %>><%= complex.getName() %> - <%= complex.getLocation() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </td>
                        <td>
                            <input type="date" name="availableDate" value="<%= showtimeDetail.getAvailableDate() %>" required>
                        </td>
                        <td>
                            <input type="number" step="0.01" name="price" value="<%= showtimeDetail.getPrice() %>" required>
                        </td>
                        <td>
                            <input type="hidden" name="detailId" value="<%= showtimeDetail.getDetailId() %>">
                            <button type="submit" class="btn">Update</button>
                        </form>
                        <form action="ShowtimeDetailServlet?action=delete" method="post" style="display:inline;">
                            <input type="hidden" name="detailId" value="<%= showtimeDetail.getDetailId() %>">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                            }
                        } else {
                %>
                <tr>
                    <td colspan="6">No showtime details available.</td>
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
