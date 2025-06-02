package model;

import java.sql.Date;
import java.time.LocalDate;

public class Student {
    private String id_st;            // id_st
    private String name;          // name
    private LocalDate birth;      // birth
    private String describe_st;      // describeSt
    private String id_acc;     // id_acc (liên kết với account)
// id_st, name, birth, describe_st, id_acc
    public Student() {}

    public Student(String id_st, String name, LocalDate birth, String describe_st, String id_acc) {
        this.id_st = id_st;
        this.name = name;
        this.birth = birth;
        this.describe_st = describe_st;
        this.id_acc = id_acc;
    }

    // Getters and Setters
    public String getId() {
        return id_st;
    }

    public void setId(String id) {
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
        return describe_st;
    }

    public void setDescribe(String describe) {
        this.describe_st = describe;
    }

    public String getaccount_id() {
        return id_acc;
    }

    public void setaccount_id(String account_id) {
        this.id_acc = account_id;
    }
}
