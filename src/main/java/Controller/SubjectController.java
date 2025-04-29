package Controller;

import DAO.SubjectDAO;
import model.Subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/subject")
public class SubjectController extends HttpServlet {
    private SubjectDAO subjectDAO;

    public void init() {
        try {
            subjectDAO = new SubjectDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action == null ? "list" : action) {
                case "add":
                    request.getRequestDispatcher("/subject/add.jsp").forward(request, response);
                    break;
                case "edit":
                    int id = Integer.parseInt(request.getParameter("id"));
                    Subject subject = subjectDAO.getSubjectById(id);
                    request.setAttribute("subject", subject);
                    request.getRequestDispatcher("/subject/edit.jsp").forward(request, response);
                    break;
                case "delete":
                    subjectDAO.deleteSubject(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("subject");
                    break;
                default:
                    List<Subject> list = subjectDAO.getAllSubjects();
                    request.setAttribute("subjects", list);
                    request.getRequestDispatcher("/subject/list.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            Subject s = new Subject();
            s.setName(request.getParameter("name"));
            s.setDescription(request.getParameter("description"));

            if (idStr == null || idStr.isEmpty()) {
                subjectDAO.addSubject(s);
            } else {
                s.setId(Integer.parseInt(idStr));
                subjectDAO.updateSubject(s);
            }
            response.sendRedirect("subject");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
