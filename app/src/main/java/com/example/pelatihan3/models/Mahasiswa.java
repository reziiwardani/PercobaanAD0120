package com.example.pelatihan3.models;

public class Mahasiswa extends Jurusan{
    private int id_mahasiswa, jenis_kelamin;
    private String nama_mahasiswa, tanggal_lahir_mahasiswa, nim_mahasiswa;

    public String getNim_mahasiswa() {
        return nim_mahasiswa;
    }

    public void setNim_mahasiswa(String nim_mahasiswa) {
        this.nim_mahasiswa = nim_mahasiswa;
    }

    public int getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(int id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }


    public int getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(int jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNama_mahasiswa() {
        return nama_mahasiswa;
    }

    public void setNama_mahasiswa(String nama_mahasiswa) {
        this.nama_mahasiswa = nama_mahasiswa;
    }

    public String getTanggal_lahir_mahasiswa() {
        return tanggal_lahir_mahasiswa;
    }

    public void setTanggal_lahir_mahasiswa(String tanggal_lahir_mahasiswa) {
        this.tanggal_lahir_mahasiswa = tanggal_lahir_mahasiswa;
    }
}
