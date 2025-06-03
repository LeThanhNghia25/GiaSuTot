package model;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private String courseId;
    private String tutorId;
    private String studentId; // Thêm studentId
    private double amount;
    private LocalDateTime paymentDate;
    private String status;

    public Payment(String id, String courseId, String tutorId, String studentId, double amount, LocalDateTime paymentDate, String status) {
        this.id = id;
        this.courseId = courseId;
        this.tutorId = tutorId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}