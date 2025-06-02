package model;

public class Subject {
    private String id_sub;            // id_sub
    private String name;
    private String level;
    private String describe_sb;   // describeSb
    private double fee;
    private String status_sub;        // statusSub (active/inactive)

    public Subject() {}

    public Subject(String id_sub, String name, String level, String describe_sb, double fee, String status_sub) {
        this.id_sub = id_sub;
        this.name = name;
        this.level = level;
        this.describe_sb = describe_sb;
        this.fee = fee;
        this.status_sub =status_sub;
    }

    // Getter & Setter cho id
    public String getId() {
        return id_sub;
    }

    public void setId(String id) {
        this.id_sub =id_sub;
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
        return describe_sb;
    }

    public void setDescription(String description) {
        this.describe_sb = describe_sb;
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
        return status_sub;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id_sub='" + id_sub + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", description='" + describe_sb + '\'' +
                ", fee=" + fee +
                ", status='" + status_sub + '\'' +
                '}';
    }

    public void setStatus(String status) {
        this.status_sub = status_sub;
    }
}
