package DAO;

import model.Course;
import model.Tutor;
import model.Subject;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getAllAvailableCourses() {
        System.out.println("Fetching all available courses...");
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.*, s.name AS subject_name, s.level, s.fee, t.name AS tutor_name, t.specialization, t.address " +
                "FROM course c JOIN subject s ON c.subject_id = s.id " +
                "JOIN tutor t ON c.tutor_id = t.id " +
                "WHERE s.status = 'active'";

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
                subject.setStatus("active"); // Đã lọc theo status = 'active' trong truy vấn
                course.setSubject(subject);
                Tutor tutor = new Tutor();
                tutor.setId(rs.getString("tutor_id"));
                tutor.setName(rs.getString("tutor_name"));
                tutor.setAddress(rs.getString("address"));
                tutor.setSpecialization(rs.getString("specialization"));
                course.setTutor(tutor);
                courses.add(course);
            }
            System.out.println("Fetched " + courses.size() + " courses from database.");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return courses;
    }

    public Course getCourseById(String courseId) throws SQLException {
        System.out.println("Fetching course with ID: " + courseId);
        String sql = "SELECT c.*, s.name AS subject_name, s.level, s.fee, s.status, t.name AS tutor_name, t.specialization, t.address " +
                "FROM course c JOIN subject s ON c.subject_id = s.id " +
                "JOIN tutor t ON c.tutor_id = t.id " +
                "WHERE c.id = ?";

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
                subject.setStatus(rs.getString("status")); // Bây giờ cột status được lấy từ DB
                course.setSubject(subject);
                Tutor tutor = new Tutor();
                tutor.setId(rs.getString("tutor_id"));
                tutor.setName(rs.getString("tutor_name"));
                tutor.setAddress(rs.getString("address"));
                tutor.setSpecialization(rs.getString("specialization"));
                course.setTutor(tutor);
                System.out.println("Course found: " + course.getId() + ", subject: " + subject.getName());
                return course;
            }
            System.out.println("Course not found for ID: " + courseId);
        }
        return null;
    }

    public void registerCourse(String courseId, String studentId) {
        System.out.println("Registering course " + courseId + " for student " + studentId);
        String sql = "INSERT INTO registered_subjects (course_id, student_id, registration_date, number_of_lessons, status) " +
                "VALUES (?, ?, CURDATE(), 10, 'pending_approval')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            stmt.setString(2, studentId);
            stmt.executeUpdate();
            System.out.println("Course registered.");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
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
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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
}