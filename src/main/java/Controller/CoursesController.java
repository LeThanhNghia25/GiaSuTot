package Controller;

import DAO.CourseDAO;
import DAO.SearchDAO;
import DAO.StudentDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Course;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/courses")
public class CoursesController extends HttpServlet {
    private CourseDAO courseDAO;
    private SearchDAO searchDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        courseDAO = new CourseDAO();
        searchDAO = new SearchDAO();
        studentDAO = new StudentDAO();
        System.out.println("CoursesController initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received GET request for /courses");

        String tenMon = request.getParameter("tenMon");
        String trinhDo = request.getParameter("trinhDo");
        String pageParam = request.getParameter("page");
        String search = request.getParameter("search");

        long startTime = System.currentTimeMillis();
        List<Course> allCourses = new ArrayList<>();
        try {
            if (search != null && !search.trim().isEmpty()) {
                allCourses = searchDAO.findBySubjectName(search);
            } else {
                allCourses = courseDAO.getAllAvailableCourses();
            }
        } catch (SQLException e) {
            System.err.println("Error fetching courses: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching courses");
            return;
        }
        long fetchTime = System.currentTimeMillis() - startTime;
        System.out.println("Time to fetch courses: " + fetchTime + "ms");

        List<Course> filteredCourses = allCourses.stream()
                .filter(course -> {
                    boolean match = true;
                    if (tenMon != null && !tenMon.trim().isEmpty()) {
                        match = match && SearchDAO.removeDiacritics(course.getSubject().getName().toLowerCase())
                                .contains(SearchDAO.removeDiacritics(tenMon.toLowerCase()));
                    }
                    if (trinhDo != null && !trinhDo.trim().isEmpty()) {
                        match = match && SearchDAO.removeDiacritics(course.getSubject().getLevel().toLowerCase())
                                .contains(SearchDAO.removeDiacritics(trinhDo.toLowerCase()));
                    }
                    return match;
                })
                .collect(Collectors.toList());

        int page = (pageParam != null && !pageParam.trim().isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int pageSize = 9;
        int totalCourses = filteredCourses.size();
        int totalPages = (int) Math.ceil((double) totalCourses / pageSize);
        page = Math.max(1, Math.min(page, totalPages));

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalCourses);
        List<Course> paginatedCourses = filteredCourses.subList(start, end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        paginatedCourses.forEach(course -> {
            String formattedTime = course.getTime().format(formatter);
            request.setAttribute("formattedTime_" + course.getId(), formattedTime);
        });

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }

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
        System.out.println("StudentId from session: " + studentId);
        if (studentId == null) {
            // Nếu studentId chưa có, thử lấy lại từ accountId
            StudentDAO studentDAO = new StudentDAO();
            try {
                Student student = studentDAO.getStudentByAccountId(account.getId());
                if (student != null) {
                    studentId = student.getId();
                    session.setAttribute("studentId", studentId);
                    System.out.println("Recovered studentId: " + studentId);
                } else {
                    System.out.println("No student record found for account: " + account.getId());
                    session.setAttribute("message", "Dữ liệu học sinh không hợp lệ, vui lòng liên hệ admin");
                    response.sendRedirect(request.getContextPath() + "/courses");
                    return;
                }
            } catch (SQLException e) {
                System.err.println("Error fetching student: " + e.getMessage());
                session.setAttribute("message", "Lỗi khi lấy thông tin học sinh");
                response.sendRedirect(request.getContextPath() + "/courses");
                return;
            }
        }

        if (courseId != null && studentId != null) {
            try {
                if ("register".equalsIgnoreCase(action)) {
                    System.out.println("Registering course: " + courseId + " for student: " + studentId);
                    courseDAO.registerCourse(courseId, studentId);
                    System.out.println("Course registered, redirecting to payment info");
                    response.sendRedirect(request.getContextPath() + "/payment-info?courseId=" + courseId + "&studentId=" + studentId);
                } else if ("trial".equalsIgnoreCase(action)) {
                    System.out.println("Registering trial for course: " + courseId + " for student: " + studentId);
                    courseDAO.registerTrial(courseId, studentId);
                    session.setAttribute("message", "Đăng ký học thử thành công!");
                    response.sendRedirect(request.getContextPath() + "/courses");
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