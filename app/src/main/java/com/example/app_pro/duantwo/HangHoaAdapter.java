package com.example.app_pro.duantwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by APP-PRO on 12/17/2017.
 */

public class HangHoaAdapter extends BaseAdapter {

    Context myContext;
    ArrayList<HangHoaTon> arr;

    public HangHoaAdapter(Context myContext, ArrayList<HangHoaTon> arr) {
        this.myContext = myContext;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
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
        view=inflater.inflate(R.layout.hanghoa_item,null);
        HangHoaTon hh=arr.get(i);
        TextView txtTen=view.findViewById(R.id.txtTenHangHoa);
        TextView txtSoLuong=view.findViewById(R.id.txtSoLuongTon);
        txtTen.setText("Tên hàng: "+hh.getTenHang());
        txtSoLuong.setText("Số lượng tồn: "+hh.getSoLuong()+"");
        return view;
    }
}
