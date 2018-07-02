package com.example.app_pro.duantwo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import com.example.app_pro.duantwo.activity.SuaHangHoaActivity;
import com.example.app_pro.duantwo.model.HangHoaNhapfm;
import com.example.app_pro.duantwo.model.MyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.katso.livebutton.LiveButton;

import static android.R.attr.id;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class HangHoaFragmentAdapter extends BaseAdapter {
    Context myContext;
    ArrayList<HangHoaNhapfm> arrFm;

    //Khởi tạo Database
    final String DATABASE_NAME = "quanlykhohang.sqlite";
    SQLiteDatabase sqLiteDatabase;

    public HangHoaFragmentAdapter(Context myContext, ArrayList<HangHoaNhapfm> arrFm) {
        this.myContext = myContext;
        this.arrFm = arrFm;
        sqLiteDatabase = MyDatabase.initDatabase((Activity) myContext, DATABASE_NAME);
    }

    @Override
    public int getCount() {
        return arrFm.size();
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
        final LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dong_item_hang_hoa_fragment, null);
        TextView txtmahangfm = view.findViewById(R.id.txtfragmenthhmahang);
        TextView txttenhangfm = view.findViewById(R.id.txtfragmenthhtenhang);
        TextView txtghichufm = view.findViewById(R.id.txtfragmenthhghichu);
        Button btnSua = view.findViewById(R.id.btnHHSua);
        Button btnXoa = view.findViewById(R.id.btnHHXoa);
        final HangHoaNhapfm hh = arrFm.get(i);
        txtmahangfm.setText("Mã hàng: " + hh.getMa());
        txttenhangfm.setText("Tên hàng: " + hh.getTen());
        txtghichufm.setText("Ghi chú: " + hh.getGhichu());

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myContext, SuaHangHoaActivity.class);
                intent.putExtra("hh", hh);
                myContext.startActivity(intent);
            }
        });
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
//                        XoaHH(General.URL_XOA_HANG_HOA, hh.getMa());
                        boolean check = XoaHangHoa(hh.getMa());
                        if (check==true) {
                            Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            myContext.startActivity(new Intent(myContext, HienThiChiTietHangHoaNhapActivity.class));

                        } else {
                            final Dialog thongbao=new Dialog(myContext);
                            thongbao.setTitle("Cảnh báo");
                            thongbao.setContentView(R.layout.dialog_xoa_hang_hoa);
                            TextView tb=thongbao.findViewById(R.id.thongbao);
                            LiveButton btnCancel=thongbao.findViewById(R.id.btnCancel);
                            String s="Không được phép xóa,bởi mã hàng trong phần" +
                                    " nhập hàng và xuất hàng tham chiếu tới.\n" +
                                    "Bạn phải xóa mã hàng trong phần nhập hàng và xuất\n" +
                                    " hàng trước,thì mới xóa được.Xin cảm ơn!";
                            tb.setText(s);
                            thongbao.show();
                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    thongbao.dismiss();
                                }
                            });
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

    private boolean XoaHangHoa(String ma){
       return sqLiteDatabase.delete("hanghoa","mahanghoa=?",new String[]{ma})>0;
    }

//    private void XoaHH(String url, final String mahh) {
//        RequestQueue requestQueue = Volley.newRequestQueue(myContext);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.trim().equals("ok")) {
//                    Toast.makeText(myContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                    myContext.startActivity(new Intent(myContext, HienThiChiTietHangHoaNhapActivity.class));
//
//                } else {
//                    final Dialog thongbao=new Dialog(myContext);
//                    thongbao.setTitle("Cảnh báo");
//                    thongbao.setContentView(R.layout.dialog_xoa_hang_hoa);
//                    TextView tb=thongbao.findViewById(R.id.thongbao);
//                    LiveButton btnCancel=thongbao.findViewById(R.id.btnCancel);
//                    String s="Không được phép xóa,bởi mã hàng trong phần" +
//                            " nhập hàng và xuất hàng tham chiếu tới.\n" +
//                            "Bạn phải xóa mã hàng trong phần nhập hàng và xuất\n" +
//                            " hàng trước,thì mới xóa được.Xin cảm ơn!";
//                    tb.setText(s);
//                    thongbao.show();
//                    btnCancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            thongbao.dismiss();
//                        }
//                    });
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
//                map.put("mahh", mahh);
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
}
