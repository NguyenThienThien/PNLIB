package com.example.pnlib.model;

public class LoaiSach {
    private int MaLS;
    private String TenLS;

    public LoaiSach() {
    }

    public LoaiSach(int maLS, String tenLS) {
        MaLS = maLS;
        TenLS = tenLS;
    }

    public int getMaLS() {
        return MaLS;
    }

    public void setMaLS(int maLS) {
        MaLS = maLS;
    }

    public String getTenLS() {
        return TenLS;
    }

    public void setTenLS(String tenLS) {
        TenLS = tenLS;
    }
}
