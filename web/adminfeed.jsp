<%@ page import="model.Feedback" %>
<%@ page import="service.FeedbackService" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Feedback Management</title>
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
        <h1>View Feedback</h1>
        <navbar class="navbar">
            <a href="admin.jsp"><h3>Movie Management</h3></a>
            <a href="admint.jsp"><h3>Time Management</h3></a>
            <a href="adminComplexShowtime.jsp"><h3>Complex & Show</h3></a>
            <a href="viewFeedback.jsp"><h3>Feedback</h3></a>
        </navbar>

        <h2>Feedback List</h2>

        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Feedback</th>
                    <th>Email</th>
                    <th>Rating</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Get feedback from the database
                    FeedbackService feedbackService = new FeedbackService();
                    List<Feedback> feedbackList = feedbackService.getAllFeedback();

                    if (feedbackList != null && !feedbackList.isEmpty()) {
                        for (Feedback feedback : feedbackList) {
                %>
                <tr>
                    <td><%= feedback.getName() %></td>
                    <td><%= feedback.getFeedback() %></td>
                    <td><%= feedback.getEmail() %></td>
                    <td><%= feedback.getRating() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4">No feedback available.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
