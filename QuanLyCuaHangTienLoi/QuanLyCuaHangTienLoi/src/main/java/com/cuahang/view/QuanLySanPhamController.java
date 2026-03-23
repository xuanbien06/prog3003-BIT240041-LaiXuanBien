package com.cuahang.view;

import com.cuahang.dao.SanPhamDAO;
import com.cuahang.model.SanPham;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuanLySanPhamController implements Initializable {

    @FXML private TableView<SanPham> tblSanPham;
    @FXML private TableColumn<SanPham, String> colMaSP;
    @FXML private TableColumn<SanPham, String> colTenSP;
    @FXML private TableColumn<SanPham, Double> colGiaBan;
    @FXML private TableColumn<SanPham, Integer> colTonKho;

    @FXML private TextField txtMaSP;
    @FXML private TextField txtTenSP;
    @FXML private TextField txtGiaBan;
    @FXML private TextField txtTonKho;

    private SanPhamDAO dao = new SanPhamDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMaSP.setCellValueFactory(new PropertyValueFactory<>("maSP"));
        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenSP"));
        colGiaBan.setCellValueFactory(new PropertyValueFactory<>("giaBan"));
        colTonKho.setCellValueFactory(new PropertyValueFactory<>("soLuongTon"));
        loadData();
    }

    private void loadData() {
        List<SanPham> list = dao.layDanhSachSanPham();
        tblSanPham.setItems(FXCollections.observableArrayList(list));
    }

    // Khi click vào bảng, đẩy dữ liệu ngược xuống các ô nhập liệu
    @FXML
    void xuLyClickBang(MouseEvent event) {
        SanPham sp = tblSanPham.getSelectionModel().getSelectedItem();
        if (sp != null) {
            txtMaSP.setText(sp.getMaSP());
            txtMaSP.setEditable(false); // Không cho sửa Mã SP khi đang xem/sửa
            txtTenSP.setText(sp.getTenSP());
            txtGiaBan.setText(String.valueOf(sp.getGiaBan()));
            txtTonKho.setText(String.valueOf(sp.getSoLuongTon()));
        }
    }

    @FXML
    void xuLyThem(ActionEvent event) {
        try {
            // 1. Tạo một đối tượng Sản Phẩm rỗng
            SanPham sp = new SanPham();
            
            // 2. Nhét từng thông tin vào
            sp.setMaSP(txtMaSP.getText());
            sp.setTenSP(txtTenSP.getText());
            sp.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
            sp.setSoLuongTon(Integer.parseInt(txtTonKho.getText()));

            // 3. Gọi DAO để lưu
            if (dao.themSanPham(sp)) {
                hienThongBao("Thành công", "Đã thêm sản phẩm mới!");
                xuLyLamMoi(null);
            } else {
                hienThongBao("Lỗi", "Thêm thất bại (Có thể trùng Mã SP)!");
            }
        } catch (Exception e) {
            hienThongBao("Lỗi", "Vui lòng nhập đúng định dạng số cho Giá và Số lượng!");
        }
    }

    @FXML
    void xuLySua(ActionEvent event) {
        try {
            // 1. Tạo một đối tượng Sản Phẩm rỗng
            SanPham sp = new SanPham();
            
            // 2. Nhét từng thông tin vào
            sp.setMaSP(txtMaSP.getText());
            sp.setTenSP(txtTenSP.getText());
            sp.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
            sp.setSoLuongTon(Integer.parseInt(txtTonKho.getText()));

            // 3. Gọi DAO để cập nhật
            if (dao.suaSanPham(sp)) {
                hienThongBao("Thành công", "Đã cập nhật sản phẩm!");
                xuLyLamMoi(null);
            } else {
                hienThongBao("Lỗi", "Cập nhật thất bại!");
            }
        } catch (Exception e) {
            hienThongBao("Lỗi", "Vui lòng kiểm tra lại dữ liệu nhập!");
        }
    }

    @FXML
    void xuLyXoa(ActionEvent event) {
        if (dao.xoaSanPham(txtMaSP.getText())) {
            hienThongBao("Thành công", "Đã xóa sản phẩm!");
            xuLyLamMoi(null);
        } else {
            hienThongBao("Lỗi", "Xóa thất bại!");
        }
    }

    @FXML
    void xuLyLamMoi(ActionEvent event) {
        txtMaSP.clear();
        txtMaSP.setEditable(true); // Cho phép nhập lại Mã SP
        txtTenSP.clear();
        txtGiaBan.clear();
        txtTonKho.clear();
        loadData(); // Tải lại bảng
    }

    private void hienThongBao(String tieuDe, String noiDung) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tieuDe);
        alert.setHeaderText(null);
        alert.setContentText(noiDung);
        alert.showAndWait();
    }
}