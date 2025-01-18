<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.paypal.api.payments.Payment" %>
<%@ page import="com.paypal.api.payments.Transaction" %>
<%@ page import="java.util.List" %>

<%
    String paymentId = request.getParameter("paymentId");
    String payerId = request.getParameter("PayerID");
    String userEmail = (String) request.getSession().getAttribute("userEmail");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Cancelled</title>
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
    <link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Instrument+Sans:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            padding-left: 10px;
            padding-right: 10px;
            font-family: Arial, sans-serif;
            background-color: var(--background-color);
            margin: 0;
            padding: 0;
        }
        
        :root {
             --primary-color: #1E1E1E;
             --secondary-color: #FFF;
             --background-color: #080808;
             --hover-color: #ff9900;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: var(--primary-color);
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-radius: 8px;
            text-align: center;
        }
        .title {
            font-size: 24px;
            color: var(--hover-color);
            margin-bottom: 20px;
        }
        .details {
            font-size: 18px;
            color: var(--secondary-color);
        }
        .details span {
            display: block;
            margin-bottom: 10px;
        }
        .cancel-icon {
            font-size: 48px;
            color: #f44336;
            margin-top: 20px;
            margin-bottom: 20px;
        }
        .retry-button {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: var(--hover-color);
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .retry-button:hover {
            background-color: #e68a00;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="cancel-icon">✘</div>
        <h1 class="title">Payment Cancelled</h1>
        <div class="details">
            <p>We're sorry, but your payment was cancelled. You can try again or contact support for assistance.</p>
            <span><strong>Payment ID:</strong> <%= paymentId %></span>
            <span><strong>Payer ID:</strong> <%= payerId %></span>
        </div>
        <form action="retryPayment.jsp" method="get">
            <button type="submit" class="retry-button">Try Again</button>
        </form>
        <form action="EmailServlet" method="post">
            <input type="hidden" name="userEmail" value="<%= userEmail %>">
            <input type="hidden" name="paymentId" value="<%= paymentId %>">
            <input type="hidden" name="payerId" value="<%= payerId %>">
            <button type="submit" class="retry-button">Contact Support</button>
        </form>
    </div>
</body>
</html>
