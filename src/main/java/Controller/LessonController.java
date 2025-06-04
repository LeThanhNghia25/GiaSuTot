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
import model.Course;
import model.Tutor;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "lessontController", urlPatterns = {"/lesson"})
public class LessonController extends HttpServlet {
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        System.out.println("Initializing TutorController");
        try {
            tutorDAO = new TutorDAO();
        } catch (Exception e) {
            System.err.println("Failed to initialize TutorDAO: " + e.getMessage());
            throw new RuntimeException("Database connection error during initialization", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Handling GET request for /profile");

        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            System.out.println("No account found in session, redirecting to login.");
            response.sendRedirect("login.jsp");
            return;
        }

        String accountId = account.getId();
        System.out.println("Fetching tutor with account id: " + accountId);

        Tutor tutor = tutorDAO.getTutorByAccountId(accountId);
        LessonDAO lessonDAO = new LessonDAO();
        List<String> IdStudenList = null;
        try {
            IdStudenList = lessonDAO.getListStudentIdByTutor(tutor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("StIDList", IdStudenList);
        request.setAttribute("tutor", tutor);

        RequestDispatcher dispatcher = request.getRequestDispatcher("subjectManagement.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        }

}
