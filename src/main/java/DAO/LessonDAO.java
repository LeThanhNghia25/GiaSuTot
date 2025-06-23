package DAO;

import Utils.DBConnection;
import model.*;
import java.sql.*;
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
    public Lesson getLessionById(String courseId,String st, String time) throws SQLException {
        String sql = "SELECT * FROM LESSON WHERE course_id = ? AND student_id = ? AND time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, courseId);
            ps.setString(2, st);
            ps.setString(3, time);
            ResultSet rs = ps.executeQuery();
            Lesson lesson = new Lesson();
            if (rs.next()) {

                lesson.setCourse_id(rs.getString("course_id"));
                lesson.setStudent_id(rs.getString("student_id"));
                lesson.setStatus(rs.getString("status"));
                String date = rs.getString("time");
                Timestamp timeStamp = Timestamp.valueOf(date);
                lesson.setTime(timeStamp);
            }
        return lesson;
    }}
    public void updateLesson(String idCourse, String idStudent, String timeStr) throws SQLException {
        String sql = "UPDATE LESSON SET status = ?" +
                "WHERE course_id = ? AND student_id = ? AND time = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
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
    }
    public void updateLessonCompleted(String idCourse, String idStudent, String timeStr) throws SQLException {
        String sql = "UPDATE LESSON SET status = ?" +
                "WHERE course_id = ? AND student_id = ? AND time = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setString(1,"completed");
            ps.setString(2, idCourse);
            ps.setString(3, idStudent);
            ps.setString(4, timeStr);
            int rs = ps.executeUpdate();
            System.out.println("UPDATE where course_id = " + idCourse +
                    ", student_id = " + idStudent +
                    ", time = " + timeStr);
            System.out.println(getLessionById(idCourse,idStudent,timeStr));



    }}
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



    public List<Lesson> getListLessonByTutor(Tutor tutor) throws SQLException {
        List<Course> courses = getListCourseByTutor(tutor);
        if (courses.isEmpty()) return new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM LESSON WHERE course_id IN (");
        for (int i = 0; i < courses.size(); i++) {
            sql.append("?");
            if (i < courses.size() - 1) sql.append(",");
        }
        sql.append(") AND status = 'scheduled'");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < courses.size(); i++) {
                ps.setString(i + 1, courses.get(i).getId());
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Lesson> lessons = new ArrayList<>();
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setCourse_id(rs.getString("course_id"));
                    lesson.setStudent_id(rs.getString("student_id"));
                    lesson.setStatus(rs.getString("status"));
                    String date = rs.getString("time");
                    Timestamp timeStamp = Timestamp.valueOf(date);
                    lesson.setTime(timeStamp);
                    lessons.add(lesson);

                }
                return lessons;
            }
        }

    }}

