package Controller;

import DAO.InterestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/add-interest")
public class AddInterestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = (String) request.getSession().getAttribute("id_st");
        String tutorId = request.getParameter("tutorId");

        System.out.println("===> StudentId: " + studentId);
        System.out.println("===> TutorId: " + tutorId);

        if (studentId != null && tutorId != null) {
            try {
                InterestDAO dao = new InterestDAO();
                dao.insertInterest(studentId, tutorId);
                System.out.println("===> Insert success");
                response.getWriter().write("success");
            } catch (Exception e) {
                System.out.println("===> Insert failed");
                e.printStackTrace(); // Quan trọng!
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("error");
            }
        } else {
            if (studentId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // ❗ Thêm dòng này
                response.getWriter().write("not_logged_in");
                return;
            }

            if (tutorId == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("error");
                return;
            }

            try {
                InterestDAO dao = new InterestDAO();
                dao.insertInterest(studentId, tutorId);
                response.getWriter().write("success");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("error");
            }
        }
    }


}

