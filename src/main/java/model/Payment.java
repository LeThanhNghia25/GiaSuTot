package model;

import java.util.Date;

public class Payment {
    private String id;
    private String courseId;
    private String tutorId;
    private String studentId;
    private double amount;
    private Date paymentDate;
    private String fileName; // Thêm trường fileName
    private String filePath; // Thêm trường filePath

    public Payment() {
        // Constructor mặc định
    }

    public Payment(String id, String courseId, String tutorId, String studentId, double amount, Date paymentDate) {
        this.id = id;
        this.courseId = courseId;
        this.tutorId = tutorId;
        this.studentId = studentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", tutorId='" + tutorId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}