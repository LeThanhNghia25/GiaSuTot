package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void deleteInterest(String studentId, String tutorId) {
        String sql = "DELETE FROM interest WHERE id_st = ? AND id_tt = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, tutorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean hasInterest(String studentId, String tutorId) {
        String sql = "SELECT 1 FROM interest WHERE id_st = ? AND id_tt = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, tutorId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Trả về true nếu có bản ghi
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
