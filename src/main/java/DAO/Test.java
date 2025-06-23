package DAO;

import model.Course;
import model.Lesson;
import model.RegisteredSubjects;
import model.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws SQLException {
        LessonDAO dao = new LessonDAO();
        String acc = "acc014";
        StudentDAO studentDAO = new StudentDAO();
        RegisteredSubjectDAO registeredSubjectDAO = new RegisteredSubjectDAO();
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courseList = new ArrayList<>();
        Student student = studentDAO.getStudentByAccountId(acc);
        String studenId = "st008";
        HashMap<Course, Integer> data = new HashMap<>();
        int pross =0;
        try {

            List<RegisteredSubjects> registeredSubjects= registeredSubjectDAO.getRegisteredSubjectByID(student.getId());
            for (RegisteredSubjects rs : registeredSubjects) {
                Course course = new Course();
                course= courseDAO.getCourseById(rs.getCourse_id());
                courseList.add(course);
                pross = registeredSubjectDAO.course_Progress(student.getId(), course.getId());
                data.put(course, pross);


            }
            for (Course course : courseList) {
                System.out.println(course);
            }
            for (Course key : data.keySet()) {
                System.out.println("Key: " + key + ", Value: " + data.get(key));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
