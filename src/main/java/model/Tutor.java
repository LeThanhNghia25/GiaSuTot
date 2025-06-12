package model;

import java.time.LocalDate;

public class Tutor {
    private String id;
    private String name;
    private String email;
    private LocalDate birth;
    private String phone;
    private String address;
    private String specialization;
    private String description;
    private long idCardNumber;
    private long bankAccountNumber;
    private String bankName;
    private Account account;
    private int evaluate;

    public Tutor() {}

    public Tutor(String id, String name, String email, LocalDate birth, String phone, String address,
                 String specialization, String description, long idCardNumber, long bankAccountNumber,
                 String bankName, Account account, int evaluate) {
        this.id = id;
        this.name = name != null ? name : "";
        this.email = email != null ? email : "";
        this.birth = birth;
        this.phone = phone != null ? phone : "";
        this.address = address != null ? address : "";
        this.specialization = specialization != null ? specialization : "";
        this.description = description != null ? description : "";
        this.idCardNumber = idCardNumber;
        this.bankAccountNumber = bankAccountNumber;
        this.bankName = bankName != null ? bankName : "";
        this.account = account != null ? account : new Account();
        this.evaluate = evaluate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(long idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public long getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", specialization='" + specialization + '\'' +
                ", description='" + description + '\'' +
                ", idCardNumber=" + idCardNumber +
                ", bankAccountNumber=" + bankAccountNumber +
                ", bankName='" + bankName + '\'' +
                ", account=" + (account != null ? account.getId() : "null") +
                ", evaluate=" + evaluate +
                '}';
    }
}