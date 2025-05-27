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
                "JOIN course c ON rs.id_course = c.id_course " +
                "JOIN subject s ON c.id_sub = s.id_sub " +
                "WHERE rs.status_rsub = 'completed' AND YEAR(rs.registration_date) = ? " +
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

    // Cập nhật phương thức getRevenueBySubject để hỗ trợ lọc theo năm
    public Map<String, Double> getRevenueBySubject(int year) throws SQLException {
        Map<String, Double> revenueBySubject = new HashMap<>();
        String sql = "SELECT s.name AS subject, SUM(s.fee) AS revenue " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.id_course = c.id_course " +
                "JOIN subject s ON c.id_sub = s.id_sub " +
                "WHERE rs.status_rsub = 'completed' AND YEAR(rs.registration_date) = ? " +
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