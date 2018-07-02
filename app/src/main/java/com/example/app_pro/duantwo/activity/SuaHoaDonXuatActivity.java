package com.example.app_pro.duantwo.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_pro.duantwo.General;
import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.model.HoaDonBan;
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class SuaHoaDonXuatActivity extends AppCompatActivity {

    EditText edtMaHD, edtMH, edtNgay, edtSL,edtGhiChu;
    LiveButton btnXuatSua, btnXuatHuy;
    HoaDonBan hdb;
    String s = "";
    String ngayOk = "";

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_hoa_don_xuat);
        init();
        hdb = (HoaDonBan) getIntent().getSerializableExtra("xuat");
        setData();
        addEvent();
    }

    private void setData() {
        edtMaHD.setText(hdb.getMahdb());
        edtMaHD.setEnabled(false);
        edtMH.setText(hdb.getMahh());
        edtMH.setEnabled(false);
        edtNgay.setText(ChuyenNgay(hdb.getNgay()));
        ngayOk=hdb.getNgay();
        edtSL.setText(hdb.getSl() + "");
        edtGhiChu.setText(hdb.getGhichu());

    }
    public  String ChuyenNgay(String ngayt) {
        String ngay = "", thang = "", nam = "";
        String[] split = ngayt.split("-");
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                nam = split[i];
            }
            if (i == 1) {
                thang = split[i];
            }
            if (i == 2) {
                ngay = split[i];
            }

        }
        return ngay + "-" + thang + "-" + nam;
    }

    private void addEvent() {
        findViewById(R.id.btnXuatHuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnXuatSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                POST_DATA(General.URL_SUA_HOA_DON_XUAT);
                boolean check = SuaHDXuat();
                if (check==true) {
                    Toast.makeText(SuaHoaDonXuatActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SuaHoaDonXuatActivity.this, HoaDonBanFragmentDemo.class));
                    finish();
                } else {
                    Toast.makeText(SuaHoaDonXuatActivity.this, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SuaHoaDonXuatActivity.this, HoaDonBanFragmentDemo.class));
                    finish();
                }

            }
        });
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SuaHoaDonXuatActivity.this);
                dialog.setTitle("Dialog chọn ngày-tháng-năm");
                dialog.setContentView(R.layout.dialog_item_ngay_thang);
                dialog.show();
                LiveButton btnChonNgay = dialog.findViewById(R.id.btnChonNgay);
                final DatePicker datePicker = dialog.findViewById(R.id.datePickerOK);
                btnChonNgay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int nam = datePicker.getYear();
                        int thang = datePicker.getMonth() + 1;
                        int ngay = datePicker.getDayOfMonth();

                        String YEAR_FORMAT="";
                        String MONTH_FORMAT="";
                        String DAY_FORMAT="";
                        YEAR_FORMAT=nam+"";
                        MONTH_FORMAT=thang+"";
                        DAY_FORMAT=ngay+"";

                        if(ngay<10){
                            DAY_FORMAT="0"+ngay;
                        }
                        if(thang<10){
                            MONTH_FORMAT="0"+thang;
                        }


                        ngayOk = YEAR_FORMAT + "-" + MONTH_FORMAT + "-" + DAY_FORMAT;
                        s = DAY_FORMAT + "-" + MONTH_FORMAT + "-" + YEAR_FORMAT;

//                        s = ngay + "-" + thang + "-" + nam;
//                        ngayOk=nam + "-" + thang + "-" + ngay;
                        edtNgay.setText(s);
                        dialog.dismiss();
                    }
                });
            }

        });
    }
    private boolean SuaHDXuat(){
        String mahd=edtMaHD.getText().toString().trim();
        String mahh=edtMH.getText().toString().trim();
        int sl=Integer.parseInt(edtSL.getText().toString().trim());
        String ghichu=edtGhiChu.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put("maxuat", mahd);
        contentValues.put("mahang", mahh);
        contentValues.put("ngayxuat", ngayOk);
        contentValues.put("soluong", sl);
        contentValues.put("ghichu", ghichu);

        return sqLiteDatabase.update("xuathang", contentValues, "maxuat=? and mahang=?", new String[]{
                mahd,mahh
        })>0;
    }

//    private void POST_DATA(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(SuaHoaDonXuatActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SuaHoaDonXuatActivity.this, HoaDonBanFragmentDemo.class));
//                    finish();
//                } else {
//                    Toast.makeText(SuaHoaDonXuatActivity.this, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SuaHoaDonXuatActivity.this, HoaDonBanFragmentDemo.class));
//                    finish();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(SuaHoaDonXuatActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("mahd", edtMaHD.getText().toString().trim());
//                map.put("mahh", edtMH.getText().toString().trim());
//                map.put("ngay", ngayOk);
//                map.put("sl", edtSL.getText().toString().trim());
//                map.put("ghichu", edtGhiChu.getText().toString().trim());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void init() {
        edtMaHD = (EditText) findViewById(R.id.edtXuatMaHD);
        edtMH = (EditText) findViewById(R.id.edtXuatMaHang);
        edtNgay = (EditText) findViewById(R.id.edtXuatNgay);
        edtSL = (EditText) findViewById(R.id.edtXuatSoLuong);
        edtGhiChu= (EditText) findViewById(R.id.edtSHDXGhiChu);
        btnXuatSua = (LiveButton) findViewById(R.id.btnXuatSua);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
