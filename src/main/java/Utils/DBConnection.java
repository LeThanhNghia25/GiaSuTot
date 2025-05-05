package Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Thay user/pass nếu bạn có cài khác
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/giasutot?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8",
                    "root",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Kết nối đến database thành công!");
            try {
                conn.close(); // Đóng kết nối sau khi kiểm tra
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Kết nối đến database thất bại!");
        }
    }
}

