package controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import util.PayPalConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "PaymentHandleServlet", urlPatterns = {"/PaymentHandleServlet"})
public class PaymentHandleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve payment details from request
        String totalPrice = request.getParameter("totalPrice");
        String seat = request.getParameter("selectedSeats");
        String date = request.getParameter("availableDate");
        String location = request.getParameter("location");

        // Set payment amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(totalPrice);

        // Set transaction details
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        // Create a detailed description
        String description =
                "The Seat Number is: " + seat + "<br>" +
                "The Time is: 9.00PM. " + "<br>" +
                "The Booked Date is: " + date + "<br>" +
                "The Location is: " + location + " Complex.";

        transaction.setDescription(description);

        // Add transaction to list
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/ABC-Cinema/cancel.jsp");
        redirectUrls.setReturnUrl("http://localhost:8080/ABC-Cinema/reviewPayment");

        // Create payment
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        // Create APIContext
        APIContext apiContext;
        try {
            apiContext = PayPalConfig.getAPIContext();
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }

        try {
            Payment createdPayment = payment.create(apiContext);
            for (Links link : createdPayment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    response.sendRedirect(link.getHref());
                    return;
                }
            }
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        APIContext apiContext;
        try {
            apiContext = PayPalConfig.getAPIContext();
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        try {
            Payment executedPayment = payment.execute(apiContext, paymentExecution);
            // Handle successful payment here
            HttpSession session = request.getSession();
            String userEmail = (String) session.getAttribute("userEmail"); // Retrieve email from session
            String emailSubject = "Booking Confirmation - ABC Cinema";
            String emailBody = "Thank you for your payment. Your booking has been confirmed." + "<br>" +
                    "Payment ID: " + payment.getId() + "<br>" +
                    "Payer ID: " + request.getParameter("PayerID") + "<br>" +
                    "Movie: " + payment.getTransactions().get(0).getDescription() + "<br>" +
                    "Amount: " + payment.getTransactions().get(0).getAmount().getTotal() + " USD";

            sendEmail(userEmail, emailSubject, emailBody);

            response.sendRedirect("success.jsp");
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }
    }

    private void sendEmail(String to, String subject, String body) {
        final String username = "hasithakashmika@gmail.com"; // Replace with your Gmail address
        final String password = "opka-psxo-tmgq-itcf";   // Replace with your App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        props.put("mail.smtp.port", "587"); // Port 587 for TLS
        props.put("mail.debug", "true"); // Debugging enabled

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Sender email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(body, "text/html"); // Send HTML content

            // Debug log
            System.out.println("Attempting to send email...");
            System.out.println("Recipient: " + to);
            System.out.println("Subject: " + subject);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully to: " + to);

        } catch (MessagingException e) {
            System.err.println("Error occurred while sending email:");
            e.printStackTrace();
            System.err.println("Troubleshooting tips:");
            System.err.println("1. Verify App Password is correct.");
            System.err.println("2. Check if 'to' email address is valid.");
            System.err.println("3. Ensure Gmail SMTP settings are not restricted.");
            System.err.println("4. Check your network/firewall for SMTP port access.");
        }
    }
}
