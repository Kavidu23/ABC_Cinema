package controller;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import util.PayPalConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReviewPaymentServlet", urlPatterns = {"/reviewPayment"})
public class ReviewPaymentServlet extends HttpServlet {

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
            request.setAttribute("payment", executedPayment);
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } catch (PayPalRESTException e) {
            throw new ServletException(e);
        }
    }
}
