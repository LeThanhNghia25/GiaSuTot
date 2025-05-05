package DAO;

import model.Subject;
import Utils.DBConnection;
import java.sql.*;
import java.util.*;

public class SubjectDAO {
    private Connection conn;

    public SubjectDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }

    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT * FROM subjects";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        System.out.println("Fetching subjects...");
        while (rs.next()) {
            System.out.println("Subject: " + rs.getString("name"));
            list.add(new Subject(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("status")
            ));
        }
        System.out.println("Total subjects: " + list.size());
        return list;
    }

    public void addSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subjects (name, description, status) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, subject.getName());
        stmt.setString(2, subject.getDescription());
        stmt.setInt(3, subject.getStatus());
        stmt.executeUpdate();
        System.out.println("Added subject: " + subject.getName());
    }

    public void updateSubject(Subject subject) throws SQLException {
        String sql = "UPDATE subjects SET name = ?, description = ?, status = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, subject.getName());
        stmt.setString(2, subject.getDescription());
        stmt.setInt(3, subject.getStatus());
        stmt.setInt(4, subject.getId());
        stmt.executeUpdate();
        System.out.println("Updated subject: " + subject.getName());
    }

    public void hideSubject(int id) throws SQLException {
        String sql = "UPDATE subjects SET status = 0 WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Hid subject with id: " + id);
    }

    public void restoreSubject(int id) throws SQLException {
        String sql = "UPDATE subjects SET status = 1 WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Restored subject with id: " + id);
    }

    public Subject getSubjectById(int id) throws SQLException {
        String sql = "SELECT * FROM subjects WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Subject(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("status")
            );
        }
        return null;
    }
}