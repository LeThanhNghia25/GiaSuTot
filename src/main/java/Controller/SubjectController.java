package Controller;

import DAO.SubjectDAO;
import model.Subject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/subject")
public class SubjectController extends HttpServlet {
    private SubjectDAO subjectDAO;

    public void init() {
        try {
            subjectDAO = new SubjectDAO();
            System.out.println("SubjectDAO initialized successfully at " + new java.util.Date());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                // Hiển thị danh sách môn học
                List<Subject> subjects = subjectDAO.getAllSubjects();
                System.out.println("Subjects retrieved: " + subjects.size());
                request.setAttribute("subjects", subjects);
                request.getRequestDispatcher("/admin/subject-management.jsp").forward(request, response);
            } else if (action.equals("add")) {
                // Hiển thị form thêm môn học
                request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                // Hiển thị form sửa môn học
                int id = Integer.parseInt(request.getParameter("id"));
                Subject subject = subjectDAO.getSubjectById(id);
                if (subject != null) {
                    request.setAttribute("subject", subject);
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Môn học không tồn tại.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                }
            } else if (action.equals("delete")) {
                // Ẩn môn học
                int id = Integer.parseInt(request.getParameter("id"));
                subjectDAO.hideSubject(id);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            } else if (action.equals("restore")) {
                // Hiện môn học
                int id = Integer.parseInt(request.getParameter("id"));
                subjectDAO.restoreSubject(id);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error processing action: " + e.getMessage());
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("add")) {
                // Thêm môn học
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int status = Integer.parseInt(request.getParameter("status"));
                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "Tên môn học không được để trống.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }
                Subject subject = new Subject(0, name, description, status);
                subjectDAO.addSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            } else if (action.equals("edit")) {
                // Sửa môn học
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int status = Integer.parseInt(request.getParameter("status"));
                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "Tên môn học không được để trống.");
                    request.setAttribute("subject", new Subject(id, name, description, status));
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                    return;
                }
                Subject subject = new Subject(id, name, description, status);
                subjectDAO.updateSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error processing POST action: " + e.getMessage());
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
}