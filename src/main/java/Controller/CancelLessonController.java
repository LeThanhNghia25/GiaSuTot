package Controller;
import DAO.AddLessionDao;
import DAO.LessonDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;

@WebServlet(name = "CancelLessonController", urlPatterns = {"/cancelLesson"})
public class CancelLessonController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStudent = request.getParameter("studentId");
        String idCourse = request.getParameter("courseId");
        String timeLesson = request.getParameter("time");

        LessonDAO lessonDao = new LessonDAO();


        try {
            lessonDao.updateLesson(idCourse, idStudent, timeLesson);
            response.sendRedirect("schedule");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
