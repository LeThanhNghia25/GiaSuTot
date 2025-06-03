package DAO;

import model.Account;
import model.Student;
import model.Subject;
import Utils.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminSubjectDAO {

    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subject";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Subject(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("level"),
                        rs.getString("description"),
                        rs.getDouble("fee"),
                        rs.getString("status")
                ));
            }
        }
        return list;
    }

    public List<Subject> searchSubjects(String tenMonHoc, String lop) throws SQLException {
        List<Subject> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM subject WHERE 1=1");

        if (tenMonHoc != null && !tenMonHoc.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (lop != null && !lop.trim().isEmpty()) {
            sql.append(" AND level LIKE ?");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (tenMonHoc != null && !tenMonHoc.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + tenMonHoc + "%");
            }
            if (lop != null && !lop.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + lop + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subject sb = new Subject(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("level"),
                            rs.getString("description"),
                            rs.getDouble("fee"),
                            rs.getString("status")
                    );
                    list.add(sb);
                }
            }
        }
        return list;
    }

    public void addSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subject (id, name, level, description, fee, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subject.getId());
            stmt.setString(2, subject.getName());
            stmt.setString(3, subject.getLevel());
            stmt.setString(4, subject.getDescription());
            stmt.setDouble(5, subject.getFee());
            stmt.setString(6, subject.getStatus());
            stmt.executeUpdate();
        }
    }

    public void updateSubject(Subject subject) throws SQLException {
        String sql = "UPDATE subject SET name = ?, level = ?, description = ?, fee = ?, status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subject.getName());
            stmt.setString(2, subject.getLevel());
            stmt.setString(3, subject.getDescription());
            stmt.setDouble(4, subject.getFee());
            stmt.setString(5, subject.getStatus());
            stmt.setString(6, subject.getId());
            stmt.executeUpdate();
        }
    }

    public void hideSubject(String id) throws SQLException {
        String sql = "UPDATE subject SET status = 'inactive' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void restoreSubject(String id) throws SQLException {
        String sql = "UPDATE subject SET status = 'active' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public Subject getSubjectById(String id) throws SQLException {
        String sql = "SELECT * FROM subject WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Subject(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("level"),
                            rs.getString("description"),
                            rs.getDouble("fee"),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public List<Student> getEnrolledStudents(String subjectId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM student s " +
                "JOIN registered_subjects rs ON s.id = rs.student_id " +
                "JOIN course c ON rs.course_id = c.id " +
                "WHERE c.subject_id = ? " +
                "AND rs.status = 'registered'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subjectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Account acc = new Account();
                    acc.setId(rs.getString("id"));
                    students.add(new Student(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDate("birth").toLocalDate(),
                            rs.getString("description"),
                            acc
                    ));
                }
            }
        }
        return students;
    }

    public void sendNotification(String accountId, String message) throws SQLException {
        String notificationId = "notif" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 31);
        String sql = "INSERT INTO notifications (id, account_id, message, created_at, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, notificationId);
            stmt.setString(2, accountId);
            stmt.setString(3, message);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(5, "sent");
            stmt.executeUpdate();
        }
    }

    public void cancelRegistrations(String subjectId) throws SQLException {
        String sql = "UPDATE registered_subjects rs " +
                "SET rs.status = 'cancelled' " +
                "WHERE rs.course_id IN (SELECT c.id FROM course c WHERE c.subject_id = ?)" +
                "AND rs.status = 'registered'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, subjectId);
            stmt.executeUpdate();
        }
    }
}