package com.example.app_pro.duantwo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_pro.duantwo.activity.HoaDonBanFragmentDemo;
import com.example.app_pro.duantwo.baocao.BaoCaoNhap;
import com.example.app_pro.duantwo.baocao.BaoCaoXuat;
import com.example.app_pro.duantwo.model.MyDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

public class BaoCaoActivity extends AppCompatActivity {

    EditText edtNgayBaoCao;
    TextView txtThongBaoChung;
    LiveButton btnThongKe;
    ListView listView;
    HangHoaAdapter adapter;
    ArrayList<HangHoaTon> listHangHoa;
    ProgressDialog title;
    String s = "";
    String ngayOk = "2016-5-6";

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);
        init();
        addEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menubaocao:
                final Dialog dialog = new Dialog(BaoCaoActivity.this);
                dialog.setTitle("Báo Cáo Nhập Xuất");
                dialog.setContentView(R.layout.dialog_bao_cao);
                LiveButton btnBaoCaoNhap = dialog.findViewById(R.id.btnBaoCaoNhap);
                LiveButton btnBaoCaoXuat = dialog.findViewById(R.id.btnBaoCaoXuat);

                dialog.show();
                //Báo cáo nhập
                btnBaoCaoNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BaoCaoActivity.this, BaoCaoNhap.class);
                        startActivity(intent);
                    }
                });

                //Báo cáo xuất
                btnBaoCaoXuat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentOne = new Intent(BaoCaoActivity.this, BaoCaoXuat.class);
                        startActivity(intentOne);
                    }
                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bao_cao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvent() {
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ngayBaoCao = edtNgayBaoCao.getText().toString().trim();

                if (!ngayBaoCao.equals("")) {
                    title = ProgressDialog.
                            show(BaoCaoActivity.this, "Dialog tìm kiếm hàng tồn", "Loading...");
                    new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {

                            title.dismiss();

//                            POST_DATA(General.URL_BAO_CAO);
                            HangTon(ngayOk);

                        }
                    }.start();
                } else {
                    Toast.makeText(BaoCaoActivity.this, "nhập vào ngày", Toast.LENGTH_SHORT).show();
                }
            }
        });
        edtNgayBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(BaoCaoActivity.this);
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
                        edtNgayBaoCao.setText(s);
                        dialog.dismiss();
                    }
                });
            }

        });
    }

    public String ChuyenNgay(String ngayt) {
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

    private void HangTon(String ngayl) {

//        Toast.makeText(this, "Ngay="+ngay, Toast.LENGTH_SHORT).show();
        String sql = "select hanghoa.tenhanghoa,(a.TongNhap-COALESCE(b.TongXuat,0)) as tonkho\n" +
                "from\n" +
                "\n" +
                "(select mahang,sum(soluong) as TongNhap\n" +
                "from nhaphang\n" +
                "where ngaynhap <= '" + ngayl + "'\n" +
                "group by mahang) a\n" +
                "\n" +
                "left outer join\n" +
                "\n" +
                "(select mahang,sum(soluong) as TongXuat\n" +
                "from xuathang\n" +
                "where ngayxuat <= '" + ngayl + "'\n" +
                "group by mahang) b \n" +
                "\n" +
                "on a.mahang=b.mahang\n" +
                "join hanghoa on hanghoa.mahanghoa=a.mahang";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

            listHangHoa.clear();
        if (cursor.getCount() > 0) {
//            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
            txtThongBaoChung.setVisibility(View.GONE);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String ten = cursor.getString(0);
                int ton = cursor.getInt(1);
                if (ton < 1) continue;
                listHangHoa.add(new HangHoaTon(ten, ton));

            }
            Collections.sort(listHangHoa, new Comparator<HangHoaTon>() {
                @Override
                public int compare(HangHoaTon t1, HangHoaTon t2) {
                    return t2.getSoLuong() - t1.getSoLuong();
                }
            });
            adapter.notifyDataSetChanged();

        } else {

            adapter.notifyDataSetChanged();

            txtThongBaoChung.setVisibility(View.VISIBLE);
            txtThongBaoChung.setText("Không có hàng tồn trong ngày : " +
                    edtNgayBaoCao.getText().toString().trim());
        }



    }

//    private void POST_DATA(String url) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray arr = new JSONArray(response);
//                    listHangHoa.clear();
//                    if(arr.length()==0){
//                        adapter.notifyDataSetChanged();
//                        txtThongBaoChung.setVisibility(View.VISIBLE);
//                        txtThongBaoChung.setText("Không có hàng tồn trong ngày : "+
//                                edtNgayBaoCao.getText().toString().trim());
//                        return;
//                    }
//                    txtThongBaoChung.setVisibility(View.GONE);
//
//                    for (int i = 0; i < arr.length(); i++) {
//                        try {
//                            JSONObject obj = arr.getJSONObject(i);
//                            listHangHoa.add(new HangHoaTon(
//                                    obj.getString("ten"),
//                                    obj.getInt("tonkho")
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
//                        Toast.makeText(BaoCaoActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("ngay", ngayOk);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }


    private void init() {
        txtThongBaoChung = (TextView) findViewById(R.id.txtThongBaoChung);

        listHangHoa = new ArrayList<>();
        edtNgayBaoCao = (EditText) findViewById(R.id.edtNgayBaoCao);
        btnThongKe = (LiveButton) findViewById(R.id.btnThongKe);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new HangHoaAdapter(BaoCaoActivity.this, listHangHoa);
        listView.setAdapter(adapter);

        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);

    }
}
