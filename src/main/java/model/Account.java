package model;

public class Account {
    private String id_acc;
    private String email;
    private String password;
    private int role;
    private String status;

    public Account() {}

    public Account(String id, String email, String password, int role, String status) {
        this.id_acc = id_acc;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // Getter & Setter cho id
    public String getId() {
        return id_acc;
    }

    public void setId(String id_acc) {
        this.id_acc =id_acc;
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}