<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.paypal.api.payments.Payment" %>
<%@ page import="com.paypal.api.payments.Transaction" %>
<%@ page import="java.util.List" %>

<%
    Payment payment = (Payment) request.getAttribute("payment");
    Transaction transaction = payment.getTransactions().get(0);
    String paymentId = payment.getId();
    String payerId = request.getParameter("PayerID");
    String description = transaction.getDescription();
    String amount = transaction.getAmount().getTotal();
    String userEmail = (String) request.getSession().getAttribute("userEmail");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Success</title>
    <link rel="icon" type="image/x-icon" href="Resources/favicon.ico">
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
        .success-icon {
            font-size: 48px;
            color: #4caf50;
            margin-top: 20px;
            margin-bottom: 20px;
        }
        .email-button {
            margin-top: 20px;
            padding: 10px 20px;
            font-size: 16px;
            color: #fff;
            background-color: var(--hover-color);
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .email-button:hover {
            background-color: #e68a00;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="success-icon">✔</div>
        <h1 class="title">Payment Successful!</h1>
        <div class="details">
            <p>Thank you for your payment. Your booking has been confirmed. Kindly arrive at the cinema 15 minutes ahead of the movie's commencement.</p>
            <span><strong>Payment ID:</strong> <%= paymentId %></span>
            <span><strong>Payer ID:</strong> <%= payerId %></span>
            <span><%= description %></span>
            <span><strong>Amount:</strong> <%= amount %> USD</span>
        </div>
        <form action="EmailServlet" method="post">
            <input type="hidden" name="userEmail" value="<%= userEmail %>">
            <input type="hidden" name="paymentId" value="<%= paymentId %>">
            <input type="hidden" name="payerId" value="<%= payerId %>">
            <input type="hidden" name="description" value="<%= description %>">
            <input type="hidden" name="amount" value="<%= amount %>">
            <button type="submit" class="email-button">GET THE CONFIRMATION EMAIL</button>
        </form>
    </div>
</body>
</html>
