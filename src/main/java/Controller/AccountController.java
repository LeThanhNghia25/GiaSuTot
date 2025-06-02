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
import java.time.LocalDate;

@WebServlet(name = "AccountController", urlPatterns = {"/account", "/signup-user"})
public class AccountController extends HttpServlet {
    private AccountDAO accountDAO;
    private TutorDAO tutorDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        accountDAO = new AccountDAO();
        tutorDAO = new TutorDAO();
        try {
            studentDAO = new StudentDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("AccountDAO initialized successfully at " + new java.util.Date());
    }

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
                response.sendRedirect(request.getContextPath() + "/account?action=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("AccountController: doPost được gọi");

        String action = request.getParameter("action");
        System.out.println("Action nhận được: " + action);

        try {
            if ("login".equals(action)) {
                String email = request.getParameter("email");
                String pass = request.getParameter("pass");

                Account acc = accountDAO.getAccountByEmail(email);


                if (acc != null && acc.getPassword().equals(pass) && "active".equalsIgnoreCase(acc.getStatus())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("account", acc);

                    // Thiết lập userName và role dựa trên vai trò
                    if (acc.getRole() == 1) { // Student
                        Student student = studentDAO.getStudentByAccountId(acc.getId());
                        if (student != null) {
                            session.setAttribute("userName", student.getName());
                            session.setAttribute("role", "student"); // Thiết lập role dạng chuỗi
                        }
                    } else if (acc.getRole() == 2) { // Tutor
                        Tutor tutor = tutorDAO.getTutorByAccountId(acc.getId());
                        if (tutor != null) {
                            session.setAttribute("userName", tutor.getName());
                            session.setAttribute("role", "tutor"); // Thiết lập role dạng chuỗi
                        }
                    }

                    System.out.println("Login successful. Account ID: " + acc.getId());
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    request.setAttribute("error_login", "Email hoặc mật khẩu không đúng, hoặc tài khoản chưa kích hoạt.");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } else if ("register".equals(action)) {
                String email = request.getParameter("email");
                String pass = request.getParameter("pass");
                String name = request.getParameter("name");
                String birthStr = request.getParameter("birth");
                LocalDate birth = LocalDate.parse(birthStr);
                String description = request.getParameter("description"); // Đổi từ describe thành description

                if (email == null || email.trim().isEmpty() || pass == null || pass.trim().isEmpty()
                        || name == null || name.trim().isEmpty() || birth == null || birthStr.trim().isEmpty()) {
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
                    String idAcc = accountDAO.generateAccountId();
                    Account acc = new Account(idAcc, email, pass, 1, "inactive", null); // role = 1, status = inactive
                    accountDAO.insertAccount(acc);

                    // Forward đến StudentController để lưu student

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
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi không xác định: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}