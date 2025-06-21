package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ReviewDAO {

    public ReviewDAO() {

    }

    public void InsertReview(String tutorId,String courseId,String studentId,String reviewText) throws SQLException {
        String sql = "insert into reviews values(?,?,?,?,?)";

        LocalDateTime localDateTime = LocalDateTime.now();

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, tutorId);
        ps.setString(2, courseId);
        ps.setString(3, studentId);
        ps.setTimestamp(4, (Timestamp.valueOf(localDateTime)));
        ps.setString(5, reviewText);
        ps.executeUpdate();
        int rs = ps.getUpdateCount();
        System.out.println("Insert Review Successfully");
    }
}
