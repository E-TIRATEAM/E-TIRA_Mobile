package com.rememberdev.tirtaagung.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.activity.MainTirtaActivity;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DaftarGambar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdapterDaftarGambar extends RecyclerView.Adapter<AdapterDaftarGambar.ViewHolder> {

    private Context ctx;
    private List<DaftarGambar> listGambar;
    private ViewPager2 viewPager2;

    public AdapterDaftarGambar(Context ctx, List<DaftarGambar> listGambar) {
        this.ctx = ctx;
        this.listGambar = listGambar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_gambar, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DaftarGambar daftarGambar = listGambar.get(position);
        Glide.with(holder.itemView.getContext())
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + daftarGambar.getNama_gambar())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgAutoSlide);
    }

    @Override
    public int getItemCount() {
        return listGambar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAutoSlide;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAutoSlide = itemView.findViewById(R.id.img_auto_slide);
        }
    }
}
