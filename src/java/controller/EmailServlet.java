package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(name = "EmailServlet", urlPatterns = {"/EmailServlet"})
public class EmailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("payerId");
        String description = request.getParameter("description");
        String amount = request.getParameter("amount");

        String subject = "Booking Confirmation - ABC Cinema";
        String body = "Thank you for your payment. Your booking has been confirmed." +
                      "<br><strong>Payment ID:</strong> " + paymentId +
                      "<br><strong>Payer ID:</strong> " + payerId +
                      "<br><strong>Details:</strong> " + description +
                      "<br><strong>Amount:</strong> " + amount + " USD";

        final String username = "hasithakashmika@gmail.com"; // Replace with your Gmail address
        final String password = "kadp olpl ffaw etjz";   // Replace with your App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject(subject);
            message.setContent(body, "text/html");

            Transport.send(message);
            response.sendRedirect("dashboard.jsp");
        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().write("Failed to send email: " + e.getMessage());
        }
    }
}
