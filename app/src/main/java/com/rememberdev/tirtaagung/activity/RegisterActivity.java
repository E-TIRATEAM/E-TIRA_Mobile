package com.rememberdev.tirtaagung.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rememberdev.tirtaagung.MainActivity;
import com.rememberdev.tirtaagung.R;
import com.rememberdev.tirtaagung.global.GlobalVariabel;
import com.rememberdev.tirtaagung.model.Request;
import com.rememberdev.tirtaagung.model.User;
import com.rememberdev.tirtaagung.retrofit.MethodHTTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private int flagExit = 0;
    private ImageButton imgViewHidePassword, imgViewHideConfirmPassword;
    private EditText edtPasswordRegister, edtConfirmPasswordRegister, edtNamaLengkapRegister, edtNomorHPRegister, edtEmailRegister;

    private String typeConn = "";
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgViewHidePassword = findViewById(R.id.img_btn_view_hide_password);
        imgViewHideConfirmPassword = findViewById(R.id.img_btn_view_hide_confirm_password);
        edtPasswordRegister = findViewById(R.id.edt_password_register);
        edtConfirmPasswordRegister = findViewById(R.id.edt_confirm_password_register);
        edtNamaLengkapRegister = findViewById(R.id.edt_nama_lengkap_register);
        edtNomorHPRegister = findViewById(R.id.edt_nomor_hp_register);
        edtEmailRegister = findViewById(R.id.edt_email_register);

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            typeConn = extras.getString("typeConnection");
//        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(RegisterActivity.this)
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

    public void actionRegister(View view) {
        boolean isInputValid = false;

        if (edtNamaLengkapRegister.getText().toString().isEmpty()) {
            edtNamaLengkapRegister.setError("Tidak boleh kosong!");
            edtNamaLengkapRegister.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtNomorHPRegister.getText().toString().isEmpty()) {
            edtNomorHPRegister.setError("Tidak boleh kosong!");
            edtNomorHPRegister.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtEmailRegister.getText().toString().isEmpty()) {
            edtEmailRegister.setError("Tidak boleh kosong!");
            edtEmailRegister.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtPasswordRegister.getText().toString().isEmpty()) {
            edtPasswordRegister.setError("Tidak boleh kosong!");
            edtPasswordRegister.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtConfirmPasswordRegister.getText().toString().isEmpty()) {
            edtConfirmPasswordRegister.setError("Tidak boleh kosong!");
            edtConfirmPasswordRegister.requestFocus();
            isInputValid = false;
        } else {
            isInputValid = true;
        }

        if (edtConfirmPasswordRegister.getText().toString().equalsIgnoreCase(edtPasswordRegister.getText().toString())) {
            isInputValid = true;
        } else {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Peringatan!")
                    .setMessage("Password tidak sesuai!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
            isInputValid = false;
        }

        if (isInputValid) {
            User user= new User();
            user.setNama_lengkap(edtNamaLengkapRegister.getText().toString());
            user.setNomor_hp(edtNomorHPRegister.getText().toString());
            user.setEmail_user(edtEmailRegister.getText().toString());
            user.setPassword_user(edtPasswordRegister.getText().toString());

            registerUsingRetrofit(user);
        }
    }

    public void actionToLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void registerUsingRetrofit(User user) {
        ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setTitle(getString(R.string.app_name));
        proDialog.setMessage("Silahkan tunggu");
        proDialog.show();

        GlobalVariabel globalVariabel = new GlobalVariabel();
        String globalUrlServer = globalVariabel.getGlobalUrlServer();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(globalUrlServer)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        MethodHTTP client = retrofit.create(MethodHTTP.class);
        Call<Request> call = client.sendUser(user);

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                proDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getCode() == 201) {
                        Toast.makeText(getApplicationContext(), "Response : " + response.body().getStatus(), Toast.LENGTH_LONG).show();
                        finish();
                    } else if (response.body().getCode() == 406) {
                        Toast.makeText(getApplicationContext(), "Response : " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Response : " + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                Log.e(TAG, "Error : " + response.message());
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                proDialog.dismiss();
                Log.e(TAG, "Error2 : " + t.getMessage());
            }
        });
    }

    public void actionViewHidePassword(View view) {
        if (edtPasswordRegister.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            imgViewHidePassword.setImageResource(R.drawable.ic_view_pass);
            edtPasswordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edtPasswordRegister.setSelection(edtPasswordRegister.length());
        } else {
            imgViewHidePassword.setImageResource(R.drawable.ic_hide_pass);
            edtPasswordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtPasswordRegister.setSelection(edtPasswordRegister.length());
        }
    }

    public void actionViewHideConfirmPassword(View view) {
        if (edtConfirmPasswordRegister.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            imgViewHideConfirmPassword.setImageResource(R.drawable.ic_view_pass);
            edtConfirmPasswordRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            edtConfirmPasswordRegister.setSelection(edtConfirmPasswordRegister.length());
        } else {
            imgViewHideConfirmPassword.setImageResource(R.drawable.ic_hide_pass);
            edtConfirmPasswordRegister.setTransformationMethod(PasswordTransformationMethod.getInstance());
            edtConfirmPasswordRegister.setSelection(edtConfirmPasswordRegister.length());
        }
    }
}