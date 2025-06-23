package Controller;

import DAO.TutorRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "TutorRequestController", urlPatterns = {"/tutor-request"})
public class TutorRequestController extends HttpServlet {
    private final TutorRequestDAO dao = new TutorRequestDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // Kiểm tra đăng nhập và vai trò
        if (account == null || account.getRole() != 1) {
            response.sendRedirect(request.getContextPath() + "/account?action=login");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String accountId = request.getParameter("accountId");
            String name = request.getParameter("name");
            String birth = request.getParameter("birth");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String idCardNumber = request.getParameter("idCardNumber");
            String bankAccountNumber = request.getParameter("bankAccountNumber");
            String bankName = request.getParameter("bankName");
            String address = request.getParameter("address");
            String[] specialization = request.getParameterValues("specialization");
            String description = request.getParameter("description");

            // Kiểm tra dữ liệu
            if (accountId == null || name == null || birth == null || email == null || phone == null ||
                    idCardNumber == null || bankAccountNumber == null || bankName == null || address == null ||
                    specialization == null || description == null) {
                session.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }

            if (!phone.matches("\\d{10,11}")) {
                session.setAttribute("error", "Số điện thoại phải có 10-11 chữ số.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }
            if (!idCardNumber.matches("\\d{12}")) {
                session.setAttribute("error", "Số CCCD phải có đúng 12 chữ số.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }
            if (!bankAccountNumber.matches("\\d{1,15}")) {
                session.setAttribute("error", "Số tài khoản ngân hàng phải có từ 1 đến 15 chữ số.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }
            if (address.trim().isEmpty()) {
                session.setAttribute("error", "Địa chỉ không được để trống.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }
            if (description.trim().isEmpty()) {
                session.setAttribute("error", "Mô tả không được để trống.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }
            if (specialization.length == 0) {
                session.setAttribute("error", "Vui lòng chọn ít nhất một môn học.");
                response.sendRedirect(request.getContextPath() + "/student");
                return;
            }

            // Lưu yêu cầu vào cơ sở dữ liệu
            dao.saveTutorRequest(accountId, name, birth, email, phone, idCardNumber, bankAccountNumber, bankName, address, String.join(",", specialization), description);

            // Thêm thông báo thành công vào session
            session.setAttribute("success", "Yêu cầu trở thành gia sư đã được gửi thành công!");
            response.sendRedirect(request.getContextPath() + "/student");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("error", "Không thể gửi yêu cầu: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/student");
        }
    }
}