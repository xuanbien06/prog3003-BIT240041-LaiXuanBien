package com.cuahang.view;

import com.cuahang.dao.NhanVienDAO;
import com.cuahang.model.NhanVien;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class QuanLyTaiKhoanController implements Initializable {

    @FXML private TableView<NhanVien> tblTaiKhoan;
    @FXML private TableColumn<NhanVien, String> colMaNV, colTenNV, colTaiKhoan, colMatKhau, colVaiTro;
    @FXML private TextField txtMaNV, txtTenNV, txtTaiKhoan, txtMatKhau, txtVaiTro;

    private NhanVienDAO dao = new NhanVienDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colTenNV.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        colTaiKhoan.setCellValueFactory(new PropertyValueFactory<>("taiKhoan"));
        colMatKhau.setCellValueFactory(new PropertyValueFactory<>("matKhau"));
        colVaiTro.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));
        loadData();
    }

    private void loadData() {
        tblTaiKhoan.setItems(FXCollections.observableArrayList(dao.layDanhSachNhanVien()));
    }

    @FXML
    void xuLyClickBang(MouseEvent event) {
        NhanVien nv = tblTaiKhoan.getSelectionModel().getSelectedItem();
        if (nv != null) {
            txtMaNV.setText(nv.getMaNV()); txtMaNV.setEditable(true);
            txtTenNV.setText(nv.getTenNV());
            txtTaiKhoan.setText(nv.getTaiKhoan());
            txtMatKhau.setText(nv.getMatKhau());
            txtVaiTro.setText(nv.getVaiTro());
        }
    }

    @FXML
    void xuLyThem(ActionEvent event) {
        NhanVien nv = new NhanVien(txtMaNV.getText(), txtTenNV.getText(), txtTaiKhoan.getText(), txtMatKhau.getText(), txtVaiTro.getText());
        if (dao.themNhanVien(nv)) { hienThongBao("Thành công", "Đã thêm!"); xuLyLamMoi(null); } 
        else { hienThongBao("Lỗi", "Thêm thất bại!"); }
    }

    @FXML
    void xuLySua(ActionEvent event) {
        NhanVien nv = new NhanVien(txtMaNV.getText(), txtTenNV.getText(), txtTaiKhoan.getText(), txtMatKhau.getText(), txtVaiTro.getText());
        if (dao.suaNhanVien(nv)) { hienThongBao("Thành công", "Đã sửa!"); xuLyLamMoi(null); } 
        else { hienThongBao("Lỗi", "Sửa thất bại!"); }
    }

    @FXML
    void xuLyXoa(ActionEvent event) {
        if (txtMaNV.getText().equals("NV01") || txtTaiKhoan.getText().equals("admin")) {
            hienThongBao("Cảnh báo", "Không được xóa tài khoản Admin gốc!"); return;
        }
        if (dao.xoaNhanVien(txtMaNV.getText())) { hienThongBao("Thành công", "Đã xóa!"); xuLyLamMoi(null); }
    }

    @FXML
    void xuLyLamMoi(ActionEvent event) {
        txtMaNV.clear(); txtMaNV.setEditable(true);
        txtTenNV.clear(); txtTaiKhoan.clear(); txtMatKhau.clear(); txtVaiTro.clear();
        loadData();
    }

    private void hienThongBao(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); alert.setHeaderText(null); alert.setContentText(content); alert.showAndWait();
    }
}