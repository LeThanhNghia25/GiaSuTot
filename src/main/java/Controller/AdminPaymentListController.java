package Controller;

import DAO.AdminPaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/payment-list")
public class AdminPaymentListController extends HttpServlet {
    private AdminPaymentDAO paymentDAO;

    @Override
    public void init() {
        paymentDAO = new AdminPaymentDAO();
        System.out.println("AdminPaymentListController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy danh sách biên lai chờ xác nhận
            request.setAttribute("pendingPayments", paymentDAO.getPendingPayments());
            request.getRequestDispatcher("/admin/payment-confirmation.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error fetching pending payments: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/index.jsp?message=Lỗi khi tải danh sách biên lai");
        }
    }
}