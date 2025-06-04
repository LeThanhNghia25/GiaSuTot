package DAO;

import model.Course;
import model.Notification;
import model.Payment;
import model.Subject;
import model.Tutor;
import Utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public List<Course> getCompletedCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, s.*, t.*, rs.student_id, rs.registration_date, st.name AS student_name, " +
                "(SELECT MIN(l.time) FROM lesson l WHERE l.course_id = c.id AND l.student_id = rs.student_id AND l.status = 'completed') AS start_date, " +
                "(SELECT MAX(l.time) FROM lesson l WHERE l.course_id = c.id AND l.student_id = rs.student_id AND l.status = 'completed') AS end_date " +
                "FROM course c " +
                "JOIN subject s ON c.subject_id = s.id " +
                "JOIN tutor t ON c.tutor_id = t.id " +
                "JOIN registered_subjects rs ON c.id = rs.course_id " +
                "JOIN student st ON rs.student_id = st.id " +
                "LEFT JOIN payment p ON c.id = p.course_id AND rs.student_id = p.student_id " +
                "WHERE rs.status = 'completed' AND p.course_id IS NULL";
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
                // Thêm thông tin học viên và ngày
                course.setStudentId(rs.getString("student_id"));
                course.setStudentName(rs.getString("student_name"));
                Timestamp startDate = rs.getTimestamp("start_date");
                Timestamp endDate = rs.getTimestamp("end_date");
                course.setStartDate(startDate != null ? startDate.toLocalDateTime().toLocalDate() : null);
                course.setEndDate(endDate != null ? endDate.toLocalDateTime().toLocalDate() : null);
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
        String sql = "INSERT INTO payment (id, course_id, tutor_id, student_id, amount, payment_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getId());
            stmt.setString(2, payment.getCourseId());
            stmt.setString(3, payment.getTutorId());
            stmt.setString(4, payment.getStudentId());
            stmt.setDouble(5, payment.getAmount());
            stmt.setTimestamp(6, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setString(7, payment.getStatus());
            stmt.executeUpdate();
        }
    }

    public void addNotification(Notification notification) throws SQLException {
        String sql = "INSERT INTO notifications (id, account_id, message, created_at, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, notification.getId());
            stmt.setString(2, notification.getAccountId());
            stmt.setString(3, notification.getMessage());
            stmt.setTimestamp(4, Timestamp.valueOf(notification.getCreatedAt()));
            stmt.setString(5, notification.getStatus());
            stmt.executeUpdate();
        }
    }
}