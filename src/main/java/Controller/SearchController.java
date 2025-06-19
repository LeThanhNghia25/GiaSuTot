package Controller;

import DAO.SearchDAO;
import model.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/searchServlet")
public class SearchController extends HttpServlet {
    private SearchDAO searchDAO = new SearchDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        if (search == null || search.trim().isEmpty()) {
            search = "";
        }
        try {
            List<Course> searchCourses = searchDAO.findBySubjectName(search);
            request.setAttribute("allCourses", searchCourses);
            request.setAttribute("search", search);
            request.getRequestDispatcher("/courses.jsp").forward(request, response); // Chuyển về courses.jsp
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}