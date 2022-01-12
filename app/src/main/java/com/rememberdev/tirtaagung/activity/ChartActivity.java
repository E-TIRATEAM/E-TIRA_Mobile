package com.rememberdev.tirtaagung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.adapter.AdapterDaftarGambar;
import com.rememberdev.tirtaagung.adapter.AdapterPemesananUser;
import com.rememberdev.tirtaagung.model.DaftarGambar;
import com.rememberdev.tirtaagung.model.PemesananUser;
import com.rememberdev.tirtaagung.model.ResponsePemesananUser;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;
import com.rememberdev.tirtaagung.retrofit.RetroServer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    private SharedPreferences sp;

    BottomNavigationView bottomNavigationView;
    String emailUser, namaLengkap;

    private RecyclerView rvDataPemesananUser;
    private RecyclerView.Adapter adDataPemesananUser;
    private RecyclerView.LayoutManager lmDataPemesananUser;
    private List<PemesananUser> listPemesananUser = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //cek session
        sp = getSharedPreferences("login_session", MODE_PRIVATE);
        emailUser = sp.getString("username", null);
        namaLengkap = sp.getString("nama_lengkap", null);

        swipeRefreshLayout = findViewById(R.id.swl_data);
        progressBar = findViewById(R.id.pb_data);
        rvDataPemesananUser = findViewById(R.id.rv_pemesanan);
        lmDataPemesananUser = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDataPemesananUser.setLayoutManager(lmDataPemesananUser);
        rvDataPemesananUser.setHasFixedSize(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                viewDataPemesananUser();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        bottomNavigationView = findViewById(R.id.nav_bottom_view);
        //Set Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_chart);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), MainTirtaActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_chart:
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDataPemesananUser();
    }

    private void viewDataPemesananUser() {
        progressBar.setVisibility(View.VISIBLE);
        MethodHTTP client = RetroServer.konekRetrofit().create(MethodHTTP.class);
        Call<ResponsePemesananUser> tampilListPemesananUser = client.ambilSpesifikPemesanan(emailUser);

        tampilListPemesananUser.enqueue(new Callback<ResponsePemesananUser>() {
            @Override
            public void onResponse(Call<ResponsePemesananUser> call, Response<ResponsePemesananUser> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(ChartActivity.this, "Kode : " + kode+"| Pesan : " + pesan, Toast.LENGTH_SHORT).show();

                listPemesananUser = response.body().getData();

                adDataPemesananUser = new AdapterPemesananUser(ChartActivity.this, listPemesananUser);
                rvDataPemesananUser.setAdapter(adDataPemesananUser);
                adDataPemesananUser.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponsePemesananUser> call, Throwable t) {
                Toast.makeText(ChartActivity.this, "gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ChartActivity.this)
                .setTitle("Peringatan!")
                .setMessage("tekan OK untuk menutup aplikasi!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}