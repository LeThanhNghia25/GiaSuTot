package Controller;

import DAO.StudentScheduleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StudentScheduleController", urlPatterns = {"/student-schedule"})
public class StudentScheduleController extends HttpServlet {
    private final StudentScheduleDAO dao = new StudentScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null || acc.getRole() != 1) {
            response.sendRedirect(request.getContextPath() + "/account?action=login");
            return;
        }

        try {
            String studentId = getStudentIdByAccountId(acc.getId());
            List<StudentScheduleDAO.ScheduleItem> schedule = dao.getWeeklySchedule(studentId);
            request.setAttribute("schedule", schedule);
            request.getRequestDispatcher("/student_schedule.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Không thể tải lịch học.");
            request.getRequestDispatcher("/student_schedule.jsp").forward(request, response);
        }
    }

    private String getStudentIdByAccountId(String accId) throws SQLException {
        try (Connection conn = Utils.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM student WHERE account_id = ?")) {
            ps.setString(1, accId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        }
        return null;
    }
}
