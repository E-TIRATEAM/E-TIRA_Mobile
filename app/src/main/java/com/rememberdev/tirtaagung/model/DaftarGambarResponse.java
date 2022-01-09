package com.rememberdev.tirtaagung.model;

import java.util.List;

public class DaftarGambarResponse {
    private int code;
    private String status;
    private List<DaftarGambar> data;

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

    public List<DaftarGambar> getData() {
        return data;
    }

    public void setData(List<DaftarGambar> data) {
        this.data = data;
    }
}
