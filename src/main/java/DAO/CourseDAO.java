package DAO;

import Utils.DBConnection;
import model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Connection conn;

    public CourseDAO() throws SQLException {
        conn = DBConnection.getConnection();
    }


        // Phương thức để lấy tất cả dữ liệu từ bảng Course
        public List<Course> getAllCourses() throws SQLException {
            List<Course> courseList = new ArrayList<>();
            String sql = "SELECT * FROM course";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            System.out.println("Fetching subjects...");

                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getString("id_course"));
                    course.setId_subject(resultSet.getString("id_sub"));
                    course.setId_tutor(resultSet.getString("id_tutor"));
                    course.setDateTime(resultSet.getTimestamp("timeCourse")); // Lấy timestamp và chuyển sang Date
                    courseList.add(course);
                }

            return courseList;
        }
}


