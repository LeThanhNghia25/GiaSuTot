package model;

public class Subject {
    private String id;
    private String name;
    private String level;
    private String description;
    private double fee;
    private String status;

    public Subject() {}

    public Subject(String id, String name, String level, String description, double fee, String status) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.description = description;
        this.fee = fee;
        this.status = status;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", description='" + description + '\'' +
                ", fee=" + fee +
                ", status='" + status + '\'' +
                '}';
    }
}