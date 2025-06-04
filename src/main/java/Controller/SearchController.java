package Controller;

import DAO.SearchDAO;
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
public class SearchController extends HttpServlet {
    private SearchDAO searchDAO = new SearchDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        if (search == null || search.trim().isEmpty()) {
            search = ""; // Giá trị mặc định
        }
        try {
            HashMap<Course, Subject> resultMap = searchDAO.FindByName(search);

            request.setAttribute("resultMap", resultMap);
            request.setAttribute("search", search);
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

