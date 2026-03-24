package com.cuahang.view;

import com.cuahang.dao.SanPhamDAO;
import com.cuahang.model.SanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.cuahang.model.ChiTietGioHang;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// Thêm chữ "implements Initializable" để có thể chạy code lúc mở form
public class BanHangController implements Initializable {
    @FXML
    private Button btnQuanLyTaiKhoan;

    // --- KHAI BÁO BẢNG VÀ CỘT (Phải giống hệt fx:id trong Scene Builder) ---
    @FXML
    private TableView<SanPham> tblSanPham; // Chỉ định rõ bảng này chứa class SanPham

    @FXML
    private TableColumn<SanPham, String> colMaSP;

    @FXML
    private TableColumn<SanPham, String> colTenSP;

    @FXML
    private TableColumn<SanPham, Double> colGiaBan;

    @FXML
    private TableColumn<SanPham, Integer> colTonKho;

    // Các thành phần khác giữ nguyên
    @FXML private TextField txtKhachDua;
    @FXML private TextField txtTienThua;
    @FXML private TextField txtTongTien;
    @FXML private Button btnThanhToan;
    
    // ... (Các code cũ ở trên giữ nguyên) ...

    // Khai báo Bảng và Cột cho Giỏ Hàng
    @FXML
    private TableView<ChiTietGioHang> tblGioHang;

    @FXML
    private TableColumn<ChiTietGioHang, String> colTenSPGio;

    @FXML
    private TableColumn<ChiTietGioHang, Integer> colSoLuongGio;

    @FXML
    private TableColumn<ChiTietGioHang, Double> colThanhTienGio;

    // Danh sách ảo chứa dữ liệu giỏ hàng
    private ObservableList<ChiTietGioHang> danhSachGioHang = FXCollections.observableArrayList();

    // --- HÀM NÀY SẼ TỰ ĐỘNG CHẠY KHI FORM VỪA MỞ LÊN ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataToTable();
        colTenSPGio.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colSoLuongGio.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colThanhTienGio.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));
        
        // Gắn danh sách ảo vào bảng Giỏ Hàng
        tblGioHang.setItems(danhSachGioHang);
        // --- LOGIC PHÂN QUYỀN (ĐÃ SỬA THÀNH QuanLy) ---
        if (DangNhapController.taiKhoanDangNhap != null) {
            String vaiTro = DangNhapController.taiKhoanDangNhap.getVaiTro();
            
            // Nếu vai trò KHÔNG PHẢI là "QuanLy" (không phân biệt hoa thường) thì ẩn nút
            if (vaiTro == null || !vaiTro.trim().equalsIgnoreCase("QuanLy")) {
                btnQuanLyTaiKhoan.setVisible(false); // Giấu nút đi (Dành cho nhân viên thường)
            } else {
                btnQuanLyTaiKhoan.setVisible(true);  // Hiện nút lên (Dành cho Quản Lý)
            }
        }
    }

    // --- HÀM ĐỔ DỮ LIỆU TỪ MYSQL LÊN BẢNG ---
    private void loadDataToTable() {
        // 1. Chỉ định cho cột biết nó phải lấy dữ liệu từ biến nào trong class SanPham
        // LƯU Ý: Chữ trong ngoặc kép phải gõ Y HỆT tên biến bạn đã khai báo trong file SanPham.java
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colTonKho.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));

        // 2. Gọi DAO lấy danh sách từ MySQL
        SanPhamDAO dao = new SanPhamDAO();
        List<SanPham> danhSachTuDB = dao.layDanhSachSanPham();

        // 3. JavaFX dùng ObservableList thay vì List thông thường
        ObservableList<SanPham> observableList = FXCollections.observableArrayList(danhSachTuDB);

        // 4. Nhét toàn bộ dữ liệu vào bảng
        tblSanPham.setItems(observableList);
    }

    @FXML
    void xuLyThanhToan(ActionEvent event) {
        // 1. Kiểm tra xem giỏ hàng có trống không
        if (danhSachGioHang.isEmpty()) {
            hienThongBao("Lỗi", "Giỏ hàng đang trống, hãy chọn món trước khi thanh toán!");
            return;
        }

        try {
            // 2. Lấy Tổng Tiền hiện tại
            double tongTien = Double.parseDouble(txtTongTien.getText());
            
            // 3. Lấy Tiền Khách Đưa do thu ngân nhập vào
            String tienKhachDuaStr = txtKhachDua.getText();
            if (tienKhachDuaStr.isEmpty()) {
                hienThongBao("Cảnh báo", "Vui lòng nhập số tiền khách đưa!");
                return;
            }
            double tienKhachDua = Double.parseDouble(tienKhachDuaStr);

            // 4. Kiểm tra xem khách đưa có đủ tiền không
            if (tienKhachDua < tongTien) {
                double tienThieu = tongTien - tienKhachDua;
                hienThongBao("Lỗi", "Khách đưa thiếu tiền! Cần thu thêm: " + tienThieu);
                return;
            }

            // 5. Tính Tiền Thừa và hiển thị ra ô Text Field
            double tienThua = tienKhachDua - tongTien;
            txtTienThua.setText(String.valueOf(tienThua));
            
            com.cuahang.dao.HoaDonDAO hdDao = new com.cuahang.dao.HoaDonDAO();
            String maNVTruc = DangNhapController.taiKhoanDangNhap.getMaNV();
            // Đẩy nguyên cái giỏ hàng và tổng tiền xuống Database
            boolean luuThanhCong = hdDao.luuHoaDon(maNVTruc, tongTien, danhSachGioHang);
            
            if (!luuThanhCong) {
                hienThongBao("Lỗi Hệ Thống", "Không thể lưu lịch sử hóa đơn vào cơ sở dữ liệu!");
                return; // Dừng lại không làm tiếp
            }

            // 6. Báo cáo thành công!
            hienThongBao("Hoàn tất", "Thanh toán thành công!\nTiền thừa trả khách: " + tienThua);
            
            // --- ĐOẠN CODE MỚI THÊM: TRỪ TỒN KHO TRONG MYSQL ---
            SanPhamDAO dao = new SanPhamDAO();
            // Chạy vòng lặp qua từng món trong Giỏ Hàng để trừ đi
            for (ChiTietGioHang item : danhSachGioHang) {
                dao.capNhatSoLuongTon(item.getMaSP(), item.getSoLuong());
            }
            
            // Gọi lại hàm đổ dữ liệu để cái bảng bên trái lập tức cập nhật số lượng mới
            loadDataToTable();

            // 7. Dọn dẹp quầy thu ngân để đón khách mới
            danhSachGioHang.clear();  // Xóa sạch giỏ hàng
            tblGioHang.refresh();     // Làm mới bảng
            txtTongTien.clear();      // Xóa chữ ở ô Tổng tiền
            txtKhachDua.clear();      // Xóa chữ ô Khách đưa
            txtTienThua.clear();      // Xóa chữ ô Tiền thừa

        } catch (NumberFormatException e) {
            // Nếu thu ngân lỡ gõ chữ cái "abc" vào ô nhập tiền thì báo lỗi ngay
            hienThongBao("Lỗi nhập liệu", "Vui lòng chỉ gõ số vào ô Tiền khách đưa!");
        }
    }
    // Hàm này chạy khi bạn click chuột vào 1 dòng ở bảng Sản Phẩm bên trái
    @FXML
    void xuLyChonSanPham(javafx.scene.input.MouseEvent event) {
        // 1. Lấy sản phẩm đang được chọn
        SanPham spDuocChon = tblSanPham.getSelectionModel().getSelectedItem();
        if (spDuocChon == null) return; // Nếu click ra ngoài bảng thì bỏ qua

        // 2. Kiểm tra xem món này đã có trong Giỏ Hàng chưa
        boolean daCoTrongGio = false;
        for (ChiTietGioHang item : danhSachGioHang) {
            if (item.getMaSP().equals(spDuocChon.getMaSP())) {
                // Nếu đã có -> Tăng số lượng lên 1
                item.setSoLuong(item.getSoLuong() + 1);
                daCoTrongGio = true;
                break;
            }
        }

        // 3. Nếu chưa có thì thêm mới vào giỏ với số lượng là 1
        if (!daCoTrongGio) {
            ChiTietGioHang newItem = new ChiTietGioHang(
                spDuocChon.getMaSP(), 
                spDuocChon.getTenSP(), 
                1, 
                spDuocChon.getGiaBan()
            );
            danhSachGioHang.add(newItem);
        }

        // 4. Làm mới (Refresh) lại bảng giỏ hàng để nó hiện số lượng/thành tiền mới
        tblGioHang.refresh();
        
        // 5. Tính lại tổng tiền
        tinhTongTien();
    }

    // Hàm tính tổng tiền cộng dồn
    private void tinhTongTien() {
        double tongTien = 0;
        for (ChiTietGioHang item : danhSachGioHang) {
            tongTien += item.getThanhTien();
        }
        // Hiển thị ra ô Text Field
        txtTongTien.setText(String.valueOf(tongTien));
    }
    // Hàm này chạy khi click chuột vào bảng Giỏ Hàng bên phải
    @FXML
    void xuLyGiamSoLuong(javafx.scene.input.MouseEvent event) {
        // 1. Kiểm tra xem người dùng có nháy đúp chuột (nhấn 2 lần) không
        // (Dùng nháy đúp để tránh trường hợp khách chỉ click 1 lần để xem mà bị trừ nhầm)
        if (event.getClickCount() == 2) {
            
            // 2. Lấy món hàng đang được chọn trong giỏ
            ChiTietGioHang monHang = tblGioHang.getSelectionModel().getSelectedItem();
            
            // Nếu click ra ngoài vùng trống thì bỏ qua
            if (monHang == null) return; 

            // 3. Giảm số lượng đi 1
            monHang.setSoLuong(monHang.getSoLuong() - 1);

            // 4. Nếu số lượng giảm về 0 (nghĩa là khách không mua nữa), thì xóa hẳn món đó khỏi giỏ
            if (monHang.getSoLuong() == 0) {
                danhSachGioHang.remove(monHang);
            }

            // 5. Làm mới lại bảng để nó hiển thị số lượng và thành tiền mới
            tblGioHang.refresh();

            // 6. Tính lại ô Tổng Tiền ở bên dưới
            tinhTongTien();
        }
    }
    // Hàm tiện ích để hiển thị hộp thoại thông báo
    private void hienThongBao(String tieuDe, String noiDung) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
    @FXML
    void moCuaSoQuanLySP(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/cuahang/view/QuanLySanPham.fxml"));
            javafx.scene.Parent root = loader.load();
            
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Quản Lý Sản Phẩm");
            stage.setScene(new javafx.scene.Scene(root));
            
            // showAndWait() có nghĩa là: Mở cửa sổ lên và TẠM DỪNG code ở đây
            // Khi nào người dùng ĐÓNG cửa sổ quản lý lại, code mới chạy tiếp xuống dòng dưới
            stage.showAndWait(); 
            
            // Lập tức làm mới lại bảng Bán Hàng sau khi tắt cửa sổ quản lý
            loadDataToTable(); 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void moCuaSoTaiKhoan(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/cuahang/view/QuanLyTaiKhoan.fxml"));
            javafx.scene.Parent root = loader.load();
            
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Quản Lý Tài Khoản (Dành cho Quản Lý)");
            stage.setScene(new javafx.scene.Scene(root));
            
            // Dùng showAndWait để nếu có tắt đi thì form Bán Hàng vẫn đứng yên
            stage.showAndWait(); 
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi mở bảng Tài Khoản: " + e.getMessage());
        }
    }
    @FXML
    void xuLyDangXuat(ActionEvent event) {
        // 1. Tạo hộp thoại hỏi xác nhận cho chắc chắn
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất để đổi ca không?");
        
        // 2. Bắt sự kiện người dùng chọn OK hay Cancel
        java.util.Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // 3. Xóa thông tin người đang đăng nhập (Reset phiên làm việc)
                DangNhapController.taiKhoanDangNhap = null;

                // 4. Mở lại màn hình Đăng Nhập
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/cuahang/view/DangNhap.fxml"));
                javafx.scene.Parent root = loader.load();
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setTitle("Đăng Nhập - Hệ Thống Quản Lý");
                stage.setScene(new javafx.scene.Scene(root));
                stage.show();

                // 5. Đóng cửa sổ Bán Hàng hiện tại đi
                ((javafx.scene.Node)(event.getSource())).getScene().getWindow().hide();

            } catch (Exception e) {
                e.printStackTrace();
                hienThongBao("Lỗi", "Đã xảy ra lỗi khi quay lại màn hình đăng nhập!");
            }
        }
    }
    @FXML
    void moCuaSoLichSu(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/cuahang/view/LichSuGiaoDich.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = new javafx.stage.Stage();
            
            // Hiện tên người trực trên thanh tiêu đề cho chuyên nghiệp
            String tenNV = DangNhapController.taiKhoanDangNhap.getTenNV();
            stage.setTitle("Lịch Sử Giao Dịch (2 Ngày) - Ca trực: " + tenNV);
            
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) { 
            e.printStackTrace(); 
            System.out.println("Lỗi mở bảng Lịch sử: " + e.getMessage());
        }
    }
}