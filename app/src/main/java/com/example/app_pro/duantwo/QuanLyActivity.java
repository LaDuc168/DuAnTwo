package com.example.app_pro.duantwo;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app_pro.duantwo.activity.HoaDonBanFragmentDemo;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class QuanLyActivity extends AppCompatActivity implements OnMenuSelectedListener {
    CircleMenu menu;
    public static final int NHAP = 0;
    public static final int XUAT = 1;
    public static final int BAOCAO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        init();
    }

    private void init() {
        menu = (CircleMenu) findViewById(R.id.menu);
//Luc dau hien thi facebook khi click thi hien thi viber
        menu.setMainMenu(Color.BLUE, R.drawable.home, R.drawable.back);
        menu.addSubMenu(Color.GREEN, R.drawable.nhapkho);//0
        menu.addSubMenu(Color.CYAN, R.drawable.xuatkho);//1
        menu.addSubMenu(Color.RED, R.drawable.baocao);//1
        menu.setOnMenuSelectedListener(this);

    }

    @Override
    public void onMenuSelected(int i) {
        switch (i) {
            case NHAP: //0
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(QuanLyActivity.this, HienThiChiTietHangHoaNhapActivity.class);
                        startActivity(intent);
                    }
                }.start();

                break;
            case XUAT: //1
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Intent intent1 = new Intent(QuanLyActivity.this, HoaDonBanFragmentDemo.class);
                        startActivity(intent1);
                    }
                }.start();
                break;
            case BAOCAO: //1
                new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        Intent intent2 = new Intent(QuanLyActivity.this, BaoCaoActivity.class);
                        startActivity(intent2);
                    }
                }.start();
                break;
        }
    }
}
