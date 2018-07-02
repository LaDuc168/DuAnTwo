package com.example.app_pro.duantwo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.example.app_pro.duantwo.HienThiChiTietHangHoaNhapActivity;
import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.model.HangHoaNhapfm;
import com.example.app_pro.duantwo.model.MyDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class SuaHangHoaActivity extends AppCompatActivity {
    EditText edtMa, edtTen, edtGhiChu;
    LiveButton btnCapNhat;
    HangHoaNhapfm hh = null;

    //Khởi tạo Database
    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_hang_hoa);
        init();
        hh = (HangHoaNhapfm) getIntent().getSerializableExtra("hh");
        setData();
        addEvent();
    }

    private void setData() {
        edtMa.setEnabled(false);
        edtMa.setText(hh.getMa());
        edtTen.setText(hh.getTen());
        edtGhiChu.setText(hh.getGhichu());
    }

    private void addEvent() {
        findViewById(R.id.btnHuyHH).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                POST_DATA(General.URL_SUA_HANG_HOA);

                boolean checkMa = SuaHangHoa();
                if (checkMa==true) {
                    Toast.makeText(SuaHangHoaActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SuaHangHoaActivity.this, HienThiChiTietHangHoaNhapActivity.class));
                    finish();
                } else {
                    Toast.makeText(SuaHangHoaActivity.this, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SuaHangHoaActivity.this, HienThiChiTietHangHoaNhapActivity.class));
                    finish();
                }
            }
        });
    }

//    private void POST_DATA(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(SuaHangHoaActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SuaHangHoaActivity.this, HienThiChiTietHangHoaNhapActivity.class));
//                    finish();
//                } else {
//                    Toast.makeText(SuaHangHoaActivity.this, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SuaHangHoaActivity.this, HienThiChiTietHangHoaNhapActivity.class));
//                    finish();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("ma", edtMa.getText().toString().trim());
//                map.put("ten", edtTen.getText().toString().trim());
//                map.put("ghichu", edtGhiChu.getText().toString().trim());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private boolean SuaHangHoa(){
        String ma=edtMa.getText().toString().trim();
        String ten=edtTen.getText().toString().trim();
        String ghichu=edtGhiChu.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put("mahanghoa", ma);
        contentValues.put("tenhanghoa", ten);
        contentValues.put("ghichu", ghichu);

        return sqLiteDatabase.update("hanghoa", contentValues, "mahanghoa=?", new String[]{
                ma
        })>0;
    }

    private void init() {
        edtMa = (EditText) findViewById(R.id.edtHHMa);
        edtTen = (EditText) findViewById(R.id.edtHHTen);
        edtGhiChu = (EditText) findViewById(R.id.edtHHGhiChu);
        btnCapNhat = (LiveButton) findViewById(R.id.btnCapNhatHH);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
