package model;

import java.time.LocalDateTime;

public class Course {
    private String id;
    private String subjectId;
    private String tutorId;
    private LocalDateTime time;
    private Subject subject;
    private Tutor tutor;

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

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", tutorId='" + tutorId + '\'' +
                ", time=" + time +
                ", subject=" + (subject != null ? subject.getName() : "null") +
                ", tutor=" + (tutor != null ? tutor.getName() : "null") +
                '}';
    }

    public static void main(String[] args) {
        Course course = new Course("course001", "sub001", "tut001", LocalDateTime.of(2025, 5, 12, 8, 20));
        System.out.println(course);
    }
}