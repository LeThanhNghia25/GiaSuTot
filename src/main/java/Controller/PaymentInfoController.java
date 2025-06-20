package Controller;

import DAO.CourseDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Course;

import java.io.IOException;

@WebServlet("/payment-info")
public class PaymentInfoController extends HttpServlet {
    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
        System.out.println("PaymentInfoController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        if (courseId == null || studentId == null) {
            response.sendRedirect(request.getContextPath() + "/courses?message=Dữ liệu không hợp lệ");
            return;
        }

        try {
            Course course = courseDAO.getCourseById(courseId);
            if (course != null) {
                request.setAttribute("course", course);
                request.setAttribute("studentId", studentId);
                request.setAttribute("amount", course.getSubject().getFee());
            } else {
                request.setAttribute("message", "Khóa học không tồn tại");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/payment-info.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error fetching course: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/courses?message=Lỗi khi tải thông tin thanh toán");
        }
    }
}