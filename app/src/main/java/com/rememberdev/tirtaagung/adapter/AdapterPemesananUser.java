package com.rememberdev.tirtaagung.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DaftarGambar;
import com.rememberdev.tirtaagung.model.Pemesanan;
import com.rememberdev.tirtaagung.model.PemesananUser;

import java.util.List;

public class AdapterPemesananUser extends RecyclerView.Adapter<AdapterPemesananUser.ViewHolder> {

    private Context ctx;
    private List<PemesananUser> listPemesanan;

    public AdapterPemesananUser(Context ctx, List<PemesananUser> listPemesanan) {
        this.ctx = ctx;
        this.listPemesanan = listPemesanan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pemesanan_user, parent, false);
        ViewHolder holder = new ViewHolder(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PemesananUser daftarPemesanan = listPemesanan.get(position);
        Glide.with(holder.itemView.getContext())
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + daftarPemesanan.getGambar_satu())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgPemesanan);
        holder.tvJudul.setText(daftarPemesanan.getJudul());
        holder.tvLamaKapasitas.setText(daftarPemesanan.getLama_sewa() + " | " + daftarPemesanan.getKapasitas());
        holder.tvTanggal.setText(daftarPemesanan.getTanggal_pemesanan());
        holder.tvHargaMetode.setText(daftarPemesanan.getHarga() + " | " + daftarPemesanan.getMetode_pembayaran());
        holder.tvStatus.setText(daftarPemesanan.getStatus_pemesanan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageStr =
                        daftarPemesanan.getNama_lengkap() + "\n" +
                        daftarPemesanan.getJudul() + "\n" +
                        daftarPemesanan.getHarga();
                String urlNew = "https://api.whatsapp.com/send?phone="+"+6289619713034"+"&text="+messageStr;
                String url = "https://api.whatsapp.com/send?phone="+"+6289619713034";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                i.setData(Uri.parse(urlNew));
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPemesanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPemesanan;
        TextView tvJudul, tvLamaKapasitas, tvTanggal, tvHargaMetode, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPemesanan = itemView.findViewById(R.id.img_pemesanan);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvLamaKapasitas = itemView.findViewById(R.id.tv_lama_dan_kapasitas);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvHargaMetode = itemView.findViewById(R.id.tv_harga_dan_metode);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
