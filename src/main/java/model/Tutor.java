package model;

import java.time.LocalDate;

public class Tutor extends Account {
    private String id;
    private String name;
    private String email;
    private LocalDate birth; // Thay Date thành LocalDate
    private String phone;
    private String address;
    private String specialization;
    private String description;
    private long idCardNumber; // Thay int thành long
    private long bankAccountNumber; // Thay int thành long
    private String bankName;
    private String accountId;
    private int evaluate;

    public Tutor() {}

    public Tutor(String id, String name, String email, LocalDate birth, String phone, String address,
                 String specialization, String description, long idCardNumber, long bankAccountNumber,
                 String bankName, String accountId, int evaluate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.specialization = specialization;
        this.description = description;
        this.idCardNumber = idCardNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName;
        this.accountId = accountId;
        this.evaluate = evaluate;
    }

    // Getter & Setter cho id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter & Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter & Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter & Setter cho birth
    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    // Getter & Setter cho phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter & Setter cho address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter & Setter cho specialization
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    // Getter & Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter & Setter cho idCardNumber
    public long getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(long idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    // Getter & Setter cho bankAccountNumber
    public long getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    // Getter & Setter cho bankName
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    // Getter & Setter cho accountId
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    // Getter & Setter cho evaluate
    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }
}