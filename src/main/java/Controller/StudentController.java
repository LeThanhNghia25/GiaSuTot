package Controller;

import DAO.StudentDAO;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu được set attribute từ AccountController
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
            student.setAccountId(id_acc);

            studentDAO.insertStudent(student);

            // Sau khi lưu thành công, chuyển về trang login với thông báo thành công
            request.getSession().setAttribute("success", "Đăng ký thành công, vui lòng đăng nhập.");
            response.sendRedirect(request.getContextPath() + "/account?action=login");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error_register", "Lỗi lưu thông tin sinh viên: " + e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}