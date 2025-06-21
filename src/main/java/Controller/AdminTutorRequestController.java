package Controller;

import DAO.TutorRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AdminTutorRequestController", urlPatterns = {"/admin/tutor-requests"})
public class AdminTutorRequestController extends HttpServlet {
    private final TutorRequestDAO dao = new TutorRequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            request.setAttribute("requests", dao.getAllTutorRequests());
            request.getRequestDispatcher("/admin/tutor_requests.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Lỗi khi tải yêu cầu");
        }
    }
}
