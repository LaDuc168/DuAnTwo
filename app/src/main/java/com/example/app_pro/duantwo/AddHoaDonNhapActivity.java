package com.example.app_pro.duantwo;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class AddHoaDonNhapActivity extends AppCompatActivity {

    EditText edtMaHD,edtMaHang,edtNgayNhap,edtSoLuong,edtGhiChu;
    LiveButton btnThemCHiTiet,btnHuyChiTiet;
    String maHang="";
    String s="";
    String ngayOk="";

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_nhap);
        inti();
        maHang=getIntent().getStringExtra("MaHang");
        edtMaHang.setText(maHang);
        edtMaHang.setEnabled(false);
        addEvent();
    }

    private void addEvent() {
        btnHuyChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemCHiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maHD=edtMaHD.getText().toString().trim();
                String ngayNhap=edtNgayNhap.getText().toString().trim();
                String msLuong=edtSoLuong.getText().toString().trim();
                if(maHD.equals("")|| ngayNhap.equals("")||msLuong.equals("")){
                    Toast.makeText(AddHoaDonNhapActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                AddHangHoaCHiTiet(General.URL_ADD_DETAILS);

                boolean check = ThemHDNhap();

                if (check==true) {
                    Toast.makeText(AddHoaDonNhapActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AddHoaDonNhapActivity.this,HienThiChiTietHangHoaNhapActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddHoaDonNhapActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtNgayNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(AddHoaDonNhapActivity.this);
                dialog.setTitle("Dialog chọn ngày-tháng-năm");
                dialog.setContentView(R.layout.dialog_item_ngay_thang);
                dialog.show();
                LiveButton btnChonNgay=dialog.findViewById(R.id.btnChonNgay);
                final DatePicker datePicker=dialog.findViewById(R.id.datePickerOK);
                btnChonNgay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int nam = datePicker.getYear();
                        int thang = datePicker.getMonth()+1;
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
                        edtNgayNhap.setText(s);
                        dialog.dismiss();
                    }
                });
            }

        });
    }

//    private boolean CheckMa(String mahh){
//        Cursor cursor=sqLiteDatabase.rawQuery("select * from nhaphang where manhap='"+mahh+"'",null);
//        if(cursor.getCount()>0)
//        {
//            return false;
//        }
//        return true;
//    }

    private boolean ThemHDNhap(){
        String mahd=edtMaHD.getText().toString();
        String mahh=edtMaHang.getText().toString();
        int sl=Integer.parseInt(edtSoLuong.getText().toString());
        String ghichu=edtGhiChu.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("manhap", mahd);
        contentValues.put("mahang", mahh);
        contentValues.put("ngaynhap", ngayOk);
        contentValues.put("soluong", sl);
        contentValues.put("ghichu", ghichu);


        return sqLiteDatabase.insert("nhaphang", null, contentValues)>0;

    }
//    public void AddHangHoaCHiTiet(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(AddHoaDonNhapActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(AddHoaDonNhapActivity.this,HienThiChiTietHangHoaNhapActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(AddHoaDonNhapActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(AddHoaDonNhapActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String ,String> map=new HashMap<>();
//                map.put("mahd",edtMaHD.getText().toString());
//                map.put("mahh",edtMaHang.getText().toString());
//                map.put("ngaynhap",ngayOk);
//                map.put("soluong",edtSoLuong.getText().toString());
//                map.put("ghichu",edtGhiChu.getText().toString());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void inti() {
        edtGhiChu= (EditText) findViewById(R.id.edtHDNGhiChu);
        edtMaHD= (EditText) findViewById(R.id.edtHDChiTiet);
        edtMaHang= (EditText) findViewById(R.id.edtMaHangChiTiet);
        edtNgayNhap= (EditText) findViewById(R.id.edtNgayNhap);
        edtSoLuong= (EditText) findViewById(R.id.edtSoLuong);
        edtGhiChu= (EditText) findViewById(R.id.edtHDNGhiChu);
        btnThemCHiTiet= (LiveButton) findViewById(R.id.btnThemChiTiet);
        btnHuyChiTiet= (LiveButton) findViewById(R.id.btnHuyChiTiet);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
