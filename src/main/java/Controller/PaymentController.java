package Controller;

import DAO.PaymentDAO;
import DAO.TutorDAO;
import model.Payment;
import model.Tutor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

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
            // Lấy danh sách khóa học đã hoàn thành
            request.setAttribute("completedCourses", paymentDAO.getCompletedCourses());
            request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi tải danh sách khóa học: " + e.getMessage());
            request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String courseId = request.getParameter("courseId");
            String tutorId = request.getParameter("tutorId");
            double amount = Double.parseDouble(request.getParameter("amount"));

            // Tạo mã thanh toán
            String paymentId = "pay" + String.format("%03d", paymentDAO.getPaymentCount() + 1);
            Payment payment = new Payment(paymentId, courseId, tutorId, amount, LocalDateTime.now(), "completed");

            // Lưu vào cơ sở dữ liệu
            paymentDAO.addPayment(payment);

            // Cập nhật thông báo cho gia sư (tùy chọn)
            Tutor tutor = tutorDAO.getTutorById(tutorId);
            if (tutor != null) {
                String message = "Bạn đã nhận được thanh toán " + amount + " VND cho khóa học " + courseId;
                // Giả sử có DAO để gửi thông báo
                // paymentDAO.sendNotification(tutor.getAccountId(), message);
            }

            request.setAttribute("success", "Thanh toán thành công cho gia sư!");
            request.setAttribute("completedCourses", paymentDAO.getCompletedCourses());
            request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", "Lỗi khi thực hiện thanh toán: " + e.getMessage());
            request.getRequestDispatcher("/admin/payment.jsp").forward(request, response);
        }
    }
}