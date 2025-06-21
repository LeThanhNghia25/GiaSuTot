package model;

import java.util.Date;

public class Lesson {
    String course_id;
    String student_id;
    String status;
    Date time;
    public Lesson(){}

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Lession{" +
                "course_id='" + course_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
