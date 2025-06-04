package DAO;

import Utils.DBConnection;
import model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public String generateStudentId() throws SQLException {
        String sql = "SELECT COUNT(*) FROM student";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("st%03d", count);
            }
        }
        return null;
    }

    public boolean insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (id, name, birth, description, account_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDate(3, java.sql.Date.valueOf(student.getBirth()));
            ps.setString(4, student.getDescription());
            ps.setString(5, student.getAccountId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Student getStudentByAccountId(String accountId) throws SQLException {
        String sql = "SELECT * FROM student WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    LocalDate birth = rs.getDate("birth").toLocalDate();
                    String description = rs.getString("description");
                    return new Student(id, name, birth, description, accountId);
                }
            }
        }
        return null;
    }
    public Student getStudentById(String id_st) throws SQLException {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id_st);
            ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    LocalDate birth = rs.getDate("birth").toLocalDate();
                    String description = rs.getString("description");
                    String accountId = rs.getString("account_id");
                    return new Student(id, name, birth, description, accountId);
                }
        return null;
    }

    }

    public static void main(String[] args) throws SQLException {
        StudentDAO dao = new StudentDAO();
        Student student = dao.getStudentById("st001");
        System.out.println(student);
        List<String> ids = new ArrayList<>();
        ids.add("st001");
        ids.add("st002");
        ids.add("st003");
        List<Student> students = new ArrayList<>();
        for (String id : ids) {
            Student student1 = dao.getStudentById(id);
            students.add(student1);
        }
        for (Student student1 : students) {
            System.out.println(student1);
        }
    }
}