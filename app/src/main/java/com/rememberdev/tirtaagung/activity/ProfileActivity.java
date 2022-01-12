package com.rememberdev.tirtaagung.activity;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.DataModel;
import com.rememberdev.tirtaagung.model.ResponseUpdateUser;
import com.rememberdev.tirtaagung.model.ResponseUser;
import com.rememberdev.tirtaagung.model.UserResponse;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;
import com.rememberdev.tirtaagung.retrofit.RetroServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private String emailUser;
    private List<DataModel> listUser;
    int varId;
    String varUsername, varNamaLengkap, varNomorHP, varPassword, varFotoProfil, varAlamat;

    int Xid;
    String XUsername, XNamaLengkap, XNomorHP, XPassword, XFotoProfil, XAlamat;

    SharedPreferences sp;

    BottomNavigationView bottomNavigationView;

    private ImageView imgProfil;
    private TextView txtNamaProfil, txtUsernameProfil, txtAlamatProfil, txtNomorHPProfil, txtPasswordProfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profil");
//        // Customize the back button
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        // showing the back button in action bar
//        actionBar.setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_toolbar_profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
            case R.id.nav_logout:
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Peringatan!")
                        .setMessage("Yakin untuk logout?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sp.edit().clear().commit();
                                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void actionInstagram(View view) {
        String urlNew = "https://www.instagram.com/wisata.tirta_agung/?utm_medium=copy_link";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlNew));
        startActivity(intent);
    }

    public void editNamaLengkap(View view) {
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        final View formsView = inflater.inflate(R.layout.edit_text_dialog, null, false);
        final EditText edt_edit = (EditText) formsView.findViewById(R.id.edt_edit);
        edt_edit.setHint(varNamaLengkap);

        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Masukkan Nama Lengkap")
                .setView(formsView)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nama_lengkap = String.valueOf(edt_edit.getText());
                        Xid = varId;
                        XUsername = varUsername;
                        XNamaLengkap = nama_lengkap;
                        XNomorHP = varNomorHP;
                        XPassword = varPassword;
                        XFotoProfil = varFotoProfil;
                        XAlamat = varAlamat;
                        UpdateDataUser();
                        Toast.makeText(ProfileActivity.this, nama_lengkap, Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        getSpesifikUser();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void editAlamat(View view) {
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        final View formsView = inflater.inflate(R.layout.edit_text_dialog, null, false);
        final EditText edt_edit = (EditText) formsView.findViewById(R.id.edt_edit);
        edt_edit.setHint(varAlamat);

        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Masukkan Alamat")
                .setView(formsView)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String alamat = String.valueOf(edt_edit.getText());
                        Xid = varId;
                        XUsername = varUsername;
                        XNamaLengkap = varNamaLengkap;
                        XNomorHP = varNomorHP;
                        XPassword = varPassword;
                        XFotoProfil = varFotoProfil;
                        XAlamat = alamat;
                        UpdateDataUser();
                        Toast.makeText(ProfileActivity.this, alamat, Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        getSpesifikUser();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void editNomorHp(View view) {
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        final View formsView = inflater.inflate(R.layout.edit_text_dialog, null, false);
        final EditText edt_edit = (EditText) formsView.findViewById(R.id.edt_edit);
        edt_edit.setHint(varNomorHP);

        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Masukkan Nomor HP")
                .setView(formsView)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String nomor_hp = String.valueOf(edt_edit.getText());
                        Xid = varId;
                        XUsername = varUsername;
                        XNamaLengkap = varNamaLengkap;
                        XNomorHP = nomor_hp;
                        XPassword = varPassword;
                        XFotoProfil = varFotoProfil;
                        XAlamat = varAlamat;
                        UpdateDataUser();
                        Toast.makeText(ProfileActivity.this, nomor_hp, Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                        getSpesifikUser();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public void editPassword(View view) {
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this.getSystemService(this.LAYOUT_INFLATER_SERVICE);

        final View formsView = inflater.inflate(R.layout.edit_password_dialog, null, false);
        final EditText edtPassBaru1 = (EditText) formsView.findViewById(R.id.edt_pass_baru_1);
        edtPassBaru1.setHint("Masukkan password baru");
        final EditText edtPassBaru2 = (EditText) formsView.findViewById(R.id.edt_pass_baru_2);
        edtPassBaru2.setHint("Konfirmasi password baru");

        new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Ubah Password")
                .setView(formsView)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String passwordBaru1 = String.valueOf(edtPassBaru1.getText());
                        String passwordBaru2 = String.valueOf(edtPassBaru2.getText());

                        if (passwordBaru1.equalsIgnoreCase(passwordBaru2)) {
                            Xid = varId;
                            XUsername = varUsername;
                            XNamaLengkap = varNamaLengkap;
                            XNomorHP = varNomorHP;
                            XPassword = passwordBaru1;
                            XFotoProfil = varFotoProfil;
                            XAlamat = varAlamat;
                            UpdateDataUser();
                            Toast.makeText(ProfileActivity.this, passwordBaru1, Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                            getSpesifikUser();
                        } else {
                            new AlertDialog.Builder(ProfileActivity.this)
                                    .setTitle("Peringatan!")
                                    .setMessage("Gagal mengubah!" + "\nPassword tidak sesuai!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                            }
                        }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void UpdateDataUser() {
        MethodHTTP methodHTTP = RetroServer.konekRetrofit().create(MethodHTTP.class);
        Call<ResponseUpdateUser> updateData = methodHTTP.ardUpdateDataUser(Xid, XNamaLengkap, XNomorHP, XUsername, XPassword, XFotoProfil, XAlamat);

        updateData.enqueue(new Callback<ResponseUpdateUser>() {
            @Override
            public void onResponse(Call<ResponseUpdateUser> call, Response<ResponseUpdateUser> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ProfileActivity.this, "Kode : " + kode + "| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseUpdateUser> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}