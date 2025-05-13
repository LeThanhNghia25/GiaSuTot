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
public class TutorController extends HttpServlet {
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        System.out.println("Initializing TutorController");
        tutorDAO = new TutorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Handling GET request for /profile");
        String id_tutor = "tut001"; // Hardcode id_tutor để test
        System.out.println("Fetching tutor with id: " + id_tutor);
        Tutor tutor = tutorDAO.getTutorById(id_tutor); // Truy vấn thông tin gia sư từ database
        System.out.println("Tutor fetched: " + (tutor != null ? tutor.getName() : "null"));

        if (tutor == null) {
            System.out.println("No tutor found with id: " + id_tutor);
        } else {
            System.out.println("Tutor found: " + tutor.getName());
        }

        request.setAttribute("tutor", tutor); // Gắn tutor vào request
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

}