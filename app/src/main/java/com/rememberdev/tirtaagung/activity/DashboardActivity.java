package com.rememberdev.tirtaagung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.opengl.GLES10;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.adapter.AdapterDaftarPaket;
import com.rememberdev.tirtaagung.adapter.AdapterFasilitas;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DaftarPaket;
import com.rememberdev.tirtaagung.model.DaftarPaketResponse;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;
import com.rememberdev.tirtaagung.retrofit.RetroServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class DashboardActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private Handler sliderHandler = new Handler();

    private ViewPager2 viewPager2;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap> listData;

    private AdapterFasilitas adapterFasilitas;
    private RequestQueue requestQueueFasilitas;
    private StringRequest stringRequestFasilitas;
    private ArrayList<HashMap> listFasilitas;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        /**View Pager Daftar Paket*/
        showRecyclerList();

        viewPager2 = findViewById(R.id.view_pager_tirta);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(6);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        }));
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        recyclerView = findViewById(R.id.rv_fasilitas);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(DashboardActivity.this, 1, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        showRecyclerListFasilitas();

        /**Bottom Navigation Handle*/
        bottomNavigationView = findViewById(R.id.nav_bottom_view);
        //Set Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
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
                        return true;
                    case R.id.nav_chart:
                        startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                        overridePendingTransition(0,0);
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

    private void showRecyclerListFasilitas() {
        String url = GlobalVariabel.getGlobalUrlServer() + "Daftar_Fasilitas.php";
        requestQueueFasilitas = Volley.newRequestQueue(DashboardActivity.this);
        listFasilitas = new ArrayList<>();

        stringRequestFasilitas = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id", json.getString("id"));
                        hashMap.put("nama", json.getString("nama"));
                        hashMap.put("deskripsi", json.getString("deskripsi"));
                        hashMap.put("ikon", json.getString("ikon"));
                        listFasilitas.add(hashMap);

                        adapterFasilitas = new AdapterFasilitas(DashboardActivity.this, listFasilitas);
                        recyclerView.setAdapter(adapterFasilitas);
                        adapterFasilitas.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueueFasilitas.add(stringRequestFasilitas);
    }

    public void showRecyclerList() {
        String url = GlobalVariabel.getGlobalUrlServer() + "Daftar_Paket_Full.php";
        requestQueue = Volley.newRequestQueue(DashboardActivity.this);
        listData = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("id_paket", json.getString("id_paket"));
                        hashMap.put("judul", json.getString("judul"));
                        hashMap.put("deskripsi", json.getString("deskripsi"));
                        hashMap.put("rating", json.getString("rating"));
                        hashMap.put("no_paket", json.getString("no_paket"));
                        hashMap.put("lama_sewa", json.getString("lama_sewa"));
                        hashMap.put("kapasitas", json.getString("kapasitas"));
                        hashMap.put("harga", json.getString("harga"));
                        hashMap.put("fasilitas", json.getString("fasilitas"));
                        hashMap.put("gambar_satu", json.getString("gambar_satu"));
                        hashMap.put("gambar_dua", json.getString("gambar_dua"));
                        hashMap.put("gambar_tiga", json.getString("gambar_tiga"));
                        hashMap.put("gambar_empat", json.getString("gambar_empat"));
                        listData.add(hashMap);

                        AdapterDaftarPaket list = new AdapterDaftarPaket(DashboardActivity.this, listData, viewPager2);

                        viewPager2.setAdapter(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }
}