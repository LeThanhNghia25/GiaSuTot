package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import DAO.TutorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import model.Student;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/activate-account")
public class ActivateAccountServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        if (email == null || email.isEmpty()) {
            response.sendRedirect("error.jsp"); // Trang báo lỗi chung nếu không có email
            return;
        }

        AccountDAO dao = new AccountDAO();
        try {
            boolean success = dao.activateAccount(email);
            if (success) {
                Account savedAccount = dao.getAccountByEmail(email);
                setUserSession(request.getSession(), savedAccount);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            } else {
                request.setAttribute("error", "Kích hoạt thất bại. Email không hợp lệ hoặc đã kích hoạt.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi máy chủ: không thể kích hoạt.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }


    }
    private void setUserSession(HttpSession session, Account account) throws SQLException {
        session.setAttribute("account", account);

        StudentDAO studentDAO = new StudentDAO();
        TutorDAO tutorDAO = new TutorDAO();

        if (account.getRole() == 1) {
            Student student = studentDAO.getStudentByAccountId(account.getId());
            if (student != null) {
                session.setAttribute("userName", student.getName());
                session.setAttribute("role", "student");
            }
        } else if (account.getRole() == 2) {
            Tutor tutor = tutorDAO.getTutorByAccountId(account.getId());
            if (tutor != null) {
                session.setAttribute("userName", tutor.getName());
                session.setAttribute("role", "tutor");
            }
        }
    }

}
