package com.rememberdev.tirtaagung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;

public class DetailPaketActivity extends AppCompatActivity {
    public static final String EXTRA_KONTEN = "extra_konten";

    TextView tvJudul, tvDeskripsi, tvHarga, tvLamaKapasitas, tvFasilitas;
    ImageView imgDetail1, imgDetail11, imgDetail2, imgDetail3, imgDetail4;
    private String id_paket, judul, deskripsi, harga, lama_sewa, kapasitas, fasilitas, gambar_satu, gambar_dua, gambar_tiga, gambar_empat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_paket);

        tvJudul = findViewById(R.id.txt_judul);
        tvDeskripsi = findViewById(R.id.txt_deskripsi);
        tvHarga = findViewById(R.id.txt_harga);
        tvLamaKapasitas = findViewById(R.id.txt_lama_dan_kapasitas);
        tvFasilitas = findViewById(R.id.txt_fasilitas);

        imgDetail1 = findViewById(R.id.img_detail1);
        imgDetail11 = findViewById(R.id.img_detail11);
        imgDetail2 = findViewById(R.id.img_detail2);
        imgDetail3 = findViewById(R.id.img_detail3);
        imgDetail4 = findViewById(R.id.img_detail4);

        Intent intent = getIntent();

        id_paket = intent.getStringExtra("id_paket");
        judul = intent.getStringExtra("judul");
        deskripsi = intent.getStringExtra("deskripsi");
        harga = intent.getStringExtra("harga");
        lama_sewa = intent.getStringExtra("lama_sewa");
        kapasitas = intent.getStringExtra("kapasitas");
        fasilitas = intent.getStringExtra("fasilitas");
        gambar_satu = intent.getStringExtra("gambar_satu");
        gambar_dua = intent.getStringExtra("gambar_dua");
        gambar_tiga = intent.getStringExtra("gambar_tiga");
        gambar_empat = intent.getStringExtra("gambar_empat");

        //text view
        tvJudul.setText(judul + " " + id_paket);
        tvDeskripsi.setText(deskripsi);
        tvHarga.setText("Rp. " + harga);
        tvLamaKapasitas.setText(lama_sewa + " hari  |  " + kapasitas + " orang");
        tvFasilitas.setText(fasilitas);

        //gambar
        Glide.with(DetailPaketActivity.this)
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + gambar_satu)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgDetail1);
        Glide.with(DetailPaketActivity.this)
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + gambar_satu)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgDetail11);
        Glide.with(DetailPaketActivity.this)
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + gambar_dua)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgDetail2);
        Glide.with(DetailPaketActivity.this)
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + gambar_tiga)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgDetail3);
        Glide.with(DetailPaketActivity.this)
                .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + gambar_empat)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgDetail4);
    }

    public void actionPesan(View view) {
        Intent intent = new Intent(DetailPaketActivity.this, KonfirmasiPemesananActivity.class);
        intent.putExtra("id_paket", id_paket);
        intent.putExtra("judul", judul);
        intent.putExtra("harga", harga);
        intent.putExtra("lama_sewa", lama_sewa);
        intent.putExtra("kapasitas", kapasitas);
        intent.putExtra("fasilitas", fasilitas);
        intent.putExtra("gambar_satu", gambar_satu);
        startActivity(intent);
    }
}