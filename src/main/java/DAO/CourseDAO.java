package DAO;

import Utils.DBConnection;
import model.Course;
import model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class CourseDAO {
    private Connection conn = DBConnection.getConnection();

    public CourseDAO() {}

    // Lấy toàn bộ danh sách khoá học
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courseList = new ArrayList<>();
        String sql = "SELECT * FROM course";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        System.out.println("Fetching courses...");

        while (resultSet.next()) {
            Course course = new Course();
            course.setId(resultSet.getString("id"));
            course.setSubjectId(resultSet.getString("subject_id"));
            course.setTutorId(resultSet.getString("tutor_id"));
            course.setTotal_lesson(resultSet.getInt("total_lesson")); // <-- thêm dòng này
            course.setTime(resultSet.getTimestamp("time")); // Timestamp sang Date
            courseList.add(course);
        }

        return courseList;
    }

    // Map giữa Course và Subject
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

    // Tìm kiếm theo tên môn học (không dấu)
    public HashMap<Course, Subject> FindByName(String subName) throws SQLException {
        HashMap<Course, Subject> subjectMap = getAllSubjects();
        HashMap<Course, Subject> result = new HashMap<>();

        String findname = removeDiacritics(subName.toLowerCase());

        for (Course course : subjectMap.keySet()) {
            String sName = removeDiacritics(subjectMap.get(course).getName().toLowerCase());
            if (sName.contains(findname)) {
                result.put(course, subjectMap.get(course));
            }
        }

        return result;
    }

    // Xoá dấu tiếng Việt khỏi chuỗi
    public static String removeDiacritics(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");
        return result.replaceAll("Đ", "D").replaceAll("đ", "d");
    }

    public static void main(String[] args) throws SQLException {
        CourseDAO courseDAO = new CourseDAO();

        System.out.println("--- All Courses ---");
        List<Course> courses = courseDAO.getAllCourses();
        for (Course course : courses) {
            System.out.println(course);
        }

        System.out.println("\n--- All Course-Subject Mappings ---");
        HashMap<Course, Subject> subjectMap = courseDAO.getAllSubjects();
        for (Course key : subjectMap.keySet()) {
            System.out.println("Key: " + key + ", Value: " + subjectMap.get(key));
        }

        System.out.println("\n--- Search by Subject Name: 'Hóa học' ---");
        HashMap<Course, Subject> findSub = courseDAO.FindByName("Hóa học");
        for (Course key : findSub.keySet()) {
            System.out.println("Key: " + key + ", Value: " + findSub.get(key));
        }
    }
}
