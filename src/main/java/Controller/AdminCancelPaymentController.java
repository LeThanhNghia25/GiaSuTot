package Controller;

import DAO.AdminPaymentDAO;
import Utils.EmailSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/cancel-payment")
public class AdminCancelPaymentController extends HttpServlet {
    private AdminPaymentDAO paymentDAO;

    @Override
    public void init() {
        paymentDAO = new AdminPaymentDAO();
        System.out.println("CancelPaymentController initialized.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");

        try {
            // Lấy email học sinh
            String studentEmail = paymentDAO.getStudentEmail(studentId);
            if (studentEmail == null) {
                throw new Exception("Không tìm thấy email của học sinh.");
            }

            // Cập nhật trạng thái về 'cancelled'
            paymentDAO.updatePaymentStatus(courseId, studentId, "cancelled");

            // Gửi email thông báo
            String subject = "Hủy xác nhận thanh toán khóa học " + courseId;
            String body = "Xin chào,\n\nHệ thống đã hủy xác nhận thanh toán cho khóa học " + courseId + ".\n" +
                    "Hiện tại, chúng tôi chưa nhận được tiền thanh toán từ bạn.\n" +
                    "Vui lòng kiểm tra và gửi lại biên lai nếu cần.\n\nTrân trọng,\nĐội ngũ Gia Sư Tốt";
            EmailSender.sendTextEmail(studentEmail, subject, body);

            // Chuẩn bị phản hồi JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Đã hủy xác nhận thành công.\"}");
        } catch (Exception e) {
            System.err.println("Error cancelling payment: " + e.getMessage());
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Lỗi khi hủy xác nhận: " + e.getMessage() + "\"}");
        }
    }
}