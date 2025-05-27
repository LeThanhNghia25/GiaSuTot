package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import DAO.TutorDAO;
import jakarta.servlet.http.HttpSession;
import model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "AccountController", urlPatterns = {"/account", "/signup-user"})
public class AccountController extends HttpServlet {
    private AccountDAO accountDAO;
    private TutorDAO tutorDAO;
    private StudentDAO studentDAO;
    @Override
    public void init() {
        try {
            accountDAO = new AccountDAO();
            tutorDAO = new TutorDAO();
            studentDAO = new StudentDAO();
            System.out.println("AccountDAO initialized successfully at " + new java.util.Date());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hiển thị trang login/register hoặc các trang quản lý tài khoản
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("login".equals(action)) {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else if ("logout".equals(action)) {
                request.getSession().invalidate();
                response.sendRedirect(request.getContextPath() + "/account?action=login");
            } else {
                // Mặc định chuyển về trang login
                response.sendRedirect(request.getContextPath() + "/account?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    // Xử lý đăng nhập và đăng ký
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("login".equals(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                Account acc = accountDAO.getAccountByEmail(email);

                if (acc != null && acc.getPassword().equals(password) && "active".equalsIgnoreCase(acc.getStatus())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("account", acc);

                    System.out.println("Login successful. Account ID: " + acc.getId());

                    if (acc.getRole() == 1) { // Student
                        Student student = studentDAO.getStudentByAccountId(acc.getId());
                        if (student != null) {
                            session.setAttribute("userName", student.getName());
                            session.setAttribute("role", "student"); // <-- THÊM DÒNG NÀY
                            session.setAttribute("student", student); // Nếu bạn cần dùng thông tin student sau
                        }
                    } else if (acc.getRole() == 2) { // Tutor
                        Tutor tutor = tutorDAO.getTutorByAccountId(acc.getId());
                        if (tutor != null) {
                            session.setAttribute("userName", tutor.getName());
                            session.setAttribute("role", "tutor"); // <-- THÊM DÒNG NÀY
                            session.setAttribute("tutor", tutor);   // Nếu bạn cần dùng thông tin tutor sau
                        }
                    }

                    // Login thành công, chuyển hướng tới trang chính
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    request.setAttribute("error", "Email hoặc mật khẩu không đúng, hoặc tài khoản chưa kích hoạt.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }

            } else if ("register".equals(action)) {
                // Xử lý đăng ký
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String name = request.getParameter("name");
                String birthStr = request.getParameter("birth");
                LocalDate birth = LocalDate.parse(birthStr);
                String describe = request.getParameter("describe");

                if (email == null || email.trim().isEmpty() ||
                        password == null || password.trim().isEmpty() ||
                        name == null || name.trim().isEmpty() ||
                        birthStr == null || birthStr.trim().isEmpty()) {

                    request.setAttribute("error_register", "Vui lòng điền đầy đủ thông tin.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }

                Account existing = accountDAO.getAccountByEmail(email);
                if (existing != null) {
                    request.setAttribute("error_register", "Email đã tồn tại, vui lòng dùng email khác.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                }

                try {
                    // Tạo account mới
                    String idAcc = accountDAO.generateaccount_id();
                    Account acc = new Account(idAcc, email, password, 1, "inactive"); // Mặc định là student
                    accountDAO.insertAccount(acc);

                    // Gửi thông tin student sang StudentController
                    request.setAttribute("name", name);
                    request.setAttribute("birth", birth);
                    request.setAttribute("describe", describe);
                    request.setAttribute("id_acc", idAcc);

                    // Forward để lưu student
                    request.getRequestDispatcher("/student").forward(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("error_register", "Lỗi đăng ký: " + e.getMessage());
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}