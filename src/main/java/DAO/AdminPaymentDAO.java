package DAO;

import model.Course;
import model.Payment;
import model.Subject;
import model.Tutor;
import model.Account;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPaymentDAO {
    // Phần admin chuyển tiền cho gia sư tutor
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
        String sql = "INSERT INTO payment (id, course_id, tutor_id, student_id, amount, payment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getId());
            stmt.setString(2, payment.getCourseId());
            stmt.setString(3, payment.getTutorId());
            stmt.setString(4, payment.getStudentId());
            stmt.setDouble(5, payment.getAmount());
            stmt.setTimestamp(6, payment.getPaymentDate() != null ? new java.sql.Timestamp(payment.getPaymentDate().getTime()) : null);
            stmt.executeUpdate();
        }
    }

    // Lấy danh sách biên lai chờ xác nhận
    public List<Payment> getPendingPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT rs.course_id, rs.student_id, p.file_name, p.file_path, p.payment_date " +
                "FROM registered_subjects rs " +
                "LEFT JOIN payment p ON rs.course_id = p.course_id AND rs.student_id = p.student_id " +
                "WHERE rs.status = 'pending_payment'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setCourseId(rs.getString("course_id"));
                payment.setStudentId(rs.getString("student_id"));
                payment.setFileName(rs.getString("file_name"));
                payment.setFilePath(rs.getString("file_path"));
                payment.setPaymentDate(rs.getTimestamp("payment_date")); // Trả về Date trực tiếp
                payments.add(payment);
            }
            System.out.println("Total pending payments retrieved: " + payments.size());
        } catch (SQLException e) {
            System.err.println("SQL Error in getPendingPayments: " + e.getMessage());
            throw e;
        }
        return payments;
    }

    // Cập nhật trạng thái biên lai
    public void updatePaymentStatus(String courseId, String studentId, String status) throws SQLException {
        String sql = "UPDATE registered_subjects SET status = ? WHERE course_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, courseId);
            stmt.setString(3, studentId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated status to " + status + " for course " + courseId + " and student " + studentId);
            }
        }
    }

    // Lấy email của học sinh dựa trên studentId
    public String getStudentEmail(String studentId) throws SQLException {
        String email = null;
        String sql = "SELECT a.email FROM account a JOIN student s ON a.id = s.account_id WHERE s.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student email: " + e.getMessage());
            throw e;
        }
        return email;
    }

    // Lấy email của giáo viên dựa trên courseId
    public String getTutorEmailByCourseId(String courseId) throws SQLException {
        String email = null;
        String sql = "SELECT a.email FROM account a JOIN tutor t ON a.id = t.account_id " +
                "JOIN course c ON t.id = c.tutor_id WHERE c.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tutor email: " + e.getMessage());
            throw e;
        }
        return email;
    }

    public java.sql.Timestamp getPaymentDate(String courseId, String studentId) throws SQLException {
        String sql = "SELECT payment_date FROM payment WHERE course_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("payment_date");
            }
            return null;
        }
    }
}