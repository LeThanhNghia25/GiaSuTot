package Controller;

import DAO.LessonDAO;
import DAO.RegisteredSubjectDAO;
import DAO.StudentDAO;
import DAO.CourseDAO;
import DAO.TutorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Student;
import model.Tutor;
import model.RegisteredSubjects;
import model.Course;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet(name = "lessonController", urlPatterns = {"/lesson"})
public class LessonController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received GET request for /lesson");
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            System.out.println("No account found in session, redirecting to login.");
            response.sendRedirect("login.jsp");
            return;
        }

        String accountId = account.getId();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = null;
        tutor = tutorDAO.getTutorByAccountId(accountId);

        if (tutor == null) {
            System.out.println("Tutor not found for account: " + accountId);
            response.sendRedirect("error.jsp");
            return;
        }

        LessonDAO lessonDAO = new LessonDAO();
        List<String> idStudentList = null;
        List<Student> studentList = new ArrayList<>();
        try {
            idStudentList = lessonDAO.getListStudentIdByTutor(tutor);
            if (idStudentList != null && !idStudentList.isEmpty()) {
                StudentDAO studentDAO = new StudentDAO();
                for (String id : idStudentList) {
                    Student student = studentDAO.getStudentById(id);
                    if (student != null) {
                        studentList.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Error retrieving students", e);
        }

        request.setAttribute("studentList", studentList);
        request.setAttribute("StIDList", idStudentList);
        request.setAttribute("tutor", tutor);

        RequestDispatcher dispatcher = request.getRequestDispatcher("TutorSubjectManagement.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Received POST request for /lesson"); // Debug
        String studentId = request.getParameter("studentId");
        Map<String, Object> responseData = new HashMap<>();

        System.out.println("Received studentId in doPost: " + studentId);

        if (studentId != null && !studentId.isEmpty()) {
            StudentDAO studentDAO = new StudentDAO();
            RegisteredSubjectDAO registeredSubjectDAO = new RegisteredSubjectDAO();
            CourseDAO courseDAO = new CourseDAO();
            try {
                System.out.println("Fetching student with ID: " + studentId);
                Student student = studentDAO.getStudentById(studentId);
                if (student != null) {
                    System.out.println("Student found: " + student.getName());
                    System.out.println("Fetching registered subjects...");
                    List<RegisteredSubjects> registeredSubjectsList = registeredSubjectDAO.getAllReSubject();
                    System.out.println("Total registered subjects: " + registeredSubjectsList.size());
                    RegisteredSubjects registeredSubject = null;
                    for (RegisteredSubjects rs : registeredSubjectsList) {
                        if (rs.getStudent_id().equals(studentId) && "registered".equals(rs.getStatus())) {
                            registeredSubject = rs;
                            System.out.println("Found registered subject for studentId: " + studentId + ", courseId: " + rs.getCourse_id());
                            break;
                        }
                    }
                    if (registeredSubject != null) {
                        System.out.println("Fetching course with ID: " + registeredSubject.getCourse_id());
                        Course course = courseDAO.getCourseById(registeredSubject.getCourse_id());
                        if (course != null && course.getSubject() != null) {
                            System.out.println("Course found: " + course.getId() + ", subject: " + course.getSubject().getName());
                            responseData.put("status", "success");
                            responseData.put("studentName", student.getName());
                            responseData.put("subject", mapSubjectToDropdownValue(course.getSubject().getName()));
                        } else {
                            responseData.put("status", "error");
                            responseData.put("message", "Không tìm thấy khóa học hoặc môn học cho course_id: " + registeredSubject.getCourse_id());
                            System.out.println("Course or subject not found for course_id: " + registeredSubject.getCourse_id());
                        }
                    } else {
                        responseData.put("status", "error");
                        responseData.put("message", "Không tìm thấy khóa học registered cho học viên với studentId: " + studentId);
                        System.out.println("No registered subject found for studentId: " + studentId);
                    }
                } else {
                    responseData.put("status", "error");
                    responseData.put("message", "Không tìm thấy học viên với mã: " + studentId);
                    System.out.println("Student not found for studentId: " + studentId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                responseData.put("status", "error");
                responseData.put("message", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
                System.out.println("SQLException in doPost: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                responseData.put("status", "error");
                responseData.put("message", "Lỗi bất ngờ: " + e.getMessage());
                System.out.println("Unexpected error in doPost: " + e.getMessage());
            }
        } else {
            responseData.put("status", "error");
            responseData.put("message", "Mã học viên không hợp lệ");
            System.out.println("Invalid studentId received: " + studentId);
        }

        // Trả về JSON
        String jsonResponse = new Gson().toJson(responseData);
        System.out.println("Returning response: " + jsonResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    private String mapSubjectToDropdownValue(String subjectName) {
        if (subjectName == null) return "other";
        switch (subjectName.toLowerCase()) {
            case "toán học": return "math";
            case "vật lý": return "physics";
            case "hóa học": return "chemistry";
            case "ngữ văn": return "literature";
            case "tiếng anh": return "english";
            default: return "other";
        }
    }
}