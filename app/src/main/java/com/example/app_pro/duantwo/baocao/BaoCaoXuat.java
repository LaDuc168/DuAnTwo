package com.example.app_pro.duantwo.baocao;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.app_pro.duantwo.adapterbaocao.BaoCaoNhapAdapter;
import com.example.app_pro.duantwo.adapterbaocao.BaoCaoXuatAdapter;
import com.example.app_pro.duantwo.model.MyDatabase;
import com.example.app_pro.duantwo.modelbaocao.BaoCaoNhapInstance;
import com.example.app_pro.duantwo.modelbaocao.BaoCaoXuatInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class BaoCaoXuat extends AppCompatActivity {

    BaoCaoXuatAdapter adapter;
    ArrayList<BaoCaoXuatInstance> listXuat;
    EditText edtNgayBatDau, edtNgayKetThuc;
    LiveButton btnBaoCaoNhapChitiet;
    ListView listVievNhap;
    TextView txtThongBaoXuat;
    String sDau = "";
    String ngayOkDau = "";
    String sCuoi = "";
    String ngayOkCuoi = "";

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_xuat);

        init();
        addEvent();
    }

    private void addEvent() {
        btnBaoCaoNhapChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngayBD = edtNgayBatDau.getText().toString().trim();
                String ngayKT = edtNgayKetThuc.getText().toString().trim();
                if (ngayBD.equals("") || ngayKT.equals("")) {
                    Toast.makeText(BaoCaoXuat.this, "Mời nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                POST_DATA(General.URL_BAO_CAO_XUAT);
                BaoCaoXuat();
            }
        });
        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(BaoCaoXuat.this);
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


                        ngayOkDau = YEAR_FORMAT + "-" + MONTH_FORMAT + "-" + DAY_FORMAT;
                        sDau = DAY_FORMAT + "-" + MONTH_FORMAT + "-" + YEAR_FORMAT;

//                        sDau = ngay + "-" + thang + "-" + nam;
//                        ngayOkDau = nam + "-" + thang + "-" + ngay;

                        edtNgayBatDau.setText(sDau);
                        dialog.dismiss();
                    }
                });
            }

        });
        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(BaoCaoXuat.this);
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


                        ngayOkCuoi = YEAR_FORMAT + "-" + MONTH_FORMAT + "-" + DAY_FORMAT;
                        sCuoi = DAY_FORMAT + "-" + MONTH_FORMAT + "-" + YEAR_FORMAT;

//                        sCuoi = ngay + "-" + thang + "-" + nam;
//                        ngayOkCuoi = nam + "-" + thang + "-" + ngay;
                        edtNgayKetThuc.setText(sCuoi);
                        dialog.dismiss();
                    }
                });
            }

        });
    }

    private void BaoCaoXuat() {
        String sql = "select h.tenhanghoa,nh.ngayxuat,nh.soluong,nh.ghichu from hanghoa h inner join xuathang nh on\n" +
                "h.mahanghoa=nh.mahang where nh.ngayxuat between '" + ngayOkDau + "' and '" + ngayOkCuoi + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

            listXuat.clear();
        if (cursor.getCount() > 0) {
//            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            txtThongBaoXuat.setVisibility(View.GONE);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String ten = cursor.getString(0);
                String ngay = cursor.getString(1);
                int sl = cursor.getInt(2);
                String ghichu = cursor.getString(3);

                listXuat.add(new BaoCaoXuatInstance(ten, ngay, sl, ghichu));

            }
            adapter.notifyDataSetChanged();

        } else {
            adapter.notifyDataSetChanged();
            txtThongBaoXuat.setVisibility(View.VISIBLE);
            txtThongBaoXuat.setText("Không có mặt hàng nào xuất từ ngày : " +
                    edtNgayBatDau.getText().toString().trim() + " đến ngày: " +
                    edtNgayKetThuc.getText().toString().trim());
        }


    }

//    private void POST_DATA(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray arr = new JSONArray(response);
//                    listXuat.clear();
//                    if (arr.length() == 0) {
//                        adapter.notifyDataSetChanged();
//                        txtThongBaoXuat.setVisibility(View.VISIBLE);
//                        txtThongBaoXuat.setText("Không có mặt hàng nào xuất từ ngày : " +
//                                edtNgayBatDau.getText().toString().trim() + " đến ngày: " +
//                                edtNgayKetThuc.getText().toString().trim());
//                        return;
//                    }
//                    txtThongBaoXuat.setVisibility(View.GONE);
//                    for (int i = 0; i < arr.length(); i++) {
//                        try {
//                            JSONObject obj = arr.getJSONObject(i);
//                            listXuat.add(new BaoCaoXuatInstance(
//                                    obj.getString("ten"),
//                                    obj.getString("ngay"),
//                                    obj.getInt("sl"),
//                                    obj.getString("ghichu")
//                            ));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(BaoCaoXuat.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("ngaybd", ngayOkDau);
//                map.put("ngaykt", ngayOkCuoi);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

    private void init() {
        txtThongBaoXuat = (TextView) findViewById(R.id.txtThongBaoXuat);
        edtNgayBatDau = (EditText) findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = (EditText) findViewById(R.id.edtNgayKetThuc);
        btnBaoCaoNhapChitiet = (LiveButton) findViewById(R.id.btnBaoCaoNhapChiTiet);
        listXuat = new ArrayList<>();
        adapter = new BaoCaoXuatAdapter(this, listXuat);
        listVievNhap = (ListView) findViewById(R.id.listViewBaoCaoNhap);
        listVievNhap.setAdapter(adapter);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);
    }
}
