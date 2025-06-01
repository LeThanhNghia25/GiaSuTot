package Controller;

import DAO.AdminSubjectDAO;
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
public class AdminSubjectController extends HttpServlet {
    private AdminSubjectDAO adminSubjectDAO;

    public void init() {
        adminSubjectDAO = new AdminSubjectDAO();
        System.out.println("SubjectDAO initialized successfully at " + new java.util.Date());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                // BỎ CHỨC NĂNG TÌM KIẾM -> luôn lấy tất cả môn học
                List<Subject> subjects = adminSubjectDAO.getAllSubjects();
                request.setAttribute("subjects", subjects);
                request.getRequestDispatcher("/admin/subject-management.jsp").forward(request, response);
            } else if (action.equals("add")) {
                request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                String id = request.getParameter("id");
                Subject subject = adminSubjectDAO.getSubjectById(id);
                if (subject != null) {
                    request.setAttribute("subject", subject);
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Môn học không tồn tại.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                }
            } else if (action.equals("delete")) {
                String id = request.getParameter("id");
                adminSubjectDAO.hideSubject(id);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            } else if (action.equals("restore")) {
                String id = request.getParameter("id");
                adminSubjectDAO.restoreSubject(id);
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
                String id = request.getParameter("id"); // Đổi từ id_sub
                String name = request.getParameter("name");
                String level = request.getParameter("level");
                String description = request.getParameter("description");
                String feeStr = request.getParameter("fee");
                String status = request.getParameter("status");

                if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "ID và tên môn học không được để trống.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

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

                if (status == null || (!status.equals("active") && !status.equals("inactive"))) {
                    request.setAttribute("error", "Trạng thái không hợp lệ.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                if (adminSubjectDAO.getSubjectById(id) != null) {
                    request.setAttribute("error", "ID môn học đã tồn tại.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                Subject subject = new Subject(id, name, level, description, fee, status);
                adminSubjectDAO.addSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            } else if (action.equals("edit")) {
                String id = request.getParameter("id"); // Đổi từ id_sub
                String name = request.getParameter("name");
                String level = request.getParameter("level");
                String description = request.getParameter("description");
                String feeStr = request.getParameter("fee");
                String status = request.getParameter("status");

                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("error", "Tên môn học không được để trống.");
                    request.setAttribute("subject", new Subject(id, name, level, description, 0, status));
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                    return;
                }

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

                if (status == null || (!status.equals("active") && !status.equals("inactive"))) {
                    request.setAttribute("error", "Trạng thái không hợp lệ.");
                    request.setAttribute("subject", new Subject(id, name, level, description, fee, status));
                    request.getRequestDispatcher("/admin/edit-subject.jsp").forward(request, response);
                    return;
                }

                Subject subject = new Subject(id, name, level, description, fee, status);
                adminSubjectDAO.updateSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi SQL: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
}