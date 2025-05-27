package model;

import java.sql.Date;
import java.time.LocalDate;

public class Student {
    private String id_st;            // id_st
    private String name;          // name
    private LocalDate birth;      // birth
    private String describe;      // describeSt
    private String id_acc;     // id_acc (liên kết với account)

    public Student() {}

    public Student(String id_st, String name, LocalDate birth, String describe, String id_acc) {
        this.id_st = id_st;
        this.name = name;
        this.birth = birth;
        this.describe = describe;
        this.id_acc = id_acc;
    }

    // Getters and Setters
    public String getId() {
        return id_st;
    }

    public void setId(String id_st) {
        this.id_st = id_st;
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

    public String getAccountId() {
        return id_acc;
    }

    public void setAccountId(String id_acc) {
        this.id_acc = id_acc;
    }
}
