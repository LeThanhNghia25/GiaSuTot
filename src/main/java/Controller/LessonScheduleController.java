package Controller;

import DAO.LessonDAO;
import DAO.TutorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
import model.Lesson;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "scheduleController", urlPatterns = {"/schedule"})
public class LessonScheduleController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account account = (Account) request.getSession().getAttribute("account");
        String accountId = account.getId();
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = null;
        tutor = tutorDAO.getTutorByAccountId(accountId);
        LessonDAO lessonDAO = new LessonDAO();
        List<Lesson> lessons = new ArrayList<>();
        try {
           lessons = lessonDAO.getListLessonByTutor(tutor);
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("lessons",lessons );
        request.setAttribute("tutor", tutor);

        RequestDispatcher dispatcher = request.getRequestDispatcher("tutor_created_lesson.jsp");
        dispatcher.forward(request, response);
    }
}
