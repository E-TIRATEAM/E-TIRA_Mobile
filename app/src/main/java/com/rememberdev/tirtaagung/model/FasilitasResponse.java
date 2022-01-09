package com.rememberdev.tirtaagung.model;

import java.util.List;

public class FasilitasResponse {
    private int code;
    private String status;
    private List<Fasilitas> daftarFasilitas;

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

    public List<Fasilitas> getDaftarFasilitas() {
        return daftarFasilitas;
    }

    public void setDaftarFasilitas(List<Fasilitas> daftarFasilitas) {
        this.daftarFasilitas = daftarFasilitas;
    }
}
