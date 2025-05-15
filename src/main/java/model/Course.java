package model;

import Controller.TutorController;
import DAO.TutorDAO;

import java.util.Date;

public class Course {
    private String id;
    private String id_subject;
    private String id_tutor;
    private Date dateTime;
  public Course(String id, String id_subject, String id_tutor, Date dateTime) {
      this.id = id;
      this.id_subject = id_subject;
      this.id_tutor = id_tutor;
      this.dateTime = dateTime;
  }
  public Course() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_subject() {
        return id_subject;
    }

    public void setId_subject(String id_subject) {
        this.id_subject = id_subject;
    }

    public String getId_tutor() {
        return id_tutor;
    }

    public void setId_tutor(String id_tutor) {
        this.id_tutor = id_tutor;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public Tutor getTutor() {
        TutorDAO dao = new TutorDAO();
        return dao.getTutorById(this.id_tutor);
    }
    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", id_subject='" + id_subject + '\'' +
                ", id_tutor='" + id_tutor + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public static void main(String[] args) {
        Course course = new Course("1", "<UNK>", "<UNK>", new Date(2025,5,12,8,20,00));
        System.out.println(course);
    }
}


