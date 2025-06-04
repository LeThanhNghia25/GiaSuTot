package DAO;

import Utils.DBConnection;
import model.Course;
import model.RegisteredSubjects;
import model.Student;
import model.Tutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LessonDAO {
    public LessonDAO() {}

    public List<Course> getListCourseByTutor(Tutor tutor) throws SQLException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = courseDAO.getAllAvailableCourses();
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTutorId().equals(tutor.getId())) {
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
    }

    public List<String> getListStudentIdByTutor(Tutor tutor) throws SQLException {
        Set<String> studentIds = new HashSet<>();
        RegisteredSubjectDAO registeredSubjectDAO = new RegisteredSubjectDAO();
        List<Course> courses = getListCourseByTutor(tutor);
        List<RegisteredSubjects> registeredSubjectsList = registeredSubjectDAO.getAllReSubject();
        for (Course course : courses) {
            for (RegisteredSubjects registeredSubject : registeredSubjectsList) {
                if (registeredSubject.getCourse_id().equals(course.getId()) && "registered".equals(registeredSubject.getStatus())) {
                    studentIds.add(registeredSubject.getStudent_id());
                }
            }
        }
        return new ArrayList<>(studentIds);
    }

    public static void main(String[] args) throws SQLException {
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = tutorDAO.getTutorById("tut001");
        LessonDAO lessonDAO = new LessonDAO();
        List<Course> courses = lessonDAO.getListCourseByTutor(tutor);
        for (Course course : courses) {
            System.out.println(course);
        }
        List<String> studentIds = lessonDAO.getListStudentIdByTutor(tutor);
        for (String studentId : studentIds) {
            System.out.println(studentId);
        }
    }
}