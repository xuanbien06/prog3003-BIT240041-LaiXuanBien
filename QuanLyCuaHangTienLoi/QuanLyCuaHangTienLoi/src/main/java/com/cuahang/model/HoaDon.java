package com.cuahang.model;

import java.sql.Timestamp;

public class HoaDon {
    private int maHD;
    private Timestamp ngayLap; // Dùng Timestamp để lưu cả ngày lẫn giờ phút giây
    private double tongTien;
    private String maNV;

    public HoaDon(int maHD, Timestamp ngayLap, double tongTien, String maNV) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.maNV = maNV;
    }

    public int getMaHD() { return maHD; }
    public Timestamp getNgayLap() { return ngayLap; }
    public double getTongTien() { return tongTien; }
    public String getMaNV() { return maNV; }
}