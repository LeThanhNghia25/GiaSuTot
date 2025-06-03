package Controller;

import DAO.PaymentDAO;
import DAO.TutorDAO;
import model.Notification;
import model.Payment;
import model.Tutor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/admin/payment")
public class PaymentController extends HttpServlet {
    private PaymentDAO paymentDAO;
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        paymentDAO = new PaymentDAO();
        tutorDAO = new TutorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("completedCourses", paymentDAO.getCompletedCourses());
            request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi tải danh sách khóa học: " + e.getMessage());
            request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String courseId = request.getParameter("courseId");
        String tutorId = request.getParameter("tutorId");
        String studentId = request.getParameter("studentId"); // Thêm studentId
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
                // Lấy thông tin tutor
                Tutor tutor = tutorDAO.getTutorById(tutorId);
                if (tutor == null) {
                    System.out.println("Tutor not found for id: " + tutorId);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy gia sư với ID: " + tutorId);
                    return;
                }
                System.out.println("Tutor found: " + tutor.getName());

                // Tạo Map chỉ chứa các trường cần thiết để tránh xung đột
                Map<String, Object> tutorData = new HashMap<>();
                tutorData.put("id", tutor.getId());
                tutorData.put("bankName", tutor.getBankName());
                tutorData.put("bankAccountNumber", tutor.getBankAccountNumber());

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("tutor", tutorData);
                responseData.put("courseId", courseId);
                responseData.put("studentId", studentId); // Trả về studentId để sử dụng trong modal
                responseData.put("amount", amount);

                // Trả về JSON
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
                // Tạo mã thanh toán
                String paymentId = "pay" + String.format("%03d", paymentDAO.getPaymentCount() + 1);
                Payment payment = new Payment(paymentId, courseId, tutorId, studentId, amount, LocalDateTime.now(), "completed");

                // Lưu vào cơ sở dữ liệu
                paymentDAO.addPayment(payment);

                // Tạo và lưu thông báo cho gia sư
                Tutor tutor = tutorDAO.getTutorById(tutorId);
                if (tutor != null) {
                    String message = "Bạn đã nhận được thanh toán " + amount + " VND cho khóa học " + courseId + " vào " + LocalDateTime.now();
                    Notification notification = new Notification(
                            java.util.UUID.randomUUID().toString(),
                            tutor.getAccountId(),
                            message,
                            LocalDateTime.now(),
                            "pending"
                    );
                    paymentDAO.addNotification(notification);
                }

                // Thông báo thành công với thông tin chi tiết
                request.setAttribute("success", "Thanh toán thành công cho gia sư với khóa học " + courseId + "-" + tutorId);
            } catch (SQLException e) {
                request.setAttribute("error", "Lỗi khi thực hiện thanh toán: " + e.getMessage());
            }
        } else if ("deferPayment".equals(action)) {
            // Không hiển thị thông báo và không làm mới trang
            return;
        }

        try {
            request.setAttribute("completedCourses", paymentDAO.getCompletedCourses());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
    }
}