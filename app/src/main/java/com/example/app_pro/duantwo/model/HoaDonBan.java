package com.example.app_pro.duantwo.model;

import java.io.Serializable;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class HoaDonBan implements Serializable {




    private String mahdb;
    private String mahh;
    private String ngay;
    private int sl;
    private String ghichu;

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public HoaDonBan(String mahdb, String mahh, String ngay, int sl, String ghichu) {
        this.mahdb = mahdb;
        this.mahh = mahh;
        this.ngay = ngay;
        this.sl = sl;
        this.ghichu = ghichu;
    }

    public String getMahdb() {
        return mahdb;
    }

    public void setMahdb(String mahdb) {
        this.mahdb = mahdb;
    }

    public String getMahh() {
        return mahh;
    }

    public void setMahh(String mahh) {
        this.mahh = mahh;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public HoaDonBan(String mahdb, String mahh, String ngay, int sl) {

        this.mahdb = mahdb;
        this.mahh = mahh;
        this.ngay = ngay;
        this.sl = sl;
    }
}
