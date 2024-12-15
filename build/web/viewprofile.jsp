<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%-- Prevent caching of the page --%>
<% 
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Profile</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="viewprofile.css">
</head>
<body>
    <div class="container">
        <h3>Welcome, <%= session.getAttribute("userName") != null ? session.getAttribute("userName") : "Guest" %>!</h3>
        <p>Email: <%= session.getAttribute("userEmail") != null ? session.getAttribute("userEmail") : "Not Available" %></p>

        <div class="actions">
            <form action="LogoutServlet" method="post">
                <button type="submit" class="button logout">Logout</button>
            </form>
        </div>
    </div>
</body>
</html>
