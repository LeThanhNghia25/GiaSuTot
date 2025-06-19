package Controller;

import DAO.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reset_password")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy mật khẩu mới từ form
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra xác nhận mật khẩu
        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            return;
        }

        // Lấy email từ session
        String email = (String) request.getSession().getAttribute("resetEmail");

        if (email != null) {
            AccountDAO dao = new AccountDAO();

            // Thực hiện cập nhật mật khẩu trong cơ sở dữ liệu
            boolean success = dao.updatePassword(email, newPassword);  // Bạn có thể băm mật khẩu ở đây nếu muốn

            if (success) {
                // Xóa session
                request.getSession().removeAttribute("resetEmail");
                request.getSession().removeAttribute("verificationCode");

                // Hiển thị thông báo và chuyển đến trang đăng nhập
                request.setAttribute("success", "Đặt lại mật khẩu thành công. Vui lòng đăng nhập.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Có lỗi xảy ra khi cập nhật mật khẩu.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            }
        } else {
            // Không tìm thấy email trong session
            response.sendRedirect("forgot_password.jsp");
        }
    }

    // Nếu người dùng truy cập bằng GET thì chuyển hướng về form reset
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("reset_password.jsp");
    }
}
