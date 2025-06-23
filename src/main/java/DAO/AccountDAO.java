package DAO;

import model.Account;
import Utils.DBConnection;

import java.sql.*;

public class AccountDAO {

    public String generateAccountId() throws SQLException {
        String sql = "SELECT COUNT(*) FROM account";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("acc%03d", count);
            }
        }
        return null;
    }

    public void insertAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO account (id, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acc.getId());
            ps.setString(2, acc.getEmail());
            ps.setString(3, acc.getPassword());
            ps.setInt(4, acc.getRole());
            ps.setString(5, acc.getStatus());
            ps.executeUpdate();
        }
    }

    public boolean insertgg(Account account) {
        String sql = "INSERT INTO account (id, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getId());
            ps.setString(2, account.getEmail());
            ps.setString(3, account.getPassword());
            ps.setInt(4, account.getRole());
            ps.setString(5, account.getStatus());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account getAccountByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM account WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account acc = new Account();
                    acc.setId(rs.getString("id"));
                    acc.setEmail(rs.getString("email"));
                    acc.setPassword(rs.getString("password"));
                    acc.setRole(rs.getInt("role"));
                    acc.setStatus(rs.getString("status"));
                    return acc;
                }
            }
        }
        return null;
    }
    public Account getAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account acc = new Account();
                    acc.setId(rs.getString("id"));
                    acc.setEmail(rs.getString("email"));
                    acc.setPassword(rs.getString("password"));
                    acc.setRole(rs.getInt("role"));
                    acc.setStatus(rs.getString("status"));
                    return acc;
                }
            }
        }
        return null;
    }
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE account SET password = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);  // hoặc mã hóa nếu cần
            ps.setString(2, email);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean activateAccount(String email) throws SQLException {
        String sql = "UPDATE account SET status = 'active' WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        }
    }




}