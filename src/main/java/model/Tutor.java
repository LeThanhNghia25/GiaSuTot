package model;

import java.util.Date;

public class Tutor extends Account{
    private String id; // Đổi từ id_tutor thành id
    private String name;
    private String email;
    private Date birth; // DATE trong SQL ánh xạ thành java.util.Date
    private String phone;
    private String address;
    private String specialization;
    private String description; // Đổi từ describe_tutor thành description
    private int idCardNumber; // Đổi từ cccd thành idCardNumber
    private int bankAccountNumber; // Đổi từ bank_code thành bankAccountNumber
    private String bankName;
    private String accountId; // Đổi từ id_acc thành accountId
    private int evaluate;

    public Tutor() {}

    public Tutor(String id, String name, String email, Date birth, String phone, String address,
                 String specialization, String description, int idCardNumber, int bankAccountNumber,
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
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
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
    public int getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(int idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    // Getter & Setter cho bankAccountNumber
    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
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