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
        while (rs.next()) {
            list.add(new Subject(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
            ));
        }
        return list;
    }

    public void addSubject(Subject s) throws SQLException {
        String sql = "INSERT INTO subjects (name, description) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, s.getName());
        stmt.setString(2, s.getDescription());
        stmt.executeUpdate();
    }

    public Subject getSubjectById(int id) throws SQLException {
        String sql = "SELECT * FROM subjects WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Subject(id, rs.getString("name"), rs.getString("description"));
        }
        return null;
    }

    public void updateSubject(Subject s) throws SQLException {
        String sql = "UPDATE subjects SET name=?, description=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, s.getName());
        stmt.setString(2, s.getDescription());
        stmt.setInt(3, s.getId());
        stmt.executeUpdate();
    }

    public void deleteSubject(int id) throws SQLException {
        String sql = "DELETE FROM subjects WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}

