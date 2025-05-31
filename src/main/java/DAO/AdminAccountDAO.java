package DAO;

import model.Account;
import Utils.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminAccountDAO {

    public List<Account> getAllAccounts() throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Account(
                        rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role"),
                        rs.getString("status")
                ));
            }
            System.out.println("Total accounts retrieved: " + list.size());
        }
        return list;
    }

    public Account getAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM account WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("role"),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public void updateAccount(Account account) throws SQLException {
        String sql;
        PreparedStatement stmt;
        try (Connection conn = DBConnection.getConnection()) {
            if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
                sql = "UPDATE account SET email = ?, role = ?, status = ? WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, account.getEmail());
                stmt.setInt(2, account.getRole());
                stmt.setString(3, account.getStatus());
                stmt.setString(4, account.getId());
            } else {
                sql = "UPDATE account SET email = ?, password = ?, role = ?, status = ? WHERE id = ?";
                stmt = conn.prepareStatement(sql);
                String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
                stmt.setString(1, account.getEmail());
                stmt.setString(2, hashedPassword);
                stmt.setInt(3, account.getRole());
                stmt.setString(4, account.getStatus());
                stmt.setString(5, account.getId());
            }
            stmt.executeUpdate();
        }
    }

    public void hideAccount(String id) throws SQLException {
        String sql = "UPDATE account SET status = 'inactive' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void restoreAccount(String id) throws SQLException {
        String sql = "UPDATE account SET status = 'active' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}