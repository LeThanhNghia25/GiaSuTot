package DAO;

import Utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.Date; // CHỈ import java.util.Date
import java.util.*;


public class StudentScheduleDAO {

    public static class ScheduleItem {
        private final String dayOfWeek;
        private final String subjectName;
        private final String tutorName;
        private final java.util.Date time;
        private final String status;

        public ScheduleItem(String dayOfWeek, String subjectName, String tutorName, java.util.Date time, String status) {
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

        public java.util.Date getTime() {
            return time;
        }

        public String getStatus() {
            return status;
        }

        public String getDateString() {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            cal.setTime(time);
            return String.format("%02d/%02d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1);
        }

        public String getHourString() {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            cal.setTime(time);
            return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        }
    }

    public List<ScheduleItem> getWeeklySchedule(String studentId, LocalDate from, LocalDate to) throws SQLException {
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
        WHERE l.student_id = ? AND l.time BETWEEN ? AND ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ps.setTimestamp(2, Timestamp.valueOf(from.atStartOfDay()));
            ps.setTimestamp(3, Timestamp.valueOf(to.atTime(23, 59, 59)));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String subjectName = rs.getString("subject_name");
                    String tutorName = rs.getString("tutor_name");

                    // Lấy Timestamp trực tiếp với múi giờ Asia/Ho_Chi_Minh
                    Timestamp timestamp = rs.getTimestamp("time", Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")));
                    java.util.Date time = timestamp;

                    String status = rs.getString("status");

                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
                    cal.setTime(time);
                    int dayOfWeekInt = cal.get(Calendar.DAY_OF_WEEK);
                    String dayOfWeek = convertDay(dayOfWeekInt);

                    schedule.add(new ScheduleItem(dayOfWeek, subjectName, tutorName, time, status));
                }
            }
        }

        schedule.sort(Comparator.comparingInt(item -> getDayOrder(item.getDayOfWeek())));
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

    private int getDayOrder(String day) {
        return switch (day) {
            case "Thứ 2" -> 1;
            case "Thứ 3" -> 2;
            case "Thứ 4" -> 3;
            case "Thứ 5" -> 4;
            case "Thứ 6" -> 5;
            case "Thứ 7" -> 6;
            case "Chủ nhật" -> 7;
            default -> 8;
        };
    }
}