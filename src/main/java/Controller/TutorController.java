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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        request.setAttribute("message", "Cập nhật thông tin thành công!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String id_tutor = request.getParameter("id_tutor");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String specialization = request.getParameter("specialization");
            String describeTutor = request.getParameter("describeTutor");

            String birthStr = request.getParameter("birth");
            Date birth = null;
            if (birthStr != null && !birthStr.trim().isEmpty()) {
                try {
                    birth = new SimpleDateFormat("yyyy-MM-dd").parse(birthStr);
                } catch (ParseException e) {
                    e.printStackTrace();  // hoặc log lỗi rõ hơn
                }
            } else {
                System.err.println("Birth date is null or empty.");
            }


            Tutor updatedTutor = new Tutor(id_tutor, name, email, birth, phone, address, specialization, describeTutor, 0);
            tutorDAO.updateTutor(updatedTutor);

            // Load lại thông tin từ DB sau khi cập nhật
            Tutor refreshedTutor = tutorDAO.getTutorById(id_tutor);
            request.setAttribute("tutor", refreshedTutor);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

}