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
    private InterestDAO interestDAO;

    @Override
    public void init() {
        interestDAO = new InterestDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String studentId = (String) request.getSession().getAttribute("id_st");
        String tutorId = request.getParameter("tutorId");

        System.out.println("===> StudentId: " + studentId);
        System.out.println("===> TutorId: " + tutorId);

        // Kiểm tra đăng nhập
        if (studentId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("not_logged_in");
            return;
        }

        // Kiểm tra tutorId
        if (tutorId == null || tutorId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("invalid_tutor_id");
            return;
        }

        try {
            boolean hasLiked = interestDAO.hasInterest(studentId, tutorId);

            if (hasLiked) {
                interestDAO.deleteInterest(studentId, tutorId);
                System.out.println("===> Đã hủy quan tâm");
                response.getWriter().write("unliked");
            } else {
                interestDAO.insertInterest(studentId, tutorId);
                System.out.println("===> Đã thêm quan tâm");
                response.getWriter().write("liked");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("error");
        }
    }
}
