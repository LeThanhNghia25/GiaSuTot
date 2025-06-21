package Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/verify-email")
public class VerifyEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Ghép 4 ô mã người dùng nhập
        String code = request.getParameter("code1")
                + request.getParameter("code2")
                + request.getParameter("code3")
                + request.getParameter("code4");

        // Lấy mã từ session đã lưu khi gửi mail
        HttpSession session = request.getSession();
        String expectedCode = (String) session.getAttribute("verificationCode");

        if (expectedCode != null && expectedCode.equals(code)) {
            // Mã đúng → chuyển tới trang đổi mật khẩu
            request.getRequestDispatcher("reset_password.jsp").forward(request, response);
        } else {
            // Mã sai → thông báo lỗi và cho nhập lại
            request.setAttribute("error_code", "Mã xác thực không đúng!");
            request.setAttribute("verificationRequested", true); // để hiện lại modal
            request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
        }
    }
}
