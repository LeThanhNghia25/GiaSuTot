package Controller;

import DAO.TutorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;
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
        if (tutor == null) {
            System.out.println("No tutor found with account id: " + accountId);
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
            Account account = (Account) request.getSession().getAttribute("account");
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String accountId = account.getId();
            Tutor existingTutor = tutorDAO.getTutorByAccountId(accountId);
            if (existingTutor == null) {
                response.sendRedirect("error.jsp");
                return;
            }

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String specialization = request.getParameter("specialization");
            String description = request.getParameter("description"); // Đổi từ describe_tutor
            int idCardNumber = Integer.parseInt(request.getParameter("id_card_number")); // Đổi từ cccd
            int bankAccountNumber = Integer.parseInt(request.getParameter("bank_account_number")); // Đổi từ bank_code
            String bankName = request.getParameter("bank_name");
            String birthStr = request.getParameter("birth");
            Date birth = null;
            if (birthStr != null && !birthStr.trim().isEmpty()) {
                birth = new SimpleDateFormat("yyyy-MM-dd").parse(birthStr);
            }

            Tutor updatedTutor = new Tutor(
                    existingTutor.getId(), name, email, birth, phone, address, specialization,
                    description, idCardNumber, bankAccountNumber, bankName, accountId, existingTutor.getEvaluate()
            );
            tutorDAO.updateTutor(updatedTutor);

            Tutor refreshedTutor = tutorDAO.getTutorById(existingTutor.getId());
            request.setAttribute("tutor", refreshedTutor);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}