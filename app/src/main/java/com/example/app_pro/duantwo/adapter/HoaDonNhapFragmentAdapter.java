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
import com.example.app_pro.duantwo.SuaHoaDonNhapActivity;
import com.example.app_pro.duantwo.activity.SuaHangHoaActivity;
import com.example.app_pro.duantwo.model.HoaDonNhap;
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

import static android.R.attr.id;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class HoaDonNhapFragmentAdapter extends BaseAdapter {
    Context myContext;
    ArrayList<HoaDonNhap> arrHDFm;

    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    public HoaDonNhapFragmentAdapter(Context myContext, ArrayList<HoaDonNhap> arrHDFm) {
        this.myContext = myContext;
        this.arrHDFm = arrHDFm;
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
        final LayoutInflater inflater= (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_item_hoa_don_nhap_fragment,null);
        TextView mhd=view.findViewById(R.id.txtfragmenthdmahoadon);
        TextView mhh=view.findViewById(R.id.txtfragmenthdmahang);
        TextView ngay=view.findViewById(R.id.txtfragmenthdngay);
        TextView sl=view.findViewById(R.id.txtfragmenthdsoluong);
        TextView ghichu=view.findViewById(R.id.txtfragmentHDNGhiChu);
        final HoaDonNhap hd=arrHDFm.get(i);
        mhd.setText("Mã hóa đơn: "+hd.getMaHD());
        mhh.setText("Mã hàng hóa: "+hd.getMaH());
        ngay.setText("Ngày nhập: "+ChuyenNgay(hd.getNgayHD()));
        sl.setText("Số lượng: "+hd.getSoLuongHD()+"");
        ghichu.setText("Ghi chú: "+hd.getGhichu());

        Button btnSua=view.findViewById(R.id.btnNhapSua);
        Button btnXoa=view.findViewById(R.id.btnNhapXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(myContext);
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
//                        POST_DATA(General.URL_XOA_HOA_DON_NHAP,hd.getMaHD());
                        boolean checkHD = XoaHHNhap(hd.getMaHD());
                        if (checkHD==true) {
                            Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(myContext, HienThiChiTietHangHoaNhapActivity.class);
                            intent.putExtra("hai",2);
                            myContext.startActivity(intent);
                        } else {
                            Toast.makeText(myContext, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                            myContext.startActivity(new Intent(myContext, HienThiChiTietHangHoaNhapActivity.class));
                        }


                    }
                });
                builder.show();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(myContext, SuaHoaDonNhapActivity.class);
                intent.putExtra("nhap",hd);
                myContext.startActivity(intent);

            }
        });
        Animation animation = AnimationUtils.loadAnimation(myContext, R.anim.anim_listview);
        view.startAnimation(animation);

        return view;
    }

    private boolean XoaHHNhap(String ma){
        return sqLiteDatabase.delete("nhaphang","manhap=?",new String[]{ma})>0;
    }
    public  String ChuyenNgay(String ngayt) {
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
//    private void POST_DATA(String url, final String mahd) {
//        RequestQueue requestQueue = Volley.newRequestQueue(myContext);
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(myContext, HienThiChiTietHangHoaNhapActivity.class);
//                    intent.putExtra("hai",2);
//                    myContext.startActivity(intent);
//                } else {
//                    Toast.makeText(myContext, "Xảy ra lỗi dữ liệu", Toast.LENGTH_SHORT).show();
//                    myContext.startActivity(new Intent(myContext, HienThiChiTietHangHoaNhapActivity.class));
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(myContext, "Lỗi mạng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String ,String> map=new HashMap<>();
//                map.put("mahdn",mahd);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }

}
