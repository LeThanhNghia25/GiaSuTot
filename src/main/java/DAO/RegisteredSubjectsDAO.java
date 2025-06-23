package DAO;

import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisteredSubjectsDAO {
    public List<PendingRequest> getPendingRequests(String tutorId) throws SQLException {
        String sql = "SELECT rs.course_id, rs.student_id " +
                "FROM registered_subjects rs " +
                "JOIN course c ON rs.course_id = c.id " +
                "WHERE rs.status = 'pending_approval' AND c.tutor_id = ?";
        List<PendingRequest> requests = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                requests.add(new PendingRequest(rs.getString("course_id"), rs.getString("student_id")));
            }
        }
        return requests;
    }

    public void updateRegistrationStatus(String courseId, String studentId, String status) throws SQLException {
        String sql = "UPDATE registered_subjects SET status = ? WHERE course_id = ? AND student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, courseId);
            stmt.setString(3, studentId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated status to " + status + " for course_id=" + courseId + ", student_id=" + studentId);
            } else {
                throw new SQLException("No record found to update");
            }
        }
    }

    // Class nội bộ để đại diện cho yêu cầu
    public static class PendingRequest {
        private String courseId;
        private String studentId;

        public PendingRequest(String courseId, String studentId) {
            this.courseId = courseId;
            this.studentId = studentId;
        }

        public String getCourseId() { return courseId; }
        public String getStudentId() { return studentId; }
    }
}