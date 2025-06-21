package DAO;

import Utils.DBConnection;
import model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class LessonDAO {
    private Connection conn = DBConnection.getConnection();
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
    public List<Lession> getListLessonByTutor(Tutor tutor) throws SQLException, ParseException {
        List<Course> courses = getListCourseByTutor(tutor);
        String sql = "SELECT * FROM LESSON WHERE course_id = ? AND status = 'scheduled'";
        List<Lession> lessions = new ArrayList<>();
        conn = DBConnection.getConnection();
        for (Course course : courses) {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, course.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Lession lession = new Lession();
            lession.setCourse_id(rs.getString("course_id"));
            lession.setStudent_id(rs.getString("student_id"));
            lession.setStatus(rs.getString("status"));
            String dateStr = String.valueOf(rs.getDate("time"));
            String timeStr = String.valueOf(rs.getTime("time"));
            String datetimeStr = dateStr + " " + timeStr;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(datetimeStr);
            lession.setTime(date);
            lessions.add(lession);

        }
        }
        return lessions;
    }
    public Lession getLessionById(String courseId,String st, String time) throws SQLException {
        String sql = "SELECT * FROM LESSON WHERE course_id = ? AND student_id = ? AND time = ?";
        conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, courseId);
        ps.setString(2, st);
        ps.setString(3, time);
        ResultSet rs = ps.executeQuery();
        Lession lession = new Lession();
        if (rs.next()) {

            lession.setCourse_id(rs.getString("course_id"));
            lession.setStudent_id(rs.getString("student_id"));
            lession.setStatus(rs.getString("status"));
            String date = rs.getString("time");
            Timestamp timeStamp = Timestamp.valueOf(date);
            lession.setTime(timeStamp);


        }
        return lession;
    }
    public void updateLesson(String idCourse, String idStudent, String timeStr) throws SQLException {
        String sql = "UPDATE LESSON SET status = ?" +
                "WHERE course_id = ? AND student_id = ? AND time = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"absent");
        ps.setString(2, idCourse);
        ps.setString(3, idStudent);
        ps.setString(4, timeStr);
        int rs = ps.executeUpdate();
        System.out.println("UPDATE where course_id = " + idCourse +
                ", student_id = " + idStudent +
                ", time = " + timeStr);
        System.out.println(getLessionById(idCourse,idStudent,timeStr));



    }
    public void updateLessonCompleted(String idCourse, String idStudent, String timeStr) throws SQLException {
        String sql = "UPDATE LESSON SET status = ?" +
                "WHERE course_id = ? AND student_id = ? AND time = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"completed");
        ps.setString(2, idCourse);
        ps.setString(3, idStudent);
        ps.setString(4, timeStr);
        int rs = ps.executeUpdate();
        System.out.println("UPDATE where course_id = " + idCourse +
                ", student_id = " + idStudent +
                ", time = " + timeStr);
        System.out.println(getLessionById(idCourse,idStudent,timeStr));



    }
    public static void main(String[] args) throws SQLException {
        TutorDAO tutorDAO = new TutorDAO();
        Tutor tutor = tutorDAO.getTutorById("tut004");
        LessonDAO lessonDAO = new LessonDAO();
//        List<Course> courses = lessonDAO.getListCourseByTutor(tutor);
//        for (Course course : courses) {
//            System.out.println(course);
//        }
        String idStudent = "st005";
        String idCourse = "course004";
        String timeStr = "2025-06-09 10:00:00";
//        Timestamp time= Timestamp.valueOf(timeStr);


       lessonDAO.updateLesson(idCourse,idStudent,timeStr);
        Lession lession= lessonDAO.getLessionById( idCourse,idStudent, timeStr);
        System.out.println(lession);
        System.out.println("Time from DB: " + lession.getTime());
        //lessonDAO.updateLesson(idStudent, idCourse, time);

//        List<Lession> lessons = lessonDAO.getListLessonByTutor(tutor);
//        for (Lession lesson : lessons) {
//            System.out.println(lesson);
//        }
//        List<String> studentIds = lessonDAO.getListStudentIdByTutor(tutor);
//        for (String studentId : studentIds) {
//            System.out.println(studentId);
//        }

    }
}