package DAO;

import Utils.DBConnection;
import model.Course;
import model.Subject;
import model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
                    course.setId(resultSet.getString("id_course"));
                    course.setId_subject(resultSet.getString("id_sub"));
                    course.setId_tutor(resultSet.getString("id_tutor"));
                    course.setDateTime(resultSet.getTimestamp("timeCourse")); // Lấy timestamp và chuyển sang Date
                    courseList.add(course);
                }

            return courseList;
        }
        public HashMap<Course, Subject> getAllSubjects() throws SQLException {
        HashMap<Course, Subject> subjectMap = new HashMap<>();
        List<Course> courseList = getAllCourses();
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectList = subjectDAO.getAllSubjects();
        for (Course course : courseList) {
            for (Subject subject : subjectList) {
                if(course.getId_subject().equals(subject.getId())){
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
            String sName = subjectMap.get(key).getName().toLowerCase();
          if(sName.contains(findname) && subjectMap.get(key).getStatus().equals("active")){
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
        HashMap<Course, Subject> findSub = courseDAO.FindByName("Hóa học");
        for (Course key : findSub.keySet()) {
            System.out.println("Key: " + key + ", Value: " + findSub.get(key));

        }
    }

    }



