package DAO;

import model.Student;
import model.Subject;
import Utils.DBConnection;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AdminSubjectDAO {
    private Connection conn;

    public AdminSubjectDAO() throws SQLException {
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
    public List<Subject> searchSubjects(String tenMonHoc, String lop, String tinh, String tenGiaoVien) throws SQLException {
        List<Subject> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Subject WHERE 1=1");

        if (tenMonHoc != null && !tenMonHoc.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (lop != null && !lop.trim().isEmpty()) {
            sql.append(" AND level LIKE ?");
        }
        if (tinh != null && !tinh.trim().isEmpty()) {
            sql.append(" AND status LIKE ?");
        }
        // Nếu bạn có trường giáo viên, thêm điều kiện
        if (tenGiaoVien != null && !tenGiaoVien.trim().isEmpty()) {
            sql.append(" AND teacher_name LIKE ?");
        }

        PreparedStatement ps = conn.prepareStatement(sql.toString());

        int paramIndex = 1;
        if (tenMonHoc != null && !tenMonHoc.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + tenMonHoc + "%");
        }
        if (lop != null && !lop.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + lop + "%");
        }
        if (tinh != null && !tinh.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + tinh + "%");
        }
        if (tenGiaoVien != null && !tenGiaoVien.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + tenGiaoVien + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Subject sb = new Subject(
                    rs.getString("id_sub"),
                    rs.getString("name"),
                    rs.getString("level"),
                    rs.getString("description"),
                    rs.getDouble("fee"),
                    rs.getString("status")
            );
            list.add(sb);
        }
        return list;
    }


    public void addSubject(Subject subject) throws SQLException {
        String sql = "INSERT INTO subject (id_sub, name, level, describe_sb, fee, status_sub) VALUES (?, ?, ?, ?, ?, ?)";
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
        String sql = "UPDATE subject SET name = ?, level = ?, describe_sb = ?, fee = ?, status_sub = ? WHERE id_sub = ?";
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
        String sql = "UPDATE subject SET status_sub = 'inactive' WHERE id_sub = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

    public void restoreSubject(String id) throws SQLException {
        String sql = "UPDATE subject SET status_sub = 'inactive' WHERE id_sub = ?";
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
            ps.setDate(3, Date.valueOf(student.getBirth()));
            ps.setString(4, student.getDescribe());
            ps.setString(5, student.getaccount_id());
            ps.executeUpdate();
        }
    }
}

