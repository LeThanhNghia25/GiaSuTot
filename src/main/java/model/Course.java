package model;

import DAO.TutorDAO;

import java.util.Date;

public class Course {
    private String id;
    private String subjectId; // Đổi từ id_subject thành subjectId
    private String tutorId; // Đổi từ id_tutor thành tutorId
    private Date time; // Đổi từ dateTime thành time

    public Course() {}

    public Course(String id, String subjectId, String tutorId, Date time) {
        this.id = id;
        this.subjectId = subjectId;
        this.tutorId = tutorId;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() { // Đổi từ getId_subject thành getSubjectId
        return subjectId;
    }

    public void setSubjectId(String subjectId) { // Đổi từ setId_subject thành setSubjectId
        this.subjectId = subjectId;
    }

    public String getTutorId() { // Đổi từ getId_tutor thành getTutorId
        return tutorId;
    }

    public void setTutorId(String tutorId) { // Đổi từ setId_tutor thành setTutorId
        this.tutorId = tutorId;
    }

    public Date getTime() { // Đổi từ getDateTime thành getTime
        return time;
    }

    public void setTime(Date time) { // Đổi từ setDateTime thành setTime
        this.time = time;
    }

    public Tutor getTutor() {
        TutorDAO dao = new TutorDAO();
        return dao.getTutorById(this.tutorId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", tutorId='" + tutorId + '\'' +
                ", time=" + time +
                '}';
    }

    public static void main(String[] args) {
        // Sửa lại cách tạo Date (năm - 1900, tháng từ 0-11)
        Course course = new Course("course001", "sub001", "tut001", new Date(2025 - 1900, 5 - 1, 12, 8, 20, 0));
        System.out.println(course);
    }
}