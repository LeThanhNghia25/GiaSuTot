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
        String sql = "INSERT INTO student (id, name, birth, description, account_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDate(3, java.sql.Date.valueOf(student.getBirth()));
            ps.setString(4, student.getDescription());
            ps.setString(5, student.getAccountId()); // Đổi từ student.getAccountId().getId() thành student.getAccountId()
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public Student getStudentByAccountId(String accountId) throws SQLException {
        String sql = "SELECT s.id, s.name, s.birth, s.description, a.email " +
                "FROM student s JOIN account a ON s.account_id = a.id " + // Đổi id_acc thành account_id, a.id_acc thành a.id
                "WHERE s.account_id = ?"; // Đổi id_acc thành account_id

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id"); // Đổi id_st thành id
                String name = rs.getString("name");
                LocalDate birth = rs.getDate("birth").toLocalDate();
                String description = rs.getString("description"); // Đổi describe_st thành description
                String email = rs.getString("email");

                Account acc = new Account();
                acc.setId(accountId);
                acc.setEmail(email);

                return new Student(id, name, birth, description, accountId); // Đổi tham số cuối thành accountId (String)
            }
        }
        return null;
    }

    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET name = ?, birth = ?, description = ? WHERE id = ?"; // Đổi describe_st thành description, id_st thành id
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setDate(2, Date.valueOf(student.getBirth()));
            ps.setString(3, student.getDescription()); // Đổi getDescribe thành getDescription
            ps.setString(4, student.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Account getAccountByEmail(String email) throws SQLException {
        String sql = "SELECT id FROM account WHERE email = ?"; // Đổi id_acc thành id
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getString("id")); // Đổi id_acc thành id
                acc.setEmail(email);
                return acc;
            }
        }
        return null;
    }
}