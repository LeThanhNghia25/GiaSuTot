package Controller;

import DAO.AdminPaymentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/payment-detail")
public class AdminPaymentDetailController extends HttpServlet {
    private AdminPaymentDAO paymentDAO;

    @Override
    public void init() {
        paymentDAO = new AdminPaymentDAO();
        System.out.println("AdminPaymentDetailController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");

        try {
            // Giả sử lấy thông tin chi tiết (có thể mở rộng trong DAO)
            request.setAttribute("courseId", courseId);
            request.setAttribute("studentId", studentId);
            request.setAttribute("fileName", request.getParameter("fileName"));
            request.setAttribute("filePath", request.getParameter("filePath"));
            request.getRequestDispatcher("/admin/payment-detail.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("Error fetching payment detail: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/payment-list?message=Lỗi khi xem chi tiết biên lai");
        }
    }
}