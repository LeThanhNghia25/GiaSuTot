package Controller;

import DAO.CourseDAO;
import DAO.LessonDAO;
import DAO.StudentDAO;
import DAO.TutorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Course;
import model.Student;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "lessontController", urlPatterns = {"/lesson"})
public class LessonController extends HttpServlet {


//    @Override
//    public void init() {
//        System.out.println("Initializing TutorController");
//        try {
//            tutorDAO = new TutorDAO();
//        } catch (Exception e) {
//            System.err.println("Failed to initialize TutorDAO: " + e.getMessage());
//            throw new RuntimeException("Database connection error during initialization", e);
//        }
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            System.out.println("No account found in session, redirecting to login.");
            response.sendRedirect("login.jsp");
            return;
        }

        String accountId = account.getId();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = tutorDAO.getTutorByAccountId(accountId);
        LessonDAO lessonDAO = new LessonDAO();
        List<String> IdStudenList = null;
        List<Course> courseList = null;
        try {
            IdStudenList = lessonDAO.getListStudentIdByTutor(tutor);
            StudentDAO studentDAO = new StudentDAO();
            CourseDAO courseDAO = new CourseDAO();
            courseList= courseDAO.FindByIdTutor(tutor.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Student> studentList = new ArrayList<>();
        for (String id : IdStudenList) {
            StudentDAO studentDAO2 = new StudentDAO();
            Student student = null;
            try {
                student = studentDAO2.getStudentById(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            studentList.add(student);
        }
        request.setAttribute("listcourse", courseList);
        request.setAttribute("studentList", studentList);
        request.setAttribute("StIDList", IdStudenList);
        request.setAttribute("tutor", tutor);

        RequestDispatcher dispatcher = request.getRequestDispatcher("subjectManagement.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//            String studentId = request.getParameter("idStudent");
//            StudentDAO studentDAO = new StudentDAO();
//            Student student = null;
//        try {
//            student = studentDAO.getStudentById(studentId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//            request.setAttribute("studentN", student.getName());
//            RequestDispatcher dispatcher = request.getRequestDispatcher("subjectManagement.jsp");
//            dispatcher.forward(request, response);



    }

}
