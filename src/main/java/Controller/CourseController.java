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
    private CourseDAO courseDAO = new CourseDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subName = request.getParameter("subName");
        if (subName == null || subName.trim().isEmpty()) {
            subName = ""; // Giá trị mặc định
        }
        try {
            HashMap<Course, Subject> resultMap = courseDAO.FindByName(subName);

            request.setAttribute("resultMap", resultMap);
            request.setAttribute("subName", subName);
            request.getRequestDispatcher("/FindCourse.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Xử lý POST giống GET
    }

}

