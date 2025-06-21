package Controller;
import DAO.AddLessonDao;
import DAO.LessonDAO;
import DAO.ReviewDAO;
import DAO.TutorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;

@WebServlet(name = "ComfirmLessonController", urlPatterns = {"/confirmLesson"})
public class ConfirmLessonController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStudent = request.getParameter("lessonId");
        String idCourse = request.getParameter("courseId");
        String timeLesson = request.getParameter("time");
        String comments = request.getParameter("comments");
        Account account = (Account) request.getSession().getAttribute("account");
        String accountId = account.getId();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = null;
        tutor = tutorDAO.getTutorByAccountId(accountId);

        LessonDAO lessonDAO = new LessonDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            lessonDAO.updateLessonCompleted(idCourse,idStudent,timeLesson);
            reviewDAO.InsertReview(tutor.getId(),idCourse,idStudent,comments);
            response.sendRedirect("schedule");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
