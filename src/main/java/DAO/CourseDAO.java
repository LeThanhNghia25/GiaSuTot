package DAO;

import model.Course;
import model.Tutor;
import model.Subject;
import Utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getAllAvailableCourses() {
        System.out.println("Fetching all available courses...");
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, s.name AS subject_name, s.level, s.fee, s.status, s.description, " +
                "t.name AS tutor_name, t.specialization, t.address, t.evaluate, " +
                "COUNT(rs.course_id) AS student_count " +
                "FROM course c " +
                "JOIN subject s ON c.subject_id = s.id " +
                "JOIN tutor t ON c.tutor_id = t.id " +
                "LEFT JOIN registered_subjects rs ON c.id = rs.course_id " +
                "WHERE s.status = 'active' " +
                "GROUP BY c.id, c.subject_id, c.tutor_id, c.time, " +
                "s.name, s.level, s.fee, s.status, s.description, " +
                "t.name, t.specialization, t.address, t.evaluate";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getString("id"));
                course.setSubjectId(rs.getString("subject_id"));
                course.setTutorId(rs.getString("tutor_id"));
                course.setTime(rs.getTimestamp("time").toLocalDateTime());
                Subject subject = new Subject();
                subject.setId(rs.getString("subject_id"));
                subject.setName(rs.getString("subject_name"));
                subject.setLevel(rs.getString("level"));
                subject.setFee(rs.getDouble("fee"));
                subject.setStatus("active");
                subject.setDescription(rs.getString("description"));
                course.setSubject(subject);
                Tutor tutor = new Tutor();
                tutor.setId(rs.getString("tutor_id"));
                tutor.setName(rs.getString("tutor_name"));
                tutor.setAddress(rs.getString("address"));
                tutor.setSpecialization(rs.getString("specialization"));
                tutor.setEvaluate(rs.getInt("evaluate"));
                course.setTutor(tutor);
                course.setStudentCount(rs.getInt("student_count"));
                courses.add(course);
            }
            System.out.println("Fetched " + courses.size() + " courses from database.");
        } catch (SQLException e) {
            System.err.println("SQL Error in getAllAvailableCourses: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch courses", e);
        }
        return courses;
    }

    public Course getCourseById(String courseId) throws SQLException {
        System.out.println("Fetching course with ID: " + courseId);
        String sql = "SELECT c.*, s.name AS subject_name, s.level, s.fee, s.status, s.description, " +
                "t.name AS tutor_name, t.specialization, t.address, t.evaluate, " +
                "COUNT(rs.course_id) AS student_count " +
                "FROM course c " +
                "JOIN subject s ON c.subject_id = s.id " +
                "JOIN tutor t ON c.tutor_id = t.id " +
                "LEFT JOIN registered_subjects rs ON c.id = rs.course_id " +
                "WHERE c.id = ? " +
                "GROUP BY c.id, c.subject_id, c.tutor_id, c.time, " +
                "s.name, s.level, s.fee, s.status, s.description, " +
                "t.name, t.specialization, t.address, t.evaluate";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getString("id"));
                course.setSubjectId(rs.getString("subject_id"));
                course.setTutorId(rs.getString("tutor_id"));
                course.setTime(rs.getTimestamp("time").toLocalDateTime());
                Subject subject = new Subject();
                subject.setId(rs.getString("subject_id"));
                subject.setName(rs.getString("subject_name"));
                subject.setLevel(rs.getString("level"));
                subject.setFee(rs.getDouble("fee"));
                subject.setStatus(rs.getString("status"));
                subject.setDescription(rs.getString("description"));
                course.setSubject(subject);
                Tutor tutor = new Tutor();
                tutor.setId(rs.getString("tutor_id"));
                tutor.setName(rs.getString("tutor_name"));
                tutor.setAddress(rs.getString("address"));
                tutor.setSpecialization(rs.getString("specialization"));
                tutor.setEvaluate(rs.getInt("evaluate"));
                course.setTutor(tutor);
                course.setStudentCount(rs.getInt("student_count"));
                System.out.println("Course found: " + course.getId() + ", subject: " + subject.getName());
                return course;
            }
            System.out.println("Course not found for ID: " + courseId);
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Error in getCourseById: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void registerCourse(String courseId, String studentId) throws SQLException {
        String checkSql = "SELECT status FROM registered_subjects WHERE course_id = ? AND student_id = ?";
        String insertSql = "INSERT INTO registered_subjects (course_id, student_id, registration_date, number_of_lessons, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            checkStmt.setString(1, courseId);
            checkStmt.setString(2, studentId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if ("registered".equalsIgnoreCase(status)) {
                    System.out.println("Registration already exists with status 'registered' for course: " + courseId + " and student: " + studentId);
                    throw new RuntimeException("Bạn đã đăng ký và thanh toán khóa học này trước đó.");
                }
                System.out.println("Existing registration with status: " + status + ", allowing re-registration");
                return;
            }

            insertStmt.setString(1, courseId);
            insertStmt.setString(2, studentId);
            insertStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            insertStmt.setInt(4, 12);
            insertStmt.setString(5, "pending_payment");
            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course registered with status: " + courseId + " for student: " + studentId);
            } else {
                throw new RuntimeException("Failed to register course");
            }
        }
    }

    public void registerTrial(String courseId, String studentId) {
        System.out.println("Registering trial lesson for course " + courseId + " for student " + studentId);

        String sqlRegister = "INSERT INTO registered_subjects (course_id, student_id, registration_date, number_of_lessons, status) " +
                "VALUES (?, ?, CURDATE(), 1, 'trial')";
        String sqlLesson = "INSERT INTO lesson (course_id, student_id, status, time) " +
                "SELECT ?, ?, 'scheduled', DATE_ADD(time, INTERVAL 1 DAY) FROM course WHERE id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstmtRegister = conn.prepareStatement(sqlRegister)) {
                pstmtRegister.setString(1, courseId);
                pstmtRegister.setString(2, studentId);
                pstmtRegister.executeUpdate();
            }

            try (PreparedStatement pstmtLesson = conn.prepareStatement(sqlLesson)) {
                pstmtLesson.setString(1, courseId);
                pstmtLesson.setString(2, studentId);
                pstmtLesson.setString(3, courseId);
                pstmtLesson.executeUpdate();
            }

            conn.commit();
            System.out.println("Trial lesson scheduled successfully.");
        } catch (SQLException e) {
            System.err.println("SQL Error in registerTrial: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Failed to register trial lesson", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updatePaymentStatus(String courseId, String studentId, String status) throws SQLException {
        String sql = "UPDATE registered_subjects SET status = ? WHERE course_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, courseId);
            stmt.setString(3, studentId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated status to " + status + " for course: " + courseId + ", student: " + studentId);
            } else {
                System.out.println("No record updated for course: " + courseId + ", student: " + studentId);
            }
        }
    }
}