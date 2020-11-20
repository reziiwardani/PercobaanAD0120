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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.adapte.JurusanAdapter;
import com.example.pelatihan3.models.Jurusan;

import java.util.ArrayList;
import java.util.List;

public class JurusanActivity extends AppCompatActivity {
    private Button btnAdd;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;

    SqlAdapter dbAdapter;
    List<Jurusan> jurusanList = new ArrayList<>();
    JurusanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurusan);
        btnAdd = findViewById(R.id.btnJurusan);
        recyclerView= findViewById(R.id.recycle2);
        progressBar= findViewById(R.id.progressBar2);
        textView=findViewById(R.id.text_empty2);
        //title bar
        getSupportActionBar().setTitle("JURUSAN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tombol add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JurusanActivity.this, AddJurusanActivity.class);
                startActivity(intent);


            }
        });

        getData();

    }

    public void getData() {
        //clear list
        jurusanList.clear();
        dbAdapter = new SqlAdapter(this);
        dbAdapter.open();
// tampung database menggunakan cursor sebagai perantara
        Cursor cursor ;
        cursor= dbAdapter.getDataJurusanAll();
        cursor.moveToFirst();
        showLoading(true);
        if(cursor.getCount()>0){ //looping
            showLoading(false);
            while (cursor.isAfterLast()==false){
                Jurusan jurusan = new Jurusan();
                jurusan.setId_jurusan(cursor.getInt(cursor.getColumnIndexOrThrow("id_jurusan")));
                jurusan.setNama_jurusan(cursor.getString(cursor.getColumnIndexOrThrow("nama_jurusan")));
                jurusan.setId_fakultas(cursor.getInt(cursor.getColumnIndexOrThrow("id_fakultas")));
                jurusan.setNama_fakultas(cursor.getString(cursor.getColumnIndexOrThrow("nama_fakultas")));

                //masukan ke list
                jurusanList.add(jurusan);
                cursor.moveToNext();
            }
            //masukan ke adapter jurusan
            adapter= new JurusanAdapter(this, jurusanList);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getBaseContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }else {
            showLoading(false);
            textView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Data Belum Ada", Toast.LENGTH_LONG).show();
        }
    }
    private void showLoading(boolean isLoading){
        if(isLoading) {
            btnAdd.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else {
            btnAdd.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

    }
}