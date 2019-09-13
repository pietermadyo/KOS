package com.pam.pieter.tgs_plbtw_kos;

/**
 * Created by pieter on 11/27/2016.
 */
public class ModelKos
{
    private String id;
    private String namakos;
    private String pemilik;
    private String alamat;
    private String daerah;
    private double harga;
    private String fasilitas;
    private String picture;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamakos() {
        return namakos;
    }

    public void setNamakos(String namakos) {
        this.namakos = namakos;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDaerah() {
        return daerah;
    }

    public void setDaerah(String daerah) {
        this.daerah = daerah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
