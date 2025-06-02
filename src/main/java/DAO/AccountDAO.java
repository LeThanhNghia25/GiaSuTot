package DAO;

import model.Account;
import Utils.DBConnection;
import java.sql.*;

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
    public Account findByGoogleId(String googleId) throws SQLException {
        String sql = "SELECT * FROM account WHERE google_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, googleId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setId(generateaccount_id());        // id_acc thay cho userId
                    account.setEmail(rs.getString("email"));
                    account.setPassword(rs.getString("pass"));         // nếu cần mật khẩu
                    account.setRole(rs.getInt("role"));
                    account.setStatus("inactive"); // thêm trường status_acc
                    account.setGoogle_id(rs.getString("google_id")); // google_id phải có trong bảng
                    return account;
                }
            }
        }
        return null;
    }
    public boolean insertgg(Account account) {
        String sql = "INSERT INTO account (id_acc, email, role, statusAcc, google_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, generateaccount_id());
            stmt.setString(2, account.getEmail());
            stmt.setInt(3, 1);
            stmt.setString(4, "inactive");
            stmt.setString(5, account.getGoogle_id());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}