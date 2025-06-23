package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    private AccountDAO accountDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
        System.out.println("ResetPasswordServlet initialized at " + new java.util.Date());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String source = request.getParameter("source"); // null nếu từ forgot_password
        String email;
        String oldPassword = request.getParameter("old-password"); // null nếu từ forgot_password
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        if ("profile".equals(source)) {
            // Đổi mật khẩu từ profile đang đăng nhập
            email = request.getParameter("email");

            if (email == null || oldPassword == null || newPassword == null || confirmPassword == null) {
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
                request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                return;
            }

            try {
                Account account = accountDAO.getAccountByEmail(email);
                if (account == null) {
                    request.setAttribute("error", "Không tìm thấy tài khoản.");
                    request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                    return;
                }

                if (!oldPassword.equals(account.getPassword())) {
                    request.setAttribute("error", "Mật khẩu hiện tại không chính xác.");
                    request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
                    request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                    return;
                }

                if (newPassword.length() < 6) {
                    request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                    request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                    return;
                }

                accountDAO.updatePassword(email, newPassword);

// Lấy lại thông tin tài khoản và học viên để gán lại cho trang JSP
                Account updatedAccount = accountDAO.getAccountByEmail(email);
                StudentDAO studentDAO = new StudentDAO();
                Student student = studentDAO.getStudentByAccountId(updatedAccount.getId());

// Gán vào request
                request.setAttribute("success", "Đổi mật khẩu thành công.");
                request.setAttribute("student", student);

// Forward lại trang profile
                request.getRequestDispatcher("student_profile.jsp").forward(request, response);


            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi khi cập nhật mật khẩu: " + e.getMessage());
                request.getRequestDispatcher("student_profile.jsp").forward(request, response);
            }

        } else {
            // Trường hợp forgot password
            email = (String) session.getAttribute("resetEmail");

            if (email == null) {
                request.setAttribute("error", "Phiên đặt lại mật khẩu đã hết hạn. Vui lòng bắt đầu lại.");
                request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
                return;
            }

            if (newPassword == null || newPassword.trim().isEmpty()) {
                request.setAttribute("error", "Mật khẩu không được để trống.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }

            if (newPassword.length() < 6) {
                request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }

            try {
                Account account = accountDAO.getAccountByEmail(email);
                if (account == null) {
                    request.setAttribute("error", "Tài khoản không tồn tại.");
                    session.removeAttribute("resetEmail");
                    session.removeAttribute("verificationCode");
                    request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
                    return;
                }

                accountDAO.updatePassword(email, newPassword);

                session.removeAttribute("resetEmail");
                session.removeAttribute("verificationCode");

                request.setAttribute("success", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi hệ thống khi đặt lại mật khẩu: " + e.getMessage());
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            }
        }
    }

}