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
                System.out.println("Executing query for tutor id: " + id);
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
                        System.out.println("Tutor found: " + tutor.getName());
                    } else {
                        System.out.println("No tutor found with id: " + id);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
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

    public static void main(String[] args) {
        TutorDAO tutorDAO = new TutorDAO();
        String testId = "tut001";
        System.out.println("Testing getTutorById with id: " + testId);
        Tutor tutor = tutorDAO.getTutorById(testId);

        if (tutor != null) {
            System.out.println("Tutor Details:");
            System.out.println("ID: " + tutor.getId());
            System.out.println("Name: " + tutor.getName());
            System.out.println("Email: " + tutor.getEmail());
            System.out.println("Birth: " + tutor.getBirth());
            System.out.println("Phone: " + tutor.getPhone());
            System.out.println("Address: " + tutor.getAddress());
            System.out.println("Specialization: " + tutor.getSpecialization());
            System.out.println("Description: " + tutor.getDescription());
            System.out.println("ID Card Number: " + tutor.getIdCardNumber());
            System.out.println("Bank Account Number: " + tutor.getBankAccountNumber());
            System.out.println("Bank Name: " + tutor.getBankName());
            System.out.println("Account ID: " + (tutor.getAccount() != null ? tutor.getAccount().getId() : "null"));
        } else {
            System.out.println("Failed to retrieve tutor with id: " + testId);
        }
    }
}