package DAO;

import model.Student;
import model.Subject;
import model.RegisteredSubjects;
import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TutorSubjectDAO {
    public TutorSubjectDAO() {}

    // Lấy danh sách môn học active mà tutor dạy
    public List<Subject> getActiveSubjectsByTutor(String tutorId) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT DISTINCT s.id, s.name, s.level, s.description, s.fee, s.status " +
                "FROM subject s " +
                "JOIN course c ON s.id = c.subject_id " +
                "WHERE s.status = 'active' AND c.tutor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tutorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setId(rs.getString("id"));
                    subject.setName(rs.getString("name"));
                    subject.setLevel(rs.getString("level"));
                    subject.setDescription(rs.getString("description"));
                    subject.setFee(rs.getDouble("fee"));
                    subject.setStatus(rs.getString("status"));
                    subjects.add(subject);
                }
            }
        }
        return subjects;
    }

    public List<RegisteredSubjects> getRegisteredSubjectsByCourse(String subjectId) throws SQLException {
        List<RegisteredSubjects> registeredSubjects = new ArrayList<>();
        String sql = "SELECT rs.course_id, rs.student_id, rs.registration_date, rs.number_of_lessons, rs.status, s.name AS student_name " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.course_id = c.id " +
                "JOIN student s ON rs.student_id = s.id " +
                "WHERE c.subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, subjectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegisteredSubjects rsSub = new RegisteredSubjects();
                    rsSub.setCourse_id(rs.getString("course_id"));
                    rsSub.setStudent_id(rs.getString("student_id"));
                    rsSub.setRegistration_date(rs.getDate("registration_date") != null ? rs.getDate("registration_date").toLocalDate() : null);
                    rsSub.setNumber_of_lessons(rs.getInt("number_of_lessons"));
                    rsSub.setStatus(rs.getString("status"));

                    Student student = new Student();
                    student.setId(rs.getString("student_id"));
                    student.setName(rs.getString("student_name"));
                    rsSub.setStudent(student);

                    registeredSubjects.add(rsSub);
                }
            }
        }
        return registeredSubjects;
    }

    public Map<String, Integer> getCompletedLessonCounts() throws SQLException {
        Map<String, Integer> lessonCounts = new HashMap<>();
        String sql = "SELECT course_id, student_id, COUNT(*) AS completed_count " +
                "FROM lesson " +
                "WHERE status = 'completed' " +
                "GROUP BY course_id, student_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String key = rs.getString("course_id") + "_" + rs.getString("student_id");
                lessonCounts.put(key, rs.getInt("completed_count"));
            }
        }
        return lessonCounts;
    }


}