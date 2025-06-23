package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import DAO.TutorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Student;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reset-password")
public class ResetPasswordController extends HttpServlet {
    private AccountDAO accountDAO;
    private StudentDAO studentDAO;
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
        studentDAO = new StudentDAO();
        tutorDAO = new TutorDAO();
        System.out.println("ResetPasswordServlet initialized at " + new java.util.Date());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account sessionAccount = (Account) session.getAttribute("account");

        String source = request.getParameter("source"); // null nếu từ forgot_password
        String email = request.getParameter("email");
        String oldPassword = request.getParameter("old-password"); // null nếu từ forgot_password
        String newPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm-password");

        System.out.println("Source: " + source + ", Email: " + email + ", Old Password: " + oldPassword);

        if ("profile".equals(source)) {
            // Đổi mật khẩu từ profile đang đăng nhập
            if (sessionAccount == null) {
                System.out.println("No session account found.");
                request.setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            System.out.println("Session account email: " + sessionAccount.getEmail() + ", Role: " + sessionAccount.getRole());

            if (email == null || oldPassword == null || newPassword == null || confirmPassword == null) {
                System.out.println("Missing required parameters.");
                request.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
                forwardToProfile(request, response, sessionAccount);
                return;
            }

            try {
                Account account = accountDAO.getAccountByEmail(email);
                if (account == null || !account.getEmail().equals(sessionAccount.getEmail())) {
                    System.out.println("Account not found or email mismatch. Provided email: " + email);
                    request.setAttribute("error", "Không tìm thấy tài khoản hoặc email không khớp.");
                    forwardToProfile(request, response, sessionAccount);
                    return;
                }

                System.out.println("Account found: " + account.getEmail());

                if (!oldPassword.equals(account.getPassword())) {
                    System.out.println("Old password incorrect.");
                    request.setAttribute("error", "Mật khẩu hiện tại không chính xác.");
                    forwardToProfile(request, response, sessionAccount);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    System.out.println("Confirm password mismatch.");
                    request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
                    forwardToProfile(request, response, sessionAccount);
                    return;
                }

                if (newPassword.length() < 6) {
                    System.out.println("New password too short.");
                    request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                    forwardToProfile(request, response, sessionAccount);
                    return;
                }

                System.out.println("Updating password for email: " + email);
                accountDAO.updatePassword(email, newPassword);
                System.out.println("Password update attempted.");

                // Lấy lại thông tin tài khoản
                Account updatedAccount = accountDAO.getAccountByEmail(email);
                if (updatedAccount == null) {
                    System.out.println("Failed to retrieve updated account.");
                    request.setAttribute("error", "Lỗi khi tải lại thông tin tài khoản.");
                    forwardToProfile(request, response, sessionAccount);
                    return;
                }

                // Cập nhật tài khoản trong phiên
                session.setAttribute("account", updatedAccount);
                System.out.println("Session account updated: " + updatedAccount.getEmail());

                // Lấy thông tin học viên hoặc gia sư dựa trên vai trò
                if (sessionAccount.getRole() == 1) { // Học viên
                    Student student = studentDAO.getStudentByAccountId(updatedAccount.getId());
                    if (student == null) {
                        System.out.println("No student found for account ID: " + updatedAccount.getId());
                        request.setAttribute("error", "Không tìm thấy thông tin học viên liên kết với tài khoản này.");
                        request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                        return;
                    }
                    request.setAttribute("student", student);
                    request.setAttribute("success", "Đổi mật khẩu thành công.");
                    System.out.println("Forwarding to student_profile.jsp with student: " + student.getName());
                    request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                } else if (sessionAccount.getRole() == 2) { // Gia sư
                    Tutor tutor = tutorDAO.getTutorByAccountId(updatedAccount.getId());
                    if (tutor == null) {
                        System.out.println("No tutor found for account ID: " + updatedAccount.getId());
                        request.setAttribute("error", "Không tìm thấy thông tin gia sư liên kết với tài khoản này.");
                        request.getRequestDispatcher("tutor_profile.jsp").forward(request, response);
                        return;
                    }
                    request.setAttribute("tutor", tutor);
                    request.setAttribute("success", "Đổi mật khẩu thành công.");
                    System.out.println("Forwarding to tutor_profile.jsp with tutor: " + tutor.getName());
                    request.getRequestDispatcher("tutor_profile.jsp").forward(request, response);
                } else {
                    System.out.println("Invalid role: " + sessionAccount.getRole());
                    request.setAttribute("error", "Vai trò tài khoản không hợp lệ.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQLException in doPost: " + e.getMessage());
                request.setAttribute("error", "Lỗi khi cập nhật mật khẩu: " + e.getMessage());
                forwardToProfile(request, response, sessionAccount);
            }

        } else {
            // Luồng quên mật khẩu
            email = (String) session.getAttribute("resetEmail");
            System.out.println("Forgot password flow, resetEmail: " + email);

            if (email == null) {
                System.out.println("No resetEmail in session.");
                request.setAttribute("error", "Phiên đặt lại mật khẩu đã hết hạn. Vui lòng bắt đầu lại.");
                request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
                return;
            }

            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("New password empty.");
                request.setAttribute("error", "Mật khẩu không được để trống.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Confirm password mismatch in forgot password flow.");
                request.setAttribute("error", "Mật khẩu xác nhận không khớp.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }

            if (newPassword.length() < 6) {
                System.out.println("New password too short in forgot password flow.");
                request.setAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
                return;
            }

            try {
                Account account = accountDAO.getAccountByEmail(email);
                if (account == null) {
                    System.out.println("No account found for resetEmail: " + email);
                    request.setAttribute("error", "Tài khoản không tồn tại.");
                    session.removeAttribute("resetEmail");
                    session.removeAttribute("verificationCode");
                    request.getRequestDispatcher("forgot_password.jsp").forward(request, response);
                    return;
                }

                System.out.println("Updating password for resetEmail: " + email);
                accountDAO.updatePassword(email, newPassword);
                System.out.println("Password update attempted for forgot password flow.");

                session.removeAttribute("resetEmail");
                session.removeAttribute("verificationCode");

                request.setAttribute("success", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập.");
                System.out.println("Forwarding to login.jsp after successful password reset.");
                request.getRequestDispatcher("login.jsp").forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQLException in forgot password flow: " + e.getMessage());
                request.setAttribute("error", "Lỗi hệ thống khi đặt lại mật khẩu: " + e.getMessage());
                request.getRequestDispatcher("reset_password.jsp").forward(request, response);
            }
        }
    }

    private void forwardToProfile(HttpServletRequest request, HttpServletResponse response, Account sessionAccount)
            throws ServletException, IOException {
        if (sessionAccount == null) {
            System.out.println("No session account in forwardToProfile.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        int role = sessionAccount.getRole();
        System.out.println("Forwarding to profile for role: " + role + ", account ID: " + sessionAccount.getId());
        try {
            if (role == 1) { // Học viên
                Student student = studentDAO.getStudentByAccountId(sessionAccount.getId());
                System.out.println("Student retrieved: " + (student != null ? student.getName() : "null"));
                request.setAttribute("student", student);
                request.getRequestDispatcher("student_profile.jsp").forward(request, response);
            } else if (role == 2) { // Gia sư
                Tutor tutor = tutorDAO.getTutorByAccountId(sessionAccount.getId());
                System.out.println("Tutor retrieved: " + (tutor != null ? tutor.getName() : "null"));
                request.setAttribute("tutor", tutor);
                request.getRequestDispatcher("tutor_profile.jsp").forward(request, response);
            } else {
                System.out.println("Invalid role in forwardToProfile: " + role);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException in forwardToProfile: " + e.getMessage());
            request.setAttribute("error", "Lỗi khi tải thông tin hồ sơ: " + e.getMessage());
            if (role == 1) {
                request.getRequestDispatcher("student_profile.jsp").forward(request, response);
            } else if (role == 2) {
                request.getRequestDispatcher("tutor_profile.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }
}