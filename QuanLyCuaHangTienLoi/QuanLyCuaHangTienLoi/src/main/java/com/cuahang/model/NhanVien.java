package com.cuahang.model;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String taiKhoan;
    private String matKhau;
    private String vaiTro;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String taiKhoan, String matKhau, String vaiTro) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    // Bạn dùng Alt + Insert -> Getter and Setter để NetBeans tự tạo các hàm get/set nhé
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }

    public String getTenNV() { return tenNV; }
    public void setTenNV(String tenNV) { this.tenNV = tenNV; }

    public String getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(String taiKhoan) { this.taiKhoan = taiKhoan; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
}