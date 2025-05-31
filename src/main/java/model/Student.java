package model;

import java.time.LocalDate;

public class Student {
    private String id; // Đổi từ id_st thành id
    private String name;
    private LocalDate birth;
    private String description; // Đổi từ describe thành description
    private String accountId; // Đổi từ id_acc thành accountId

    public Student() {}

    public Student(String id, String name, LocalDate birth, String description, String accountId) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.description = description;
        this.accountId = accountId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getDescription() { // Đổi từ getDescribe thành getDescription
        return description;
    }

    public void setDescription(String description) { // Đổi từ setDescribe thành setDescription
        this.description = description;
    }

    public String getAccountId() { // Đổi từ getAccountId thành getAccountId (đã đúng, chỉ cần đồng bộ tên biến)
        return accountId;
    }

    public void setAccountId(String accountId) { // Đổi từ setAccountId thành setAccountId (đã đúng, chỉ cần đồng bộ tên biến)
        this.accountId = accountId;
    }
}