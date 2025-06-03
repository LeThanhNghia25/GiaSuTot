package model;

import java.sql.Date;
import java.time.LocalDate;

public class Student extends Account{
    private String id; // Đổi từ id_st thành id
    private String name;
    private LocalDate birth;
    private String description; // Đổi từ describe thành description
    private Account account; // Thay vì String accountId

    public Student() {}

    public Student(String id, String name, LocalDate birth, String description,  Account account) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.description = description;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}