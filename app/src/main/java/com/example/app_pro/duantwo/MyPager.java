package com.example.app_pro.duantwo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class MyPager extends FragmentStatePagerAdapter {


    String[] mangTitle = {"Hàng Hóa Nhập", "Hóa Đơn Nhập"};
    HangHoaFragment hangHoaFragment;
    HoaDonNhapFragment hoaDonNhapFragment;

    public MyPager(FragmentManager fm) {
        super(fm);
        hangHoaFragment = new HangHoaFragment();
        hoaDonNhapFragment = new HoaDonNhapFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) return hangHoaFragment;
        else if(position==1) return hoaDonNhapFragment;
        return null;
    }

    @Override
    public int getCount() {
        return mangTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mangTitle[position];
    }
}

