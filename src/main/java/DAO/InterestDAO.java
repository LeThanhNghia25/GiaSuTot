package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InterestDAO {
    public void insertInterest(String studentId, String tutorId) {
        String sql = "INSERT INTO interest (id_st, id_tt) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, tutorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi để dễ debug
            throw new RuntimeException(e);
        }
    }
}
