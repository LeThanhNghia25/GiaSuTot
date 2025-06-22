package Controller;

import DAO.AdminPaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
            List<model.Payment> payments = paymentDAO.getPendingPayments();
            List<model.Payment> convertedPayments = new ArrayList<>();
            for (model.Payment payment : payments) {
                model.Payment convertedPayment = new model.Payment();
                convertedPayment.setId(payment.getId());
                convertedPayment.setCourseId(payment.getCourseId());
                convertedPayment.setTutorId(payment.getTutorId());
                convertedPayment.setStudentId(payment.getStudentId());
                convertedPayment.setAmount(payment.getAmount());
                convertedPayment.setPaymentDate(payment.getPaymentDate()); // Gán trực tiếp Date
                convertedPayment.setFileName(payment.getFileName());
                convertedPayment.setFilePath(payment.getFilePath());
                convertedPayments.add(convertedPayment);
            }
            request.setAttribute("pendingPayments", convertedPayments);
            request.getRequestDispatcher("/admin/payment-confirmation.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error fetching pending payments: " + e.getMessage());
            e.printStackTrace();
            String encodedMessage = URLEncoder.encode("Lỗi khi tải danh sách biên lai", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/index.jsp?message=" + encodedMessage);
        }
    }
}