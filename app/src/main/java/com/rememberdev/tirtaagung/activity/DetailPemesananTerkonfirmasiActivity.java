package com.rememberdev.tirtaagung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rememberdev.tirtaagung.R;

public class DetailPemesananTerkonfirmasiActivity extends AppCompatActivity {

    private  TextView txtNamaLengkap, txtJudul, txtFasilitas, txtLamaKapasitas, txtHarga, txtTanggal, txtMetode, txtStatus;

    String emailUser, namaLengkap, id_paket, judul, harga, lama_sewa, kapasitas, fasilitas, tanggal_pemesanan, metode_pembayaran, status_pembarayan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_terkonfirmasi);

        Intent intent = getIntent();

        namaLengkap = intent.getStringExtra("nama_lengkap");
        judul = intent.getStringExtra("judul");
        harga = intent.getStringExtra("harga");
        lama_sewa = intent.getStringExtra("lama_sewa");
        kapasitas = intent.getStringExtra("kapasitas");
        fasilitas = intent.getStringExtra("fasilitas");
        tanggal_pemesanan = intent.getStringExtra("tanggal_pemesanan");
        metode_pembayaran = intent.getStringExtra("metode_pembayaran");
        status_pembarayan = intent.getStringExtra("status_pemesanan");

        txtNamaLengkap = findViewById(R.id.txt_nama_lengkap);
        txtJudul = findViewById(R.id.txt_judul);
        txtFasilitas = findViewById(R.id.txt_fasilitas);
        txtLamaKapasitas = findViewById(R.id.txt_lama_dan_kapasitas);
        txtHarga = findViewById(R.id.txt_harga);
        txtMetode = findViewById(R.id.txt_metode);
        txtTanggal = findViewById(R.id.txt_tanggal);
        txtStatus = findViewById(R.id.txt_status);

        txtNamaLengkap.setText(namaLengkap);
        txtJudul.setText(judul);
        txtFasilitas.setText(fasilitas);
        txtLamaKapasitas.setText(lama_sewa + " hari  |  " + kapasitas + " orang");
        txtHarga.setText(harga);
        txtMetode.setText(metode_pembayaran);
        txtTanggal.setText(tanggal_pemesanan);
        txtStatus.setText(status_pembarayan);
    }
}