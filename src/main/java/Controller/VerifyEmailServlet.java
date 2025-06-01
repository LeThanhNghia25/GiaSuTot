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
    private static final long serialVersionUID = 1L;

    // Đây là ví dụ mã xác thực, thực tế bạn lấy từ DB hoặc session khi gửi mail
    private static final String EXPECTED_CODE = "1234";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy 4 phần của mã nhập từ form
        String code1 = request.getParameter("code1");
        String code2 = request.getParameter("code2");
        String code3 = request.getParameter("code3");
        String code4 = request.getParameter("code4");

        String enteredCode = (code1 == null ? "" : code1)
                + (code2 == null ? "" : code2)
                + (code3 == null ? "" : code3)
                + (code4 == null ? "" : code4);

        // Lấy session để truyền dữ liệu và kiểm tra
        HttpSession session = request.getSession();

        if (EXPECTED_CODE.equals(enteredCode)) {
            // Xác thực thành công
            session.setAttribute("emailVerified", true);
            // Xóa các biến liên quan nếu cần
            session.removeAttribute("verificationRequested");
            session.removeAttribute("error_code");

            // Chuyển hướng đến trang thành công hoặc trang tiếp theo
            response.sendRedirect("verification-success.jsp");
        } else {
            // Sai mã xác thực, trả lại lỗi để hiển thị trên modal
            session.setAttribute("verificationRequested", true); // để modal hiện lại
            session.setAttribute("error_code", "Mã xác thực không đúng. Vui lòng thử lại.");

            // Quay lại trang trước (ví dụ trang đăng ký hoặc trang đang chứa modal)
            response.sendRedirect("register.jsp");
        }
    }
}
