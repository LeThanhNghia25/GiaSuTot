package model;

public class Account {
    private String id_acc;
    private String email;
    private String password;
    private int role;
    private String status_acc;

    public Account() {}

    public Account(String id_acc, String email, String password, int role, String status_acc) {
        this.id_acc = id_acc;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status_acc = status_acc;
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
    public String getStatusAcc() {
        return status_acc;
    }

    public void setStatusAcc(String status_acc) {
        this.status_acc = status_acc;
    }
}