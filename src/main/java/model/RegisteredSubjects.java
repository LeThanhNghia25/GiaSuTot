package model;

import java.util.Date;

public class RegisteredSubjects {
    private String course_id;
    private String student_id;
    private Date registration_date;
    private int number_of_lessons;
    private String status;

    // Thêm đối tượng student
    private Student student;

    public RegisteredSubjects() {}

    public RegisteredSubjects(String course_id, String student_id, Date registration_date, int number_of_lessons, String status) {
        this.course_id = course_id;
        this.student_id = student_id;
        this.registration_date = registration_date;
        this.number_of_lessons = number_of_lessons;
        this.status = status;
    }

    // Getters và Setters
    public String getCourseId() {
        return course_id;
    }

    public void setCourseId(String course_id) {
        this.course_id = course_id;
    }

    public String getStudentId() {
        return student_id;
    }

    public void setStudentId(String student_id) {
        this.student_id = student_id;
    }

    public Date getRegistrationDate() {
        return registration_date;
    }

    public void setRegistrationDate(Date registration_date) {
        this.registration_date = registration_date;
    }

    public int getNumberOfLessons() {
        return number_of_lessons;
    }

    public void setNumberOfLessons(int number_of_lessons) {
        this.number_of_lessons = number_of_lessons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter và Setter cho student
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
