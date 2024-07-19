package com.example.pnlib.model;

public class ThanhVien {
    private int MaTV;
    private String HoTen,NamSinh;

    public ThanhVien() {
    }

    public ThanhVien(int maTV, String hoTen, String namSinh) {
        MaTV = maTV;
        HoTen = hoTen;
        NamSinh = namSinh;
    }

    public int getMaTV() {
        return MaTV;
    }

    public void setMaTV(int maTV) {
        MaTV = maTV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String namSinh) {
        NamSinh = namSinh;
    }
}
