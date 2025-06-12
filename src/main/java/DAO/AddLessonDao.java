package DAO;

import Utils.DBConnection;

import java.sql.*;

public class AddLessonDao {
    private Connection conn = DBConnection.getConnection();    public AddLessonDao() {}
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


