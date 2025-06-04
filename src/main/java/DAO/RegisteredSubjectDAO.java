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
}