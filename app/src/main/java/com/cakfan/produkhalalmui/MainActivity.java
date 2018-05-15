package com.cakfan.produkhalalmui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText keyword;
    private ImageView btn_cari;

    private List<DataHalal> data = new ArrayList<>();

    private ProgressBar progressBar;
    private RecyclerView halal_list;
    private LinearLayoutManager manager;
    private HalalAdapter halalAdapter;

    private ConstraintLayout tidak_ada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyword = findViewById(R.id.input_keyword);
        btn_cari = findViewById(R.id.btn_cari);
        progressBar = findViewById(R.id.progressBar);
        halal_list = findViewById(R.id.list_halal);
        tidak_ada = findViewById(R.id.tidak_ada_data);

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(keyword.getText())){

                    tidak_ada.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    manager.scrollToPositionWithOffset(0,0);
                    data.clear();
                    dapatkanData();

                } else {
                    Toast.makeText(MainActivity.this, "Masukkan nama barang, nama produsen atau nomor sertifikat.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        manager = new LinearLayoutManager(this);
        halalAdapter = new HalalAdapter(this,data);
        halal_list.setLayoutManager(manager);
        halal_list.setAdapter(halalAdapter);

    }

    private void dapatkanData() {

//        final String url;
//
//        if (set_tipe.equals("Nama Produk")){
//
//            url = API.BASE_URL + API.KEY + API.MENU + API.NAMA_PRODUK + API.KUERI + keyword.getText();
//
//        } else if (set_tipe.equals("Nama Produsen")){
//
//            url = API.BASE_URL + API.KEY  + API.MENU + API.NAMA_PRODUSEN + API.KUERI + keyword.getText();
//
//        } else {
//
//            url = API.BASE_URL + API.KEY + API.MENU + API.NOMOR_SERTIFIKAT + API.KUERI + keyword.getText();
//
//        }

        String url_satu = API.BASE_URL + API.KEY + API.MENU + API.NAMA_PRODUK + API.KUERI + keyword.getText();
        final String url_dua = API.BASE_URL + API.KEY  + API.MENU + API.NAMA_PRODUSEN + API.KUERI + keyword.getText();
        final String url_tiga = API.BASE_URL + API.KEY + API.MENU + API.NOMOR_SERTIFIKAT + API.KUERI + keyword.getText();

        final Call<ProdukHalal> produkHalal = API.getService().getProdukList(url_satu);

        produkHalal.enqueue(new Callback<ProdukHalal>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ProdukHalal> call, Response<ProdukHalal> response) {

                ProdukHalal halal = response.body();

                if (!halal.getStatus().equals("error")) {
                    data.addAll(halal.getData());
                    halalAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                    //Toast.makeText(MainActivity.this,"Hasil: "+ halal.toString(),Toast.LENGTH_LONG).show();
                } else {
                    final Call<ProdukHalal> produkHalal2 = API.getService().getProdukList(url_dua);
                    produkHalal2.enqueue(new Callback<ProdukHalal>() {
                        @Override
                        public void onResponse(Call<ProdukHalal> call, Response<ProdukHalal> response) {
                            ProdukHalal halal2 = response.body();
                            if (!halal2.getStatus().equals("error")){
                                data.addAll(halal2.getData());
                                halalAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                final Call<ProdukHalal> produkHalal3 = API.getService().getProdukList(url_tiga);
                                produkHalal3.enqueue(new Callback<ProdukHalal>() {
                                    @Override
                                    public void onResponse(Call<ProdukHalal> call, Response<ProdukHalal> response) {

                                        ProdukHalal halal3 = response.body();
                                        if (!halal3.getStatus().equals("error")){
                                            data.addAll(halal3.getData());
                                            halalAdapter.notifyDataSetChanged();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            TextView error, ket;
                                            tidak_ada.setVisibility(View.VISIBLE);
                                            error = findViewById(R.id.tv_belum_ada);
                                            ket = findViewById(R.id.tv_keterangan);
                                            error.setText(Html.fromHtml("Oppps.. Hasil pencarian untuk <u><i>"+ keyword.getText() +"</i></u> tidak ditemukan."));
                                            ket.setText(R.string.keterangan);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ProdukHalal> call, Throwable t) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onFailure(Call<ProdukHalal> call, Throwable t) {

                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<ProdukHalal> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Gagal memuat hasil, perisak koneksi internet anda.",Toast.LENGTH_LONG).show();

            }
        });

    }
}
