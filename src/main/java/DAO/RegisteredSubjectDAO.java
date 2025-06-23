package DAO;

import Utils.DBConnection;
import model.RegisteredSubjects;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisteredSubjectDAO {
    public List<RegisteredSubjects> getAllReSubject() throws SQLException {
        List<RegisteredSubjects> registeredSubjects = new ArrayList<>();
        String sql = "SELECT rs.*, s.name AS student_name " +
                "FROM registered_subjects rs " +
                "LEFT JOIN student s ON rs.student_id = s.id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RegisteredSubjects rsSubject = new RegisteredSubjects();
                rsSubject.setCourse_id(rs.getString("course_id"));
                rsSubject.setStudent_id(rs.getString("student_id"));
                rsSubject.setRegistration_date(rs.getDate("registration_date").toLocalDate());
                rsSubject.setNumber_of_lessons(rs.getInt("number_of_lessons"));
                rsSubject.setStatus(rs.getString("status"));

                // Thêm thông tin Student (tùy chọn)
                if (rs.getString("student_name") != null) {
                    Student student = new Student();
                    student.setId(rs.getString("student_id"));
                    student.setName(rs.getString("student_name"));
                    rsSubject.setStudent(student);
                }

                registeredSubjects.add(rsSubject);
            }
        }
        return registeredSubjects;
    }
    public List<RegisteredSubjects> getRegisteredSubjectByID(String id) throws SQLException {
        String sql = "SELECT * FROM registered_subjects WHERE student_id= ?";
        List<RegisteredSubjects> registeredSubjects = new ArrayList<>();


        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RegisteredSubjects registeredSubject = new RegisteredSubjects();
                registeredSubject.setCourse_id(rs.getString("course_id"));
                registeredSubject.setStudent_id(rs.getString("student_id"));
                registeredSubject.setRegistration_date(rs.getDate("registration_date").toLocalDate());
                registeredSubject.setNumber_of_lessons(rs.getInt("number_of_lessons"));
                registeredSubject.setStatus(rs.getString("status"));
                registeredSubjects.add(registeredSubject);

                System.out.println("Registered Subject ID: " + registeredSubject.getCourse_id()+ "student ID: " + registeredSubject.getStudent_id());
            }
        }
        return registeredSubjects;

    }
    public int course_Progress(String stID, String courseId) throws SQLException {
        int progress = 0;
        String sql = "SELECT COUNT(lesson.course_id) as 'count' " +
                "FROM registered_subjects " +
                "INNER JOIN lesson ON registered_subjects.course_id = lesson.course_id " +
                "AND registered_subjects.student_id = lesson.student_id " +
                "WHERE registered_subjects.student_id = ? " +
                "AND lesson.status = 'completed' " +
                "AND lesson.course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stID);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                progress = rs.getInt("count");
            }
        }
        return progress;
    }
}