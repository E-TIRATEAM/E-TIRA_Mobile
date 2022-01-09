package com.rememberdev.tirtaagung.model;

import java.util.List;

public class DaftarPaketResponse {
    private int code;
    private String status;
    private List<DaftarPaket> daftar_paket;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DaftarPaket> getDaftar_paket() {
        return daftar_paket;
    }

    public void setDaftar_paket(List<DaftarPaket> daftar_paket) {
        this.daftar_paket = daftar_paket;
    }
}
