package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Bật/tắt log debug
    private static final boolean DEBUG = false;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            if (DEBUG) System.out.println("Loading MySQL driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/giasutot?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
            String user = "root";
            String password = "";

            if (DEBUG) System.out.println("Connecting to DB: " + url);
            conn = DriverManager.getConnection(url, user, password);

            if (DEBUG) System.out.println("Database connected successfully");

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Connection Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }

        return conn;
    }
}
