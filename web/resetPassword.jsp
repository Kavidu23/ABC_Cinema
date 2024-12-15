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
        <h3>Reset Your Password</h3>
        <p class="intro">Please enter the credentials to reset your password.</p>
        
        <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <form class="login-form" action="reset-password" method="POST" onsubmit="return verify();">
            <input id="pass" type="password" name="newPassword" placeholder="New Password" required>
            <input id="passc" type="password" name="confirmPassword" placeholder="Confirm Password" required>
            <button type="submit">Reset Password</button>
        </form>
    </div>

</body>
</html>
