package Controller;

import DAO.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.System.out;

@WebServlet(name = "StudentCourseController", urlPatterns = {"/studentCourse"})
public class StudentCourseController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String accountId = account.getId();
        StudentDAO studentDAO = new StudentDAO();
        RegisteredSubjectDAO registeredSubjectDAO = new RegisteredSubjectDAO();
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courseList = new ArrayList<>();
        Map<Course,String > data = new HashMap<>();
        try {
            Student student = studentDAO.getStudentByAccountId(accountId);
            List<RegisteredSubjects> registeredSubjects= registeredSubjectDAO.getRegisteredSubjectByID(student.getId());
            for (RegisteredSubjects rs : registeredSubjects) {
                Course course = new Course();
                course= courseDAO.getCourseById(rs.getCourse_id());
                courseList.add(course);
                if(rs.getStatus().equals("registered")){
                    int Progress = registeredSubjectDAO.course_Progress(student.getId(), course.getId());
                    String value= Progress+"/"+rs.getNumber_of_lessons();
                    data.put(course,value);
                }else {
                    if(rs.getStatus().equals("pending_payment")){
                        String value ="Chờ xác nhận thanh toán";
                        data.put(course, value);
                    }else {
                        if(rs.getStatus().equals("pending_approval")){
                            data.put(course,"Chờ gia sư xác nhận");
                        }else {
                        data.put(course, "Hoàn thành");}
                    }

                }
            }
            request.setAttribute("data", data);
            RequestDispatcher dispatcher = request.getRequestDispatcher("student_course.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
