package Controller;

import DAO.CourseDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/courses")
public class CourseListController extends HttpServlet {
    private CourseDAO courseDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
        System.out.println("CourseListController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received GET request for /courses");

        // Lấy tham số lọc từ request
        String tenMon = request.getParameter("tenMon");
        String lop = request.getParameter("lop");
        String tinh = request.getParameter("tinh");

        // Lấy danh sách tất cả khóa học
        List<Course> allCourses = courseDAO.getAllAvailableCourses();

        // Áp dụng bộ lọc
        if (allCourses != null) {
            List<Course> filteredCourses = allCourses.stream()
                    .filter(course -> {
                        boolean match = true;
                        if (tenMon != null && !tenMon.trim().isEmpty()) {
                            match = match && course.getSubject().getName().toLowerCase().contains(tenMon.toLowerCase());
                        }
                        if (lop != null && !lop.trim().isEmpty()) {
                            match = match && course.getSubject().getLevel().equalsIgnoreCase("Lớp " + lop);
                        }
                        if (tinh != null && !tinh.trim().isEmpty()) {
                            match = match && course.getTutor().getAddress().toLowerCase().contains(tinh.toLowerCase());
                        }
                        return match;
                    })
                    .collect(Collectors.toList());

            allCourses = filteredCourses;
        }

        System.out.println("Number of available courses after filtering: " + (allCourses != null ? allCourses.size() : "null"));
        request.setAttribute("allCourses", allCourses);

        // Xử lý phân trang
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 15; // 5 hàng x 3 cột = 15 khóa học mỗi trang
        int totalCourses = allCourses != null ? allCourses.size() : 0;
        int totalPages = (int) Math.ceil((double) totalCourses / pageSize);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalCourses);
        List<Course> paginatedCourses = allCourses != null && !allCourses.isEmpty() ? allCourses.subList(start, end) : new ArrayList<>();

        request.setAttribute("allCourses", paginatedCourses);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        System.out.println("Forwarding to courses.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/courses.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received POST request for /courses");
        String action = request.getParameter("action");
        String courseId = request.getParameter("courseId");
        String studentId = (String) request.getSession().getAttribute("studentId");

        if (courseId != null && studentId != null) {
            if ("register".equalsIgnoreCase(action)) {
                // Đăng ký khóa học chính thức
                courseDAO.registerCourse(courseId, studentId);
                response.sendRedirect(request.getContextPath() + "/courses?page=" + request.getParameter("page"));
            } else if ("trial".equalsIgnoreCase(action)) {
                // Đăng ký học thử
                courseDAO.registerTrial(courseId, studentId);
                response.sendRedirect(request.getContextPath() + "/courses?page=" + request.getParameter("page"));
            }
            return;
        }
        response.sendRedirect(request.getContextPath() + "/courses?page=" + request.getParameter("page"));
    }
}