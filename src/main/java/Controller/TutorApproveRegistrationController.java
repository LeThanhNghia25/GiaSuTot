package Controller;

import DAO.RegisteredSubjectsDAO;
import DAO.StudentDAO;
import DAO.TutorDAO;
import Utils.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;
import model.Tutor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/approve-registrations")
public class TutorApproveRegistrationController extends HttpServlet {
    private RegisteredSubjectsDAO registeredSubjectsDAO;
    private StudentDAO studentDAO;
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        registeredSubjectsDAO = new RegisteredSubjectsDAO();
        studentDAO = new StudentDAO();
        tutorDAO = new TutorDAO();
        System.out.println("TutorApproveRegistrationController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy accountId và role từ session
            String accountId = (String) request.getSession().getAttribute("accountId");
            String role = (String) request.getSession().getAttribute("role");

            if (accountId == null || !role.equals("tutor")) {
                response.sendRedirect(request.getContextPath() + "/account?action=login");
                return;
            }

            // Lấy tutor_id từ accountId
            Tutor tutor = tutorDAO.getTutorByAccountId(accountId);
            if (tutor == null) {
                System.err.println("No tutor found for accountId: " + accountId);
                response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                        URLEncoder.encode("Lỗi: Không tìm thấy thông tin gia sư", "UTF-8"));
                return;
            }
            String tutorId = tutor.getId();
            System.out.println("TutorId retrieved: " + tutorId + " for accountId: " + accountId);

            // Lấy danh sách yêu cầu pending_approval
            List<RegisteredSubjectsDAO.PendingRequest> pendingRequests = registeredSubjectsDAO.getPendingRequests(tutorId);
            System.out.println("Number of pending requests for tutorId " + tutorId + ": " + pendingRequests.size());
            request.setAttribute("pendingRequests", pendingRequests);
            request.getRequestDispatcher("/approve_registrations.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error fetching pending requests: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                    URLEncoder.encode("Lỗi khi tải danh sách yêu cầu", "UTF-8"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        String action = request.getParameter("action"); // "approve" hoặc "reject"

        if (courseId == null || studentId == null || action == null) {
            response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                    URLEncoder.encode("Lỗi: Dữ liệu không hợp lệ", "UTF-8"));
            return;
        }

        try {
            String newStatus = "registered".equalsIgnoreCase(action) ? "registered" : "cancelled";
            registeredSubjectsDAO.updateRegistrationStatus(courseId, studentId, newStatus);

            // Lấy thông tin học sinh để lấy email
            Student student = studentDAO.getStudentById(studentId);
            String studentEmail = (student != null && student.getAccount() != null) ? student.getAccount().getEmail() : null;

            if (studentEmail == null) {
                System.err.println("Không tìm thấy email cho học sinh: " + studentId);
                response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                        URLEncoder.encode("Lỗi: Không thể gửi email, email học sinh không tồn tại", "UTF-8"));
                return;
            }

            String message = "Đăng ký khóa học " + courseId + " đã được " +
                    (newStatus.equals("registered") ? "chấp nhận" : "từ chối") + " bởi gia sư.";

            // Gửi email thông báo chỉ cho học sinh sử dụng sendTextEmail
            try {
                EmailSender.sendTextEmail(studentEmail, "Thông báo đăng ký khóa học", message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                System.err.println("Lỗi gửi email: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                        URLEncoder.encode("Lỗi: Không thể gửi email thông báo", "UTF-8"));
                return;
            }

            String successMessage = "Cập nhật trạng thái thành công: " + message;
            response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                    URLEncoder.encode(successMessage, "UTF-8"));
        } catch (SQLException e) {
            System.err.println("Error updating registration status: " + e.getMessage());
            String errorMessage = "Lỗi khi cập nhật trạng thái: " + e.getMessage();
            response.sendRedirect(request.getContextPath() + "/approve-registrations?message=" +
                    URLEncoder.encode(errorMessage, "UTF-8"));
        }
    }
}