package model;

public class Account {
    private String id_acc;
    private String email;
    private String pass;
    private int role;
    private String status_acc; // id_acc, email, pass, role, status_acc
    private String google_id;
    public Account() {}

    public Account(String id_acc, String email, String pass, int role, String status_acc, String google_id) {
        this.id_acc = id_acc;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.status_acc = status_acc;
        this.google_id = google_id;
    }
    public Account(String id_acc, String email, String pass, int role, String status_acc) {
        this.id_acc = id_acc;
        this.email = email;
        this.pass = pass;
        this.role = role;
        this.status_acc = status_acc;
    }

    // Getter & Setter cho id
    public String getId() {
        return id_acc;
    }

    public void setId(String id) {
        this.id_acc = id;
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
        return this.pass;
    }

    public void setPassword(String password) {
        this.pass = password;
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
        return status_acc;
    }

    public void setStatus(String status) {
        this.status_acc = status_acc;
    }
    public String getGoogle_id() {
        return status_acc;
    }

    public void setGoogle_id(String status) {
        this.status_acc = status_acc;
    }
}