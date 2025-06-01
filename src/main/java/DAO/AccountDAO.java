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
}