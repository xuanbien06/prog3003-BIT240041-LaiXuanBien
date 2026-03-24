package com.cuahang.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.cuahang.dao.NhanVienDAO;
import com.cuahang.model.NhanVien;

public class DangNhapController {
    public static NhanVien taiKhoanDangNhap;

    // Khai báo đúng tên fx:id đã đặt bên Scene Builder, có chữ @FXML đứng trước
    @FXML
    private TextField txtTaiKhoan;

    @FXML
    private PasswordField txtMatKhau;

    // Hàm này tương ứng với On Action đã đặt bên Scene Builder
    @FXML
    void xuLyDangNhap(ActionEvent event) {
        // 1. Lấy dữ liệu
        String taiKhoan = txtTaiKhoan.getText();
        String matKhau = txtMatKhau.getText();

        // 2. Kiểm tra rỗng
        if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
            hienThongBao("Lỗi", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            return;
        }

        // 3. Gọi DAO (Giữ nguyên y hệt bên Swing, không phải sửa gì!)
        NhanVienDAO dao = new NhanVienDAO();
        NhanVien nv = dao.kiemTraDangNhap(taiKhoan, matKhau);

        // 4. Xử lý kết quả
        if (nv != null) {
            taiKhoanDangNhap = nv;
            hienThongBao("Thành công", "Đăng nhập thành công! Chào " + nv.getTenNV());
            try {
                // Tải giao diện Bán Hàng
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/cuahang/view/BanHang.fxml"));
                javafx.scene.Parent root = loader.load();
                
                // Tạo một cửa sổ (Stage) mới
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("Phần Mềm Quản Lý Cửa Hàng - Bán Hàng");
                stage.setScene(new javafx.scene.Scene(root));
                stage.show(); // Hiển thị cửa sổ Bán Hàng
                
                // Đóng cửa sổ Đăng Nhập hiện tại đi
                ((javafx.scene.Node)(event.getSource())).getScene().getWindow().hide();
                
            } catch (Exception e) {
                e.printStackTrace();
                hienThongBao("Lỗi", "Không thể mở form Bán Hàng: " + e.getMessage());
            }
        } else {
            hienThongBao("Thất bại", "Sai tài khoản hoặc mật khẩu!");
        }
    }
    

    // Hàm tiện ích để hiển thị hộp thoại thông báo đẹp mắt của JavaFX
    private void hienThongBao(String tieuDe, String noiDung) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
}