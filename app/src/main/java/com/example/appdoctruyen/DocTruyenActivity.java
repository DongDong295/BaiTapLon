package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DocTruyenActivity extends AppCompatActivity {
    ArrayList<String> arrUrlAnh;
    ImageView viewTruyen;
    ChapTruyen itemChap;
    int soTrang, trangHienTai;
    String idChap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);
        init();
        anhXa();
        setUp();
        setClick();
    }

    private void init() {
        trangHienTai = 1;
        arrUrlAnh = new ArrayList<>();
        Bundle b = getIntent().getBundleExtra("data");
        idChap = b.getString("idChap");
    }

    private void anhXa() {
        viewTruyen = findViewById(R.id.vTruyen);
    }

    private void setUp() {
        GetJsonArray(this, new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<String> imageUrlList) {
                arrUrlAnh = imageUrlList;
                soTrang = arrUrlAnh.size();
                DocTruyen(0);
            }

            @Override
            public void onError(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    private void setClick() {
        // Các xử lý sự kiện khác nếu cần
    }

    public void left(View view) {
        DocTruyen(-1);
    }

    public void right(View view) {
        DocTruyen(1);
    }

    private void DocTruyen(int i) {
        trangHienTai = trangHienTai + i;
        if (trangHienTai <= 0) {
            trangHienTai = 1;
        }
        if (trangHienTai > soTrang) {
            trangHienTai = soTrang;
        }

        if (!arrUrlAnh.isEmpty()) {
            Glide.with(this).load(arrUrlAnh.get(trangHienTai - 1)).into(viewTruyen);
        }
        else {
            Toast.makeText(this, "No images available", Toast.LENGTH_SHORT).show();
        }
    }

    private void GetJsonArray(Context context, final VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://appuddd.000webhostapp.com/getAnh.php?idChap="+idChap;
        /*JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String imageUrl = jsonArray.getString(i);
                                arrUrlAnh.add(imageUrl);
                            }

                            callback.onSuccess(arrUrlAnh);
                        } catch (JSONException e) {
                            callback.onError(new VolleyError(e));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Gọi onError của callback khi có lỗi
                callback.onError(error);
                Log.e("XDS", "Error during Volley request: " + error.getMessage());
            }

        });*/
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String imageUrl = response.getString(i);
                                arrUrlAnh.add(imageUrl);
                            }
                            callback.onSuccess(arrUrlAnh                                                                                                                                                                                                                                                                          );
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
    public interface VolleyCallback {
        void onSuccess(ArrayList<String> imageUrlList);
        void onError(VolleyError error);
    }
}