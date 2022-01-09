package com.rememberdev.tirtaagung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.activity.DetailPaketActivity;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DaftarPaket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterDaftarPaket extends RecyclerView.Adapter<AdapterDaftarPaket.DaftarPaketViewHolder> {

    private Context context;
    private ArrayList<HashMap> listDaftarPaket;
    private ViewPager2 viewPager2;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public AdapterDaftarPaket(Context context, ArrayList<HashMap> listDaftarPaket, ViewPager2 viewPager2) {
        this.context = context;
        this.listDaftarPaket = listDaftarPaket;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public DaftarPaketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_paket, parent, false);
        return new DaftarPaketViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(@NonNull DaftarPaketViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView.getContext())
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + listDaftarPaket.get(position).get("gambar_satu"))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgPaket);
        holder.tvJudul.setText((String) listDaftarPaket.get(position).get("judul"));
        holder.tvHarga.setText((String) listDaftarPaket.get(position).get("harga"));
        holder.tvLamaKapasitas.setText("( " + listDaftarPaket.get(position).get("lama_sewa") + " hari | " + listDaftarPaket.get(position).get("kapasitas") + " orang)");
        holder.tvRating.setText((String)listDaftarPaket.get(position).get("rating"));
        holder.ratingBar.setRating(Float.parseFloat((String) listDaftarPaket.get(position).get("rating")));

        if (position == listDaftarPaket.size()-2) {
            viewPager2.post(runnable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "kamu memilih : " + listDaftarPaket.get(position).get("judul"), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, DetailPaketActivity.class);
                intent.putExtra("id_paket", String.valueOf(listDaftarPaket.get(position).get("id_paket")));
                intent.putExtra("judul", String.valueOf(listDaftarPaket.get(position).get("judul")));
                intent.putExtra("deskripsi", String.valueOf(listDaftarPaket.get(position).get("deskripsi")));
                intent.putExtra("harga", String.valueOf(listDaftarPaket.get(position).get("harga")));
                intent.putExtra("lama_sewa", String.valueOf(listDaftarPaket.get(position).get("lama_sewa")));
                intent.putExtra("kapasitas", String.valueOf(listDaftarPaket.get(position).get("kapasitas")));
                intent.putExtra("fasilitas", String.valueOf(listDaftarPaket.get(position).get("fasilitas")));
                intent.putExtra("gambar_satu", String.valueOf(listDaftarPaket.get(position).get("gambar_satu")));
                intent.putExtra("gambar_dua", String.valueOf(listDaftarPaket.get(position).get("gambar_dua")));
                intent.putExtra("gambar_tiga", String.valueOf(listDaftarPaket.get(position).get("gambar_tiga")));
                intent.putExtra("gambar_empat", String.valueOf(listDaftarPaket.get(position).get("gambar_empat")));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDaftarPaket.size();
    }

    public class DaftarPaketViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPaket;
        TextView tvJudul, tvHarga, tvLamaKapasitas, tvRating;
        RatingBar ratingBar;
        public DaftarPaketViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPaket = itemView.findViewById(R.id.img_paket);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvHarga = itemView.findViewById(R.id.tv_harga);
            tvLamaKapasitas = itemView.findViewById(R.id.tv_lama_dan_kapasitas);
            tvRating = itemView.findViewById(R.id.tv_rating);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(HashMap data);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            listDaftarPaket.addAll(listDaftarPaket);
            notifyDataSetChanged();
        }
    };

}
