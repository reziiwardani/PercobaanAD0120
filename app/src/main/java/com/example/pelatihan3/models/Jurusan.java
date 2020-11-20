package com.example.pelatihan3.models;

public class Jurusan extends Fakultas{
    private int id_jurusan, id_fakultas;
    private String nama_jurusan;

    public int getId_fakultas() {
        return id_fakultas;
    }

    public void setId_fakultas(int id_fakultas) {
        this.id_fakultas = id_fakultas;
    }

    public int getId_jurusan() {
        return id_jurusan;
    }

    public void setId_jurusan(int id_jurusan) {
        this.id_jurusan = id_jurusan;
    }

    public String getNama_jurusan() {
        return nama_jurusan;
    }

    public void setNama_jurusan(String nama_jurusan) {
        this.nama_jurusan = nama_jurusan;
    }
}
