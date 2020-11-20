package com.example.pelatihan3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.adapte.FakultasAdapter;
import com.example.pelatihan3.adapte.JurusanAdapter;
import com.example.pelatihan3.adapte.MahasiswaAdapter;
import com.example.pelatihan3.models.Fakultas;
import com.example.pelatihan3.models.Jurusan;
import com.example.pelatihan3.models.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

public class MahasiswaActivity extends AppCompatActivity {
    private Button btnAdd;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;

    SqlAdapter dbAdapter;
    List<Mahasiswa> mahasiswaList = new ArrayList<>();
    MahasiswaAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        btnAdd = findViewById(R.id.btn3);
        recyclerView= findViewById(R.id.recycle3);
        progressBar= findViewById(R.id.progressBar3);
        textView=findViewById(R.id.text_empty2);
        //title bar
        getSupportActionBar().setTitle("Data Mahasiswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MahasiswaActivity.this, AddMahasiswaActivity2.class);
                startActivity(intent);


            }
        });
        getData();
    }
    public void getData() {
        //clear list
        mahasiswaList.clear();
        dbAdapter = new SqlAdapter(this);
        dbAdapter.open();
        Cursor cursor;
// tampung database menggunakan cursor sebagai perantara
        cursor= dbAdapter.getDataMahasiswaAll();
        cursor.moveToFirst();
        btnAdd.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if(cursor.getCount()>0){
            progressBar.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            while (cursor.isAfterLast()==false) {
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId_jurusan(cursor.getInt(cursor.getColumnIndexOrThrow("id_jurusan")));
                mahasiswa.setNama_jurusan(cursor.getString(cursor.getColumnIndexOrThrow("nama_jurusan")));
                mahasiswa.setNim_mahasiswa(cursor.getString(cursor.getColumnIndexOrThrow("nim_mahasiswa")));

                mahasiswa.setTanggal_lahir_mahasiswa(cursor.getString(cursor.getColumnIndexOrThrow("tanggal_lahir_mahasiswa")));
                mahasiswa.setJenis_kelamin(cursor.getInt(cursor.getColumnIndexOrThrow("jenis_kelamin")));

                mahasiswa.setId_mahasiswa(cursor.getInt(cursor.getColumnIndexOrThrow("id_mahasiswa")));
                mahasiswa.setNama_mahasiswa(cursor.getString(cursor.getColumnIndexOrThrow("nama_mahasiswa")));
                //masukin arraylist
                mahasiswaList.add(mahasiswa);
                //next data selanjutnya
                cursor.moveToNext();
            }
            adapter= new MahasiswaAdapter(this, mahasiswaList);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getBaseContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }else{
            progressBar.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), "Data belum ada", Toast.LENGTH_LONG).show();
//            textView.setVisibility(View.VISIBLE);
        }
        dbAdapter.close();



    }
}