package com.example.pelatihan3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelatihan3.Helper.Constans;
import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.models.Fakultas;
import com.example.pelatihan3.models.Jurusan;
import com.example.pelatihan3.models.Mahasiswa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddMahasiswaActivity2 extends AppCompatActivity {
    private Spinner spinner2, spinner3;
    private EditText etNim, etNama, etTgl;
    private Button btnSubmit;


    //setting datatimepicker
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    DatePickerDialog datePickerDialog;
    Calendar dateCalender;
    private int mYear, mMonth, mDay;

    //list jurusan
    private ArrayList<Jurusan> dataJurusan= new ArrayList<>();
    private ArrayList<String> namaJurusan = new ArrayList<>();
    private int jurusanSelected=0;
    //list jenis kelamin
    private String[] listJenis= {"Laki-laki", "Perempuan"};
    private int jenisSelected = 0;



    //untuk set Edit
    private boolean isEdit=false;
    private int idMahasiswa, idJenisKelamin, idJurusan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mahasiswa2);
        spinner2=findViewById(R.id.spinner3);
        spinner3=findViewById(R.id.spinner4);
        etNim=findViewById(R.id.etNim);
        etNama=findViewById(R.id.etNama);
        etTgl=findViewById(R.id.etTgl);
        btnSubmit=findViewById(R.id.submit3);

        isEdit = getIntent().getBooleanExtra(Constans.IS_EDIT, false);
        getSupportActionBar().setTitle("Data " );
        if(isEdit){
            getSupportActionBar().setTitle("Edit Data Mahasiswa");
            idMahasiswa= getIntent().getIntExtra(Constans.ID_MAHASISWA, 0);
            idJurusan= getIntent().getIntExtra(Constans.ID_JURUSAN,0);
            idJenisKelamin = getIntent().getIntExtra(Constans.JENIS_KELAMIN, 0);
//            Log.e("TAG", getIntent().getStringExtra(Constans.NAMA));

            jenisSelected = idJenisKelamin;

            String nama= getIntent().getStringExtra(Constans.NAMA);
            String nim_mahasiswa = getIntent().getStringExtra(Constans.NIM);
            String tglLahir = getIntent().getStringExtra(Constans.TANGGAL_LAHIR);
            Log.e("TAG", "tanggal"+ tglLahir);

            spinner3.setSelection(idJenisKelamin);
            etNama.setText(nama);
            etNim.setText(nim_mahasiswa);
            etTgl.setText(tglLahir);
            btnSubmit.setText("Update");

        }
// seuo data spinner jurusan
        getDataJurusan();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Jurusan jurusan= dataJurusan.get(position);
                idJurusan = jurusan.getId_jurusan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        isEdit = getIntent().getBooleanExtra(Constans.IS_EDIT, false);
//        getSupportActionBar().setTitle("Tambah Mahasiswa");
//        getDataJurusan();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Jurusan jurusan =dataJurusan.get(position);
                idJurusan= jurusan.getId_jurusan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Pembuatan spinner3 untuk jenis kelamin

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listJenis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set adapter ke spinner
        spinner3.setAdapter(adapter);
        // agar saat edit jk tetap tersimpan
        spinner3.setSelection(jenisSelected, true);

        //action pada spinner
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisSelected = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }
        });


        //set tgl
        setTgl();
    }
    private void simpanData(){
        //handle error edittext kosong
        etNim.setError(null);
        etNama.setError(null);
        etTgl.setError(null);
        boolean cancel = false;
        View focusView = null;

        if(etNim.getText().toString().isEmpty()){
            etNim.setError("Nama jurusan harus diisi");
            cancel = true;
            focusView= etNim;
        }
        if(etNama.getText().toString().isEmpty()){
            etNama.setError("Nama jurusan harus diisi");
            cancel = true;
            focusView= etNama;
        }
        if(etTgl.getText().toString().isEmpty()){
            etTgl.setError("Harus diisi");
            cancel = true;
            focusView= etTgl;
        }
        if(cancel){
            focusView.requestFocus();
        }else {
            SqlAdapter dbAdapter = new SqlAdapter(this);
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim_mahasiswa(etNim.getText().toString());
            mahasiswa.setNama_mahasiswa(etNama.getText().toString());
            mahasiswa.setTanggal_lahir_mahasiswa(etTgl.getText().toString());
            mahasiswa.setId_jurusan(idJurusan);
            mahasiswa.setJenis_kelamin(jenisSelected);
            dbAdapter.open();
            if(isEdit){
                //untuk edit
                mahasiswa.setId_mahasiswa(idMahasiswa);
                boolean hasil = dbAdapter.updateDataMahasiswa(mahasiswa);
                if (hasil){
                    Toast.makeText(this, "Simpan Data Berhasil", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                    Intent intent= new Intent(AddMahasiswaActivity2.this, MahasiswaActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getBaseContext(), "Data Gagal disimpan", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                }

            }else{
                boolean hasil = dbAdapter.insertDataMahasiswa(mahasiswa);
                if (hasil){
                    Toast.makeText(this, "Simpan Data Berhasil", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                    Intent intent= new Intent(AddMahasiswaActivity2.this, MahasiswaActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getBaseContext(), "Data Gagal disimpan", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                }
            }
        }
    }




    private void setTgl(){
        etTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentDate = Calendar.getInstance();
                mYear = mCurrentDate.get(Calendar.YEAR);
                mMonth = mCurrentDate.get(Calendar.MONTH);
                mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddMahasiswaActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateCalender = Calendar.getInstance();
                        dateCalender.set(year, month, dayOfMonth);
                        etTgl.setText(formatter.format(dateCalender.getTime()));
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.setTitle("Pilih Tgl");
                datePickerDialog.show();
            }
        });

    }

    //fungsi dari getDataJurusan diatas
    private void getDataJurusan() {
        SqlAdapter dbAdapter = new SqlAdapter(this);
        dbAdapter.open();
        Cursor cursor;
        cursor=dbAdapter.getDataJurusanAll();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            int i =0;
            while (cursor.isAfterLast()==false){
                Jurusan jurusan = new Jurusan();
                jurusan.setId_jurusan(cursor.getInt(cursor.getColumnIndexOrThrow("id_jurusan")));
                jurusan.setNama_jurusan(cursor.getString(cursor.getColumnIndexOrThrow("nama_jurusan")));
                dataJurusan.add(jurusan);
                namaJurusan.add(jurusan.getNama_jurusan());
                //ntuk keperluan edit
                if(isEdit){
                    if(idJurusan==jurusan.getId_jurusan()){
                        jurusanSelected=i;
                    }
                }
                cursor.moveToNext();
                i++;
            }
        }else{
            Toast.makeText(this, "Data Fakultas Belum Ada", Toast.LENGTH_LONG).show();
        }
        //masukan data fakultas ke adapter

        ArrayAdapter arrayAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item, namaJurusan);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setSelection(jurusanSelected, true);
    }
}