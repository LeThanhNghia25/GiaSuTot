package DAO;

import Utils.DBConnection;
import model.Account;
import model.Student;
import java.sql.*;

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


    public void insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student (id_st, name, birth, describeSt, id_acc) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setDate(3, java.sql.Date.valueOf(student.getBirth()));
            ps.setString(4, student.getDescribe());
            ps.setString(5, student.getaccount_id());
            int rowsAffected = ps.executeUpdate();
        }
    }
    public boolean insertggSt(Student student) {
        String sql = "INSERT INTO student (id_st, name, id_acc) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, generateStudentId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getaccount_id());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}