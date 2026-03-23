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
}