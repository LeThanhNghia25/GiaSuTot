package DAO;

import model.Tutor;
import Utils.DBConnection;

import java.sql.*;

public class TutorDAO {
    public Tutor getTutorById(String id_tutor) {
        Tutor tutor = null;
        String sql = "SELECT * FROM tutor WHERE id_tu = ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, id_tutor);
                System.out.println("Executing query for tutor id: " + id_tutor);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    tutor = new Tutor();
                    tutor.setId(rs.getString("id_tu"));
                    tutor.setName(rs.getString("name"));
                    tutor.setEmail(rs.getString("email"));
                    tutor.setBirth(rs.getDate("birth"));
                    tutor.setPhone(rs.getString("phone"));
                    tutor.setAddress(rs.getString("address"));
                    tutor.setSpecialization(rs.getString("specialization"));
                    tutor.setDescribeTutor(rs.getString("describeTutor"));
                    tutor.setEvaluate(rs.getInt("evaluate"));
                    tutor.setAccountId(rs.getString("id_acc"));
                    System.out.println("Tutor found: " + tutor.getName());
                } else {
                    System.out.println("No tutor found with id: " + id_tutor);
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
        String sql = "SELECT * FROM tutor WHERE id_acc = ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return null;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, accountId);
                System.out.println("Executing query for tutor with account id: " + accountId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    tutor = new Tutor();
                    tutor.setId(rs.getString("id_tu"));
                    tutor.setName(rs.getString("name"));
                    tutor.setEmail(rs.getString("email"));
                    tutor.setBirth(rs.getDate("birth"));
                    tutor.setPhone(rs.getString("phone"));
                    tutor.setAddress(rs.getString("address"));
                    tutor.setSpecialization(rs.getString("specialization"));
                    tutor.setDescribeTutor(rs.getString("describeTutor"));
                    tutor.setEvaluate(rs.getInt("evaluate"));
                    tutor.setAccountId(rs.getString("id_acc"));
                    System.out.println("Tutor found: " + tutor.getName());
                } else {
                    System.out.println("No tutor found with account id: " + accountId);
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
            System.out.println("Description: " + tutor.getDescribeTutor());
            System.out.println("Account ID: " + tutor.getAccountId());
        } else {
            System.out.println("Failed to retrieve tutor with id: " + testId);
        }
    }
}