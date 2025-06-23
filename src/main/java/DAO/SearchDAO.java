package DAO;

import Utils.DBConnection;
import model.Course;
import model.Subject;
import model.Tutor;

import java.sql.*;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchDAO {

    // Lấy tất cả khóa học kèm chủ đề
    public List<Course> getAllCoursesWithSubjects() throws SQLException {
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
        } catch (SQLException e) {
            System.err.println("SQL Error in getAllCoursesWithSubjects: " + e.getMessage());
            throw e;
        }
        return courses;
    }

    // Tìm kiếm theo tên môn học (gần đúng)
    public List<Course> findBySubjectName(String subName) throws SQLException {
        if (subName == null || subName.trim().isEmpty()) {
            return getAllCoursesWithSubjects();
        }
        String searchTerm = removeDiacritics(subName.toLowerCase());
        return getAllCoursesWithSubjects().stream()
                .filter(course -> removeDiacritics(course.getSubject().getName().toLowerCase()).contains(searchTerm))
                .collect(Collectors.toList());
    }

    // Chuyển đổi chuỗi không dấu
    public static String removeDiacritics(String input) {
        if (input == null) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("")
                .replaceAll("Đ", "D").replaceAll("đ", "d");
    }
}