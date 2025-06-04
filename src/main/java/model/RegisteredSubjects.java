package model;

import java.util.Date;

public class RegisteredSubjects {
    String course_id;
    String student_id;
    Date register_date;
    int number_of_lessons;
    String status;
    public RegisteredSubjects() { }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumber_of_lessons() {
        return number_of_lessons;
    }

    public void setNumber_of_lessons(int number_of_lessons) {
        this.number_of_lessons = number_of_lessons;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "RegisteredSubjects{" +
                "course_id='" + course_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", register_date=" + register_date +
                ", number_of_lessons=" + number_of_lessons +
                ", status='" + status + '\'' +
                '}';
    }
}
