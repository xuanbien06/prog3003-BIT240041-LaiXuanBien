package com.cuahang.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Thông tin cấu hình database của bạn (sửa lại theo máy của bạn nếu cần)
    private static final String URL = "jdbc:mysql://localhost:3306/QuanLyCuaHangTienLoi?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";       // Tên đăng nhập MySQL mặc định
    private static final String PASSWORD = "12345";       // Mật khẩu MySQL (XAMPP mặc định là rỗng, MySQL Workbench thường là 123456 hoặc root)

    /**
     * Hàm lấy kết nối đến cơ sở dữ liệu
     * @return Connection object nếu thành công, null nếu thất bại
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Đăng ký Driver với DriverManager
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Thiết lập kết nối
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối cơ sở dữ liệu thành công!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy Driver MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Kết nối Database thất bại. Vui lòng kiểm tra lại URL, USER hoặc PASSWORD: " + e.getMessage());
        }
        return conn;
    }

    // Hàm test thử kết nối chạy độc lập
    public static void main(String[] args) {
        Connection testConn = DBConnection.getConnection();
        if (testConn != null) {
            try {
                testConn.close(); // Test xong thì đóng lại cho an toàn
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}