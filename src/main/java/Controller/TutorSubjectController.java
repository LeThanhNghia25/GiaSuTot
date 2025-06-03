package Controller;

import DAO.TutorSubjectDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Subject;
import model.RegisteredSubjects;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "TutorSubjectController", urlPatterns = {"/tutor-subjects"})
public class TutorSubjectController extends HttpServlet {
    private TutorSubjectDAO subjectDAO;

    @Override
    public void init() {
        try {
            subjectDAO = new TutorSubjectDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null || account.getRole() != 2) {
            request.setAttribute("error", "Vui lòng đăng nhập với vai trò tutor để xem môn học.");
            request.getRequestDispatcher("/account?action=login").forward(request, response);
            return;
        }

        try {
            // Lấy ID tutor từ bảng tutor dựa trên account_id
            String tutorId = getTutorIdByAccountId(account.getId());
            if (tutorId == null) {
                request.setAttribute("error", "Không tìm thấy thông tin tutor.");
                request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
                return;
            }

            // Lấy danh sách môn học active
            List<Subject> subjects = subjectDAO.getActiveSubjectsByTutor(tutorId);
            // Tạo map lưu danh sách registeredSubjects cho từng subject
            Map<String, List<RegisteredSubjects>> registeredSubjectsMap = new HashMap<>();

            for (Subject subject : subjects) {
                List<RegisteredSubjects> registeredSubjects = subjectDAO.getRegisteredSubjectsByCourse(subject.getId());
                registeredSubjectsMap.put(subject.getId(), registeredSubjects);
            }

            // Truyền dữ liệu về JSP
            request.setAttribute("subjects", subjects);
            request.setAttribute("registeredSubjectsMap", registeredSubjectsMap);
            request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách môn học: " + e.getMessage());
            request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
        }
        System.out.println("TutorSubjectController invoked");

    }

    // Lấy tutor_id từ account_id
    private String getTutorIdByAccountId(String accountId) throws SQLException {
        Connection conn = subjectDAO.getConnection();
        String sql = "SELECT id FROM tutor WHERE account_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
        }
        return null;
    }
}