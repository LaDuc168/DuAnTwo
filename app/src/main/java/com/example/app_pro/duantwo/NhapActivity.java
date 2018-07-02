package com.example.app_pro.duantwo;

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
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class NhapActivity extends AppCompatActivity {

    EditText edtMaHangHoa, edtTenHangHoa, edtGhiChu;
    LiveButton btnThem, btnHuyNhap;
    String maHang="";

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap);
        inti();
        addEvent();
    }

    private void addEvent() {
        btnHuyNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String
                String maH=edtMaHangHoa.getText().toString();
                String tenH=edtTenHangHoa.getText().toString();
                String gChu=edtGhiChu.getText().toString();
                maHang=maH;
                if(maH.equals("")|| tenH.equals("")|| gChu.equals(""))
                {
                    Toast.makeText(NhapActivity.this, "Nhập dầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                POST_DATA(General.URL_ADD);
                boolean check = ThemHangHoa(maH, tenH, gChu);

                if (check==true) {
                    Toast.makeText(NhapActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(NhapActivity.this,AddHoaDonNhapActivity.class);
                    intent.putExtra("MaHang",maHang);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NhapActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean ThemHangHoa(String mah,String tenh,String ghichu){
        ContentValues contentValues = new ContentValues();
        contentValues.put("mahanghoa", mah);
        contentValues.put("tenhanghoa", tenh);
        contentValues.put("ghichu", ghichu);

       return sqLiteDatabase.insert("hanghoa", null, contentValues)>0;
    }

//    public void POST_DATA(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(NhapActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(NhapActivity.this,AddHoaDonNhapActivity.class);
//                    intent.putExtra("MaHang",maHang);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(NhapActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(NhapActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("mah",edtMaHangHoa.getText().toString().trim());
//                map.put("tenh",edtTenHangHoa.getText().toString().trim());
//                map.put("ghichuh",edtGhiChu.getText().toString().trim());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void inti() {
        edtMaHangHoa = (EditText) findViewById(R.id.edtMaHang);
        edtTenHangHoa = (EditText) findViewById(R.id.edtTenHangHoa);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        btnThem = (LiveButton) findViewById(R.id.btnThem);
        btnHuyNhap = (LiveButton) findViewById(R.id.btnHuyNhap);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
