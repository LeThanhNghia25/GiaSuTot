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

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Account(
                        rs.getString("id_acc"),
                        rs.getString("email"),
                        rs.getString("pass"),
                        rs.getInt("role"),
                        rs.getString("statusAcc")
                ));
            }
            System.out.println("Total accounts retrieved: " + list.size());
        }
        return list;
    }

    public Account getAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM account WHERE id_acc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("id_acc"),
                            rs.getString("email"),
                            rs.getString("pass"),
                            rs.getInt("role"),
                            rs.getString("statusAcc")
                    );
                }
            }
        }
        return null;
    }

    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE account SET email = ?, pass = ?, role = ?, statusAcc = ? WHERE id_acc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getPassword());
            stmt.setInt(3, account.getRole());
            stmt.setString(4, account.getStatus());
            stmt.setString(5, account.getId());
            stmt.executeUpdate();
        }
    }


    public void hideAccount(String id) throws SQLException {
        String sql = "UPDATE account SET statusAcc = 'inactive' WHERE id_acc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void restoreAccount(String id) throws SQLException {
        String sql = "UPDATE account SET statusAcc = 'active' WHERE id_acc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }


    public String generateAccountId() throws SQLException {
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
        String sql = "INSERT INTO account (id_acc, email, pass, role, statusAcc) VALUES (?, ?, ?, ?, ?)";
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
                acc.setStatus(rs.getString("statusAcc"));
                return acc;
            }
        }
        return null;
    }

}