package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appdoctruyen.adapter.ChapTruyenAdapter;
import com.example.appdoctruyen.adapter.TruyenTranhAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    GridView dsTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<ItemTruyen> truyenTranhArray;
    Button refreshButton;

    androidx.appcompat.widget.SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setClick();
        GetJsonArray(this, new TruyenCallback() {
            @Override
            public void onSuccess(ArrayList<ItemTruyen> list) {
                truyenTranhArray = new ArrayList<>();
                truyenTranhArray = list;

                adapter = new TruyenTranhAdapter(MainActivity.this, 0, truyenTranhArray);
                setUp();
            }
            @Override
            public void onError(VolleyError error) {

            }
        });
        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetJsonArray(MainActivity.this, new TruyenCallback() {
                    @Override
                    public void onSuccess(ArrayList<ItemTruyen> list) {
                        truyenTranhArray = list;
                        adapter = new TruyenTranhAdapter(MainActivity.this, 0, truyenTranhArray);
                        dsTruyen.setAdapter(adapter);
                        //setUp();
                    }
                    @Override
                    public void onError(VolleyError error) {

                    }
                });
                Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
            }
        });
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });
    }

    private void fileList(String newText) {
        ArrayList<ItemTruyen> item = new ArrayList<>();
        if(newText.length() == 0){
            GetJsonArray(this, new TruyenCallback() {
                @Override
                public void onSuccess(ArrayList<ItemTruyen> list) {

                    adapter = new TruyenTranhAdapter(MainActivity.this, 0, truyenTranhArray);
                    setUp();
                }
                @Override
                public void onError(VolleyError error) {

                }
            });
        }
        for(ItemTruyen itemTruyen : truyenTranhArray){
            if(itemTruyen.getTenTruyen().toLowerCase().contains(newText.toLowerCase())){
                item.add(itemTruyen);
            }
        }
        adapter = new TruyenTranhAdapter(MainActivity.this, 0, item);
        dsTruyen.setAdapter(adapter);
    }

    private void setClick() {
        dsTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemTruyen item = truyenTranhArray.get(position);
                Bundle b = new Bundle();
                b.putSerializable("chap", item);
                Intent t = new Intent(MainActivity.this, ChapterActivity.class);
                t.putExtra("data",b);
                startActivity(t);
            }
        });
    }

    private void setUp() {
        dsTruyen.setAdapter(adapter);
    }

    private void init(){
        truyenTranhArray = new ArrayList<>();
        adapter = new TruyenTranhAdapter(MainActivity.this, 0 , truyenTranhArray);
    }
    private void anhXa(){
        dsTruyen = findViewById(R.id.gvDSTruyen);
        //thanhTimKiem = findViewById(R.id.editDS);
    }

    public void GetJsonArray(Context context, final TruyenCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://appuddd.000webhostapp.com/get.php";
        truyenTranhArray = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                truyenTranhArray.add(new ItemTruyen(jsonObject));
                                adapter = new TruyenTranhAdapter(MainActivity.this, 0 , truyenTranhArray);
                            } catch (JSONException e) {
                            }
                        }
                        callback.onSuccess(truyenTranhArray);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        queue.add(request);
    }
    public interface TruyenCallback {
        void onSuccess(ArrayList<ItemTruyen> list);
        void onError(VolleyError error);
    }

}