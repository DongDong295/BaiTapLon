package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

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
    EditText thanhTimKiem;
    Button refreshButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setClick();
        GetJsonArray(this);

        refreshButton = findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetJsonArray(MainActivity.this);
                Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClick() {
        thanhTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = thanhTimKiem.getText().toString();
                adapter.SortTruyen(text);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = thanhTimKiem.getText().toString();
                adapter.SortTruyen(text);
            }
        });
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
        thanhTimKiem = findViewById(R.id.editDS);
    }

    public void GetJsonArray(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.1.8:8080/get/get.php";
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