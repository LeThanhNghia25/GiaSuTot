package DAO;

import Utils.DBConnection;
import model.Course;
import model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseDAO {

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM course";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {
            System.out.println("Fetching courses...");
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getString("id"));
                course.setSubjectId(resultSet.getString("subject_id"));
                course.setTutorId(resultSet.getString("tutor_id"));
                course.setTime(resultSet.getTimestamp("time")); // Lấy timestamp và chuyển sang Date
                courseList.add(course);
            }
        }
        return courseList;
    }

    public HashMap<Course, Subject> getAllSubjects() throws SQLException {
        HashMap<Course, Subject> subjectMap = new HashMap<>();
        List<Course> courseList = getAllCourses();
        AdminSubjectDAO adminSubjectDAO = new AdminSubjectDAO();
        List<Subject> subjectList = adminSubjectDAO.getAllSubjects();
        for (Course course : courseList) {
            for (Subject subject : subjectList) {
                if (course.getSubjectId().equals(subject.getId())) {
                    subjectMap.put(course, subject);
                }
            }
        }
        return subjectMap;
    }

    public HashMap<Course, Subject> findByName(String subName) throws SQLException {
        HashMap<Course, Subject> subjectMap = getAllSubjects(); // Sử dụng instance hiện tại
        HashMap<Course, Subject> result = new HashMap<>();
        for (Course key : subjectMap.keySet()) {
            if (subName.equals(subjectMap.get(key).getName())) {
                result.put(key, subjectMap.get(key));
            }
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = courseDAO.getAllCourses();
        for (Course course : courses) {
            System.out.println(course);
        }
        HashMap<Course, Subject> subjectMap = courseDAO.getAllSubjects();
        for (Course key : subjectMap.keySet()) {
            System.out.println("Key: " + key + ", Value: " + subjectMap.get(key));
        }
        HashMap<Course, Subject> findSub = courseDAO.findByName("Hóa học");
        for (Course key : findSub.keySet()) {
            System.out.println("Key: " + key + ", Value: " + findSub.get(key));
        }
    }
}