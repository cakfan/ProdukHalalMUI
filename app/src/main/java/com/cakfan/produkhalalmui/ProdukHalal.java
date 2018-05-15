package com.cakfan.produkhalalmui;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProdukHalal {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<DataHalal> data = null;

    @SerializedName("next_page")
    @Expose
    private String next_page;

    public ProdukHalal(){}

    public ProdukHalal(String status, List<DataHalal> data, String next_page) {
        this.status = status;
        this.data = data;
        this.next_page = next_page;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataHalal> getData() {
        return data;
    }

    public void setData(List<DataHalal> data) {
        this.data = data;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }
}
