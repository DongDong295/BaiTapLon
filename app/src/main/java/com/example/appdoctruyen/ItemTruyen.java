package com.example.appdoctruyen;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ItemTruyen implements Serializable {
    String tenTruyen, tenChuong, linkAnh, id;

    public ItemTruyen(JSONObject j) throws JSONException {
        id =j.getString("id");
        tenTruyen = j.getString("tenTruyen");
        tenChuong = j.getString("tenChuong");
        linkAnh = j.getString("linkAnh");
    }
    public ItemTruyen(String tenTruyen, String tenChuong, String linkAnh) {
        this.tenTruyen = tenTruyen;
        this.tenChuong = tenChuong;
        this.linkAnh = linkAnh;
    }
    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public void setTenChuong(String tenChuong) {
        this.tenChuong = tenChuong;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
