package com.example.app_pro.duantwo.modelbaocao;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.app_pro.duantwo.General;
import com.example.app_pro.duantwo.HienThiChiTietHangHoaNhapActivity;
import com.example.app_pro.duantwo.MaHangAdapter;
import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.model.MyDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class AddHoaDonNhap_TwoActivity extends AppCompatActivity {

    EditText edtMaHD, edtNgayNhap, edtSoLuong, edtGhiChu;
    LiveButton btnThemCHiTiet, btnHuyChiTiet;
    Spinner spinnerHDN;
    String maHang = "";
    String s = "";
    String ngayOk = "";
    String MAHANGHOA = "";
    ArrayList<String> mangMaHH;
    MaHangAdapter adapterHH;

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_nhap_two);
        inti();
//        getMaHangHoa(General.URL_GET_MA);
GetMaHH();

        addEvent();
    }

    private void addEvent() {

        spinnerHDN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MAHANGHOA = mangMaHH.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnHuyChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnThemCHiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MAHANGHOA = (String) spinnerHDN.getSelectedItem();
                if (MAHANGHOA.equals("")) {
                    Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Bạn chưa chọn mã hàng hóa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String maHD = edtMaHD.getText().toString().trim();
                String ngayNhap = edtNgayNhap.getText().toString().trim();
                String msLuong = edtSoLuong.getText().toString().trim();
                if (maHD.equals("") || ngayNhap.equals("") || msLuong.equals("")) {
                    Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }


//                AddHangHoaCHiTietTwo(General.URL_ADD_DETAILS);
                boolean check = ThemHHNhap();
                if (check==true) {
                    Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddHoaDonNhap_TwoActivity.this, HienThiChiTietHangHoaNhapActivity.class);
                    intent.putExtra("hai",2);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edtNgayNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddHoaDonNhap_TwoActivity.this);
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
                        edtNgayNhap.setText(s);
                        dialog.dismiss();
                    }
                });
            }

        });
    }

    private boolean ThemHHNhap(){
        String mahd=edtMaHD.getText().toString();
        int sl=Integer.parseInt(edtSoLuong.getText().toString());
        String ghichu=edtGhiChu.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("manhap", mahd);
        contentValues.put("mahang",MAHANGHOA);
        contentValues.put("ngaynhap", ngayOk);
        contentValues.put("soluong", sl);
        contentValues.put("ghichu", ghichu);

        return sqLiteDatabase.insert("nhaphang", null, contentValues)>0;
    }

//    public void AddHangHoaCHiTietTwo(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddHoaDonNhap_TwoActivity.this, HienThiChiTietHangHoaNhapActivity.class);
//                    intent.putExtra("hai",2);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Không thêm được", Toast.LENGTH_SHORT).show();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("mahd", edtMaHD.getText().toString());
//                map.put("mahh", MAHANGHOA);
//                map.put("ngaynhap", ngayOk);
//                map.put("soluong", edtSoLuong.getText().toString());
//                map.put("ghichu", edtGhiChu.getText().toString());
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    //Lấy mã hàng

//    public void getMaHangHoa(String url) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        mangMaHH.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject obj = response.getJSONObject(i);
//                                mangMaHH.add(obj.getString("id"));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }
//                        adapterHH.notifyDataSetChanged();
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(AddHoaDonNhap_TwoActivity.this, "Lỗi mạng!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
////        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }

    private void  GetMaHH(){
        Cursor cursor=sqLiteDatabase.rawQuery("select mahanghoa from hanghoa",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String ma=cursor.getString(0);
            mangMaHH.add(ma);
        }
        adapterHH.notifyDataSetChanged();

    }

    private void inti() {
        spinnerHDN = (Spinner) findViewById(R.id.spinnerHDN);
        edtGhiChu = (EditText) findViewById(R.id.edtHDNGhiChu);
        edtMaHD = (EditText) findViewById(R.id.edtHDChiTiet);
        edtNgayNhap = (EditText) findViewById(R.id.edtNgayNhap);
        edtSoLuong = (EditText) findViewById(R.id.edtSoLuong);
        edtGhiChu = (EditText) findViewById(R.id.edtHDNGhiChu);
        btnThemCHiTiet = (LiveButton) findViewById(R.id.btnThemChiTiet);
        btnHuyChiTiet = (LiveButton) findViewById(R.id.btnHuyChiTiet);
        mangMaHH = new ArrayList<>();
        adapterHH = new MaHangAdapter(this, mangMaHH);
        spinnerHDN.setAdapter(adapterHH);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
