package com.example.app_pro.duantwo.model;

import java.io.Serializable;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class HoaDonNhap implements Serializable{




    private String maHD;
    private String maH;
    private String ngayHD;
    private int SoLuongHD;
    private String ghichu;

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public HoaDonNhap(String maHD, String maH, String ngayHD, int soLuongHD, String ghichu) {

        this.maHD = maHD;
        this.maH = maH;
        this.ngayHD = ngayHD;
        SoLuongHD = soLuongHD;
        this.ghichu = ghichu;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaH() {
        return maH;
    }

    public void setMaH(String maH) {
        this.maH = maH;
    }

    public String getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(String ngayHD) {
        this.ngayHD = ngayHD;
    }

    public int getSoLuongHD() {
        return SoLuongHD;
    }

    public void setSoLuongHD(int soLuongHD) {
        SoLuongHD = soLuongHD;
    }

    public HoaDonNhap(String maHD, String maH, String ngayHD, int soLuongHD) {

        this.maHD = maHD;
        this.maH = maH;
        this.ngayHD = ngayHD;
        SoLuongHD = soLuongHD;
    }
}
