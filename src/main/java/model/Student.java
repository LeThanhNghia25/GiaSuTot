package model;

import java.time.LocalDate;

public class Student extends Account {
    private String id;
    private String name;
    private LocalDate birth;
    private String description;
    private Account account;

    public Student() {}

    public Student(String id, String name, LocalDate birth, String description, Account account) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.description = description;
        this.account = account;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", description='" + description + '\'' +
                ", account=" + (account != null ? account.getId() + " (" + account.getEmail() + ")" : "null") +
                '}';
    }
}