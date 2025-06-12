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
import java.time.format.DateTimeFormatter;
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

        String tenMon = request.getParameter("tenMon");
        String lop = request.getParameter("lop");
        String tinh = request.getParameter("tinh");
        System.out.println("Filter parameters - tenMon: " + tenMon + ", lop: " + lop + ", tinh: " + tinh);

        long startTime = System.currentTimeMillis();
        List<Course> allCourses = null;
        try {
            allCourses = courseDAO.getAllAvailableCourses();
        } catch (Exception e) {
            System.err.println("Error fetching courses: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching courses");
            return;
        }
        long fetchTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to fetch courses: " + fetchTime + "ms");

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

        // Chuyển đổi LocalDateTime thành chuỗi trước khi gửi tới JSP
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (allCourses != null) {
            allCourses.forEach(course -> {
                String formattedTime = course.getTime().format(formatter);
                request.setAttribute("formattedTime_" + course.getId(), formattedTime);
            });
        }

        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 9;
        int totalCourses = allCourses != null ? allCourses.size() : 0;
        int totalPages = (int) Math.ceil((double) totalCourses / pageSize);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalCourses);
        List<Course> paginatedCourses = allCourses != null && !allCourses.isEmpty() ? allCourses.subList(start, end) : new ArrayList<>();

        request.setAttribute("allCourses", paginatedCourses);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        System.out.println("Forwarding to courses.jsp");
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/courses.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error forwarding to courses.jsp: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error rendering page");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received POST request for /courses");
        String action = request.getParameter("action");
        String courseId = request.getParameter("courseId");
        String studentId = (String) request.getSession().getAttribute("studentId");

        if (courseId != null && studentId != null) {
            try {
                if ("register".equalsIgnoreCase(action)) {
                    courseDAO.registerCourse(courseId, studentId);
                    response.sendRedirect(request.getContextPath() + "/courses?page=" + request.getParameter("page"));
                } else if ("trial".equalsIgnoreCase(action)) {
                    courseDAO.registerTrial(courseId, studentId);
                    response.sendRedirect(request.getContextPath() + "/courses?page=" + request.getParameter("page"));
                }
            } catch (Exception e) {
                System.err.println("Error processing POST request: " + e.getMessage());
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request");
            }
            return;
        }
        response.sendRedirect(request.getContextPath() + "/courses?page=" + request.getParameter("page"));
    }
}