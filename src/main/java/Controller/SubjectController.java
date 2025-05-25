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
                // Lấy tham số tìm kiếm từ form
                String tenMonHoc = request.getParameter("tenMonHoc");
                String lop = request.getParameter("lop");
                String tinh = request.getParameter("tinh");  // nếu là trạng thái
                String tenGiaoVien = request.getParameter("tenGiaoVien");  // nếu có, không thì bỏ

                List<Subject> subjects;

                // Kiểm tra có tham số tìm kiếm hay không
                boolean isSearch = (tenMonHoc != null && !tenMonHoc.trim().isEmpty()) ||
                        (lop != null && !lop.trim().isEmpty()) ||
                        (tinh != null && !tinh.trim().isEmpty()) ||
                        (tenGiaoVien != null && !tenGiaoVien.trim().isEmpty());

                if (isSearch) {
                    // Gọi hàm tìm kiếm trong DAO
                    subjects = subjectDAO.searchSubjects(tenMonHoc, lop, tinh, tenGiaoVien);
                    request.setAttribute("searchMessage", "Kết quả tìm kiếm");
                } else {
                    subjects = subjectDAO.getAllSubjects();
                }

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

                if (subjectDAO.getSubjectById(id) != null) {
                    request.setAttribute("error", "ID môn học đã tồn tại.");
                    request.getRequestDispatcher("/admin/add-subject.jsp").forward(request, response);
                    return;
                }

                Subject subject = new Subject(id, name, level, description, fee, status);
                subjectDAO.addSubject(subject);
                response.sendRedirect(request.getContextPath() + "/admin/subject");

            } else if (action.equals("edit")) {
                String id = request.getParameter("id_sub");
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
