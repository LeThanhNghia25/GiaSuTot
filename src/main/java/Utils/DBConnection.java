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
                    "jdbc:mysql://localhost:3306/giasutot?useSSL=false&serverTimezone=UTC",
                    "root",
                    ""
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

