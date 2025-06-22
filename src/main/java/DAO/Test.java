package DAO;

import model.Lesson;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        LessonDAO dao = new LessonDAO();

        String courseId="course010";
        String StudentId="st005";
        String time="2025-06-01 10:00:00";
        Lesson lesson = dao.getLessionById(courseId,StudentId,time);
        System.out.println( "1  "+lesson);
        dao.updateLesson(courseId,StudentId,time);

        Lesson lesson2 = dao.getLessionById(courseId,StudentId,time);
        System.out.println("2  "+ lesson2);


    }
}
