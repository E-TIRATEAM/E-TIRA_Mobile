package com.rememberdev.tirtaagung.model;

import java.util.List;

public class ResponsePemesananUser {
    private int kode;
    private String pesan;
    private List<PemesananUser> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<PemesananUser> getData() {
        return data;
    }

    public void setData(List<PemesananUser> data) {
        this.data = data;
    }
}
