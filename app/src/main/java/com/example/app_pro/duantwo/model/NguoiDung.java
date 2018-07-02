package com.example.app_pro.duantwo.model;

/**
 * Created by LaVanDuc on 1/29/2018.
 */

public class NguoiDung {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NguoiDung(String username, String password) {

        this.username = username;
        this.password = password;
    }
}
