package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TutorRevenueDAO {
    public Map<String, Double> getMonthlyRevenueByTutor(String tutorId, int year) throws SQLException {
        Map<String, Double> monthlyRevenue = new HashMap<>();
        String sql = "SELECT MONTH(rs.registration_date) AS month, SUM(s.fee) AS revenue " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.course_id = c.id " +
                "JOIN subject s ON c.subject_id = s.id " +
                "WHERE rs.status = 'completed' AND c.tutor_id = ? AND YEAR(rs.registration_date) = ? " +
                "GROUP BY MONTH(rs.registration_date)";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection in TutorRevenueDAO");
                return monthlyRevenue;
            }
            System.out.println("Executing query with tutorId: " + tutorId + ", year: " + year);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tutorId);
            stmt.setInt(2, year);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month");
                double revenue = rs.getDouble("revenue");
                monthlyRevenue.put(String.valueOf(month), revenue);
                System.out.println("Found revenue for month " + month + ": " + revenue);
            }
            System.out.println("Total records found: " + monthlyRevenue.size());
        } catch (SQLException e) {
            System.err.println("SQL Error in getMonthlyRevenueByTutor: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném lại để servlet xử lý
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { System.err.println("Error closing ResultSet: " + e.getMessage()); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { System.err.println("Error closing Statement: " + e.getMessage()); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { System.err.println("Error closing Connection: " + e.getMessage()); }
        }
        return monthlyRevenue;
    }
}