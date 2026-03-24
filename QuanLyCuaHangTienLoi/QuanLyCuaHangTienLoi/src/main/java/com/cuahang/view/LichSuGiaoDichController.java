package com.cuahang.view;

import com.cuahang.dao.HoaDonDAO;
import com.cuahang.model.ChiTietGioHang;
import com.cuahang.model.HoaDon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class LichSuGiaoDichController implements Initializable {

    // --- BẢNG HÓA ĐƠN (BÊN TRÁI) ---
    @FXML private TableView<HoaDon> tblHoaDon;
    @FXML private TableColumn<HoaDon, Integer> colMaHD;
    @FXML private TableColumn<HoaDon, Timestamp> colNgayLap;
    @FXML private TableColumn<HoaDon, Double> colTongTien;

    // --- BẢNG CHI TIẾT (BÊN PHẢI) ---
    @FXML private TableView<ChiTietGioHang> tblChiTiet;
    @FXML private TableColumn<ChiTietGioHang, String> colTenSP;
    @FXML private TableColumn<ChiTietGioHang, Integer> colSoLuong;
    @FXML private TableColumn<ChiTietGioHang, Double> colDonGia;

    private HoaDonDAO dao = new HoaDonDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Nối cột cho Bảng Hóa Đơn
        colMaHD.setCellValueFactory(new PropertyValueFactory<>("maHD"));
        colNgayLap.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));

        // 2. Nối cột cho Bảng Chi Tiết
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        colDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));

        // 3. Đổ dữ liệu lịch sử ngay khi mở form
        loadLichSu();
    }

    private void loadLichSu() {
        // Lấy đúng mã nhân viên của ca trực hiện tại
        String maNV = DangNhapController.taiKhoanDangNhap.getMaNV();
        
        // Gọi DAO lấy danh sách 2 ngày qua
        List<HoaDon> listHD = dao.layLichSu2Ngay(maNV);
        tblHoaDon.setItems(FXCollections.observableArrayList(listHD));
    }

    // --- SỰ KIỆN CLICK VÀO MỘT MỐC THỜI GIAN ĐỂ XEM CHI TIẾT ---
    @FXML
    void xuLyChonHoaDon(MouseEvent event) {
        // Lấy dòng hóa đơn đang được click
        HoaDon hd = tblHoaDon.getSelectionModel().getSelectedItem();
        
        if (hd != null) {
            // Lấy danh sách món hàng của riêng hóa đơn đó và đẩy sang bảng bên phải
            List<ChiTietGioHang> listCT = dao.layChiTietCuaHoaDon(hd.getMaHD());
            tblChiTiet.setItems(FXCollections.observableArrayList(listCT));
        }
    }
}