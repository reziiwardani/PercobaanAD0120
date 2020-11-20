package com.example.pelatihan3.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pelatihan3.models.Fakultas;
import com.example.pelatihan3.models.Jurusan;
import com.example.pelatihan3.models.Mahasiswa;

public class SqlAdapter {
    //buat nama db
    private static final String DB_NAME = "latihan.db";
    //buat versi db
    private static final int VERSION = 2;
    //buat context object lainnya
    private final Context context;
    private Databasehelper dbHelper;
    private SQLiteDatabase db;

    //Definisi tabel

    //TABLE FAKULTAS
    private static final String TABLE_FAKULTAS = "CREATE TABLE fakultas "+ "(id_fakultas INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nama_fakultas TEXT NOT NULL)";
    //TABLE JURUSAN
    private static final String TABLE_JURUSAN = "CREATE TABLE jurusan "+ "(id_jurusan INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_fakultas INTEGER, nama_jurusan TEXT NOT NULL)";
    //Table Mahasiswa
    private static final String TABLE_MAHASISWA = "CREATE TABLE mahasiswa "+ "(id_mahasiswa INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_jurusan INTEGER,"+
            "nim_mahasiswa TEXT NOT NULL, "+ //not null harus diisi, jika tidak diisi eror
            "nama_mahasiswa TEXT NOT NULL,"+
            "tanggal_lahir_mahasiswa TEXT , "+ //hanya text maka dapat dikosongkan
            "jenis_kelamin INTEGER Default 1)";
    private static final String FAKULTAS = "fakultas";
    private static final String JURUSAN = "jurusan";
    private static final String MAHASISWA = "mahasiswa";

    public SqlAdapter(Context context) {
        this.context = context;
    }
    private static class Databasehelper extends SQLiteOpenHelper{
        //constructor
        public Databasehelper(Context context){
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_FAKULTAS);
            db.execSQL(TABLE_JURUSAN);
            db.execSQL(TABLE_MAHASISWA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ FAKULTAS);
            db.execSQL("DROP TABLE IF EXISTS "+ JURUSAN);
            db.execSQL("DROP TABLE IF EXISTS "+ MAHASISWA);
            onCreate(db);

        }

    }
    //Metode untuk membuka database
    public SqlAdapter open() throws SQLException{
        dbHelper = new Databasehelper(context);
        db= dbHelper.getWritableDatabase();
        return this;
    }
    // metode untuk menutup koneksi database
    public void close(){
        dbHelper.close();
    }
    //query tabel2
    //tabel fakultas
    //insert data ke table
    public boolean insertFakultas(Fakultas fakultas){
        ContentValues cv= new ContentValues();
        cv.put("nama_fakultas",fakultas.getNama_fakultas());
        return db.insert("fakultas",null, cv)>0;
    }
    //delete all data fakultas
    public boolean deleteFakultasAll(){
        return db.delete("fakultas", null, null)>0;
    }
    //delete tabel fakultas by id
    public boolean deleteFakultasById(int id){
        return db.delete("fakultas", "id_fakultas=" + id, null)>0;
    }
    //metode untuk get data fakultas all
    public Cursor getDataFakultasAll(){
        String[] kolom = {"id_fakultas", "nama_fakultas"};
        return db.query("fakultas", kolom, null, null, null, null, "id_fakultas DESC");
    }
    public Cursor getFakultasById(int id){
        String[] kolom = {"id_fakultas", "nama_fakultas"};
        Cursor cursor = db.query("fakultas", kolom, "id_fakultas = "+id, null, null, null, null);
        if(cursor !=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    //update table fakultas
    public boolean updateFakultas (Fakultas fakultas){
        ContentValues cv = new ContentValues();
        cv.put("nama_fakultas", fakultas.getNama_fakultas());
        return db.update("fakultas", cv, "id_fakultas=" + fakultas.getId_fakultas(), null)>0;
    }
    // query2 untuk tabel jurusan
    //metode  insert data jurusan
    public boolean insertJurusan (Jurusan jurusan){
        ContentValues cv = new ContentValues();
        cv.put("id_fakultas", jurusan.getId_fakultas());
        cv.put("nama_jurusan", jurusan.getNama_jurusan());
        return db.insert("jurusan", null, cv)>0;
    }
    //delete all data jurusan
    public boolean deleteJurusanAll(){
        return db.delete("jurusan", null, null)>0;
    }
    //delete tabel fakultas by id
    public boolean deleteJurusanById(int id){
        return db.delete("jurusan", "id_jurusan=" + id, null)>0;
    }
    //metode untuk get data jurusan all
    public Cursor getDataJurusanAll(){
        String query = "SELECT * FROM fakultas f, jurusan j " +
                "WHERE f.id_fakultas = j.id_fakultas";
        return db.rawQuery(query,null);
    }
    //metode untuk get data jurusan by id
    public Cursor getDataJurusanById(int id){
        String query = "SELECT * FROM fakultas f, jurusan j"+
                "WHERE f.id_fakultas = j.id_fakultas" + "AND j.id_fakultas=" +id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
//    metode untuk update data jurusan
    public boolean updateDataJurusan(Jurusan j){
        ContentValues cv = new ContentValues();
        cv.put("id_fakultas", j.getId_fakultas());
        cv.put("nama_jurusan", j.getNama_jurusan());
        return db.update("jurusan", cv,
                "id_jurusan =" + j.getId_jurusan(), null)>0;
    }
    public boolean insertDataMahasiswa(Mahasiswa m){
        ContentValues cv = new ContentValues();
        cv.put("id_jurusan", m.getId_jurusan());
        cv.put("nim_mahasiswa", m.getNim_mahasiswa());
        cv.put("nama_mahasiswa", m.getNama_mahasiswa());
        cv.put("tanggal_lahir_mahasiswa", m.getTanggal_lahir_mahasiswa());
        cv.put("jenis_kelamin", m.getJenis_kelamin());
        return db.insert("mahasiswa", null, cv)>0;

    }
    public boolean deleteDataMahasiswaAll(){
        return db.delete("mahasiswa", null, null)>0;
    }
    public boolean deleteDataMahasiswaById(int id){
        return db.delete("mahasiswa", "id_mahasiswa = " + id, null)>0;
    }
    //metode untuk get data mahasiswa all
    public Cursor getDataMahasiswaAll(){
        String query = "SELECT * FROM mahasiswa m, jurusan j WHERE" +
                " m.id_jurusan = j.id_jurusan";
        return db.rawQuery(query,null);
    }
    //metode untuk get data mahasiswa by id
    public Cursor getDataMahasiswaById(int id){
        String query = "SELECT * FROM mahasiswa m, jurusan j WHERE"+
                " m.id_jurusan = j.id_jurusan AND m.id_mahasiswa=" +id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public boolean updateDataMahasiswa(Mahasiswa m){
        ContentValues cv = new ContentValues();
        cv.put("id_jurusan", m.getId_jurusan());
        cv.put("nim_mahasiswa", m.getNim_mahasiswa());
        cv.put("nama_mahasiswa", m.getNama_mahasiswa());
        cv.put("tanggal_lahir_mahasiswa", m.getTanggal_lahir_mahasiswa());
        cv.put("jenis_kelamin", m.getJenis_kelamin());
        return db.update("mahasiswa",cv, "id_mahasiswa =" + m.getId_mahasiswa(), null)>0;
    }

}
