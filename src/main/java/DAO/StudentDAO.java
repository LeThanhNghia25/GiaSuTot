package DAO;

import Utils.DBConnection;
import model.Account;
import model.Student;

import java.sql.*;
import java.time.LocalDate;

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
            ps.setString(5, student.getAccount().getId());
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

                    Account acc = new Account();
                    acc.setId(id);
                    return new Student(id, name, birth, description, acc);
                }
            }
        }
        return null;
    }
}