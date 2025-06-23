package Controller;

import DAO.TutorDAO;
import DAO.TutorRequestDAO;
import Utils.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "TutorRequestActionController", urlPatterns = {"/tutor-request-action"})
public class TutorRequestActionController extends HttpServlet {
    private final TutorDAO tutorDAO = new TutorDAO();
    private final TutorRequestDAO requestDAO = new TutorRequestDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra đăng nhập và vai trò admin (role = 0)
        if (account == null || account.getRole() != 0) {
            response.sendRedirect(request.getContextPath() + "/account?action=login");
            return;
        }

        String requestId = request.getParameter("requestId");
        String action = request.getParameter("action");

        try {
            if ("approve".equals(action)) {
                // Lấy thông tin yêu cầu
                TutorRequestDAO.TutorRequest tutorRequest = getTutorRequestById(Integer.parseInt(requestId));
                if (tutorRequest != null) {
                    // Tạo ID cho gia sư (tut + accountId)
                    String tutorId = "tut" + tutorRequest.getAccountId();
                    // Kiểm tra xem gia sư đã tồn tại chưa
                    if (tutorDAO.getTutorById(tutorId) == null) {
                        // Lưu vào bảng tutor
                        tutorDAO.addTutor(
                                tutorId,
                                tutorRequest.getAccountId(),
                                tutorRequest.getName(),
                                tutorRequest.getBirth(),
                                tutorRequest.getEmail(),
                                tutorRequest.getPhone(),
                                tutorRequest.getIdCardNumber(),
                                tutorRequest.getBankAccountNumber(),
                                tutorRequest.getBankName(),
                                tutorRequest.getAddress(),
                                tutorRequest.getSpecialization(),
                                tutorRequest.getDescription(),
                                0 // Đánh giá mặc định
                        );
                        // Xóa yêu cầu
                        requestDAO.rejectTutorRequest(requestId);
                        session.setAttribute("success", "Yêu cầu đã được duyệt thành công!");
                    } else {
                        session.setAttribute("error", "Gia sư đã tồn tại với tài khoản này.");
                    }
                } else {
                    session.setAttribute("error", "Yêu cầu không tồn tại.");
                }
            } else if ("reject".equals(action)) {
                requestDAO.rejectTutorRequest(requestId);
                session.setAttribute("success", "Yêu cầu đã bị từ chối!");
            } else {
                session.setAttribute("error", "Hành động không hợp lệ.");
            }
            response.sendRedirect(request.getContextPath() + "/admin/tutor-requests");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi xử lý yêu cầu: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/tutor-requests");
        }
    }

    private TutorRequestDAO.TutorRequest getTutorRequestById(int id) throws SQLException {
        String sql = """
            SELECT id, account_id, name, birth, email, phone, id_card_number, bank_account_number, 
                   bank_name, address, specialization, description
            FROM tutor_requests WHERE id = ?
        """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TutorRequestDAO.TutorRequest(
                            rs.getInt("id"),
                            rs.getString("account_id"),
                            rs.getString("name"),
                            rs.getString("birth"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("id_card_number"),
                            rs.getString("bank_account_number"),
                            rs.getString("bank_name"),
                            rs.getString("address"),
                            rs.getString("specialization"),
                            rs.getString("description")
                    );
                }
            }
        }
        return null;
    }
}