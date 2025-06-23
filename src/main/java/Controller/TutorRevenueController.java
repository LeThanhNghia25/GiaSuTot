package Controller;

import DAO.TutorRevenueDAO;
import DAO.TutorDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/tutor-revenue")
public class TutorRevenueController extends HttpServlet {
    private TutorRevenueDAO revenueDAO;
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        try {
            revenueDAO = new TutorRevenueDAO();
            tutorDAO = new TutorDAO();
            System.out.println("TutorRevenueDAO initialized successfully");
        } catch (Exception e) {
            System.err.println("Failed to initialize TutorRevenueDAO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database connection error during initialization", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String accountId = (String) request.getSession().getAttribute("accountId");
            if (accountId == null) {
                response.sendRedirect(request.getContextPath() + "/account?action=login");
                return;
            }

            Tutor tutor = tutorDAO.getTutorByAccountId(accountId);
            if (tutor == null) {
                System.err.println("No tutor found for accountId: " + accountId);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tutor not found");
                return;
            }
            String tutorId = tutor.getId();
            System.out.println("Retrieved tutorId: " + tutorId + " for accountId: " + accountId);

            int year = request.getParameter("year") != null ? Integer.parseInt(request.getParameter("year")) : 2025;
            System.out.println("Received request with tutorId: " + tutorId + ", year: " + year);

            Map<String, Double> monthlyRevenue = revenueDAO.getMonthlyRevenueByTutor(tutorId, year);
            String jsonRevenue = new Gson().toJson(monthlyRevenue);
            request.setAttribute("monthlyRevenue", jsonRevenue);
            request.setAttribute("selectedYear", year);
            request.getRequestDispatcher("/tutor_revenue.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Database error in TutorRevenueController: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } catch (NumberFormatException e) {
            System.err.println("Invalid year parameter: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid year parameter");
        }
    }
}