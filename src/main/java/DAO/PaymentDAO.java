package DAO;

import Utils.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class PaymentDAO {
    private static final String UPLOAD_DIR = "D:/workspace/intelij/GiaSuTot/src/main/webapp/WEB-INF/uploads"; // Giữ để tham khảo, nhưng không dùng để sao chép

    public String getFileName(String courseId, String studentId) throws SQLException {
        String sql = "SELECT file_name FROM payment WHERE course_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, studentId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("file_name") : null;
        }
    }

    public String getFilePath(String courseId, String studentId) throws SQLException {
        String sql = "SELECT file_path, file_name FROM payment WHERE course_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPath = rs.getString("file_path");
                String fileName = rs.getString("file_name");
                System.out.println("PaymentDAO: Retrieved filePath from DB: " + storedPath + ", fileName: " + fileName);
                return "/uploads/" + fileName; // Đường dẫn tương đối
            }
            return null;
        }
    }

    public void insertPayment(String courseId, String tutorId, String studentId, double amount, String fileName, String sourceFilePath) throws SQLException {
        String sql = "INSERT INTO payment (id, course_id, tutor_id, student_id, amount, payment_date, file_name, file_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String paymentId = "pay_" + UUID.randomUUID().toString().substring(0, 16);
            stmt.setString(1, paymentId);
            stmt.setString(2, courseId);
            stmt.setString(3, tutorId);
            stmt.setString(4, studentId);
            stmt.setDouble(5, amount);
            stmt.setTimestamp(6, new Timestamp(new Date().getTime()));
            stmt.setString(7, fileName);
            stmt.setString(8, "/uploads/" + fileName); // Lưu đường dẫn tương đối
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment recorded with ID: " + paymentId + " for course: " + courseId);
            } else {
                throw new SQLException("Failed to insert payment record");
            }
        }
    }
}