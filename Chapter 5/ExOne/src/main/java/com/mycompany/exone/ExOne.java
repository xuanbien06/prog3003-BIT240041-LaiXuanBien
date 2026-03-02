package com.mycompany.exone;

import java.sql.*;

public class ExOne {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/java_exercises";
        String user = "root"; 
        String password = "12345"; 

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            
            System.out.println("--- DANH SÁCH NGƯỜI DÙNG ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " + 
                                   rs.getString("username") + " - " + 
                                   rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}