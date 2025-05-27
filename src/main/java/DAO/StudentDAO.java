package DAO;

import Utils.DBConnection;
import model.Account;
import model.Student;
import java.sql.*;
import java.time.LocalDate;

public class StudentDAO {
    private Connection conn;

    public StudentDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public String generateStudentId() throws SQLException {
        String sql = "SELECT COUNT(*) FROM student";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("st%03d", count);
            }
        }
        return null;
    }

    public boolean insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (id_st, name, birth, describe_st, id_acc) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDate(3, java.sql.Date.valueOf(student.getBirth()));
            ps.setString(4, student.getDescribe());
            ps.setString(5, student.getAccountId().getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Student getStudentByAccountId(String id_acc) throws SQLException {
        String sql = "SELECT * FROM student WHERE id_acc = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id_acc);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id_st = rs.getString("id_st");
                String name = rs.getString("name");
                LocalDate birth = rs.getDate("birth").toLocalDate();
                String describe = rs.getString("describe_st");
                Account acc = new Account(); // Tạo đối tượng Account đơn giản
                acc.setId(id_acc);
                return new Student(id_st, name, birth, describe, acc);
            }
        }

        return null; // không tìm thấy
    }

    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET name = ?, birth = ?, describe_st = ? WHERE id_st = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setDate(2, Date.valueOf(student.getBirth()));
            ps.setString(3, student.getDescribe());
            ps.setString(4, student.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Account getAccountByEmail(String email) throws SQLException {
        String sql = "SELECT id_acc FROM account WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getString("id_acc"));
                acc.setEmail(email);
                return acc;
            }
        }
        return null;
    }

}