package com.example.app_pro.duantwo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.app_pro.duantwo.modelbaocao.AddHoaDonNhap_TwoActivity;

public class HienThiChiTietHangHoaNhapActivity extends AppCompatActivity {

    // MyPager myPager;
    ViewPager viewPager;
    int position=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_chi_tiet_hang_hoa_nhap);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setAdapter(new MyPager(getSupportFragmentManager()));
        position = getIntent().getIntExtra("hai",1);
        if(position==2){
            viewPager.setCurrentItem(position);
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.menuthem:
                startActivity(new Intent(HienThiChiTietHangHoaNhapActivity.this, NhapActivity.class));
                break;

            case R.id.menuhoadonnhap:
//                Toast.makeText(this, "HDN", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HienThiChiTietHangHoaNhapActivity.this, AddHoaDonNhap_TwoActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_sp, menu);
        getMenuInflater().inflate(R.menu.menu_them_hdn, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
