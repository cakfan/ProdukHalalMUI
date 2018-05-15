package com.cakfan.produkhalalmui;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class API {

    public static final String BASE_URL = "https://sites.google.com/";
    public static final String KEY = "macros/exec?service=AKfycbx_-gZbLP7Z2gGxehXhWMWDAAQsTp3e3bmpTBiaLuzSDQSbIFWD";
    public static final String MENU = "&menu=";
    public static final String KUERI = "&query=";
    public static final String NAMA_PRODUK = "nama_produk";
    public static final String NAMA_PRODUSEN = "nama_produsen";
    public static final String NOMOR_SERTIFIKAT = "nomor_sertifikat";

    public static PostService postService = null;

    public static PostService getService()
    {
        if(postService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService {
        @GET
        Call<ProdukHalal> getProdukList(@Url String url);
    }

}
