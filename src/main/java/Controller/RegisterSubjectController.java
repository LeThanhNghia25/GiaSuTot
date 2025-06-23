package Controller;

import DAO.AdminSubjectDAO;
import DAO.TutorDAO;
import model.Account;
import model.Subject;
import model.Tutor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Utils.DBConnection;
import Utils.EmailSender;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/registerSubject")
public class RegisterSubjectController extends HttpServlet {
    private AdminSubjectDAO subjectDAO;
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        subjectDAO = new AdminSubjectDAO();
        tutorDAO = new TutorDAO();
        System.out.println("RegisterSubjectController initialized successfully at " + new java.util.Date());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra đăng nhập
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            request.getSession().setAttribute("error", "Vui lòng đăng nhập để đăng ký môn dạy.");
            response.sendRedirect("login.jsp");
            return;
        }

        // Kiểm tra vai trò (giả định role = 2 là gia sư)
        if (account.getRole() != 2) {
            request.getSession().setAttribute("error", "Chỉ gia sư được phép đăng ký môn dạy.");
            response.sendRedirect("manager_subject.jsp");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String name = request.getParameter("name");
            String level = request.getParameter("level");
            String description = request.getParameter("description");
            String feeStr = request.getParameter("fee");
            String status = request.getParameter("status");

            // Kiểm tra dữ liệu đầu vào
            if (name == null || name.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Tên môn học không được để trống.");
                response.sendRedirect("manager_subject.jsp");
                return;
            }

            if (level == null || level.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Cấp độ không được để trống.");
                response.sendRedirect("manager_subject.jsp");
                return;
            }

            double fee;
            try {
                if (feeStr == null || feeStr.trim().isEmpty()) {
                    throw new NumberFormatException("Học phí không được để trống.");
                }
                fee = Double.parseDouble(feeStr);
                if (fee < 1200000 || fee > 3000000) {
                    request.getSession().setAttribute("error", "Học phí phải nằm trong khoảng từ 1.200.000 đến 3.000.000 VND.");
                    response.sendRedirect("manager_subject.jsp");
                    return;
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "Học phí không hợp lệ: " + e.getMessage());
                response.sendRedirect("manager_subject.jsp");
                return;
            }

            if (status == null || !status.equals("inactive")) {
                request.getSession().setAttribute("error", "Trạng thái không hợp lệ.");
                response.sendRedirect("manager_subject.jsp");
                return;
            }

            // Tạo ID môn học
            String id = subjectDAO.generateSubjectId();

            // Tạo đối tượng Subject
            Subject subject = new Subject(id, name, level, description, fee, status);

            // Lưu môn học vào cơ sở dữ liệu
            subjectDAO.addSubject(subject);

            // Lấy thông tin gia sư
            Tutor tutor = tutorDAO.getTutorByAccountId(account.getId());
            if (tutor == null) {
                request.getSession().setAttribute("error", "Không tìm thấy thông tin gia sư.");
                response.sendRedirect("manager_subject.jsp");
                return;
            }

            // Lấy email của admin (giả định account_id = 'admin001')
            String adminEmail = null;
            String sql = "SELECT email FROM account WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "admin001");
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        adminEmail = rs.getString("email");
                    }
                }
            }

            if (adminEmail == null) {
                request.getSession().setAttribute("error", "Không tìm thấy email của admin.");
                response.sendRedirect("manager_subject.jsp");
                return;
            }

            // Gửi email thông báo cho admin
            String emailSubject = "Yêu cầu duyệt môn học mới từ gia sư " + tutor.getName();
            String emailBody = "Kính gửi Admin,\n\n" +
                    "Gia sư " + tutor.getName() + " đã đăng ký môn học mới với thông tin sau:\n" +
                    "- Môn học: " + name + "\n" +
                    "- Cấp độ: " + level + "\n" +
                    "- Học phí: " + fee + " VND\n" +
                    "- Trạng thái: Đang chờ duyệt\n\n" +
                    "Vui lòng truy cập hệ thống để xem xét và duyệt môn học này.\n" +
                    "Đường dẫn duyệt: http://localhost:8080/GiaSuTot_war/admin/subject\n\n" +
                    "Trân trọng,\n" +
                    "Hệ thống Gia Sư Tốt";

            EmailSender.sendTextEmail(adminEmail, emailSubject, emailBody);

            // Gửi thông báo qua hệ thống (dùng AdminSubjectDAO.sendNotification)
            String notificationMessage = "Gia sư " + tutor.getName() + " đã đăng ký môn học: " + name + " (" + level + ") đang chờ duyệt.";
            subjectDAO.sendNotification("admin001", notificationMessage);

            // Đặt thông báo thành công
            request.getSession().setAttribute("success", "Đăng ký môn dạy thành công! Đang chờ admin duyệt.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi khi đăng ký môn dạy: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi gửi email: " + e.getMessage());
        }

        // Chuyển hướng về manager_subject.jsp
        response.sendRedirect("manager_subject.jsp");
    }
}