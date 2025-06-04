package DAO;

import model.Course;
import model.Notification;
import model.Payment;
import model.Subject;
import model.Tutor;
import model.Account;
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
                Course course = new Course();
                course.setId(rs.getString("id"));
                course.setSubjectId(rs.getString("subject_id"));
                course.setTutorId(rs.getString("tutor_id"));
                course.setTime(rs.getTimestamp("time") != null ? rs.getTimestamp("time").toLocalDateTime() : null);

                Subject subject = new Subject();
                subject.setId(rs.getString("s.id"));
                subject.setName(rs.getString("s.name"));
                subject.setLevel(rs.getString("s.level"));
                subject.setDescription(rs.getString("s.description"));
                subject.setFee(rs.getDouble("s.fee"));
                subject.setStatus(rs.getString("s.status"));
                course.setSubject(subject);

                Account account = new Account();
                account.setId(rs.getString("t.account_id"));
                account.setEmail(rs.getString("t.email"));

                Tutor tutor = new Tutor();
                tutor.setId(rs.getString("t.id"));
                tutor.setName(rs.getString("t.name"));
                tutor.setEmail(rs.getString("t.email"));
                tutor.setBirth(rs.getDate("t.birth") != null ? rs.getDate("t.birth").toLocalDate() : null);
                tutor.setPhone(rs.getString("t.phone"));
                tutor.setAddress(rs.getString("t.address"));
                tutor.setSpecialization(rs.getString("t.specialization"));
                tutor.setDescription(rs.getString("t.description"));
                tutor.setIdCardNumber(rs.getLong("t.id_card_number"));
                tutor.setBankAccountNumber(rs.getLong("t.bank_account_number"));
                tutor.setBankName(rs.getString("t.bank_name"));
                tutor.setAccount(account);
                tutor.setEvaluate(rs.getInt("t.evaluate"));
                course.setTutor(tutor);

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