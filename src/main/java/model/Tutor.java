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
    private String describe_tutor;
    private int cccd;
    private int bank_code;
    private String bank_name;
    private int evaluate;
    private String id_acc;


    // Constructors
    public Tutor() {}
    public Tutor( String id_tutor, String name, String email, Date birth, String phone, String address, String specialization, String describe_tutor, int cccd ,int bank_code, String bank_name, int evaluate) {
        this.id_tutor = id_tutor;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.specialization = specialization;
        this.describe_tutor = describe_tutor;
        this.cccd = cccd;
        this.bank_code = bank_code;
        this.bank_name = bank_name;
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
        return describe_tutor;
    }

    public void setDescribeTutor(String describe_tutor) {
        this.describe_tutor = describe_tutor;
    }

    public int getCccd() { return cccd;}
    public void setCccd(int cccd) {
        this.cccd = cccd;
    }

    public int getBankCode() { return bank_code;}
    public void setBankCode(int bank_code) {
        this.bank_code = bank_code;
    }


    public String getBankName() { return bank_name;}
    public void setBankName(String bank_name) {
        this.bank_name = bank_name;
    }


    public int getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    public String getAccountId() {
        return id_acc;
    }

    public void setAccountId(String id_acc) {
        this.id_acc = id_acc;
    }

}
