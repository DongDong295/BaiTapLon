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
        arrChap=new ArrayList<>();
        init();
        anhXa();
        setUp();
        setClick();
        GetChapJsonArray(this, new ChapCallback() {
            @Override
            public void onSuccess(ArrayList<ChapTruyen> chapList) {

                arrChap = chapList;
                chapTruyenAdapter = new ChapTruyenAdapter(context, 0, arrChap);
                setUp();
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }
    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        item =(ItemTruyen) b.getSerializable("chap");
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
                Bundle b = new Bundle();
                b.putString("idChap", arrChap.get(position).getId());
                //Log.d("ddd", "x"+arrChap.size());
                Intent t = new Intent(ChapterActivity.this,DocTruyenActivity.class);
                t.putExtra("data",b);
                startActivity(t);
            }
        });
    }

    public void GetChapJsonArray(Context context, final ChapCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://appuddd.000webhostapp.com/getChap.php?id=0" + item.id;
        arrChap = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrChap.add(new ChapTruyen(jsonObject));
                                chapTruyenAdapter = new ChapTruyenAdapter(context, 0, arrChap);
                            }
                            callback.onSuccess(arrChap);
                        } catch (JSONException e) {
                            callback.onError(new VolleyError(e));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }
        );
        queue.add(request);
    }
    public interface ChapCallback {
        void onSuccess(ArrayList<ChapTruyen> chapList);
        void onError(VolleyError error);
    }
}