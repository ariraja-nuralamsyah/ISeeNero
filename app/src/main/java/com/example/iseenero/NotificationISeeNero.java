package com.example.iseenero;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dataNotif6")
public class NotificationISeeNero {
    private String namaSungai;
    private String status;
    private String tingkatBahaya;

    @PrimaryKey
    @NonNull
    private String waktu;
    private String ketinggianSungai;
    private String phSungai;
    private String arusSungai;

    @NonNull

    public String getKetinggianSungai() {
        return ketinggianSungai;
    }

    public void setKetinggianSungai(String ketinggianSungai) {
        this.ketinggianSungai = ketinggianSungai;
    }

    public String getPhSungai() {
        return phSungai;
    }

    public void setPhSungai(String phSungai) {
        this.phSungai = phSungai;
    }

    public String getArusSungai() {
        return arusSungai;
    }

    public void setArusSungai(String arusSungai) {
        this.arusSungai = arusSungai;
    }

    public void setNamaSungai(@NonNull String namaSungai) {
        this.namaSungai = namaSungai;
    }

    public void setTingkatBahaya(String tingkatBahaya) {
        this.tingkatBahaya = tingkatBahaya;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationISeeNero(String namaSungai, String status, String tingkatBahaya,@NonNull  String waktu, String ketinggianSungai, String phSungai, String arusSungai) {
        this.namaSungai = namaSungai;
        this.status = status;
        this.tingkatBahaya = tingkatBahaya;
        this.waktu = waktu;
        this.ketinggianSungai = ketinggianSungai;
        this.phSungai = phSungai;
        this.arusSungai = arusSungai;
    }

    public String getNamaSungai() {
        return namaSungai;
    }
    public String getTingkatBahaya() {
        return tingkatBahaya;
    }
    public String getWaktu() {
        return waktu;
    }
    public String getStatus() {
        return status;
    }
}
