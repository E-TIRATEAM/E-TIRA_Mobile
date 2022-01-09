package com.rememberdev.tirtaagung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.activity.DetailPaketActivity;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterFasilitas extends RecyclerView.Adapter<AdapterFasilitas.FasilitasViewHolder> {

    private Context context;
    private ArrayList<HashMap> listFasilitas;

    private OnItemClickCallback onItemClickCallback;

    public AdapterFasilitas(Context context, ArrayList<HashMap> listFasilitas) {
        this.context = context;
        this.listFasilitas = listFasilitas;
    }

    public AdapterFasilitas(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public FasilitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fasilitas, parent, false);
        return new FasilitasViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView.getContext())
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + listFasilitas.get(position).get("ikon"))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgIkon);
        holder.tvJudul.setText((String) listFasilitas.get(position).get("nama"));
        holder.tvDeskripsi.setText((String) listFasilitas.get(position).get("deskripsi"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "kamu memilih : " + listFasilitas.get(position).get("nama"), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, DetailPaketActivity.class);
////                intent.putExtra("id_paket", String.valueOf(listDaftarPaket.get(position).get("id_paket")));
//                intent.putExtra("judul", String.valueOf(listDaftarPaket.get(position).get("judul")));
//                intent.putExtra("deskripsi", String.valueOf(listDaftarPaket.get(position).get("deskripsi")));
//                intent.putExtra("harga", String.valueOf(listDaftarPaket.get(position).get("harga")));
//                intent.putExtra("gambar_satu", String.valueOf(listDaftarPaket.get(position).get("gambar_satu")));
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFasilitas.size();
    }

    public class FasilitasViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIkon;
        TextView tvJudul, tvDeskripsi;
        public FasilitasViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIkon = itemView.findViewById(R.id.img_ikon);
            tvJudul = itemView.findViewById(R.id.tv_judul_fasilitas);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi_fasilitas);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(HashMap data);
    }
}
