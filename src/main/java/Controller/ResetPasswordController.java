package Controller;

import DAO.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

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
        String email = (String) session.getAttribute("resetEmail");

        // Kiểm tra email trong session
        if (email == null) {
            request.setAttribute("error", "Phiên đặt lại mật khẩu đã hết hạn. Vui lòng bắt đầu lại.");
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        // Kiểm tra mật khẩu
        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Mật khẩu không được để trống.");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra độ dài và định dạng mật khẩu (tùy chọn)
        if (password.length() < 6) {
            request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        try {
            // Lấy tài khoản theo email
            Account account = accountDAO.getAccountByEmail(email);
            if (account == null) {
                request.setAttribute("error", "Tài khoản không tồn tại.");
                session.removeAttribute("resetEmail");
                session.removeAttribute("verificationCode");
                request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
                return;
            }

            // Cập nhật mật khẩu (giả định mật khẩu được lưu dạng plaintext, nếu dùng hash thì cần mã hóa)
            accountDAO.updatePassword(email, password);

            // Xóa session attributes
            session.removeAttribute("resetEmail");
            session.removeAttribute("verificationCode");

            // Chuyển hướng đến login.jsp với thông báo thành công
            request.setAttribute("success", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống khi đặt lại mật khẩu: " + e.getMessage());
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
        }
    }
}