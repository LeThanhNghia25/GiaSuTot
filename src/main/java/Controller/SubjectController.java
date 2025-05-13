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
                System.out.println("Fetching subjects at " + new java.util.Date());
                List<Subject> subjects = subjectDAO.getAllSubjects();
                System.out.println("Subjects size: " + (subjects != null ? subjects.size() : "null"));
                request.setAttribute("subjects", subjects);
                request.getRequestDispatcher("/admin/subject-management.jsp").forward(request, response);
            } else if (action.equals("add")) {
                request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                String id = request.getParameter("id");
                Subject subject = subjectDAO.getSubjectById(id);
                if (subject != null) {
                    request.setAttribute("subject", subject);
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Môn học không tồn tại.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                }
            } else if (action.equals("delete")) {
                String id = request.getParameter("id");
                subjectDAO.hideSubject(id);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            } else if (action.equals("restore")) {
                String id = request.getParameter("id");
                subjectDAO.restoreSubject(id);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi SQL: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("add")) {
                String id = request.getParameter("id_sub");
                String name = request.getParameter("name");
                String level = request.getParameter("level");
                String description = request.getParameter("description");
                String feeStr = request.getParameter("fee");
                String status = request.getParameter("status");

                // Kiểm tra các trường bắt buộc
                if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "ID và tên môn học không được để trống.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra fee
                double fee;
                try {
                    if (feeStr == null || feeStr.trim().isEmpty()) {
                        throw new NumberFormatException("Phí môn học không được để trống.");
                    }
                    fee = Double.parseDouble(feeStr);
                    if (fee < 0) {
                        request.setAttribute("error", "Phí môn học không được âm.");
                        request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Phí môn học phải là một số hợp lệ: " + e.getMessage());
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra status
                if (status == null || (!status.equals("active") && !status.equals("inactive"))) {
                    request.setAttribute("error", "Trạng thái không hợp lệ.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra trùng id_sub
                if (subjectDAO.getSubjectById(id) != null) {
                    request.setAttribute("error", "ID môn học đã tồn tại.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                Subject subject = new Subject(id, name, level, description, fee, status);
                System.out.println("Adding subject: " + subject.getId() + ", " + subject.getName());
                subjectDAO.addSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");

            } else if (action.equals("edit")) {
                String id = request.getParameter("id_sub");
                String name = request.getParameter("name");
                String level = request.getParameter("level");
                String description = request.getParameter("description");
                String feeStr = request.getParameter("fee");
                String status = request.getParameter("status");

                // Kiểm tra các trường bắt buộc
                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "Tên môn học không được để trống.");
                    request.setAttribute("subject", new Subject(id, name, level, description, 0, status));
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra fee
                double fee;
                try {
                    if (feeStr == null || feeStr.trim().isEmpty()) {
                        throw new NumberFormatException("Phí môn học không được để trống.");
                    }
                    fee = Double.parseDouble(feeStr);
                    if (fee < 0) {
                        request.setAttribute("error", "Phí môn học không được âm.");
                        request.setAttribute("subject", new Subject(id, name, level, description, 0, status));
                        request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Phí môn học phải là một số hợp lệ: " + e.getMessage());
                    request.setAttribute("subject", new Subject(id, name, level, description, 0, status));
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                    return;
                }

                // Kiểm tra status
                if (status == null || (!status.equals("active") && !status.equals("inactive"))) {
                    request.setAttribute("error", "Trạng thái không hợp lệ.");
                    request.setAttribute("subject", new Subject(id, name, level, description, fee, status));
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                    return;
                }

                Subject subject = new Subject(id, name, level, description, fee, status);
                System.out.println("Updating subject: " + subject.getId() + ", " + subject.getName());
                subjectDAO.updateSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi SQL: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
}