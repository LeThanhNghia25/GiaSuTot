package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final boolean DEBUG = true;
    private static final String URL = "jdbc:mysql://localhost:3306/giasutot?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            if (DEBUG) System.out.println("Loading MySQL driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (DEBUG) System.out.println("Connecting to DB: " + URL);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (DEBUG) System.out.println("Database connected successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
            throw new SQLException("MySQL Driver not found", e);
        } catch (SQLException e) {
            System.err.println("SQL Connection Error: " + e.getMessage());
            throw e;
        }
        return conn;
    }
}