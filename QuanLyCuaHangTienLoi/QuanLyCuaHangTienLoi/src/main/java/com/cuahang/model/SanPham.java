package com.cuahang.model;

import java.sql.Date; // Lưu ý import đúng java.sql.Date cho ngày tháng của database

public class SanPham {
    private String maSP;
    private String tenSP;
    private double giaBan;
    private int soLuongTon;
    private Date hanSuDung;
    private int maDanhMuc;

    // Constructor không tham số (Bắt buộc phải có)
    public SanPham() {
    }

    // Constructor đầy đủ tham số
    public SanPham(String maSP, String tenSP, double giaBan, int soLuongTon, Date hanSuDung, int maDanhMuc) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
        this.hanSuDung = hanSuDung;
        this.maDanhMuc = maDanhMuc;
    }

    // Các hàm Getter và Setter (Dùng để lấy và gán giá trị cho biến)
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public double getGiaBan() { return giaBan; }
    public void setGiaBan(double giaBan) { this.giaBan = giaBan; }

    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }

    public Date getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(Date hanSuDung) { this.hanSuDung = hanSuDung; }

    public int getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(int maDanhMuc) { this.maDanhMuc = maDanhMuc; }
}