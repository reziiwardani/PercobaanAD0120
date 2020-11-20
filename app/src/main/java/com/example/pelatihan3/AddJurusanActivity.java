package com.example.pelatihan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pelatihan3.Helper.Constans;
import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.models.Fakultas;
import com.example.pelatihan3.models.Jurusan;

import java.util.ArrayList;
import java.util.List;

public class AddJurusanActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText editJurusan;
    private Button btnSubmit;

    private boolean isEdit = false;
    private int id;
    private int idFakultas=1;
    //untuk isi data spineer fakulatas
//    private ArrayList<Fakultas> listData= new ArrayList<>();
//    private ArrayList<String> namaFakultas = new ArrayList<>();
    private List<Fakultas> listData= new ArrayList<>();
    private List<String> namaFakultas = new ArrayList<>();

    //untuk dapatkan id fakultas
    private int fakultasSelected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jurusan);
        spinner=findViewById(R.id.spinner);
        editJurusan=findViewById(R.id.et2);
        btnSubmit=findViewById(R.id.submit2);
        //get intent edit
        isEdit = getIntent().getBooleanExtra(Constans.IS_EDIT, false);
        getSupportActionBar().setTitle("Tambah Jurusan");
        if(isEdit){
            getSupportActionBar().setTitle("Edit Jurusan");
            id= getIntent().getIntExtra(Constans.ID_JURUSAN, 0);
            idFakultas= getIntent().getIntExtra(Constans.ID,0);
            String nama= getIntent().getStringExtra(Constans.NAMA);
            editJurusan.setText(nama);
            btnSubmit.setText("Update");

        }
        getDataFakultas();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fakultas fakultas= listData.get(position);
                idFakultas = fakultas.getId_fakultas();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //handle action simpan
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }
        });

    }
    private void simpanData(){
        //handle error edittext kosong
        editJurusan.setError(null);
        boolean cancel = false;
        View focusView = null;

        if(editJurusan.getText().toString().isEmpty()){
            editJurusan.setError("Nama jurusan harus diisi");
            cancel = true;
            focusView= editJurusan;
        }
        if(cancel){
            focusView.requestFocus();
        }else {
            SqlAdapter dbAdapter = new SqlAdapter(this);
            Jurusan jurusan = new Jurusan();
            jurusan.setNama_jurusan(editJurusan.getText().toString());
            jurusan.setId_fakultas(idFakultas);
            dbAdapter.open();
        if(isEdit){
            //untuk edit
            jurusan.setId_jurusan(id);
            boolean hasil = dbAdapter.updateDataJurusan(jurusan);
            if (hasil){
                Toast.makeText(this, "Simpan Data Berhasil", Toast.LENGTH_LONG).show();
                dbAdapter.close();
                Intent intent= new Intent(AddJurusanActivity.this, JurusanActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getBaseContext(), "Data Gagal disimpan", Toast.LENGTH_LONG).show();
                dbAdapter.close();
            }

            }else{
                boolean hasil = dbAdapter.insertJurusan(jurusan);
                if (hasil){
                    Toast.makeText(this, "Simpan Data Berhasil", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                    Intent intent= new Intent(AddJurusanActivity.this, JurusanActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getBaseContext(), "Data Gagal disimpan", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                }
            }
        }
    }

    private void getDataFakultas() {
        SqlAdapter dbAdapter = new SqlAdapter(this);
        dbAdapter.open();
        Cursor cursor;
        cursor=dbAdapter.getDataFakultasAll();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            int i =0;
            while (cursor.isAfterLast()==false){
                Fakultas fakultas = new Fakultas();
                fakultas.setId_fakultas(cursor.getInt(cursor.getColumnIndexOrThrow("id_fakultas")));
                fakultas.setNama_fakultas(cursor.getString(cursor.getColumnIndexOrThrow("nama_fakultas")));
                listData.add(fakultas);
                namaFakultas.add(fakultas.getNama_fakultas());
                //ntuk keperluan edit
                if(isEdit){
                    if(idFakultas==fakultas.getId_fakultas()){
                        fakultasSelected=i;
                    }
                }
                cursor.moveToNext();
                i++;
            }
        }else{
            Toast.makeText(this, "Data Fakultas Belum Ada", Toast.LENGTH_LONG).show();
        }
        //masukan data fakultas ke adapter

        ArrayAdapter arrayAdapter = new ArrayAdapter (this, android.R.layout.simple_spinner_item, namaFakultas);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(fakultasSelected, true);
    }
}