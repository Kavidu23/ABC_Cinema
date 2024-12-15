<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="login.css"> <!-- Link to your CSS file -->
</head>
<body>

    <div class="container">
        <p class="intro">Please enter your registered email to access your account.</p>
        
        <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <form class="login-form" action="verify-email" method="POST">
            <input type="email" name="email" placeholder="Email" required>
            <button type="submit">CONFIRM</button>
        </form>
        <div class="signup-link">
            <p>Don't have an account? <a href="signup.jsp">Sign up</a></p>
        </div>
    </div>

</body>
</html>
