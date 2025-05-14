package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/giasutot"; // Thay thế bằng URL cơ sở dữ liệu của bạn
    private static final String USER = "root"; // Tên người dùng MySQL của bạn
    private static final String PASSWORD = "021204"; // Mật khẩu người dùng MySQL của bạn

    // Kết nối cơ sở dữ liệu
    public static Connection getConnection() throws SQLException {
        try {
            // Tải driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tạo kết nối
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // In ra thông báo khi kết nối thành công
            System.out.println("Connection to database " + URL + " successful!");

            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

}
