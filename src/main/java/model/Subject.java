package model;

public class Subject {
    private String id;            // id_sub
    private String name;
    private String level;
    private String description;   // describeSb
    private double fee;
    private String status;        // statusSub (active/inactive)

    public Subject() {}

    public Subject(String id, String name, String level, String description, double fee, String status) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.description = description;
        this.fee = fee;
        this.status = status;
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

    // Getter & Setter cho level
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Getter & Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter & Setter cho fee
    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    // Getter & Setter cho status
    public String getStatus() {
        return status;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
