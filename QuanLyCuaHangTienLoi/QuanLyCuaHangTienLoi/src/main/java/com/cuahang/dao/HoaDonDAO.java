package com.cuahang.dao;

import com.cuahang.model.ChiTietGioHang;
import com.cuahang.model.HoaDon;
import com.cuahang.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    // 1. Hàm lưu hóa đơn khi bấm Thanh Toán
    public boolean luuHoaDon(String maNV, double tongTien, List<ChiTietGioHang> gioHang) {
        String sqlHoaDon = "INSERT INTO HoaDon (TongTien, MaNV) VALUES (?, ?)";
        String sqlChiTiet = "INSERT INTO ChiTietHoaDon (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection()) {
            // Tắt auto-commit để đảm bảo lưu cả hóa đơn lẫn chi tiết mới thành công
            conn.setAutoCommit(false); 

            try (PreparedStatement psHD = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                psHD.setDouble(1, tongTien);
                psHD.setString(2, maNV);
                psHD.executeUpdate();

                // Lấy Mã Hóa Đơn vừa được tự động tạo ra
                ResultSet rs = psHD.getGeneratedKeys();
                if (rs.next()) {
                    int maHDVuaTao = rs.getInt(1);

                    // Lưu từng món trong giỏ hàng vào bảng ChiTietHoaDon
                    try (PreparedStatement psCT = conn.prepareStatement(sqlChiTiet)) {
                        for (ChiTietGioHang item : gioHang) {
                            psCT.setInt(1, maHDVuaTao);
                            psCT.setString(2, item.getMaSP());
                            psCT.setInt(3, item.getSoLuong());
                            psCT.setDouble(4, item.getDonGia());
                            psCT.setDouble(5, item.getThanhTien());
                            psCT.addBatch(); // Gom lại
                        }
                        psCT.executeBatch(); // Chạy một lượt cho nhanh
                    }
                }
                conn.commit(); // Xác nhận lưu thành công toàn bộ
                return true;
            } catch (Exception ex) {
                conn.rollback(); // Nếu lỗi thì hủy bỏ không lưu gì cả
                ex.printStackTrace();
                return false;
            }
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // 2. Hàm lấy lịch sử giao dịch (Chỉ lấy trong vòng 2 ngày gần nhất)
    public List<HoaDon> layLichSu2Ngay(String maNV) {
        List<HoaDon> list = new ArrayList<>();
        // Lệnh SQL: Lấy hóa đơn của nhân viên này, với điều kiện Ngày Lập >= Ngày hiện tại trừ đi 2 ngày
        String sql = "SELECT * FROM HoaDon WHERE MaNV = ? AND NgayLap >= NOW() - INTERVAL 2 DAY ORDER BY NgayLap DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new HoaDon(rs.getInt("MaHD"), rs.getTimestamp("NgayLap"), 
                                    rs.getDouble("TongTien"), rs.getString("MaNV")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 3. Hàm lấy chi tiết các món trong một hóa đơn cụ thể (tái sử dụng model ChiTietGioHang cho tiện hiển thị)
    public List<ChiTietGioHang> layChiTietCuaHoaDon(int maHD) {
        List<ChiTietGioHang> list = new ArrayList<>();
        String sql = "SELECT ct.MaSP, sp.TenSP, ct.SoLuong, ct.DonGia " +
                     "FROM ChiTietHoaDon ct JOIN SanPham sp ON ct.MaSP = sp.MaSP WHERE ct.MaHD = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ChiTietGioHang(rs.getString("MaSP"), rs.getString("TenSP"), 
                                            rs.getInt("SoLuong"), rs.getDouble("DonGia")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}