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
                        rs.getString("status_acc")
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
                            rs.getString("status_acc")
                    );
                }
            }
        }
        return null;
    }

    public void updateAccount(Account account) throws SQLException {
        String sql = "UPDATE account SET email = ?, pass = ?, role = ?, status_acc = ? WHERE id_acc = ?";
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
        String sql = "UPDATE account SET status_acc = 'inactive' WHERE id_acc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void restoreAccount(String id) throws SQLException {
        String sql = "UPDATE account SET status_acc = 'active' WHERE id_acc = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}
