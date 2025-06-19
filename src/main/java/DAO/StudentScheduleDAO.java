package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class StudentScheduleDAO {

    public static class ScheduleItem {
        private String dayOfWeek;
        private String subjectName;
        private String tutorName;
        private Date time; // java.util.Date
        private String status;

        public ScheduleItem(String dayOfWeek, String subjectName, String tutorName, Date time, String status) {
            this.dayOfWeek = dayOfWeek;
            this.subjectName = subjectName;
            this.tutorName = tutorName;
            this.time = time;
            this.status = status;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public String getTutorName() {
            return tutorName;
        }

        public Date getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }
    }

    public List<ScheduleItem> getWeeklySchedule(String studentId) throws SQLException {
        List<ScheduleItem> schedule = new ArrayList<>();

        String sql = """
            SELECT 
                s.name AS subject_name,
                t.name AS tutor_name,
                l.time,
                l.status
            FROM lesson l
            JOIN course c ON l.course_id = c.id
            JOIN subject s ON c.subject_id = s.id
            JOIN tutor t ON c.tutor_id = t.id
            WHERE l.student_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String subjectName = rs.getString("subject_name");
                    String tutorName = rs.getString("tutor_name");
                    Timestamp timestamp = rs.getTimestamp("time");
                    java.util.Date time = new java.util.Date(timestamp.getTime());
                    String status = rs.getString("status");

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(time);
                    int dayOfWeekInt = cal.get(Calendar.DAY_OF_WEEK);
                    String dayOfWeek = convertDay(dayOfWeekInt);

                    schedule.add(new ScheduleItem(dayOfWeek, subjectName, tutorName, time, status));
                }
            }
        }

        // Sắp xếp theo thời gian thực tế
        schedule.sort(Comparator.comparing(ScheduleItem::getTime));
        return schedule;
    }

    private String convertDay(int day) {
        return switch (day) {
            case Calendar.MONDAY -> "Thứ 2";
            case Calendar.TUESDAY -> "Thứ 3";
            case Calendar.WEDNESDAY -> "Thứ 4";
            case Calendar.THURSDAY -> "Thứ 5";
            case Calendar.FRIDAY -> "Thứ 6";
            case Calendar.SATURDAY -> "Thứ 7";
            case Calendar.SUNDAY -> "Chủ nhật";
            default -> "Không xác định";
        };
    }
}
