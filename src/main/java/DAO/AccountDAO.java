package DAO;

import model.Account;
import Utils.DBConnection;
import java.sql.*;
import java.util.*;

public class AccountDAO {
    private Connection conn;

    public AccountDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }
    
    public String generateaccount_id() throws SQLException {
        String sql = "SELECT COUNT(*) FROM account";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("acc%03d", count);
            }
        }
        return null;
    }
    public void insertAccount(Account acc) throws SQLException {
        String sql = "INSERT INTO account (id_acc, email, pass, role, status_acc) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getString("id_acc"));
                acc.setEmail(rs.getString("email"));
                acc.setPassword(rs.getString("pass"));
                acc.setRole(rs.getInt("role"));
                acc.setStatus(rs.getString("status_acc"));
                return acc;
            }
        }
        return null;
    }

}