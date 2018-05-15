package com.cakfan.produkhalalmui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HalalAdapter extends RecyclerView.Adapter<HalalAdapter.ViewHolder>{

    private Context context;
    private List<DataHalal> dataHalal;

    HalalAdapter(Context context, List<DataHalal> dataHalal) {
        this.context = context;
        this.dataHalal = dataHalal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.produk_halal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DataHalal halal = dataHalal.get(position);
        holder.nama_produk.setText(halal.getTitle());
        holder.nama_produsen.setText(halal.getProdusen());
        holder.nomor_sertifikat.setText(halal.getNomor_sertifikat());
        holder.berlaku.setText(halal.getBerlaku_hingga());

        holder.btn_bagikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(Intent.ACTION_SEND);
                s.setType("text/plain");
                String share = String.valueOf(Html.fromHtml("Nama Produk : <b>" + halal.getTitle() +
                        "</b><br> Nama Produsen : <b>" + halal.getProdusen() +
                        "</b><br> Nomor Sertifikat : <b>" + halal.getNomor_sertifikat() +
                        "</b><br> Berlaku : <b><i>" + halal.getBerlaku_hingga() +"</i></b>" ));
                s.putExtra(Intent.EXTRA_TEXT, share);
                context.startActivity(s);
            }
        });
        //Toast.makeText(context, halal.toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return dataHalal.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_produk,nama_produsen,nomor_sertifikat,berlaku;
        Button btn_bagikan;

        ViewHolder(View itemView) {
            super(itemView);
            nama_produk = itemView.findViewById(R.id.tv_nama_produk);
            nama_produsen = itemView.findViewById(R.id.tv_nama_produsen);
            nomor_sertifikat = itemView.findViewById(R.id.tv_nomor_sertifikat);
            berlaku = itemView.findViewById(R.id.tv_berlaku);
            btn_bagikan = itemView.findViewById(R.id.btn_bagikan);
        }
    }
}
