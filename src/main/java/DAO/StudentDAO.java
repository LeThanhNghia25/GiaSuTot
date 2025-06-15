package DAO;

import Utils.DBConnection;
import model.Account;
import model.Student;

import java.sql.*;
import java.time.LocalDate;

public class StudentDAO {
    public StudentDAO() {}

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

    public boolean insertggSt(Student student) throws SQLException {
        String sql = "INSERT INTO student (id, name, account_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("Student ID: " + student.getId());
            System.out.println("Student Name: " + student.getName());

            if (student.getAccount() == null) {
                System.out.println("Account is null! Không thể thực hiện insert.");
                return false;
            }

            System.out.println("Account ID: " + student.getAccount().getId());
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getAccount().getId());
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Student getStudentByAccountId(String accountId) throws SQLException {
        String sql = "SELECT s.id, s.name, s.birth, s.description, a.email " +
                "FROM student s JOIN account a ON s.account_id = a.id " +
                "WHERE s.account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birth");
                LocalDate birth = (birthDate != null) ? birthDate.toLocalDate() : null;
                String description = rs.getString("description");
                String email = rs.getString("email");

                Account acc = new Account();
                acc.setId(accountId);
                acc.setEmail(email);

                return new Student(id, name, birth, description, acc);
            }
        }
        return null;
    }

    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET name = ?, birth = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setDate(2, Date.valueOf(student.getBirth()));
            ps.setString(3, student.getDescription());
            ps.setString(4, student.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Account getAccountByEmail(String email) throws SQLException {
        String sql = "SELECT id FROM account WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getString("id"));
                acc.setEmail(email);
                return acc;
            }
        }
        return null;
    }

    public Student getStudentById(String id) throws SQLException {
        String sql = "SELECT s.*, a.email " +
                "FROM student s JOIN account a ON s.account_id = a.id " +
                "WHERE s.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String studentId = rs.getString("id");
                String name = rs.getString("name");
                LocalDate birth = rs.getDate("birth").toLocalDate();
                String description = rs.getString("description");
                String accountId = rs.getString("account_id");
                String email = rs.getString("email");

                Account account = new Account();
                account.setId(accountId);
                account.setEmail(email);

                return new Student(studentId, name, birth, description, account);
            }
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        StudentDAO dao = new StudentDAO();
        Student student = dao.getStudentById("st001");
        System.out.println(student);
    }
}