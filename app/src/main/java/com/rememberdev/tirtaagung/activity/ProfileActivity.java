package com.rememberdev.tirtaagung.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DataModel;
import com.rememberdev.tirtaagung.model.Pemesanan;
import com.rememberdev.tirtaagung.model.ResponseModel;
import com.rememberdev.tirtaagung.model.ResponseUser;
import com.rememberdev.tirtaagung.model.User;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;
import com.rememberdev.tirtaagung.retrofit.RetroServer;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private String emailUser;
    private List<DataModel> listUser;
    int varId;
    String varUsername, varNamaLengkap, varNomorHP, varPassword, varFotoProfil, varAlamat;

    SharedPreferences sp;

    BottomNavigationView bottomNavigationView;

    private ImageView imgProfil;
    private TextView txtNamaProfil, txtUsernameProfil, txtNomorHPProfil, txtAlamatProfil, txtPasswordProfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar();
        setTitle("Profil");

        initView();

        //cek session
        sp = getSharedPreferences("login_session", MODE_PRIVATE);
        String username = sp.getString("username", null);

        emailUser = username;
        getSpesifikUser();

        //Set Selected
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
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
                        startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_profile:
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        //TextView
        txtNamaProfil = findViewById(R.id.txt_nama_profil);
        txtUsernameProfil = findViewById(R.id.txt_username_profil);
        txtNomorHPProfil = findViewById(R.id.txt_telpon_profil);
        txtPasswordProfil = findViewById(R.id.txt_passwd_profil);
        txtAlamatProfil = findViewById(R.id.txt_alamat_profil);
        //ImageView
        imgProfil = findViewById(R.id.img_profil);
        //Bottom Navigation
        bottomNavigationView = findViewById(R.id.nav_bottom_view);
    }

    public void actionLogout(View view) {
        sp.edit().clear().commit();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(ProfileActivity.this)
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

    private void getSpesifikUser() {
        MethodHTTP methodHTTP = RetroServer.konekRetrofit().create(MethodHTTP.class);
        Call<ResponseUser> ambilData = methodHTTP.ambilSpesifikUser(emailUser);

        ambilData.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(ProfileActivity.this, "Kode : " + kode+"| Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                listUser = response.body().getData();

                varId = listUser.get(0).getId();
                varUsername = listUser.get(0).getEmail_user();
                varPassword = listUser.get(0).getPassword_user();
                varNamaLengkap = listUser.get(0).getNama_lengkap();
                varNomorHP = listUser.get(0).getNomor_hp();
                varFotoProfil = listUser.get(0).getFoto_profil();
                varAlamat = listUser.get(0).getAlamat();

                txtUsernameProfil.setText(varUsername);
                txtNamaProfil.setText(varNamaLengkap);
                txtNomorHPProfil.setText(varNomorHP);
                txtPasswordProfil.setText(varPassword);
                txtAlamatProfil.setText(varAlamat);

                Glide.with(ProfileActivity.this)
                        .load("" + GlobalVariabel.getGlobalUrlServer() + "images/" + varFotoProfil)
                        .fitCenter()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imgProfil);
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}