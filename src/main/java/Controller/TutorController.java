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

        // Lấy đối tượng Account từ session
        model.Account account = (model.Account) request.getSession().getAttribute("account");

        if (account == null) {
            System.out.println("No account found in session, redirecting to login.");
            response.sendRedirect("login.jsp"); // Chuyển hướng nếu chưa đăng nhập
            return;
        }

        String id_acc = account.getId(); // Lấy id_acc từ Account
        System.out.println("Fetching tutor with account id: " + id_acc);

        Tutor tutor = tutorDAO.getTutorByAccountId(id_acc); // Truy vấn thông tin Tutor theo id_acc

        if (tutor == null) {
            System.out.println("No tutor found with account id: " + id_acc);
            request.setAttribute("error", "Không tìm thấy thông tin gia sư.");
        } else {
            System.out.println("Tutor found: " + tutor.getName());
            request.setAttribute("tutor", tutor);
        }

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
            String describe_tutor = request.getParameter("describe_tutor");
            int cccd = Integer.parseInt(request.getParameter("cccd"));
            int bank_code = Integer.parseInt(request.getParameter("bank_code"));
            String bank_name = request.getParameter("bank_name");
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


            Tutor updatedTutor = new Tutor(id_tutor, name, email, birth, phone, address, specialization, describe_tutor, cccd, bank_code, bank_name, 0);
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