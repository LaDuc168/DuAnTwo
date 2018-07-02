package com.example.app_pro.duantwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by APP-PRO on 12/17/2017.
 */

public class MaHangAdapter extends BaseAdapter {
    Context myContext;
    ArrayList<String > arrMa;

    public MaHangAdapter(Context myContext, ArrayList<String> arrMa) {
        this.myContext = myContext;
        this.arrMa = arrMa;
    }

    @Override
    public int getCount() {
        return arrMa.size();
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
        view=inflater.inflate(R.layout.spinner_item,null);
        String s=arrMa.get(i);
        ImageView img=view.findViewById(R.id.imgKey);
        TextView txtSpinner=view.findViewById(R.id.txtSpinner);
        txtSpinner.setText(s);
        return view;
    }
}
