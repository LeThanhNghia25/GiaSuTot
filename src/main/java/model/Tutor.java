package model;

import java.util.Date;

public class Tutor {
    private String id_tutor;
    private String name;
    private String email;
    private Date birth;
    private String phone;
    private String address;
    private String specialization;
    private String describeTutor;
    private int evaluate;

    // Constructors
    public Tutor() {}
    public Tutor( String id_tutor, String name, String email, Date birth, String phone, String address, String specialization, String describeTutor, int evaluate) {
        this.id_tutor = id_tutor;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.specialization = specialization;
        this.describeTutor = describeTutor;
        this.evaluate = evaluate;
    }

    // Getters and Setters
    public String getId() {
        return id_tutor;
    }

    public void setId(String id_tutor) {
        this.id_tutor= id_tutor;
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

    public Date getBirth() {return birth;}

    public void setBirth(Date birth) {this.birth = birth;}

    public void setEmail(String email) {
        this.email = email;
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
        return describeTutor;
    }

    public void setDescribeTutor(String describeTutor) {
        this.describeTutor = describeTutor;
    }

    public int getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }



}
