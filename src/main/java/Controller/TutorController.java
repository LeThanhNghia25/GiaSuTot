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
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;

@WebServlet("/tutor")
public class TutorController extends HttpServlet {
    private TutorDAO tutorDAO;

    @Override
    public void init() {
        System.out.println("Initializing TutorController");
        tutorDAO = new TutorDAO(); // Ngoại lệ sẽ được xử lý trong TutorDAO
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
            String description = request.getParameter("description");
            long idCardNumber = 0;
            long bankAccountNumber = 0;
            try {
                idCardNumber = Long.parseLong(request.getParameter("id_card_number"));
                bankAccountNumber = Long.parseLong(request.getParameter("bank_account_number"));
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Số CMND/CCCD hoặc số tài khoản không hợp lệ: " + e.getMessage());
                return;
            }
            String bankName = request.getParameter("bank_name");
            String birthStr = request.getParameter("birth");
            LocalDate birth = null;
            if (birthStr != null && !birthStr.trim().isEmpty()) {
                birth = LocalDate.parse(birthStr, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            Tutor updatedTutor = new Tutor(
                    existingTutor.getId(), name, email, birth, phone, address, specialization,
                    description, idCardNumber, bankAccountNumber, bankName, account, existingTutor.getEvaluate()
            );
            tutorDAO.updateTutor(updatedTutor);

            Tutor refreshedTutor = tutorDAO.getTutorById(existingTutor.getId());
            request.setAttribute("tutor", refreshedTutor);

            // Phản hồi JSON thành công
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Cập nhật thông tin thành công");
            responseData.put("tutor", refreshedTutor);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(responseData));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật thông tin: " + e.getMessage());
        }
    }
}