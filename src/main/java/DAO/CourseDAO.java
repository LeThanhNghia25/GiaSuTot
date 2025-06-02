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
import java.text.Normalizer;
import java.util.regex.Pattern;

public class CourseDAO {
    private Connection conn = DBConnection.getConnection();

    public CourseDAO() {

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
                    course.setId(resultSet.getString("id"));
                    course.setSubjectId(resultSet.getString("subject_id"));
                    course.setTutorId(resultSet.getString("tutor_id"));
                    course.setTime(resultSet.getTimestamp("time")); // Lấy timestamp và chuyển sang Date
                    courseList.add(course);
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
                if(course.getSubjectId().equals(subject.getId())){
                    subjectMap.put(course, subject);
                }
            }
        }

        return subjectMap;

        }
    public HashMap<Course, Subject> FindByName(String subName) throws SQLException {
        HashMap<Course, Subject> subjectMap = new CourseDAO().getAllSubjects();
        HashMap<Course, Subject> result = new HashMap<>();
        for (Course key : subjectMap.keySet()) {
            String findname = subName.toLowerCase();
                findname = removeDiacritics(findname);
            String sName = subjectMap.get(key).getName().toLowerCase();
            if(sName.contains(findname)){
              result.put(key, subjectMap.get(key));
          }
        }

        return result;
    }
//chuyen thanh chuoi khong dau
    public static String removeDiacritics(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");
        return result.replaceAll("Đ", "D").replaceAll("đ", "d");
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
        HashMap<Course, Subject> findSub = courseDAO.FindByName("Hóa học");
        for (Course key : findSub.keySet()) {
            System.out.println("Key: " + key + ", Value: " + findSub.get(key));

        }
    }

    }



