package model;

import java.sql.Date;
import java.time.LocalDate;

public class Student {
    private String id;            // id_st
    private String name;          // name
    private LocalDate birth;      // birth
    private String describe;      // describeSt
    private String account_id;     // id_acc (liên kết với account)

    public Student() {}

    public Student(String id, String name, LocalDate birth, String describe, String account_id) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.describe = describe;
        this.account_id = account_id;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getaccount_id() {
        return account_id;
    }

    public void setaccount_id(String account_id) {
        this.account_id = account_id;
    }
}
