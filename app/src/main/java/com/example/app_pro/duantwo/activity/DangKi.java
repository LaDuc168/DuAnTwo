package com.example.app_pro.duantwo.activity;

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
import com.example.app_pro.duantwo.R;

import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class DangKi extends AppCompatActivity {
    EditText edttenTK, edtPassTK;
    LiveButton btnDK, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        init();
        addEvent();
    }

    private void addEvent() {
        findViewById(R.id.btnHuyDangKi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edttenTK.getText().toString().trim();
                String pass = edtPassTK.getText().toString().trim();
                if (name.equals("") || pass.equals("")) {
                    Toast.makeText(DangKi.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                KiemTraTrung(General.URL_KIEM_TRA_TRUNG);
            }
        });
    }

    private void KiemTraTrung(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("khongtrung")) {
                    POST_DATA(General.URL_DANG_KI);
                } else {
                    Toast.makeText(DangKi.this, "Trùng tên tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKi.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", edttenTK.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void POST_DATA(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("ok")) {
                    Toast.makeText(DangKi.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DangKi.this, "Không thể đăng kí", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangKi.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", edttenTK.getText().toString().trim());
                map.put("pass", edtPassTK.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void init() {
        edttenTK = (EditText) findViewById(R.id.edtDKTenTaiKhoan);
        edtPassTK = (EditText) findViewById(R.id.edtDKPassTaiKhoan);
        btnDK = (LiveButton) findViewById(R.id.btnDangKi);

    }
}
