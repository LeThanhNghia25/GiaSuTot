package Controller;

import DAO.StudentDAO;
import model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/studentInfo")
public class StudentInfoServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String idst = request.getParameter("id");
       StudentDAO studentDAO = new StudentDAO();
       Student student = null;
       try {
           student = studentDAO.getStudentById(idst);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       request.setAttribute("student", student);
       request.getRequestDispatcher("subjectManagement.jsp").forward(request,response);
    }
}