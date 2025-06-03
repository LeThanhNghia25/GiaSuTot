package model;

import DAO.TutorDAO;
import java.util.Date;

public class Course {
    private String id;
    private String subjectId; // Đổi từ id_subject thành subjectId
    private String tutorId;   // Đổi từ id_tutor thành tutorId
    private int total_lesson; // Tổng số buổi học
    private Date time;        // Thời gian bắt đầu

    public Course() {}

    public Course(String id, String subjectId, String tutorId, int total_lesson, Date time) {
        this.id = id;
        this.subjectId = subjectId;
        this.tutorId = tutorId;
        this.total_lesson = total_lesson;
        this.time = time;
    }

    // Getter & Setter cho id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter & Setter cho subjectId
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    // Getter & Setter cho tutorId
    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    // Getter & Setter cho total_lesson
    public int getTotal_lesson() {
        return total_lesson;
    }

    public void setTotal_lesson(int total_lesson) {
        this.total_lesson = total_lesson;
    }

    // Getter & Setter cho time
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    // Trả về đối tượng Tutor từ tutorId
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
                ", total_lesson=" + total_lesson +
                ", time=" + time +
                '}';
    }

    // Hàm main test
    public static void main(String[] args) {
        // Lưu ý: Date(int year, int month, int day, ...) deprecated
        // Nên chuyển sang sử dụng Calendar hoặc java.time nếu được
        Course course = new Course("course001", "sub001", "tut001", 10,
                new Date(2025 - 1900, 5 - 1, 12, 8, 20, 0));
        System.out.println(course);
    }
}
