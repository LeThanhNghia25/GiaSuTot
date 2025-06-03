package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Course {
    private String id;
    private String subjectId;
    private String tutorId;
    private LocalDateTime time;
    private Subject subject;
    private Tutor tutor;
    private int total_lesson;
    private String studentId; // ID học viên
    private String studentName; // Tên học viên
    private LocalDate startDate; // Ngày bắt đầu học
    private LocalDate endDate; // Ngày hoàn thành khóa học

    public Course() {}

    public Course(String id, String subjectId, String tutorId, LocalDateTime time) {
        this.id = id;
        this.subjectId = subjectId;
        this.tutorId = tutorId;
        this.time = time;
    }

    public Course(String id, String subjectId, String tutorId, int total_lesson, LocalDateTime time) {
        this.id = id;
        this.subjectId = subjectId;
        this.tutorId = tutorId;
        this.total_lesson = total_lesson;
        this.time = time;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    public int getTotal_lesson() {
        return total_lesson;
    }

    public void setTotal_lesson(int total_lesson) {
        this.total_lesson = total_lesson;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
}