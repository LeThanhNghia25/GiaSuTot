package DAO;

import model.Course;
import model.Payment;
import model.Subject;
import model.Tutor;
import Utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public List<Course> getCompletedCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, s.*, t.* " +
                "FROM course c " +
                "JOIN subject s ON c.subject_id = s.id " +
                "JOIN tutor t ON c.tutor_id = t.id " +
                "JOIN registered_subjects rs ON c.id = rs.course_id " +
                "WHERE rs.status = 'completed'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                        rs.getString("id"),
                        rs.getString("subject_id"),
                        rs.getString("tutor_id"),
                        rs.getTimestamp("time").toLocalDateTime()
                );
                course.setSubject(new Subject(
                        rs.getString("s.id"),
                        rs.getString("s.name"),
                        rs.getString("s.level"),
                        rs.getString("s.description"),
                        rs.getDouble("s.fee"),
                        rs.getString("s.status")
                ));
                course.setTutor(new Tutor(
                        rs.getString("t.id"),
                        rs.getString("t.name"),
                        rs.getString("t.email"),
                        rs.getDate("t.birth").toLocalDate(),
                        rs.getString("t.phone"),
                        rs.getString("t.address"),
                        rs.getString("t.specialization"),
                        rs.getString("t.description"),
                        rs.getLong("t.id_card_number"),
                        rs.getLong("t.bank_account_number"),
                        rs.getString("t.bank_name"),
                        rs.getString("t.account_id"),
                        rs.getInt("t.evaluate")
                ));
                courses.add(course);
            }
            System.out.println("Total completed courses retrieved: " + courses.size());
        }
        return courses;
    }

    public int getPaymentCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM payment";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public void addPayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment (id, course_id, tutor_id, amount, payment_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getId());
            stmt.setString(2, payment.getCourseId());
            stmt.setString(3, payment.getTutorId());
            stmt.setDouble(4, payment.getAmount());
            stmt.setTimestamp(5, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setString(6, payment.getStatus());
            stmt.executeUpdate();
        }
    }
}