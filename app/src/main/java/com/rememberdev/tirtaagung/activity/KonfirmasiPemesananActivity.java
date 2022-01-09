package com.rememberdev.tirtaagung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.global.UsernameToFragment;
import com.rememberdev.tirtaagung.model.Pemesanan;
import com.rememberdev.tirtaagung.model.Request;
import com.rememberdev.tirtaagung.model.ResponseModel;
import com.rememberdev.tirtaagung.model.User;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;
import com.rememberdev.tirtaagung.retrofit.RetroServer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonfirmasiPemesananActivity extends AppCompatActivity {

    private SharedPreferences sp;

    //Menginisialisasi Menu Item pada Variable Array
    private String[] Item =
            {
            "Transfer BRI (1234567891)",
            "Transfer BNI (1234567891)",
            "Transfer Mandiri (1234567891)",
            "Transfer Bank Jatim (1234567891)",
            "Transfer Dana/OVO (1234567891)"
            };

    final Calendar calendar = Calendar.getInstance();
    EditText editText;

    TextView txtNamaLengkap, txtNoHP, txtJudul, txtFasilitas, txtLamaKapasitas, txtHarga;
    Button btnProsesPemesanan;

    private final String TAG = getClass().getSimpleName();

    String selectedMetode;
    final String statusPemesanan = "Belum Terkonfirmasi";

    String emailUser, namaLengkap, id_paket, judul, harga, lama_sewa, kapasitas, fasilitas, tanggal_pemesanan, gambar_satu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pemesanan);

        //cek session
        sp = getSharedPreferences("login_session", MODE_PRIVATE);
        emailUser = sp.getString("username", null);
        namaLengkap = sp.getString("nama_lengkap", null);

        Intent intent = getIntent();

        id_paket = intent.getStringExtra("id_paket");
        judul = intent.getStringExtra("judul");
        harga = intent.getStringExtra("harga");
        lama_sewa = intent.getStringExtra("lama_sewa");
        kapasitas = intent.getStringExtra("kapasitas");
        fasilitas = intent.getStringExtra("fasilitas");
        gambar_satu = intent.getStringExtra("gambar_satu");

        txtNamaLengkap = findViewById(R.id.txt_nama_lengkap);
        txtJudul = findViewById(R.id.txt_judul);
        txtFasilitas = findViewById(R.id.txt_fasilitas);
        txtLamaKapasitas = findViewById(R.id.txt_lama_dan_kapasitas);
        txtHarga = findViewById(R.id.txt_harga);
        btnProsesPemesanan = findViewById(R.id.btn_proses_pemesanan);

        txtNamaLengkap.setText(namaLengkap);
        txtJudul.setText(judul + " " + gambar_satu);
        txtFasilitas.setText(fasilitas);
        txtLamaKapasitas.setText(lama_sewa + " hari  |  " + kapasitas + " orang");
        txtHarga.setText(harga);

        final Spinner list = findViewById(R.id.listSpinner);
        //Inisialiasi Array Adapter dengan memasukkan String Array
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,Item);
        //Memasukan Adapter pada Spinner
        list.setAdapter(adapter);
        //Mengeset listener untuk mengetahui event/aksi saat item dipilih
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                selectedMetode = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView adapterView) {

            }
        });

        editText = findViewById(R.id.edt_tanggal_pick);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(KonfirmasiPemesananActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnProsesPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(KonfirmasiPemesananActivity.this)
                        .setTitle("Proses Pemesanan!")
                        .setMessage("Lanjutkan proses pemesanan!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                createDataPemesanan();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

    }

    private void updateLabel() {
        String format = "dd-MMM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        editText.setText(dateFormat.format(calendar.getTime()));
        tanggal_pemesanan = editText.getText().toString();
    }

    private void createDataPemesanan(){
        MethodHTTP ardData = RetroServer.konekRetrofit().create(MethodHTTP.class);
        Call<ResponseModel> simpandata = ardData.ardCreateDataPemesanan(emailUser, namaLengkap, Integer.parseInt(id_paket), judul, gambar_satu, tanggal_pemesanan, selectedMetode, statusPemesanan);

        simpandata.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(KonfirmasiPemesananActivity.this, "Kode : " + kode + "| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KonfirmasiPemesananActivity.this, ChartActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(KonfirmasiPemesananActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}