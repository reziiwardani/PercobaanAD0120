package com.example.pelatihan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.pelatihan3.models.Mahasiswa;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout akun7, akun8, akun9, akun10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        akun7 = findViewById(R.id.akun7);
        akun8 = findViewById(R.id.akun8);
        akun9 = findViewById(R.id.akun9);
        akun10 = findViewById(R.id.akun10);

        akun7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FakultasActivity2.class);
                startActivity(intent);


            }
        });
        akun8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JurusanActivity.class);
                startActivity(intent);


            }
        });
        akun9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
                startActivity(intent);


            }
        });
        akun10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewAcitivity.class);
                intent.putExtra("header", "PT MSO");
                intent.putExtra("url", "https://www.kompas.com/" );
                startActivity(intent);


            }
        });



    }
}