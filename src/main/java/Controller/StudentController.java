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
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Lấy dữ liệu được set attribute từ AccountController
//        String name = (String) request.getAttribute("name");
//        LocalDate birth = (LocalDate) request.getAttribute("birth");
//        String describe = (String) request.getAttribute("describe");
//        String id_acc = (String) request.getAttribute("id_acc");
//
//        try {
//            Student student = new Student();
//            student.setId(studentDAO.generateStudentId());
//            student.setName(name);
//            student.setBirth(birth);
//            student.setDescribe(describe);
//
//            Account acc = new Account();
//            acc.setId(id_acc);
//            student.setAccountId(acc);
//
//
//            studentDAO.insertStudent(student);
//
//            // Sau khi lưu thành công, chuyển về trang login với thông báo thành công
//            request.getSession().setAttribute("success", "Đăng ký thành công, vui lòng đăng nhập.");
//            response.sendRedirect(request.getContextPath() + "/account?action=login");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            request.setAttribute("error_register", "Lỗi lưu thông tin sinh viên: " + e.getMessage());
//            request.getRequestDispatcher("/login.jsp").forward(request, response);
//        }
//    }
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
            String id_st = request.getParameter("id_st");
            String name = request.getParameter("name");
            LocalDate birth = LocalDate.parse(request.getParameter("birth"));
            String describe = request.getParameter("describe");
            String email = request.getParameter("email");

            // Kiểm tra session để lấy tài khoản đã đăng nhập
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account == null) {
                request.setAttribute("error", "Vui lòng đăng nhập để chỉnh sửa thông tin.");
                request.getRequestDispatcher("/account?action=login").forward(request, response);
                return;
            }

            // Lấy id_acc từ tài khoản đã đăng nhập
            String id_acc = account.getId();

            // Kiểm tra xem học viên có tồn tại không
            Student existingStudent = studentDAO.getStudentByAccountId(id_acc);
            if (existingStudent == null) {
                request.setAttribute("error", "Không tìm thấy thông tin học viên.");
                request.getRequestDispatcher("student_profile.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng Student và cập nhật thông tin
            Student student = new Student();
            student.setId(id_st);
            student.setName(name);
            student.setBirth(birth);
            student.setDescribe(describe);
            student.setAccountId(account);

            // Cập nhật thông tin trong cơ sở dữ liệu
            studentDAO.updateStudent(student);

            // Cập nhật lại thông tin và chuyển về profile
            request.setAttribute("student", studentDAO.getStudentByAccountId(id_acc));
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
        String describe = (String) request.getAttribute("describe");
        String id_acc = (String) request.getAttribute("id_acc");

        try {
            Student student = new Student();
            student.setId(studentDAO.generateStudentId());
            student.setName(name);
            student.setBirth(birth);
            student.setDescribe(describe);

            Account acc = new Account();
            acc.setId(id_acc);
            student.setAccountId(acc);

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