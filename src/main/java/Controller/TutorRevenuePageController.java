package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/tutor-revenue")
public class TutorRevenuePageController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session (giống cách khác)
        String role = (String) request.getSession().getAttribute("role");
        if (role == null || !role.equals("tutor")) {
            response.sendRedirect(request.getContextPath() + "/account?action=login");
            return;
        }
        // Chuyển hướng đến JSP
        request.getRequestDispatcher("/tutor-revenue.jsp").forward(request, response);
    }
}