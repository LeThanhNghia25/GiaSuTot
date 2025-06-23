package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Notification {
    private String id;
    private String accountId;
    private String message;
    private LocalDateTime createdAt;
    private String status;

    public Notification() {}

    public Notification(String id, String accountId, String message, LocalDateTime createdAt, String status) {
        this.id = id;
        this.accountId = accountId;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Notification(String string, String id, String message, Date date, String pending) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}