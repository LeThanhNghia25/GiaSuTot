package DAO;

import model.Account;
import Utils.DBConnection;
import org.mindrot.jbcrypt.BCrypt; // Thư viện BCrypt

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminAccountDAO {
    // Lấy tất cả tài khoản
    public List<Account> getAllAccounts() throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Account(
                        rs.getString("id_acc"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getInt("role"),
                        rs.getString("status_acc")
                ));
            }
            System.out.println("Total accounts retrieved: " + list.size());
        }
        return list;
    }

    // Lấy tài khoản theo ID
    public Account getAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM account WHERE id_acc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("id_acc"),
                            rs.getString("email"),
                            rs.getString("pass"),
                            rs.getInt("role"),
                            rs.getString("status_acc")
                    );
                }
            }
        }
        return null;
    }

    // Cập nhật tài khoản
    public void updateAccount(Account account) throws SQLException {
        String sql;
        PreparedStatement stmt;
        try (Connection conn = DBConnection.getConnection()) {
            // Nếu mật khẩu không thay đổi (null hoặc rỗng), không cập nhật cột pass
            if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
                sql = "UPDATE account SET email = ?, role = ?, status_acc = ? WHERE id_acc = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, account.getEmail());
                stmt.setInt(2, account.getRole());
                stmt.setString(3, account.getStatusAcc());
                stmt.setString(4, account.getId());
            } else {
                sql = "UPDATE account SET email = ?, pass = ?, role = ?, status_acc = ? WHERE id_acc = ?";
                stmt = conn.prepareStatement(sql);
                String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
                stmt.setString(1, account.getEmail());
                stmt.setString(2, hashedPassword);
                stmt.setInt(3, account.getRole());
                stmt.setString(4, account.getStatusAcc());
                stmt.setString(5, account.getId());
            }
            stmt.executeUpdate();
        }
    }

    // Ẩn tài khoản
    public void hideAccount(String id) throws SQLException {
        String sql = "UPDATE account SET status_acc = 'inactive' WHERE id_acc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    // Khôi phục tài khoản
    public void restoreAccount(String id) throws SQLException {
        String sql = "UPDATE account SET status_acc = 'active' WHERE id_acc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}