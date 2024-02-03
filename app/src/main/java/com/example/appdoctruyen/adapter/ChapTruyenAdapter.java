package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appdoctruyen.ChapTruyen;
import com.example.appdoctruyen.R;

import java.util.ArrayList;
import java.util.List;

public class ChapTruyenAdapter extends ArrayAdapter<ChapTruyen> {
    private Context ct;
    private ArrayList<ChapTruyen> arr;
    public ChapTruyenAdapter(Context context, int resource, ArrayList<ChapTruyen> objects) {
        super(context, resource, objects);
        this.ct= context;
        this.arr= new ArrayList<>(objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_chap_truyen, null);
        }
        if(arr.size() > 0) {
            TextView tenChap, ngayDang;
            tenChap=convertView.findViewById(R.id.tvChap);
            ngayDang=convertView.findViewById(R.id.tvNgayDang);

            ChapTruyen chapTruyen = arr.get(position);
            tenChap.setText(chapTruyen.getTenChap());
            ngayDang.setText(chapTruyen.getNgayDang());
        }
        return convertView;
    }
}
