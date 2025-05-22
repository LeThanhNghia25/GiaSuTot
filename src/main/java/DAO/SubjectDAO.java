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
        String sql = "SELECT * FROM subject";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new Subject(
                    rs.getString("id_sub"),
                    rs.getString("name"),
                    rs.getString("level"),
                    rs.getString("describe_sb"),
                    rs.getDouble("fee"),
                    rs.getString("status_sub")
            ));
        }
        return list;
    }

    public void addSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subject (id_sub, name, level, describeSb, fee, statusSub) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, subject.getId());
        stmt.setString(2, subject.getName());
        stmt.setString(3, subject.getLevel());
        stmt.setString(4, subject.getDescription());
        stmt.setDouble(5, subject.getFee());
        stmt.setString(6, subject.getStatus());
        stmt.executeUpdate();
    }

    public void updateSubject(Subject subject) throws SQLException {
        String sql = "UPDATE subject SET name = ?, level = ?, describeSb = ?, fee = ?, statusSub = ? WHERE id_sub = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, subject.getName());
        stmt.setString(2, subject.getLevel());
        stmt.setString(3, subject.getDescription());
        stmt.setDouble(4, subject.getFee());
        stmt.setString(5, subject.getStatus());
        stmt.setString(6, subject.getId());
        stmt.executeUpdate();
    }

    public void hideSubject(String id) throws SQLException {
        String sql = "UPDATE subject SET statusSub = 'inactive' WHERE id_sub = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

    public void restoreSubject(String id) throws SQLException {
        String sql = "UPDATE subject SET statusSub = 'active' WHERE id_sub = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

    public Subject getSubjectById(String id) throws SQLException {
        String sql = "SELECT * FROM subject WHERE id_sub = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Subject(
                    rs.getString("id_sub"),
                    rs.getString("name"),
                    rs.getString("level"),
                    rs.getString("describe_sb"),
                    rs.getDouble("fee"),
                    rs.getString("status_sub")
            );
        }
        return null;
    }
}
