package Controller;

import DAO.AdminPaymentDAO;
import DAO.TutorDAO;
import model.Payment;
import model.Tutor;
import Utils.EmailSender;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/admin/payment")
public class AdminPaymentForTutorController extends HttpServlet {
    private AdminPaymentDAO adminPaymentDAO;
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        adminPaymentDAO = new AdminPaymentDAO();
        tutorDAO = new TutorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("completedCourses", adminPaymentDAO.getCompletedCourses());
            request.getRequestDispatcher("/admin/payment-for-tutor.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi tải danh sách khóa học: " + e.getMessage());
            request.getRequestDispatcher("/admin/payment-for-tutor.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String courseId = request.getParameter("courseId");
        String tutorId = request.getParameter("tutorId");
        String studentId = request.getParameter("studentId");
        double amount = 0;

        System.out.println("Received action: " + action);
        System.out.println("courseId: " + courseId + ", tutorId: " + tutorId + ", studentId: " + studentId + ", amount: " + request.getParameter("amount"));

        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số tiền không hợp lệ: " + e.getMessage());
            return;
        }

        if ("showModal".equals(action)) {
            try {
                if (tutorId == null || courseId == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin tutorId hoặc courseId");
                    return;
                }
                Tutor tutor = tutorDAO.getTutorById(tutorId);
                if (tutor == null) {
                    System.out.println("Tutor not found for id: " + tutorId);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy gia sư với ID: " + tutorId);
                    return;
                }
                System.out.println("Tutor found: " + tutor.getName());

                Map<String, Object> tutorData = new HashMap<>();
                tutorData.put("id", tutor.getId());
                tutorData.put("bankName", tutor.getBankName());
                tutorData.put("bankAccountNumber", tutor.getBankAccountNumber());

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("tutor", tutorData);
                responseData.put("courseId", courseId);
                responseData.put("studentId", studentId);
                responseData.put("amount", amount);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.print(new Gson().toJson(responseData));
                out.flush();
            } catch (Exception e) {
                System.err.println("Unexpected Exception: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi không xác định: " + e.getMessage());
            }
            return;
        }

        if ("confirmPayment".equals(action)) {
            try {
                if (courseId == null || tutorId == null || studentId == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin cần thiết");
                    return;
                }
                String paymentId = "pay" + String.format("%03d", adminPaymentDAO.getPaymentCount() + 1);
                Payment payment = new Payment(paymentId, courseId, tutorId, studentId, amount, new Date());
                adminPaymentDAO.addPayment(payment);

                // Lấy email của gia sư dựa trên courseId
                String tutorEmail = adminPaymentDAO.getTutorEmailByCourseId(courseId);
                if (tutorEmail != null) {
                    // Lấy thông tin khóa học từ AdminPaymentDAO
                    var courseDetails = adminPaymentDAO.getCompletedCourses().stream()
                            .filter(c -> c.getId().equals(courseId))
                            .findFirst()
                            .orElse(null);
                    if (courseDetails != null) {
                        String startDate = courseDetails.getStartDate() != null
                                ? courseDetails.getStartDate().toString()
                                : "Chưa có dữ liệu";
                        String endDate = courseDetails.getEndDate() != null
                                ? courseDetails.getEndDate().toString()
                                : "Chưa có dữ liệu";
                        String subjectName = courseDetails.getSubject().getName();
                        String level = courseDetails.getSubject().getLevel();
                        String studentName = courseDetails.getStudentName();

                        // Tạo nội dung email
                        String emailBody = "Xin chào,\n\n"
                                + "Thanh toán cho khóa học của bạn đã được thực hiện. Dưới đây là thông tin chi tiết:\n\n"
                                + "Mã khóa học: " + courseId + "\n"
                                + "Tên môn học: " + subjectName + "\n"
                                + "Cấp độ: " + level + "\n"
                                + "ID Học viên: " + studentId + "\n"
                                + "Tên Học viên: " + studentName + "\n"
                                + "Ngày Bắt Đầu: " + startDate + "\n"
                                + "Ngày Hoàn Thành: " + endDate + "\n"
                                + "Mức lương: " + amount + " VND\n\n"
                                + "Trân trọng,\nĐội ngũ Gia Sư Tốt";

                        // Gửi email
                        EmailSender.sendTextEmail(tutorEmail, "Thông báo thanh toán khóa học " + courseId, emailBody);
                    }
                } else {
                    System.err.println("Không tìm thấy email của gia sư cho courseId: " + courseId);
                }

                // Chuẩn bị thông báo thành công và redirect
                String message = "Thanh toán thành công cho gia sư với khóa học " + courseId;
                String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/admin/payment?message=" + encodedMessage);
            } catch (SQLException e) {
                System.err.println("Lỗi SQL khi thực hiện thanh toán: " + e.getMessage());
                e.printStackTrace();
                String errorMessage = "Lỗi khi thực hiện thanh toán: " + e.getMessage();
                String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/admin/payment?message=" + encodedErrorMessage);
            } catch (Exception e) {
                System.err.println("Lỗi gửi email hoặc khác: " + e.getMessage());
                e.printStackTrace();
                String errorMessage = "Lỗi khi gửi email thông báo: " + e.getMessage();
                String encodedErrorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
                response.sendRedirect(request.getContextPath() + "/admin/payment?message=" + encodedErrorMessage);
            }
            return;
        }

        if ("deferPayment".equals(action)) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Thanh toán đã được tạm hoãn");
            responseData.put("courseId", courseId);
            responseData.put("tutorId", tutorId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(responseData));
            out.flush();
            return;
        }

        try {
            request.setAttribute("completedCourses", adminPaymentDAO.getCompletedCourses());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/admin/payment-for-tutor.jsp").forward(request, response);
    }
}