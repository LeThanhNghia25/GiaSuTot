package Controller;

import DAO.AccountDAO;
import Utils.EmailSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

@WebServlet("/forgot_password")
public class ForgotPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        AccountDAO dao = new AccountDAO();
        Account account = null;
        try {
            account = dao.getAccountByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (account != null) {
            // Tạo mã xác thực 4 chữ số
            String code = String.format("%04d", new Random().nextInt(10000));

            // Gửi email chứa mã
            EmailSender.sendVerificationCodeEmail(email, code);

            // Lưu vào session
            HttpSession session = request.getSession();
            session.setAttribute("verificationCode", code);
            session.setAttribute("resetEmail", email);

            // Hiển thị lại trang chứa modal nhập mã
            request.setAttribute("verificationRequested", true);
        }

        request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
    }
}
