package Controller;

import DAO.AdminPaymentDAO;
import DAO.PaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/admin/payment-detail")
public class AdminPaymentDetailController extends HttpServlet {
    private AdminPaymentDAO paymentDAO;
    private PaymentDAO paymentDetailsDAO;

    @Override
    public void init() {
        paymentDAO = new AdminPaymentDAO();
        paymentDetailsDAO = new PaymentDAO();
        System.out.println("AdminPaymentDetailController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");

        if (courseId == null || studentId == null) {
            String encodedMessage = java.net.URLEncoder.encode("Lỗi: Dữ liệu không hợp lệ", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/payment-list?message=" + encodedMessage);
            return;
        }

        try {
            java.sql.Timestamp paymentDateTimestamp = paymentDAO.getPaymentDate(courseId, studentId);
            Date paymentDate = paymentDateTimestamp != null ? new Date(paymentDateTimestamp.getTime()) : null;

            String fileName = paymentDetailsDAO.getFileName(courseId, studentId);
            String filePath = paymentDetailsDAO.getFilePath(courseId, studentId);

            request.setAttribute("courseId", courseId);
            request.setAttribute("studentId", studentId);
            request.setAttribute("fileName", fileName);
            request.setAttribute("filePath", filePath);
            request.setAttribute("paymentDate", paymentDate);
            request.getRequestDispatcher("/admin/payment-detail.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error fetching payment detail: " + e.getMessage());
            e.printStackTrace();
            String encodedMessage = java.net.URLEncoder.encode("Lỗi khi xem chi tiết biên lai", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/admin/payment-list?message=" + encodedMessage);
        }
    }
}