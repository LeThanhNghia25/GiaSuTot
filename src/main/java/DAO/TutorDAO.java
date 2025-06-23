package DAO;

import model.Tutor;
import model.Account;
import Utils.DBConnection;

import java.sql.*;

public class TutorDAO {
    public Tutor getTutorById(String id) {
        Tutor tutor = null;
        String sql = "SELECT * FROM tutor WHERE id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Account account = new Account();
                        account.setId(rs.getString("account_id"));
                        account.setEmail(rs.getString("email"));

                        tutor = new Tutor(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getDate("birth").toLocalDate(),
                                rs.getString("phone"),
                                rs.getString("address"),
                                rs.getString("specialization"),
                                rs.getString("description"),
                                rs.getLong("id_card_number"),
                                rs.getLong("bank_account_number"),
                                rs.getString("bank_name"),
                                account,
                                rs.getInt("evaluate")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return tutor;
    }

    public Tutor getTutorByAccountId(String accountId) {
        Tutor tutor = null;
        String sql = "SELECT * FROM tutor WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, accountId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Account account = new Account();
                        account.setId(rs.getString("account_id"));
                        account.setEmail(rs.getString("email"));

                        tutor = new Tutor(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getDate("birth").toLocalDate(),
                                rs.getString("phone"),
                                rs.getString("address"),
                                rs.getString("specialization"),
                                rs.getString("description"),
                                rs.getLong("id_card_number"),
                                rs.getLong("bank_account_number"),
                                rs.getString("bank_name"),
                                account,
                                rs.getInt("evaluate")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return tutor;
    }

    public void updateTutor(Tutor tutor) {
        String sql = "UPDATE tutor SET name=?, email=?, birth=?, phone=?, address=?, specialization=?, description=?, id_card_number=?, bank_account_number=?, bank_name=?, evaluate=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getName());
            stmt.setString(2, tutor.getEmail());
            stmt.setDate(3, tutor.getBirth() != null ? java.sql.Date.valueOf(tutor.getBirth()) : null);
            stmt.setString(4, tutor.getPhone());
            stmt.setString(5, tutor.getAddress());
            stmt.setString(6, tutor.getSpecialization());
            stmt.setString(7, tutor.getDescription());
            stmt.setLong(8, tutor.getIdCardNumber());
            stmt.setLong(9, tutor.getBankAccountNumber());
            stmt.setString(10, tutor.getBankName());
            stmt.setInt(11, tutor.getEvaluate());
            stmt.setString(12, tutor.getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tutor updated successfully.");
            } else {
                System.err.println("No tutor updated.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addTutor(String id, String accountId, String name, String birth, String email, String phone,
                         String idCardNumber, String bankAccountNumber, String bankName, String address,
                         String specialization, String description, int evaluate) throws SQLException {
        String sql = """
            INSERT INTO tutor (id, account_id, name, birth, email, phone, id_card_number, bank_account_number, 
                              bank_name, address, specialization, description, evaluate)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, accountId);
            ps.setString(3, name);
            ps.setDate(4, birth != null ? java.sql.Date.valueOf(birth) : null);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setLong(7, Long.parseLong(idCardNumber));
            ps.setLong(8, Long.parseLong(bankAccountNumber));
            ps.setString(9, bankName);
            ps.setString(10, address);
            ps.setString(11, specialization);
            ps.setString(12, description);
            ps.setInt(13, evaluate);
            ps.executeUpdate();
        }
    }
}