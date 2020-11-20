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
import com.example.pelatihan3.models.Fakultas;

import java.util.ArrayList;
import java.util.List;

public class FakultasActivity2 extends AppCompatActivity {
    private Button btnAdd;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;



    private SqlAdapter dbAdapter;
    List<Fakultas> list= new ArrayList<>();
    private FakultasAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakultas2);
        getSupportActionBar().setTitle("FAKULTAS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAdd = findViewById(R.id.btn);
        recyclerView= findViewById(R.id.recycle);
        progressBar= findViewById(R.id.progressBar);
        textView=findViewById(R.id.text_empty);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FakultasActivity2.this, AddFakultasActivity.class);
                startActivity(intent);


            }
        });
        getData();



        //open koneksi database

    }
//public untuk bisa dipakai kelas lain
    public void getData() {
        list.clear();
        dbAdapter= new SqlAdapter(getBaseContext());
        dbAdapter.open();

        Cursor cursor;
        cursor= dbAdapter.getDataFakultasAll();
        cursor.moveToFirst();

        btnAdd.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        if(cursor.getCount()>0){
            progressBar.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            while (cursor.isAfterLast()==false) {
                Fakultas fakultas = new Fakultas();
                fakultas.setId_fakultas(cursor.getInt(cursor.getColumnIndexOrThrow("id_fakultas")));
                fakultas.setNama_fakultas(cursor.getString(cursor.getColumnIndexOrThrow("nama_fakultas")));
                //masukin arraylist
                list.add(fakultas);
                //next data selanjutnya
                cursor.moveToNext();
            }
            adapter= new FakultasAdapter(this, list);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getBaseContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }else{
            progressBar.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            Toast.makeText(getBaseContext(), "Data belum ada", Toast.LENGTH_LONG).show();
            textView.setVisibility(View.VISIBLE);
        }

    }
}