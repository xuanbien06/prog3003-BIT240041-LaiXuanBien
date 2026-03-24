package com.mycompany.quanlycuahangtienloi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Trỏ đường dẫn tới file giao diện FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/cuahang/view/DangNhap.fxml"));
        
        // Tạo một Scene (khung cảnh) chứa giao diện
        Scene scene = new Scene(root);
        
        // Đặt Scene lên Stage (Cửa sổ ứng dụng) và hiển thị
        primaryStage.setTitle("Hệ Thống Quản Lý Cửa Hàng Tiện Lợi");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lệnh đánh thức JavaFX hoạt động
    }
}