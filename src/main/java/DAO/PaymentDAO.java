package DAO;

import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentDAO {
    public void insertPayment(String courseId, String tutorId, String studentId, double amount) throws SQLException {
        System.out.println("Inserting payment record for course: " + courseId + ", student: " + studentId);
        String paymentId = "pay_" + UUID.randomUUID().toString().substring(0, 16); // Tạo ID 20 ký tự
        String sql = "INSERT INTO payment (id, course_id, tutor_id, student_id, amount, payment_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentId);
            stmt.setString(2, courseId);
            stmt.setString(3, tutorId);
            stmt.setString(4, studentId);
            stmt.setDouble(5, amount);
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(7, "pending");
            stmt.executeUpdate();
            System.out.println("Payment record inserted successfully with ID: " + paymentId);
        } catch (SQLException e) {
            System.err.println("Error inserting payment record: " + e.getMessage());
            throw e;
        }
    }

    public void updatePaymentStatus(String paymentId, String status) throws SQLException {
        System.out.println("Updating payment status to " + status + " for payment ID: " + paymentId);
        String sql = "UPDATE payment SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, paymentId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment status updated successfully.");
            } else {
                System.out.println("No payment record found for ID: " + paymentId);
            }
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            throw e;
        }
    }
}