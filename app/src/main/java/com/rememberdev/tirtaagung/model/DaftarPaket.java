package com.rememberdev.tirtaagung.model;

public class DaftarPaket {
    private int id_paket;
    private String judul, deskripsi, no_paket, lama_sewa, kapasitas, harga, fasilitas, gambar_satu, gambar_dua, gambar_tiga, gambar_empat;
    private float rating;

    public int getId_paket() {
        return id_paket;
    }

    public void setId_paket(int id_paket) {
        this.id_paket = id_paket;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNo_paket() {
        return no_paket;
    }

    public void setNo_paket(String no_paket) {
        this.no_paket = no_paket;
    }

    public String getLama_sewa() {
        return lama_sewa;
    }

    public void setLama_sewa(String lama_sewa) {
        this.lama_sewa = lama_sewa;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getGambar_satu() {
        return gambar_satu;
    }

    public void setGambar_satu(String gambar_satu) {
        this.gambar_satu = gambar_satu;
    }

    public String getGambar_dua() {
        return gambar_dua;
    }

    public void setGambar_dua(String gambar_dua) {
        this.gambar_dua = gambar_dua;
    }

    public String getGambar_tiga() {
        return gambar_tiga;
    }

    public void setGambar_tiga(String gambar_tiga) {
        this.gambar_tiga = gambar_tiga;
    }

    public String getGambar_empat() {
        return gambar_empat;
    }

    public void setGambar_empat(String gambar_empat) {
        this.gambar_empat = gambar_empat;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
