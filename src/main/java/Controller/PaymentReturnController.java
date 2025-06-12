package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/payment-return")
public class PaymentReturnController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultCode = request.getParameter("resultCode");
        String message = request.getParameter("message");
        String orderId = request.getParameter("orderId");

        if ("0".equals(resultCode)) {
            request.setAttribute("message", "Thanh toán thành công cho đơn hàng " + orderId);
        } else {
            request.setAttribute("message", "Thanh toán thất bại: " + message);
        }
        request.getRequestDispatcher("/courses.jsp").forward(request, response);
    }
}