<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback</title>
    <!-- External CSS -->
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="feedback.css">
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
</head>
<body>
    <div class="container">
        <h3>We Value Your Feedback</h3>
        <p class="intro">Your opinions help us improve! Please share your thoughts and experiences with ABC Cinema.</p>
        
        <form action="submitFeedback" method="POST" class="feedback-form">
            <label for="name">Your Name:</label>
            <input type="text" id="name" name="name" placeholder="Enter your name" required>

            <label for="email">Your Email:</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>

            <label for="feedback">Your Feedback:</label>
            <textarea id="feedback" name="feedback" rows="5" placeholder="Write your feedback here..." required></textarea>

            <label for="rating">Rate Your Experience:</label>
            <select id="rating" name="rating" required>
                <option value="" disabled selected>Select a rating</option>
                <option value="5">Excellent</option>
                <option value="4">Good</option>
                <option value="3">Average</option>
                <option value="2">Below Average</option>
                <option value="1">Poor</option>
            </select>

            <button type="submit">SUBMIT FEEDBACK</button>
        </form>
    </div>
    
</body>
</html>
