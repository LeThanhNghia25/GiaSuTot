package model;

public class Account {
    private String id; // Đổi từ id_acc thành id
    private String email;
    private String password;
    private int role;
    private String status; // Đổi từ status_acc thành status

    public Account() {}

    public Account(String id, String email, String password, int role, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // Getter & Setter cho id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter & Setter cho email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter & Setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter & Setter cho role
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // Getter & Setter cho status
    public String getStatus() { // Đổi từ getStatusAcc thành getStatus
        return status;
    }

    public void setStatus(String status) { // Đổi từ setStatusAcc thành setStatus
        this.status = status;
    }
}