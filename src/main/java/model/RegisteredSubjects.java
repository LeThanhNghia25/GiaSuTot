package model;

import java.time.LocalDate;

public class RegisteredSubjects {
    private String course_id;
    private String student_id;
    private LocalDate registration_date;
    private int number_of_lessons;
    private String status;
    private Student student;

    public RegisteredSubjects() {}

    public RegisteredSubjects(String course_id, String student_id, LocalDate registration_date, int number_of_lessons, String status) {
        this.course_id = course_id;
        this.student_id = student_id;
        this.registration_date = registration_date;
        this.number_of_lessons = number_of_lessons;
        this.status = status;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }

    public int getNumber_of_lessons() {
        return number_of_lessons;
    }

    public void setNumber_of_lessons(int number_of_lessons) {
        this.number_of_lessons = number_of_lessons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "RegisteredSubjects{" +
                "course_id='" + course_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", registration_date=" + registration_date +
                ", number_of_lessons=" + number_of_lessons +
                ", status='" + status + '\'' +
                ", student=" + (student != null ? student.getName() : "null") +
                '}';
    }
}