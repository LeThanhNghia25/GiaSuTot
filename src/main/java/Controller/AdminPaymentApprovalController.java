package Controller;

import DAO.AdminPaymentDAO;
import Utils.EmailSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/admin/approve-payment")
public class AdminPaymentApprovalController extends HttpServlet {
    private AdminPaymentDAO paymentDAO;

    @Override
    public void init() {
        paymentDAO = new AdminPaymentDAO();
        System.out.println("AdminPaymentApprovalController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("filePath");

        try {
            // Lấy thông tin email của học sinh và giáo viên
            String studentEmail = paymentDAO.getStudentEmail(studentId);
            String tutorEmail = paymentDAO.getTutorEmailByCourseId(courseId);

            if (studentEmail == null || tutorEmail == null) {
                throw new Exception("Không tìm thấy email của học sinh hoặc giáo viên.");
            }

            // Cập nhật trạng thái từ pending_payment thành pending_approval
            paymentDAO.updatePaymentStatus(courseId, studentId, "pending_approval");

            // Gửi email cho học sinh
            String studentSubject = "Xác nhận Biên lai Khóa học " + courseId;
            String studentBody = "Xin chào,\n\nBiên lai của bạn cho khóa học " + courseId + " đã được admin xác nhận.\n" +
                    "\n\n" +
                    "Vui lòng chờ gia sư xác nhận đăng ký.\nTrân trọng,\nĐội ngũ Gia Sư Tốt";
            EmailSender.sendTextEmail(studentEmail, studentSubject, studentBody);

            // Gửi email cho giáo viên
            String tutorSubject = "Thông báo Yêu cầu Xác nhận Khóa học " + courseId;
            String tutorBody = "Xin chào,\n\nCó học sinh " + studentId + " đã đăng ký khóa học " + courseId + ".\n" +
                    "\n\n" +
                    "Vui lòng phản hồi trên hệ thống để chấp nhận hoặc từ chối.\nTrân trọng,\nĐội ngũ Gia Sư Tốt";
            EmailSender.sendTextEmail(tutorEmail, tutorSubject, tutorBody);

            // Mã hóa thông báo trước khi redirect
            String message = "Đã xác nhận biên lai cho khóa học " + courseId;
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/admin/payment-list?message=" + encodedMessage);
        } catch (Exception e) {
            System.err.println("Error approving payment: " + e.getMessage());
            e.printStackTrace();
            String errorMessage = "Lỗi khi xác nhận biên lai: " + e.getMessage();
            String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
            response.sendRedirect(request.getContextPath() + "/admin/payment-list?message=" + encodedErrorMessage);
        }
    }
}