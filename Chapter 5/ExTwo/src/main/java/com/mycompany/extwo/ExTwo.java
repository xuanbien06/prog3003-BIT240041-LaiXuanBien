package com.mycompany.extwo;

import java.sql.*;

public class ExTwo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java_exercises";
        String user = "root"; 
        String password = "root"; 

        int idToDelete = 1; // Thay đổi ID bạn muốn xóa ở đây

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            
            pstmt.setInt(1, idToDelete);
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("Đã xóa thành công user có ID: " + idToDelete);
            } else {
                System.out.println("Không tìm thấy user để xóa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}