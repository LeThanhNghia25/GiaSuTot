package model;

import java.sql.Date;

public class Tutor {
    private String id;
    private String name;
    private String email;
    private Date birth;
    private String phone;
    private String address;
    private String specialization;
    private String describe_tutor;
    private int evaluate;
    private String account_id;

    // Constructor
    public Tutor() {}

    public Tutor(String id, String name, String email, Date birth, String phone, String address, String specialization, String describe_tutor, int evaluate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.specialization = specialization;
        this.describe_tutor = describe_tutor;
        this.evaluate = evaluate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDescribeTutor() {
        return describe_tutor;
    }

    public void setDescribeTutor(String describe_tutor) {
        this.describe_tutor = describe_tutor;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public String getAccountId() {
        return account_id;
    }

    public void setAccountId(String account_id) {
        this.account_id = account_id;
    }
}