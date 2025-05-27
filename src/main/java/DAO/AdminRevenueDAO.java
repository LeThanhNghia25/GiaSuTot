package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminRevenueDAO {
    public Map<String, Double> getMonthlyRevenue() throws SQLException {
        Map<String, Double> monthlyRevenue = new HashMap<>();
        String sql = "SELECT MONTH(rs.registration_date) AS month, SUM(s.fee) AS revenue " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.id_course = c.id_course " +
                "JOIN subject s ON c.id_sub = s.id_sub " +
                "WHERE rs.status_rsub = 'completed' " +
                "GROUP BY MONTH(rs.registration_date)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int month = rs.getInt("month");
                double revenue = rs.getDouble("revenue");
                monthlyRevenue.put(String.valueOf(month), revenue);
            }
        }
        return monthlyRevenue;
    }
}