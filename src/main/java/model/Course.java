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
    private String studentId;
    private String studentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int studentCount; // Số học viên từng học

    public Course() {}

    public Course(String id, String subjectId, String tutorId, LocalDateTime time) {
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

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", tutorId='" + tutorId + '\'' +
                ", time=" + time +
                ", subject=" + (subject != null ? subject.getName() : "null") +
                ", tutor=" + (tutor != null ? tutor.getName() : "null") +
                ", studentCount=" + studentCount +
                '}';
    }
}