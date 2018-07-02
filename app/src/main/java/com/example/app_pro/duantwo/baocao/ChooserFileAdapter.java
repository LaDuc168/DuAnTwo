package com.example.app_pro.duantwo.baocao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.app_pro.duantwo.R;
import com.github.library.bubbleview.BubbleTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaVanDuc on 2/5/2018.
 */

public class ChooserFileAdapter extends BaseAdapter {
    Context myContext;
    List<String> mangFile;

    public ChooserFileAdapter(Context myContext, List<String> mangFile) {
        this.myContext = myContext;
        this.mangFile = mangFile;
    }

    @Override
    public int getCount() {
        return mangFile.size();
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

        String direct=mangFile.get(i);
        File selected = new File(direct);
        if(selected.isDirectory()){
        view=inflater.inflate(R.layout.row_chooser_file_item,null);

        }else {
            view=inflater.inflate(R.layout.row_chooser_file_item_file,null);

        }
        BubbleTextView text_message = (BubbleTextView)view.findViewById(R.id.txtChooserFile);

        int first = selected.toString().lastIndexOf("/");
        String result = selected.toString().substring((first + 1));
        text_message.setText(result);


        return view;
    }
}
