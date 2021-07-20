package com.example.iseenero;

public class data_sungai {

    private String id_sungai;
    private String nama_sungai;
    private String ketinggian_sungai;
    private String ph_sungai;
    private String arus_sungai;
    private String tanggal;
    private String jam;
    private String key;

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    //Membuat Method Getter (Wajib), Untuk mendapatkan data NIM, Nama dan Jurusan
    public String getKey() { return key; }

    public String getId_sungai() {
        return id_sungai;
    }

    public String getNama_sungai() {
        return nama_sungai;
    }

    public String getKetinggian_sungai() {
        return ketinggian_sungai;
    }

    public String getPh_sungai() {
        return ph_sungai;
    }

    public String getArus_sungai() {
        return arus_sungai;
    }

    public void setId_sungai(String id_sungai) {
        this.id_sungai = id_sungai;
    }

    public void setNama_sungai(String nama_sungai) {
        this.nama_sungai = nama_sungai;
    }

    public void setKetinggian_sungai(String ketinggian_sungai) {
        this.ketinggian_sungai = ketinggian_sungai;
    }

    public void setPh_sungai(String ph_sungai) {
        this.ph_sungai = ph_sungai;
    }

    public void setArus_sungai(String arus_sungai) {
        this.arus_sungai = arus_sungai;
    }

    public void setKey(String key) {
        this.key = key;
    }

    //Membuat Konstuktor kosong untuk membaca data snapshot
    public data_sungai(){
    }

    public data_sungai(String id_sungai, String nama_sungai, String ketinggian_sungai, String ph_sungai, String arus_sungai, String tanggal, String jam) {
        this.id_sungai = id_sungai;
        this.nama_sungai = nama_sungai;
        this.ketinggian_sungai = ketinggian_sungai;
        this.ph_sungai = ph_sungai;
        this.arus_sungai = arus_sungai;
        this.tanggal = tanggal;
        this.jam = jam;
    }
}
