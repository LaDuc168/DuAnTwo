package com.example.app_pro.duantwo;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_pro.duantwo.activity.HoaDonBanFragmentDemo;
import com.example.app_pro.duantwo.model.MyDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

import static android.R.attr.id;

public class AddHoaDonXuatActivity extends AppCompatActivity {

    EditText edtMaHDXuat, edtNgayXuat, edtSoLuongXuat, edtGhiChu;
    LiveButton btnThemXuat, btnHuyXuat;
    Spinner spinnerMaHang;
    ArrayList<String> arrMaHang;
    MaHangAdapter adapterMaHang;
    String maHangHoa = "";
    String s = "";
    String ngayOk = "";

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_xuat);
        init();
        addEvent();
    }

    private void addEvent() {
        btnHuyXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maHD = edtMaHDXuat.getText().toString();
                String ngay = edtNgayXuat.getText().toString();
                if (maHD.equals("") || ngay.equals("")) {
                    Toast.makeText(AddHoaDonXuatActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    return;
                }
//                ThemHangXuat(General.URL_ADD_XUAT);
                boolean check = ThemHHXuat();
                if (check==true) {
                    Toast.makeText(AddHoaDonXuatActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddHoaDonXuatActivity.this, HoaDonBanFragmentDemo.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddHoaDonXuatActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
                }

            }
        });
        spinnerMaHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maHangHoa = arrMaHang.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edtNgayXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddHoaDonXuatActivity.this);
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
//                        ngayOk = nam + "-" + thang + "-" + ngay;
                        edtNgayXuat.setText(s);
                        dialog.dismiss();
                    }
                });
            }

        });

    }

//    public void getJSON(String url) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        arrMaHang.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject obj = response.getJSONObject(i);
//                                arrMaHang.add(obj.getString("id"));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                        adapterMaHang.notifyDataSetChanged();
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
////        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }

    private void GetMaHang() {
        Cursor cursor = sqLiteDatabase.rawQuery("select mahanghoa from hanghoa", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String ma = cursor.getString(0);
            arrMaHang.add(ma);

        }
        adapterMaHang.notifyDataSetChanged();
    }

//    public void ThemHangXuat(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(AddHoaDonXuatActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddHoaDonXuatActivity.this, HoaDonBanFragmentDemo.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(AddHoaDonXuatActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(AddHoaDonXuatActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
////                map.put();
//                map.put("mahdx", edtMaHDXuat.getText().toString());
//                map.put("mahhx", maHangHoa);
//                map.put("ngayxuat", ngayOk);
//                map.put("soluongxuat", edtSoLuongXuat.getText().toString());
//                map.put("ghichu", edtGhiChu.getText().toString());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void init() {
        arrMaHang = new ArrayList<>();
        edtGhiChu = (EditText) findViewById(R.id.edtHDXGhiChu);
        edtMaHDXuat = (EditText) findViewById(R.id.edtMaHDXuat);
        edtNgayXuat = (EditText) findViewById(R.id.edtNgayNhapXuat);
        edtSoLuongXuat = (EditText) findViewById(R.id.edtSoLuongXuat);
        btnThemXuat = (LiveButton) findViewById(R.id.btnThemXuat);
        btnHuyXuat = (LiveButton) findViewById(R.id.btnHuyXuat);
        spinnerMaHang = (Spinner) findViewById(R.id.spinnerMaHangXuat);
//        getJSON(General.URL_GET_MA);

        adapterMaHang = new MaHangAdapter(this, arrMaHang);
        spinnerMaHang.setAdapter(adapterMaHang);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
        GetMaHang();
    }


    private boolean ThemHHXuat(){
        String mx=edtMaHDXuat.getText().toString();
        int sl=Integer.parseInt(edtSoLuongXuat.getText().toString());
        String ghichu=edtGhiChu.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("maxuat", mx);
        contentValues.put("mahang", maHangHoa);
        contentValues.put("ngayxuat", ngayOk);
        contentValues.put("soluong", sl);
        contentValues.put("ghichu", ghichu);

        return sqLiteDatabase.insert("xuathang", null, contentValues)>0;
    }

}
