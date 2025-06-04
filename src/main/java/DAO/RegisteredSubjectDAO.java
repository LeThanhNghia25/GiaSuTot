package DAO;

import Utils.DBConnection;
import model.Account;
import model.Course;
import model.RegisteredSubjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisteredSubjectDAO {
    private Connection conn = DBConnection.getConnection();
    public RegisteredSubjectDAO() {}
    public List<RegisteredSubjects> getAllReSubject() throws SQLException {
        List<RegisteredSubjects> registeredSubjects = new ArrayList<>();
        String sql = "SELECT * FROM registered_subjects";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        System.out.println("Fetching subjects...");

        while (resultSet.next()) {
            RegisteredSubjects registeredSubject= new RegisteredSubjects();
            registeredSubject.setCourse_id(resultSet.getString("course_id"));
            registeredSubject.setStudent_id(resultSet.getString("student_id"));
            registeredSubject.setNumber_of_lessons(resultSet.getInt("number_of_lessons"));
            registeredSubject.setRegister_date(resultSet.getTimestamp("registration_date")); // Lấy timestamp và chuyển sang Date
            registeredSubject.setStatus(resultSet.getString("status"));
            registeredSubjects.add(registeredSubject);
        }

        return registeredSubjects;
    }

    public static void main(String[] args) throws SQLException {
        RegisteredSubjectDAO registeredSubjectDAO = new RegisteredSubjectDAO();
        List<RegisteredSubjects> registeredSubjects = registeredSubjectDAO.getAllReSubject();
        for (RegisteredSubjects registeredSubject : registeredSubjects) {
            System.out.println(registeredSubject);
        }
    }
}
