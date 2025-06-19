package Controller;

import DAO.CourseDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Course;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/courses")
public class CoursesController extends HttpServlet {
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (allCourses != null) {
            allCourses.forEach(course -> {
                String formattedTime = course.getTime().format(formatter);
                request.setAttribute("formattedTime_" + course.getId(), formattedTime);
            });
        }

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                System.err.println("Invalid page parameter: " + pageParam);
                page = 1;
            }
        }

        int pageSize = 9;
        int totalCourses = allCourses != null ? allCourses.size() : 0;
        int totalPages = (int) Math.ceil((double) totalCourses / pageSize);

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalCourses);
        List<Course> paginatedCourses = allCourses != null && !allCourses.isEmpty() ? allCourses.subList(start, end) : new ArrayList<>();

        // Lấy thông báo từ session nếu có
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message"); // Xóa sau khi sử dụng
        }

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
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            System.out.println("Session or account is null, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        System.out.println("Account role: " + (account != null ? account.getRole() : "null"));
        if (account == null || account.getRole() != 1) {
            System.out.println("Role check failed, redirecting with error");
            session.setAttribute("message", "Chức năng này chỉ dành cho học sinh");
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }

        String studentId = (String) session.getAttribute("studentId");
        System.out.println("StudentId: " + studentId);
        if (courseId != null && studentId != null) {
            try {
                if ("register".equalsIgnoreCase(action)) {
                    System.out.println("Registering course: " + courseId + " for student: " + studentId);
                    courseDAO.registerCourse(courseId, studentId);
                    System.out.println("Course registered, redirecting to payment");
                    response.sendRedirect(request.getContextPath() + "/payment?courseId=" + courseId);
                }
            } catch (RuntimeException e) {
                System.err.println("Error processing POST request: " + e.getMessage());
                session.setAttribute("message", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/courses");
            } catch (Exception e) {
                System.err.println("Error processing POST request: " + e.getMessage());
                e.printStackTrace();
                session.setAttribute("message", "Lỗi khi xử lý đăng ký: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/courses");
            }
        } else {
            System.out.println("CourseId or studentId is null");
            session.setAttribute("message", "Dữ liệu không hợp lệ, vui lòng kiểm tra đăng ký học sinh");
            response.sendRedirect(request.getContextPath() + "/courses");
        }
    }
}