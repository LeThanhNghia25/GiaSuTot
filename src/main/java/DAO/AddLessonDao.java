package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AddLessonDao {

    public AddLessonDao() {}

    public void insertLession(String course_id, String student_id, String time) throws SQLException {
        String sql = "INSERT INTO Lesson (course_id, student_id, status, time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course_id);
            stmt.setString(2, student_id);
            stmt.setString(3, "scheduled");
            stmt.setString(4,time); // Đảm bảo time là yyyy-MM-dd HH:mm:ss
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Lỗi khi chèn buổi học: " + e.getMessage(), e);
        }
    }
}