package com.rememberdev.tirtaagung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.adapter.AdapterDaftarGambar;
import com.rememberdev.tirtaagung.model.DaftarGambar;
import com.rememberdev.tirtaagung.model.DaftarGambarResponse;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;
import com.rememberdev.tirtaagung.retrofit.RetroServer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainTirtaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private RecyclerView rvDataGambar;
    private RecyclerView.Adapter adDataGambar;
    private RecyclerView.LayoutManager lmDataGambar;
    private List<DaftarGambar> listGambar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tirta);

        rvDataGambar = findViewById(R.id.rv_auto_slide_image);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainTirtaActivity.this, 2, RecyclerView.VERTICAL, false);
        lmDataGambar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvDataGambar.setLayoutManager(gridLayoutManager);
        viewDataGambar();

        bottomNavigationView = findViewById(R.id.nav_bottom_view);
        //Set Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_chart:
                        startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }

    private void viewDataGambar() {
        MethodHTTP client = RetroServer.konekRetrofit().create(MethodHTTP.class);
        Call<DaftarGambarResponse> tampilDataGambar = client.requestGambar();

        tampilDataGambar.enqueue(new Callback<DaftarGambarResponse>() {
            @Override
            public void onResponse(Call<DaftarGambarResponse> call, retrofit2.Response<DaftarGambarResponse> response) {
                int code = response.body().getCode();
                String status = response.body().getStatus();
                Toast.makeText(MainTirtaActivity.this, "Kode : " + code+"| Pesan : " + status, Toast.LENGTH_SHORT).show();

                listGambar = response.body().getData();

                adDataGambar = new AdapterDaftarGambar(MainTirtaActivity.this, listGambar);
                rvDataGambar.setAdapter(adDataGambar);
                adDataGambar.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DaftarGambarResponse> call, Throwable t) {
                Toast.makeText(MainTirtaActivity.this, "gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainTirtaActivity.this)
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