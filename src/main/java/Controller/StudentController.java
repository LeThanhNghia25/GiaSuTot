package Controller;

import DAO.StudentDAO;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "StudentController", urlPatterns = {"/student"})
public class StudentController extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        try {
            studentDAO = new StudentDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            request.setAttribute("error", "Vui lòng đăng nhập để xem thông tin cá nhân.");
            request.getRequestDispatcher("/account?action=login").forward(request, response);
            return;
        }

        try {
            Student student = studentDAO.getStudentByAccountId(account.getId());
            if (student == null) {
                request.setAttribute("error", "Không tìm thấy thông tin học viên.");
            } else {
                request.setAttribute("student", student);
            }
            request.getRequestDispatcher("/student_profile.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lấy thông tin học viên: " + e.getMessage());
            request.getRequestDispatcher("/student_profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("update".equals(action)) {
            handleUpdateStudent(request, response);
        } else {
            handleRegisterStudent(request, response);
        }
    }

    private void handleUpdateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // Lấy thông tin từ form
            String id = request.getParameter("id"); // Đổi id_st thành id
            String name = request.getParameter("name");
            LocalDate birth = LocalDate.parse(request.getParameter("birth"));
            String description = request.getParameter("description"); // Đổi describe thành description
            String email = request.getParameter("email");

            // Kiểm tra session để lấy tài khoản đã đăng nhập
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để chỉnh sửa thông tin.");
                request.getRequestDispatcher("/account?action=login").forward(request, response);
                return;
            }

            // Lấy account_id từ tài khoản đã đăng nhập
            String accountId = account.getId(); // Đổi id_acc thành accountId

            // Kiểm tra xem học viên có tồn tại không
            Student existingStudent = studentDAO.getStudentByAccountId(accountId);
            if (existingStudent == null) {
                request.setAttribute("error", "Không tìm thấy thông tin học viên.");
                request.getRequestDispatcher("/student_profile.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng Student và cập nhật thông tin
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setBirth(birth);
            student.setaccount_id(description); // Đổi setDescribe thành setDescription
            student.setaccount_id(accountId); // Đổi setAccountId(account) thành setAccountId(accountId)

            // Cập nhật thông tin trong cơ sở dữ liệu
            studentDAO.updateStudent(student);

            // Cập nhật lại thông tin và chuyển về profile
            request.setAttribute("student", studentDAO.getStudentByAccountId(accountId));
            request.getRequestDispatcher("/student_profile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi cập nhật thông tin: " + e.getMessage());
            request.getRequestDispatcher("/student_profile.jsp").forward(request, response);
        }
    }

    private void handleRegisterStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = (String) request.getAttribute("name");
        LocalDate birth = (LocalDate) request.getAttribute("birth");
        String description = (String) request.getAttribute("description"); // Đổi describe thành description
        String accountId = (String) request.getAttribute("account_id"); // Đổi id_acc thành account_id

        try {
            Student student = new Student();
            student.setName(name);
            student.setBirth(birth);
            student.setDescribe(description); // Đổi setDescribe thành setDescription
            student.setaccount_id(accountId); // Đổi setAccountId(acc) thành setAccountId(accountId)

            studentDAO.insertStudent(student);
            request.getSession().setAttribute("success", "Đăng ký thành công, vui lòng đăng nhập.");
            response.sendRedirect(request.getContextPath() + "/account?action=login");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error_register", "Lỗi lưu thông tin sinh viên: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}