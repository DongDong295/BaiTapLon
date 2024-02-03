package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appdoctruyen.adapter.ChapTruyenAdapter;
import com.example.appdoctruyen.adapter.TruyenTranhAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ChapterActivity extends AppCompatActivity {
    TextView tenTruyen;
    ImageView anhTruyen;
    ItemTruyen item;
    ListView dsChap;
    Context context;
    ArrayList<ChapTruyen> arrChap;
    ChapTruyenAdapter chapTruyenAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        context = this;
        init();
        anhXa();
        setUp();
        setClick();
        GetChapJsonArray(this);
    }
    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        item =(ItemTruyen) b.getSerializable("chap");

        arrChap=new ArrayList<>();
        /*for(int i=0; i <20;i++){
            arrChap.add(new ChapTruyen("Placeholder Value", "1-1-2024"));
        }*/
        chapTruyenAdapter = new ChapTruyenAdapter(context, 0, arrChap);
    }
    private void anhXa(){
        tenTruyen=findViewById(R.id.tvTenTruyenC);
        anhTruyen=findViewById(R.id.ivAnhTruyenC);
        dsChap=findViewById(R.id.lvChap);
    }
    private void setUp(){
        tenTruyen.setText(item.getTenTruyen());
        Glide.with(this).load(item.getLinkAnh()).into(anhTruyen);
        dsChap.setAdapter(chapTruyenAdapter);
    }
    private void setClick(){
        dsChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ChapterActivity.this,DocTruyenActivity.class));
            }
        });
    }

    public void GetChapJsonArray(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.8:8080/get/getChap.php?id=0"+item.id;
        arrChap = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrChap.add(new ChapTruyen(jsonObject));
                                chapTruyenAdapter = new ChapTruyenAdapter(context, 0, arrChap);
                                setUp();
                            } catch (JSONException e) {
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        queue.add(request);
    }
}