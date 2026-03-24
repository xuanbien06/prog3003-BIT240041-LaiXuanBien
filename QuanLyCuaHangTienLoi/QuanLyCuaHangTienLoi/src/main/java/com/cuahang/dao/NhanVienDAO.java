package com.cuahang.dao;

import com.cuahang.model.NhanVien;
import com.cuahang.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NhanVienDAO {
    
    // Hàm này trả về đối tượng NhanVien nếu đúng, trả về null nếu sai user/pass
    public NhanVien kiemTraDangNhap(String taiKhoan, String matKhau) {
        NhanVien nv = null;
        // Dấu ? là tham số sẽ được truyền vào sau để chống hack (SQL Injection)
        String sql = "SELECT * FROM NhanVien WHERE TaiKhoan = ? AND MatKhau = ?";
        
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // Truyền tài khoản và mật khẩu vào 2 dấu ?
            ps.setString(1, taiKhoan);
            ps.setString(2, matKhau);
            
            ResultSet rs = ps.executeQuery();
            
            // Nếu có dữ liệu trả về (rs.next() == true) nghĩa là đăng nhập thành công
            if (rs.next()) {
                nv = new NhanVien();
                nv.setMaNV(rs.getString("MaNV"));
                nv.setTenNV(rs.getString("TenNV"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setVaiTro(rs.getString("VaiTro"));
            }
            
            rs.close(); ps.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv; // Trả kết quả về cho giao diện xử lý
    }
    // 1. Lấy danh sách nhân viên
    public java.util.List<NhanVien> layDanhSachNhanVien() {
        java.util.List<NhanVien> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new NhanVien(rs.getString("MaNV"), rs.getString("TenNV"), 
                        rs.getString("TaiKhoan"), rs.getString("MatKhau"), rs.getString("VaiTro")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. Thêm nhân viên
    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (MaNV, TenNV, TaiKhoan, MatKhau, VaiTro) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getMaNV()); ps.setString(2, nv.getTenNV());
            ps.setString(3, nv.getTaiKhoan()); ps.setString(4, nv.getMatKhau());
            ps.setString(5, nv.getVaiTro());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    // 3. Sửa nhân viên (Không cho sửa Mã NV)
    public boolean suaNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET TenNV=?, TaiKhoan=?, MatKhau=?, VaiTro=? WHERE MaNV=?";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNV()); ps.setString(2, nv.getTaiKhoan());
            ps.setString(3, nv.getMatKhau()); ps.setString(4, nv.getVaiTro());
            ps.setString(5, nv.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    // 4. Xóa nhân viên
    public boolean xoaNhanVien(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }
}