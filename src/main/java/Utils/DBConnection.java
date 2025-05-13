package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            System.out.println("Loading MySQL driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL driver loaded successfully");
            String url = "jdbc:mysql://localhost:3306/giasutot?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
            String user = "root";
            String password = "";
            System.out.println("Attempting to connect to database: " + url);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection successful");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Connection Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        if (conn == null) {
            System.err.println("Failed to establish database connection");
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Kết nối đến database thành công!");
            try {
                conn.close();
                System.out.println("Kết nối đã được đóng");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Kết nối đến database thất bại!");
        }
    }
}