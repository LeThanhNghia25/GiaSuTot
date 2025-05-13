package Controller;

import DAO.AccountDAO;
import model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/account")
public class AccountController extends HttpServlet {
    private AccountDAO accountDAO;

    public void init() {
        try {
            accountDAO = new AccountDAO();
            System.out.println("AccountDAO initialized successfully at " + new java.util.Date());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                System.out.println("Fetching accounts at " + new java.util.Date());
                List<Account> accounts = accountDAO.getAllAccounts();
                System.out.println("Accounts size: " + (accounts != null ? accounts.size() : "null"));
                request.setAttribute("accounts", accounts);
                request.getRequestDispatcher("/admin/account-management.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                String id = request.getParameter("id");
                Account account = accountDAO.getAccountById(id);
                if (account != null) {
                    request.setAttribute("account", account);
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Tài khoản không tồn tại.");
                    request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                }
            } else if (action.equals("delete")) {
                String id = request.getParameter("id");
                accountDAO.hideAccount(id);
                response.sendRedirect(request.getContextPath() + "/admin/account");
            } else if (action.equals("restore")) {
                String id = request.getParameter("id");
                accountDAO.restoreAccount(id);
                response.sendRedirect(request.getContextPath() + "/admin/account");
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
            if (action.equals("edit")) {
                String id = request.getParameter("id_acc");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                int role = Integer.parseInt(request.getParameter("role"));
                String status = request.getParameter("status");

                if (email == null || email.trim().isEmpty()) {
                    request.setAttribute("error", "Email không được để trống.");
                    request.setAttribute("account", new Account(id, email, password, role, status));
                    request.getRequestDispatcher("/admin/edit-account.jsp").forward(request, response);
                    return;
                }

                Account account = new Account(id, email, password, role, status);
                accountDAO.updateAccount(account);
                response.sendRedirect(request.getContextPath() + "/admin/account");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
}