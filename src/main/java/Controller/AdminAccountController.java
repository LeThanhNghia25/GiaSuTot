package Controller;

import DAO.AdminAccountDAO;
import model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminAccountController", urlPatterns = {"/admin/account"})
public class AdminAccountController extends HttpServlet {
    private AdminAccountDAO adminAccountDAO;

    @Override
    public void init() {
        adminAccountDAO = new AdminAccountDAO();
        System.out.println("AdminAccountDAO initialized successfully in AdminController at " + new java.util.Date());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                System.out.println("Fetching accounts at " + new java.util.Date());
                List<Account> accounts = adminAccountDAO.getAllAccounts();
                System.out.println("Accounts size: " + (accounts != null ? accounts.size() : "null"));
                if (accounts == null || accounts.isEmpty()) {
                    request.setAttribute("message", "Không có tài khoản nào để hiển thị.");
                }
                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("/admin/account-management.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                String id = request.getParameter("id");
                if (id == null || id.trim().isEmpty()) {
                    request.setAttribute("error", "ID tài khoản không hợp lệ.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                    return;
                }
                Account account = adminAccountDAO.getAccountById(id);
                if (account != null) {
                    request.setAttribute("account", account);
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Tài khoản không tồn tại.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                }
            } else if (action.equals("delete")) {
                String id = request.getParameter("id");
                if (id == null || id.trim().isEmpty()) {
                    request.setAttribute("error", "ID tài khoản không hợp lệ.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                    return;
                }
                adminAccountDAO.hideAccount(id);
                response.sendRedirect(request.getContextPath() + "/admin/account");
            } else if (action.equals("restore")) {
                String id = request.getParameter("id");
                if (id == null || id.trim().isEmpty()) {
                    request.setAttribute("error", "ID tài khoản không hợp lệ.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                    return;
                }
                adminAccountDAO.restoreAccount(id);
                response.sendRedirect(request.getContextPath() + "/admin/account");
            }
        } catch (SQLException e) {
            log("SQL Error: " + e.getMessage(), e);
            request.setAttribute("error", "Có lỗi xảy ra khi xử lý dữ liệu. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("edit".equals(action)) {
                String id = request.getParameter("id"); // Đổi từ id_acc
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String roleStr = request.getParameter("role");
                String status = request.getParameter("status");

                // Kiểm tra dữ liệu đầu vào
                if (id == null || id.trim().isEmpty()) {
                    request.setAttribute("error", "ID tài khoản không hợp lệ.");
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                    return;
                }
                if (email == null || email.trim().isEmpty()) {
                    request.setAttribute("error", "Email không được để trống.");
                    request.setAttribute("account", new Account(id, email, password, 0, status));
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                    return;
                }
                if (password == null || password.trim().isEmpty()) {
                    request.setAttribute("error", "Mật khẩu không được để trống.");
                    request.setAttribute("account", new Account(id, email, password, 0, status));
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                    return;
                }
                int role;
                try {
                    role = Integer.parseInt(roleStr);
                    if (role < 1 || role > 3) {
                        throw new NumberFormatException("Role phải nằm trong khoảng 1-3.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Role không hợp lệ. Vui lòng chọn giá trị từ 1 đến 3.");
                    request.setAttribute("account", new Account(id, email, password, 0, status));
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                    return;
                }
                if (status == null || (!status.equals("active") && !status.equals("inactive"))) {
                    request.setAttribute("error", "Trạng thái không hợp lệ. Vui lòng chọn 'active' hoặc 'inactive'.");
                    request.setAttribute("account", new Account(id, email, password, role, status));
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                    return;
                }

                Account account = new Account(id, email, password, role, status);
                adminAccountDAO.updateAccount(account);
                response.sendRedirect(request.getContextPath() + "/admin/account");
            }
        } catch (SQLException e) {
            log("SQL Error: " + e.getMessage(), e);
            request.setAttribute("error", "Có lỗi xảy ra khi cập nhật tài khoản. Vui lòng thử lại sau.");
            request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
        }
    }
}