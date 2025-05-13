package Controller;

import DAO.TutorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tutor;
import java.io.IOException;

@WebServlet("/tutor")
public class TutorServlet extends HttpServlet {
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        tutorDAO = new TutorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tutorId từ session hoặc request
        int tutorId = Integer.parseInt(request.getParameter("id")); // hoặc lấy từ session

        Tutor tutor = tutorDAO.getTutorById(tutorId);
        request.setAttribute("tutor", tutor);

        RequestDispatcher dispatcher = request.getRequestDispatcher("tutor-profile.jsp");
        dispatcher.forward(request, response);
    }
}
