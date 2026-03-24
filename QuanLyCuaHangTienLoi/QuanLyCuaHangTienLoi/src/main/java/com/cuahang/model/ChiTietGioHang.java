package com.cuahang.model;

public class ChiTietGioHang {
    private String maSP;
    private String tenSP;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietGioHang(String maSP, String tenSP, int soLuong, double donGia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuong * donGia; // Tự động tính thành tiền
    }

    // --- GETTER VÀ SETTER ---
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public int getSoLuong() { return soLuong; }
    
    // Khi set lại số lượng, tự động cập nhật lại thành tiền
    public void setSoLuong(int soLuong) { 
        this.soLuong = soLuong; 
        this.thanhTien = this.soLuong * this.donGia;
    }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}