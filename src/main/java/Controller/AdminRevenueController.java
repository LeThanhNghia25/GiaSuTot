package Controller;

import DAO.AdminRevenueDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/revenue-data")
public class AdminRevenueController extends HttpServlet {
    private AdminRevenueDAO revenueDAO;

    @Override
    public void init() {
        revenueDAO = new AdminRevenueDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String type = request.getParameter("type");
            int year = request.getParameter("year") != null ? Integer.parseInt(request.getParameter("year")) : 2025;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if ("subject".equals(type)) {
                Map<String, Double> revenueBySubject = revenueDAO.getRevenueBySubject(year); // Truyền year vào
                String json = new Gson().toJson(revenueBySubject);
                response.getWriter().write(json);
            } else {
                Map<String, Double> monthlyRevenue = revenueDAO.getMonthlyRevenue(year);
                String json = new Gson().toJson(monthlyRevenue);
                response.getWriter().write(json);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid year parameter");
        }
    }
}