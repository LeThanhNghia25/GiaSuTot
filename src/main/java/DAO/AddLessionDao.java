package DAO;

import Utils.DBConnection;
import model.Lession;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddLessionDao {
    private Connection conn = DBConnection.getConnection();    public AddLessionDao() {}
    public void insertLession(String course_id, String student_id, String time) throws SQLException {
        String sql = "insert into Lesson values(?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, course_id);
        stmt.setString(2, student_id);
        stmt.setString(3, "scheduled");
        stmt.setTimestamp(4, Timestamp.valueOf(time));
        stmt.executeUpdate();

    }
//    public List<Lession> getLession(String course_id,String student_id) throws SQLException {
//        String sql = "select * from Lession where course_id=? and student_id=?";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setString(1, course_id);
//        stmt.setString(2, student_id);
//        ResultSet rs = stmt.executeQuery();
//        List<Lession> list = new ArrayList<Lession>();
//        while (rs.next()) {
//
//
//        }

    }


