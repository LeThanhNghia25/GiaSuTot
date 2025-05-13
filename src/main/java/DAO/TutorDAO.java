package DAO;

import model.Tutor;
import Utils.DBConnection;

import java.sql.*;

public class TutorDAO {

    public Tutor getTutorById(int tutorId) {
        Tutor tutor = null;
        String sql = "SELECT * FROM tutors WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tutorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tutor = new Tutor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("specialization"),
                        rs.getString("description")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tutor;
    }
}
