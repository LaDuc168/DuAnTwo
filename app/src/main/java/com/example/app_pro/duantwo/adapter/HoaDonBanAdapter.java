package com.example.app_pro.duantwo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.example.app_pro.duantwo.HienThiChiTietHangHoaNhapActivity;
import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.activity.HoaDonBanFragmentDemo;
import com.example.app_pro.duantwo.activity.SuaHoaDonXuatActivity;
import com.example.app_pro.duantwo.model.HoaDonBan;
import com.example.app_pro.duantwo.model.HoaDonNhap;
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

import static android.R.attr.id;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class HoaDonBanAdapter extends BaseAdapter {
    Context myContext;
    ArrayList<HoaDonBan> arrHDFm;
    ArrayList<HoaDonBan> arrTwo;

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    public HoaDonBanAdapter(Context myContext, ArrayList<HoaDonBan> arrHDFm) {
        this.myContext = myContext;
        this.arrHDFm = arrHDFm;
        this.arrTwo = new ArrayList<>();
        this.arrTwo.addAll(arrHDFm);
        sqLiteDatabase = MyDatabase.initDatabase((Activity) myContext, DATABASE_NAME);
    }

    @Override
    public int getCount() {
        return arrHDFm.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_item_hoa_don_ban_fragment, null);
        TextView mhd = view.findViewById(R.id.txtfragmenthdmahoadon);
        TextView mhh = view.findViewById(R.id.txtfragmenthdmahang);
        TextView ngay = view.findViewById(R.id.txtfragmenthdngay);
        TextView sl = view.findViewById(R.id.txtfragmenthdsoluong);
        TextView ghichu = view.findViewById(R.id.txtfragmentHDBGhiChu);
        Button btnSua = view.findViewById(R.id.btnXuatSua);
        Button btnXoa = view.findViewById(R.id.btnXuatXoa);
        final HoaDonBan hd = arrHDFm.get(i);
        mhd.setText("Mã hóa đơn: " + hd.getMahdb());
        mhh.setText("Mã hàng hóa: " + hd.getMahh());
        ngay.setText("Ngày xuất: " + ChuyenNgay(hd.getNgay()));
        sl.setText("Số lượng: " + hd.getSl() + "");
        ghichu.setText("Ghi chú: " + hd.getGhichu());
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myContext, SuaHoaDonXuatActivity.class);
                intent.putExtra("xuat", hd);
                myContext.startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc xóa không?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        POST_DATA(General.URL_XOA_HOA_DON_XUAT, hd.getMahdb());
                        boolean check = XoaHDXuat(hd.getMahdb());
                        if (check==true) {
                            Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            myContext.startActivity(new Intent(myContext, HoaDonBanFragmentDemo.class));

                        } else {
                            Toast.makeText(myContext, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                            myContext.startActivity(new Intent(myContext, HoaDonBanFragmentDemo.class));
                        }

                    }
                });
                builder.show();
            }
        });

        Animation animation = AnimationUtils.loadAnimation(myContext, R.anim.anim_listview);
        view.startAnimation(animation);
        return view;
    }

    private boolean XoaHDXuat(String ma){
        return sqLiteDatabase.delete("xuathang","maxuat=?",new String[]{ma})>0;
    }

    public String ChuyenNgay(String ngayt) {
        String ngay = "", thang = "", nam = "";
        String[] split = ngayt.split("-");
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                nam = split[i];
            }
            else if (i == 1) {
                thang = split[i];
            }
            else if (i == 2) {
                ngay = split[i];
            }

        }
        return ngay + "-" + thang + "-" + nam;
    }

//    private void POST_DATA(String url, final String mahd) {
//        RequestQueue requestQueue = Volley.newRequestQueue(myContext);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                    myContext.startActivity(new Intent(myContext, HoaDonBanFragmentDemo.class));
//
//                } else {
//                    Toast.makeText(myContext, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
//                    myContext.startActivity(new Intent(myContext, HoaDonBanFragmentDemo.class));
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(myContext, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("mahdx", mahd);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
    public void Fillter(String newChar) {
        newChar = newChar.toLowerCase(Locale.getDefault());
        arrHDFm.clear();
        if (newChar.length() == 0) {
            arrHDFm.addAll(arrTwo);
        } else {
            for (HoaDonBan x : arrTwo) {
                if (x.getMahdb().toLowerCase(Locale.getDefault()).contains(newChar)) {
                    arrHDFm.add(x);
                }
            }
        }
        notifyDataSetChanged();
    }
}
