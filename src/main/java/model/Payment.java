package model;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private String courseId;
    private String tutorId;
    private double amount;
    private LocalDateTime paymentDate;
    private String status;

    public Payment(String id, String courseId, String tutorId, double amount, LocalDateTime paymentDate, String status) {
        this.id = id;
        this.courseId = courseId;
        this.tutorId = tutorId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters và Setters
    public String getId() { return id; }
    public String getCourseId() { return courseId; }
    public String getTutorId() { return tutorId; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getStatus() { return status; }
}