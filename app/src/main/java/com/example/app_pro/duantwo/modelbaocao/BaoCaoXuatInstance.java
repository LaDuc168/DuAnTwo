package com.example.app_pro.duantwo.modelbaocao;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class BaoCaoXuatInstance {
    private String ten;
    private String ngay;
    private int soluong;
    private String ghichu;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public BaoCaoXuatInstance(String ten, String ngay, int soluong, String ghichu) {

        this.ten = ten;
        this.ngay = ngay;
        this.soluong = soluong;
        this.ghichu = ghichu;
    }
}
