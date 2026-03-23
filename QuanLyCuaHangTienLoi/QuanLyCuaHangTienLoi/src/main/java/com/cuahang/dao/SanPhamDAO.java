package com.cuahang.dao;

import com.cuahang.model.SanPham;
import com.cuahang.utils.DBConnection; // Import file kết nối hôm trước
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    // Hàm lấy danh sách tất cả sản phẩm
    public List<SanPham> layDanhSachSanPham() {
        List<SanPham> danhSach = new ArrayList<>();
        // Câu lệnh SQL lấy dữ liệu
        String sql = "SELECT * FROM SanPham"; 

        try {
            // Bước 1: Gọi file DBConnection để mở kết nối
            Connection conn = DBConnection.getConnection();
            
            // Bước 2: Chuẩn bị câu lệnh SQL
            PreparedStatement ps = conn.prepareStatement(sql);
            
            // Bước 3: Thực thi câu lệnh và nhận kết quả trả về dạng ResultSet (Bảng dữ liệu ảo)
            ResultSet rs = ps.executeQuery();
            
            // Bước 4: Duyệt từng dòng trong ResultSet, biến nó thành đối tượng SanPham và nhét vào List
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("MaSP"));           // Tên cột phải khớp chính xác với SQL
                sp.setTenSP(rs.getString("TenSP"));
                sp.setGiaBan(rs.getDouble("GiaBan"));
                sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                sp.setHanSuDung(rs.getDate("HanSuDung"));
                sp.setMaDanhMuc(rs.getInt("MaDanhMuc"));
                
                danhSach.add(sp); // Thêm sản phẩm vào danh sách
            }
            
            // Đóng kết nối để giải phóng bộ nhớ
            rs.close();
            ps.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return danhSach;
    }
    public static void main(String[] args) throws Exception { // Thêm throws Exception ở đây
        // Thêm dòng này để ép Console NetBeans in ra tiếng Việt
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        
        SanPhamDAO dao = new SanPhamDAO();
        List<SanPham> list = dao.layDanhSachSanPham();
        
        System.out.println("--- DANH SÁCH SẢN PHẨM TRONG KHO ---");
        for (SanPham sp : list) {
            System.out.println("Mã: " + sp.getMaSP() + " | Tên: " + sp.getTenSP() + " | Giá: " + sp.getGiaBan());
        }
    }
    // Hàm này sẽ lấy Số lượng tồn kho hiện tại trừ đi Số lượng khách vừa mua
    public void capNhatSoLuongTon(String maSP, int soLuongMua) {
        // Lệnh SQL UPDATE: Lấy giá trị cũ trừ đi giá trị mới (?) tại đúng dòng có Mã SP đó
        String sql = "UPDATE SanPham SET SoLuongTon = SoLuongTon - ? WHERE MaSP = ?";
        
        try {
            java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            
            // Truyền dữ liệu vào 2 dấu chấm hỏi
            ps.setInt(1, soLuongMua);
            ps.setString(2, maSP);
            
            // Thực thi câu lệnh cập nhật
            ps.executeUpdate();
            
            ps.close(); 
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 1. Hàm Thêm Sản Phẩm Mới
    public boolean themSanPham(SanPham sp) {
        String sql = "INSERT INTO SanPham (MaSP, TenSP, GiaBan, SoLuongTon) VALUES (?, ?, ?, ?)";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setDouble(3, sp.getGiaBan());
            ps.setInt(4, sp.getSoLuongTon());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Hàm Cập Nhật (Sửa) Sản Phẩm
    public boolean suaSanPham(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP = ?, GiaBan = ?, SoLuongTon = ? WHERE MaSP = ?";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setDouble(2, sp.getGiaBan());
            ps.setInt(3, sp.getSoLuongTon());
            ps.setString(4, sp.getMaSP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Hàm Xóa Sản Phẩm
    public boolean xoaSanPham(String maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (java.sql.Connection conn = com.cuahang.utils.DBConnection.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
