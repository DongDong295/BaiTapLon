package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);

        arrUrlAnh = new ArrayList<>();
        init();
        anhXa();
        setUp();
        setClick();
    }

    void init() {
        arrUrlAnh = new ArrayList<>();
        arrUrlAnh.add("https://vnw-img-cdn.popsww.com/api/v2/containers/file2/cms_thumbnails/tocrushcaumatroi_thumb_640x960_1_-ab2fdf167de5-1591677233297-17wY1ZPW.jpg");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0sTpLylAA2cV5U5qUwAXfwQxEdS8GhCzWozc1BF4mnnYHIOU48INzZ/E/e5U8/Gxpg==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0qse9MUOfft/6aGjwjk39U5scBfiGkSoiO+fNaVjZtLtL8PDL6S1AxZC12A95c0eTQ==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0qSWDPvd1iFxmSHENX/Vu9X4RTtUshbiI5BSKycQUxXPoH1JmFWBhW/8i6dOsnvTOg==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0pV8vsGOheabgIKRdR6yXRfZFPCAx1I5w9BSvZPNpUr6g6ESDfm2rdqvvHCH639yNw==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0qzU1eT7HyTIAPdHrFFadv3bnTwk63GhGu2Z3GPcpDYJCkLd0TpQA9oUvhcmnwbpJA==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0ozj9fY9M6RCShM9IbY3Ns0eiXGgtK8tl0cJ+MZ/dh6Ony8otw63opDcQ12Fte9TUw==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0vWQRGE2suWhyl5ACRlCWgQh/uJhbxB1+EcqjNA3kWX8EYTjHjTk7fqjwkLIiCANvg==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0jSm+yQV2A1db8n+nH5FWkKGw7dz7m/K1qc/g9quiNRaUwdgsf44zdc2mEYopK5Skg==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0uE315yyLU6HekHLhrq1v8qFW37ggV3GPHMtMcAmUuuzhi4OtnVoS5rsoIQtOW4dEg==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0rO4KAQp2bXx3nkITq1n46wfWzg5Bjx9lecSdFz8fD35sF+cksAGKOItPnr96nnrhA==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0r1JnHBKQ+iwblYS2b2PqH7vAs7mu5B/yqX94P0tDOVVHE9lFKSj/n32NGsRmB8FvA==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0rAfOPmoJxTUz17oLLUDidoKknvEs339EduxkteObm7UHycFCyy1IyZP7dxlus3PTQ==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0kXjhLdtnCl45VFnFX2q062EcPL0WMv032uWv9fuhydmnhgV+eWss0KfZDpW+x4agA==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0sbWF+Vn/gn7PZGozQNt88DExxMWZK0MT5Fc+YAeh5wxltsftfgvQEBNL9+BcRksnw==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0qJ9oIlT1WQD5bBYvRe2a/9E3pBeaLxM/WxBVMvw9/SU26RH+vNLNm2e8YnoGjyYcw==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0im12i93Kjk6Qg/s4Ku0byyAAzZWgFzgtwqdcq9MuSzFPFGh+6ET12iCPJdgqNwojQ==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0l6qLJFNP1DLYfqXnwzmhMmPH1tOMgUwiLzDzgpcupW5lz/i913Ru4pHzN0m5/DH/A==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0gnAocIbzMQohDO8+QABw/LbTLMXgBzsSFrWWONM+95F683pwjCKPxRJrNFyAF5KoA==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICgtu5+wxHNEQWc9XM2WJp+/WyMkzrx1kJvjNt0M6juMvdLm7l6vcvZZXr7PT5ykcf0rpsak7ZG6NQP9/0ZwcGDyNSy7TJVn5u7wxFKKJpd2sruoo5SR+t4tVn6zhX6A8Evg==");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICglNCYeA+0s0moLKFHlDfZt7UhqTdssaD/wVgLJblwBOM6axZUIv9VVtmDq1nM7odS/ETUzlUasvF9I0LzOOhAec=");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICglNCYeA+0s0moLKFHlDfZt7TYYU0/jayegb61Z3guB3ysmHRF3TrvzTGHpjD0S+VN/cs7OGgUmTmt6DVbJtNvrU=");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICglNCYeA+0s0moLKFHlDfZt71T8XPEVrhMggRfqRAX2hhQ43DaMh4RGhnoG6CAzOGiLo9Vs2w7JPkehYj/0iqTmc=");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICglNCYeA+0s0moLKFHlDfZt6Kf3UEqxIcrkue7aZWWCOSVMBupQZsGOMc84owlKXTC55wP09eXO7evfNc2lZywFo=");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICglNCYeA+0s0moLKFHlDfZt51x9+wvtyKvzeZQCNPJQZer1qN3o+J7V5AEftpXQc49whNAPWh/1hNKudLDcu6gAI=");
        arrUrlAnh.add("https://p2.ntcdntempv26.com/content/image.jpg?data=8vw/pYOMj3vnR8KraMICglNCYeA+0s0moLKFHlDfZt4RZxq5WJcswGJHAjTmA4TckCs9XCJ3751R2AcDZ43EiHb3GgVgi5DbOg4rj0f7kvg=");
        trangHienTai = 1;
        soTrang = arrUrlAnh.size();
    }

    void anhXa() {
        viewTruyen = findViewById(R.id.vTruyen);
    }

    void setUp() {
        DocTruyen(0);
    }

    void setClick() {
    }

    public void left(View view) {
        DocTruyen(-1);
        Toast.makeText(this, "Trang: " + (trangHienTai - 1), Toast.LENGTH_SHORT);
    }

    public void right(View view) {
        DocTruyen(1);
        Toast.makeText(this, "Trang: " + (trangHienTai + 1), Toast.LENGTH_SHORT);
    }
    void DocTruyen(int i){
        trangHienTai = trangHienTai + i;
        if(trangHienTai==0){
            trangHienTai=1;
        }
        if(trangHienTai>soTrang){
            trangHienTai=soTrang;
        }
        Glide.with(this).load(arrUrlAnh.get(trangHienTai-1)).into(viewTruyen);
    }
}