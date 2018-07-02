package com.example.app_pro.duantwo.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.app_pro.duantwo.MainActivity;
import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class DoiMatKhau extends AppCompatActivity {
    EditText edtTen, edtPass;
    LiveButton btnNext;
    String name = "";
    String pass = "";
    GiaoDien giaoDien;

    //Khoi tạo database
    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    public void CapNhatGiaoDien(GiaoDien gd) {
        giaoDien = gd;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        init();
        name = getIntent().getStringExtra("name");
        pass = getIntent().getStringExtra("pass");
        setData();
        addEvent();
    }

    private void setData() {
        edtTen.setText(name);
        edtPass.setText(pass);
    }

    private void addEvent() {
        findViewById(R.id.btnHuyDoiMK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoiMatKhau.this, MainActivity.class));
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtTen.getText().toString().trim();
                name = ten;
                String mk = edtPass.getText().toString().trim();
                pass = mk;
                if (ten.equals("") || mk.equals("")) {
                    Toast.makeText(DoiMatKhau.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                KiemTraTrung(General.URL_KIEM_TRA_TK);
                boolean check = CheckConnect(ten, mk);

                if (check == true) {
                    final Dialog dialog = new Dialog(DoiMatKhau.this);
                    dialog.setTitle("Dialog đổi mật khâu");
                    dialog.setContentView(R.layout.dialog_doi_mat_khau);
                    dialog.show();
                    final EditText mot = dialog.findViewById(R.id.edtMKMoiDialog);
                    final EditText hai = dialog.findViewById(R.id.edtMKMoiLaiDialog);
                    LiveButton btnOk = dialog.findViewById(R.id.btnDoiMKDialog);
                    LiveButton huy = dialog.findViewById(R.id.btnHuyDialog);


                    huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String mk1 = mot.getText().toString().trim();
                            String mk2 = hai.getText().toString().trim();
                            if (mk1.equals(mk2)) {
//                                DoiMK(General.URL_DOI_MAT_KHAU, mk2);
                                boolean chẹkpass = UpdateMatKhau(name, mk1);
                                if (chẹkpass == true) {
                                    Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DoiMatKhau.this, MainActivity.class);

                                    startActivity(intent);
//                    giaoDien.SetTaiKhoan("admin","");
                                    finish();
                                } else {
                                    Toast.makeText(DoiMatKhau.this, "Không đổi được", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                Toast.makeText(DoiMatKhau.this, "Mật khẩu nhập không khớp", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

                } else {
                    Toast.makeText(DoiMatKhau.this, "Tài khoản không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private boolean UpdateMatKhau(String name, String pass) {
//        String sql="update nguoidung set password='"+pass+"' where username='"+name+"'";
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", pass);

        return sqLiteDatabase.update("nguoidung", contentValues, "username=?", new String[]{
                name
        }) > 0;
    }

    private boolean CheckConnect(String ma, String pass) {
        String sql = "select username from nguoidung where username='" + ma + "' and password='" + pass + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,
                null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

//    private void KiemTraTrung(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    final Dialog dialog = new Dialog(DoiMatKhau.this);
//                    dialog.setTitle("Dialog đổi mật khâu");
//                    dialog.setContentView(R.layout.dialog_doi_mat_khau);
//                    dialog.show();
//                    final EditText mot = dialog.findViewById(R.id.edtMKMoiDialog);
//                    final EditText hai = dialog.findViewById(R.id.edtMKMoiLaiDialog);
//                    LiveButton btnOk = dialog.findViewById(R.id.btnDoiMKDialog);
//                    LiveButton huy = dialog.findViewById(R.id.btnHuyDialog);
//
//
//                    huy.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.dismiss();
//                        }
//                    });
//                    btnOk.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            String mk1 = mot.getText().toString().trim();
//                            String mk2 = hai.getText().toString().trim();
//                            if (mk1.equals(mk2)) {
//                                DoiMK(General.URL_DOI_MAT_KHAU, mk2);
//                            } else {
//                                Toast.makeText(DoiMatKhau.this, "Mật khẩu nhập không khớp", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }
//                    });
//
//                } else {
//                    Toast.makeText(DoiMatKhau.this, "Tài khoản không đúng", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DoiMatKhau.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("name", edtTen.getText().toString().trim());
//                map.put("pass", edtPass.getText().toString().trim());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

//    private void DoiMK(String url, final String mk) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(DoiMatKhau.this, MainActivity.class);
//
//                    startActivity(intent);
////                    giaoDien.SetTaiKhoan("admin","");
//                    finish();
//                } else {
//                    Toast.makeText(DoiMatKhau.this, "Không đổi được", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(DoiMatKhau.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("name", edtTen.getText().toString().trim());
//                map.put("pass", mk);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void init() {
        edtTen = (EditText) findViewById(R.id.edtDoiTenTK);
        edtPass = (EditText) findViewById(R.id.edtDoiPassTK);
        btnNext = (LiveButton) findViewById(R.id.btnNext);

        //Khởi tạo Database
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);

    }
}
