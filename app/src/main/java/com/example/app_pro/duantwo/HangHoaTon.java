package com.example.app_pro.duantwo;

/**
 * Created by APP-PRO on 12/17/2017.
 */

public class HangHoaTon {
    private String tenHang;
    private int soLuong;

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public HangHoaTon(String tenHang, int soLuong) {
        this.tenHang = tenHang;
        this.soLuong = soLuong;
    }
}
