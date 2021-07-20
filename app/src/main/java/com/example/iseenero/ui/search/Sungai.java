package com.example.iseenero.ui.search;

public class Sungai {

    private String namaSungai;
    private String statusWaspada;
    private int ketinggian;
    private double pH;
    private int kecepatanArus;

    public Sungai(String namaSungai, String statusWaspada, int ketinggian, double pH, int kecepatanArus) {
        this.namaSungai = namaSungai;
        this.statusWaspada = statusWaspada;
        this.ketinggian = ketinggian;
        this.pH = pH;
        this.kecepatanArus = kecepatanArus;
    }

    public Sungai(){}

    public Sungai(String namaSungai)
    {
        this.namaSungai = namaSungai;
    }

    public String getNamaSungai() {
        return namaSungai;
    }

    public int getKetinggian() {
        return ketinggian;
    }

    public double getpH() {
        return pH;
    }

    public int getKecepatanArus() {
        return kecepatanArus;
    }

    public String getStatusWaspada() {
        return statusWaspada;
    }

    public void setStatusWaspada(String statusWaspada) {
        this.statusWaspada = statusWaspada;
    }

    public void setNamaSungai(String namaSungai) {
        this.namaSungai = namaSungai;
    }

    public void setKetinggian(int ketinggian) {
        this.ketinggian = ketinggian;
    }

    public void setpH(double pH) {
        this.pH = pH;
    }

    public void setKecepatanArus(int kecepatanArus) {
        this.kecepatanArus = kecepatanArus;
    }

}
