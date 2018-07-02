package com.example.app_pro.duantwo.adapterbaocao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app_pro.duantwo.R;
import com.example.app_pro.duantwo.modelbaocao.BaoCaoNhapInstance;

import java.util.ArrayList;

/**
 * Created by APP-PRO on 12/18/2017.
 */

public class BaoCaoNhapAdapter extends BaseAdapter {
    Context myContext;
    ArrayList<BaoCaoNhapInstance> mangNhap;

    public BaoCaoNhapAdapter(Context myContext, ArrayList<BaoCaoNhapInstance> mangNhap) {
        this.myContext = myContext;
        this.mangNhap = mangNhap;
    }

    @Override
    public int getCount() {
        return mangNhap.size();
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
        LayoutInflater inflater= (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.dong_item_bao_cao_nhap,null);
        TextView ten=view.findViewById(R.id.txtBCNTen);
        TextView ngay=view.findViewById(R.id.txtBCNNgay);
        TextView sl=view.findViewById(R.id.txtBCNSL);
        TextView ghichu=view.findViewById(R.id.txtBCNGhiChu);
        BaoCaoNhapInstance n=mangNhap.get(i);

        ten.setText("Tên: "+n.getTen());
        ngay.setText("Ngày: "+ChuyenNgay(n.getNgay()));
        sl.setText("Số lượng: "+n.getSoluong()+"");
        ghichu.setText("Ghi chú: "+n.getGhichu());
        return view;
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
}
