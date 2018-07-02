package com.example.app_pro.duantwo.model;

import java.io.Serializable;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class HangHoaNhapfm implements Serializable{
    private String ma;
    private String ten;
    private String ghichu;

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public HangHoaNhapfm(String ma, String ten, String ghichu) {

        this.ma = ma;
        this.ten = ten;
        this.ghichu = ghichu;
    }
}
