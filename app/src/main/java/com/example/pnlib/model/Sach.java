package com.example.pnlib.model;

public class Sach {
    private int MaSach;
    private String TenSach;
    private int GiaThue;

    private int MaLoai;
    private String TenLoai;
    private int SoLanMuon;
    private int NgayXuatBan;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaThue, int maLoai, String tenLoai, int ngayXuatBan) {
        MaSach = maSach;
        TenSach = tenSach;
        GiaThue = giaThue;
        MaLoai = maLoai;
        TenLoai = tenLoai;
        NgayXuatBan = ngayXuatBan;
    }

    public Sach(int maSach, String tenSach, int soLanMuon) {
        MaSach = maSach;
        TenSach = tenSach;
        SoLanMuon = soLanMuon;
    }

    public int getNgayXuatBan() {
        return NgayXuatBan;
    }

    public void setNgayXuatBan(int ngayXuatBan) {
        NgayXuatBan = ngayXuatBan;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public int getGiaThue() {
        return GiaThue;
    }

    public void setGiaThue(int giaThue) {
        GiaThue = giaThue;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public int getSoLanMuon() {
        return SoLanMuon;
    }

    public void setSoLanMuon(int soLanMuon) {
        SoLanMuon = soLanMuon;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }
}
