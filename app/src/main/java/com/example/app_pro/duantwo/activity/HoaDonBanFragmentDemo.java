package com.example.app_pro.duantwo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_pro.duantwo.General;
import com.example.app_pro.duantwo.AddHoaDonXuatActivity;
import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.adapter.HoaDonBanAdapter;
import com.example.app_pro.duantwo.model.HoaDonBan;
import com.example.app_pro.duantwo.model.HoaDonNhap;
import com.example.app_pro.duantwo.model.MyDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HoaDonBanFragmentDemo extends AppCompatActivity {

    ListView listViewXuat;
    ArrayList<HoaDonBan> listBan;
    HoaDonBanAdapter adapter;
    ProgressDialog title;

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_ban_fragment_demo);
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menuthem == item.getItemId()) {
            startActivity(new Intent(HoaDonBanFragmentDemo.this, AddHoaDonXuatActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_sp, menu);
        getMenuInflater().inflate(R.menu.menu_search_ma_hoa_don_xuat, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menusearchHDX).getActionView();
        searchView.setQueryHint("Nhập mã hóa đơn xuất...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.Fillter(newText.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        listViewXuat = (ListView) findViewById(R.id.listViewfragmentXuat);
        listBan = new ArrayList<>();
        sqLiteDatabase = MyDatabase.initDatabase(this, DATABASE_NAME);

//        getJSON(General.URL_GET_HOA_DON_BAN);
        setData();

    }

//    private void getJSON(String url) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//
//                        try {
//                            listBan.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject obj = response.getJSONObject(i);
//                                    listBan.add(new HoaDonBan(
//                                            obj.getString("mahd"),
//                                            obj.getString("mahh"),
//                                            obj.getString("ngay"),
//                                            obj.getInt("sl"),
//                                            obj.getString("ghichu")
//                                    ));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            adapter = new HoaDonBanAdapter(HoaDonBanFragmentDemo.this, listBan);
//                            listViewXuat.setAdapter(adapter);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(HoaDonBanFragmentDemo.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
////        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }

    private void setData() {
        listBan.clear();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from xuathang", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            String mn = cursor.getString(0);
            String mh = cursor.getString(1);
            String ngayn = cursor.getString(2);
            int soluong = cursor.getInt(3);
            String ghichu = cursor.getString(4);
            HoaDonBan dkh = new HoaDonBan(mn, mh, ngayn, soluong, ghichu);

            listBan.add(dkh);
        }
        adapter = new HoaDonBanAdapter(HoaDonBanFragmentDemo.this, listBan);
        listViewXuat.setAdapter(adapter);
    }
}
