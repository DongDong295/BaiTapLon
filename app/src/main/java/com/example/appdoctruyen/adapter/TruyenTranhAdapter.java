package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.ItemTruyen;
import com.example.appdoctruyen.MainActivity;
import com.example.appdoctruyen.R;

import java.util.ArrayList;
import java.util.List;

public class TruyenTranhAdapter extends ArrayAdapter<ItemTruyen> {
    private Context ct;
    private ArrayList<ItemTruyen> arr;
    public TruyenTranhAdapter(Context context, int resource,List<ItemTruyen> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }

    public void SortTruyen(String s){
        s=s.toUpperCase();
        int k = 0;
        for(int i = 0; i < arr.size(); i++){
            ItemTruyen tr = arr.get(i);
            String ten = tr.getTenTruyen().toUpperCase();
            if(ten.indexOf(s) >= 0){
                arr.set(i, arr.get(k));
                arr.set(k,tr);
                k++;
            }
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen, null);
        }

        if(arr.size() > 0){
            ItemTruyen itemTruyen = this.arr.get(position);
            TextView tenTruyen = convertView.findViewById(R.id.tvTenTruyenChapter);
            TextView chapter = convertView.findViewById(R.id.tvChapter);
            ImageView anhTruyen = convertView.findViewById(R.id.ivAnhTruyen);

            tenTruyen.setText(itemTruyen.getTenTruyen());
            chapter.setText(itemTruyen.getTenChuong());

            Glide.with(this.ct).load(itemTruyen.getLinkAnh()).into(anhTruyen);
        }
        return convertView;
    }

}

