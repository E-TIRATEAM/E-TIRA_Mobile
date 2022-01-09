package com.rememberdev.tirtaagung.retrofit;

import com.rememberdev.tirtaagung.model.DaftarGambarResponse;
import com.rememberdev.tirtaagung.model.DaftarPaketResponse;
import com.rememberdev.tirtaagung.model.FasilitasResponse;
import com.rememberdev.tirtaagung.model.Request;
import com.rememberdev.tirtaagung.model.ResponseModel;
import com.rememberdev.tirtaagung.model.ResponsePemesananUser;
import com.rememberdev.tirtaagung.model.ResponseUser;
import com.rememberdev.tirtaagung.model.User;
import com.rememberdev.tirtaagung.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MethodHTTP {

    //get spesiffic using username and password
    @GET("Login.php?")
    Call<UserResponse> login(@Query("email") String email, @Query("password") String password);

    //submit user registration
    @POST("Registrasi_User.php")
    Call<Request> sendUser(@Body User user);

    //ambil daftar paket tirta agung
    @GET("Daftar_Paket_Full.php")
    Call<DaftarPaketResponse> requestDaftarPaket();

    //getSpesifikPaket
    @FormUrlEncoded
    @POST("Get_Spesifik_Paket.php")
    Call<DaftarPaketResponse> ambilSpesifikPaket(@Field("id_paket") int id_paket);

    //getFasilitas
    @GET("Daftar_Fasilitas.php")
    Call<FasilitasResponse> requestFasilitas();

    //tambah pemesanan
    @FormUrlEncoded
    @POST("Tambah_Pemesanan.php")
    Call<ResponseModel> ardCreateDataPemesanan(
            @Field("email_user") String email_user,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("id_paket") int id_paket,
            @Field("judul") String judul,
            @Field("gambar_satu") String gambar_satu,
            @Field("tanggal_pemesanan") String tanggal_pemesanan,
            @Field("metode_pembayaran") String metode_pembayaran,
            @Field("status_pemesanan") String status_pemesanan
    );

    //getDaftarGambar
    @GET("Daftar_Gambar.php")
    Call<DaftarGambarResponse> requestGambar();

    //getSpesifikUser
    @FormUrlEncoded
    @POST("Get_Spesifik_User_byUsername.php")
    Call<ResponseUser> ambilSpesifikUser(
            @Field("email_user") String emailUser
    );

    //getSpesifikPemesanan
    @FormUrlEncoded
    @POST("Get_Spesifik_Pemesanan.php")
    Call<ResponsePemesananUser> ambilSpesifikPemesanan(
            @Field("email_user") String emailUser
    );
}
