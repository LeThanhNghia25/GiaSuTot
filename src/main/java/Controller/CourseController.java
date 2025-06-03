package Controller;

import DAO.CourseDAO;
import model.Course;
import model.Subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/searchServlet")
public class CourseController extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        if (search == null || search.trim().isEmpty()) {
            search = ""; // Mặc định nếu không nhập gì
        }

        try {
            // Gọi DAO để tìm kiếm khóa học theo tên môn học
            HashMap<Course, Subject> resultMap = courseDAO.FindByName(search);

            // Gửi dữ liệu sang JSP để hiển thị
            request.setAttribute("resultMap", resultMap);
            request.setAttribute("search", search);

            request.getRequestDispatcher("/FindCourse.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Gửi POST -> xử lý như GET
    }
}
