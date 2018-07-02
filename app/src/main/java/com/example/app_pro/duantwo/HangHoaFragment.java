package com.example.app_pro.duantwo;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_pro.duantwo.adapter.HangHoaFragmentAdapter;
import com.example.app_pro.duantwo.model.HangHoaNhapfm;
import com.example.app_pro.duantwo.model.MyDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HangHoaFragment extends Fragment {
    View root;
    TextView txtmahangfm, txttenhangfm, txtghichufm;
    ListView listViewHangHoa;
    ArrayList<HangHoaNhapfm> listHH;
    HangHoaFragmentAdapter adapter;

    //Khới tạo Database
    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    public HangHoaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sqLiteDatabase = MyDatabase.initDatabase(getActivity(), DATABASE_NAME);
        listHH = new ArrayList<>();
        root = inflater.inflate(R.layout.hang_hoa_nhap_fragment, container, false);
        listViewHangHoa = root.findViewById(R.id.listViewfragmentHH);
        adapter = new HangHoaFragmentAdapter(getContext(), listHH);
        listViewHangHoa.setAdapter(adapter);

//        getJSON(General.URL_GET_HANG_HOA);
        setData();
        return root;
    }

    private void getJSON(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listHH.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    listHH.add(new HangHoaNhapfm(
                                            obj.getString("ma"),
                                            obj.getString("ten"),
                                            obj.getString("ghichu")

                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
//        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void setData() {
        Cursor cursor=sqLiteDatabase.rawQuery("select * from hanghoa",null);
        for (int i = 0; i <cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            String mahh=cursor.getString(0);
            String tenhh=cursor.getString(1);
            String ghichu=cursor.getString(2);
            HangHoaNhapfm dkh=new HangHoaNhapfm(mahh,tenhh,ghichu);

            listHH.add(dkh);
        }
        adapter.notifyDataSetChanged();
    }

}
