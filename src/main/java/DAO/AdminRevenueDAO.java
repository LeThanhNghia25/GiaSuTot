package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminRevenueDAO {
    public Map<String, Double> getMonthlyRevenue(int year) throws SQLException {
        Map<String, Double> monthlyRevenue = new HashMap<>();
        String sql = "SELECT MONTH(rs.registration_date) AS month, SUM(s.fee) AS revenue " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.course_id = c.id " +
                "JOIN subject s ON c.subject_id = s.id " +
                "WHERE rs.status = 'completed' AND YEAR(rs.registration_date) = ? " +
                "GROUP BY MONTH(rs.registration_date)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("month");
                    double revenue = rs.getDouble("revenue");
                    monthlyRevenue.put(String.valueOf(month), revenue);
                }
            }
        }
        return monthlyRevenue;
    }

    public Map<String, Double> getRevenueBySubject(int year) throws SQLException {
        Map<String, Double> revenueBySubject = new HashMap<>();
        String sql = "SELECT s.name AS subject, SUM(s.fee) AS revenue " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.course_id = c.id " +
                "JOIN subject s ON c.subject_id = s.id " +
                "WHERE rs.status = 'completed' AND YEAR(rs.registration_date) = ? " +
                "GROUP BY s.name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String subject = rs.getString("subject");
                    double revenue = rs.getDouble("revenue");
                    revenueBySubject.put(subject, revenue);
                }
            }
        }
        return revenueBySubject;
    }
}