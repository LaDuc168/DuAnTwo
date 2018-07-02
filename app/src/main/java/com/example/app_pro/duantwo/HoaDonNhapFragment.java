package com.example.app_pro.duantwo;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_pro.duantwo.adapter.HoaDonNhapFragmentAdapter;
import com.example.app_pro.duantwo.model.HangHoaNhapfm;
import com.example.app_pro.duantwo.model.HoaDonNhap;
import com.example.app_pro.duantwo.model.MyDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HoaDonNhapFragment extends Fragment {
    View root;
    ListView listViewHDFm;
HoaDonNhapFragmentAdapter adapter;
    ArrayList<HoaDonNhap> mangHD;

    //Khởi tạo Database
    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;
    public HoaDonNhapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sqLiteDatabase = MyDatabase.initDatabase(getActivity(), DATABASE_NAME);
        mangHD=new ArrayList<>();
        root = inflater.inflate(R.layout.hoa_don_hang_hoa_nhap_fragment, container, false);
        listViewHDFm = root.findViewById(R.id.listViewfragmentHD);
        adapter=new HoaDonNhapFragmentAdapter(getContext(),mangHD);
        listViewHDFm.setAdapter(adapter);


//        getJSON(General.URL_GET_HOA_DON_NHAP);
        setData();
        return root;
    }
//    private void getJSON(String url) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            mangHD.clear();
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//                                    JSONObject obj = response.getJSONObject(i);
//                                    mangHD.add(new HoaDonNhap(
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
//                            adapter.notifyDataSetChanged();
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
//                        Toast.makeText(getContext(), "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
////        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }

    private void setData() {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from nhaphang",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);

            String mn=cursor.getString(0);
            String mh=cursor.getString(1);
            String ngayn=cursor.getString(2);
            int soluong=cursor.getInt(3);
            String ghichu=cursor.getString(4);
            HoaDonNhap dkh=new HoaDonNhap(mn,mh,ngayn,soluong,ghichu);

            mangHD.add(dkh);
        }
        adapter.notifyDataSetChanged();
    }
}
