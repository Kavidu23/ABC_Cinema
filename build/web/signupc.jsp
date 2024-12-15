<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <!-- External CSS -->
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="signup.css">
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
    <script>
        function validatePasswords() {
            // Get password and confirm password values
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            
            // Get the feedback message element
            var feedback = document.getElementById("passwordFeedback");

            // Check if the passwords match
            if (password !== confirmPassword) {
                feedback.textContent = "Passwords do not match!";
                feedback.style.color = "red";
                return false; // Prevent form submission
            } else {
                feedback.textContent = "Passwords match!";
                feedback.style.color = "green";
                return true; // Allow form submission
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h3>Create an Account</h3>
        <p class="intro">Join ABC Cinema today and enjoy the best movie experience!</p>

        <!-- Display error message if email is already registered -->
        <% 
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <p style="color: red; font-weight: bold;"><%= errorMessage %></p>
        <% 
            } 
        %>

        <form action="SignUpController" method="POST" class="signup-form" onsubmit="return validatePasswords()">
            <label for="name">User Name:</label>
            <input type="text" id="name" name="name" placeholder="Enter your full name" required>

            <label for="email">Email Address:</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>

            <label for="pnumber">Phone Number:</label>
            <input type="tel" id="pnumber" name="pnumber" placeholder="Enter your phone number" pattern="[0-9]{10}" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter a secure password" required>
            
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Re-enter your password" required>

            <p id="passwordFeedback"></p> <!-- Feedback message for password mismatch -->

            <button type="submit">Sign Up</button>
        </form>
    </div>
</body>
</html>
