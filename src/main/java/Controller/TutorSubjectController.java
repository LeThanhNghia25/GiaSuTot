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
        subjectDAO = new TutorSubjectDAO(); // Không cần try-catch nếu không có SQLException
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
            String tutorId = getTutorIdByAccountId(account.getId());
            if (tutorId == null) {
                request.setAttribute("error", "Không tìm thấy thông tin tutor.");
                request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
                return;
            }

            List<Subject> subjects = subjectDAO.getActiveSubjectsByTutor(tutorId);
            if (subjects == null || subjects.isEmpty()) {
                request.setAttribute("error", "Không có môn học nào đang hoạt động.");
                request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
                return;
            }

            Map<String, List<RegisteredSubjects>> registeredSubjectsMap = new HashMap<>();
            Map<String, Integer> lessonCountsMap = subjectDAO.getCompletedLessonCounts();
            for (Subject subject : subjects) {
                List<RegisteredSubjects> registeredSubjects = subjectDAO.getRegisteredSubjectsByCourse(subject.getId());
                if (registeredSubjects != null) {
                    registeredSubjectsMap.put(subject.getId(), registeredSubjects);
                }
            }
            request.setAttribute("subjects", subjects);
            request.setAttribute("lessonCountsMap", lessonCountsMap);
            request.setAttribute("registeredSubjectsMap", registeredSubjectsMap);
            request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi tải danh sách môn học: " + e.getMessage());
            request.getRequestDispatcher("/manager_subject.jsp").forward(request, response);
        }
        System.out.println("TutorSubjectController invoked");
    }

    private String getTutorIdByAccountId(String accountId) throws SQLException {
        try (Connection conn = Utils.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM tutor WHERE account_id = ?")) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        }
        return null;
    }
}