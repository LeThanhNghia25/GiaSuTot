package DAO;

import Controller.LessonController;
import Utils.DBConnection;
import model.Course;
import model.RegisteredSubjects;
import model.Student;
import model.Tutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {
    private Connection conn = DBConnection.getConnection();

    public LessonDAO() {}
    public List<Course> getListCourseByTutor(Tutor tutor) throws SQLException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = courseDAO.getAllCourses();
        List<Course> filteredCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTutorId().equals(tutor.getId())) {
                filteredCourses.add(course);
            }
        }
        return filteredCourses;
}
    public List<String> getListStudentIdByTutor(Tutor tutor) throws SQLException {
        List<String> studentIds = new ArrayList<>();
        RegisteredSubjectDAO registeredSubjectDAO = new RegisteredSubjectDAO();
        List<Course> courses = getListCourseByTutor(tutor);
        List<RegisteredSubjects> registeredSubjectsList = registeredSubjectDAO.getAllReSubject();
        for(Course course : courses){
            for(RegisteredSubjects registeredSubject : registeredSubjectsList){
                if (registeredSubject.getCourse_id().equals(course.getId())){
                    studentIds.add(registeredSubject.getStudent_id());

                }
            }
        }
        return studentIds;
        }

    public static void main(String[] args) throws SQLException {
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutors = tutorDAO.getTutorById("tut001");
        LessonDAO lessonDAO = new LessonDAO();
        List<Course> courses = lessonDAO.getListCourseByTutor(tutors);
        for(Course course : courses){
            System.out.println(course);
        }
        List<String> studentIds = lessonDAO.getListStudentIdByTutor(tutors);
        for(String studentId : studentIds){
            System.out.println(studentId);
        }
        }

    }



