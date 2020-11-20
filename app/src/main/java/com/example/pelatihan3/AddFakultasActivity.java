package com.example.pelatihan3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pelatihan3.Helper.Constans;
import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.models.Fakultas;

public class AddFakultasActivity extends AppCompatActivity {
    private EditText et1;
    private Button submit;
    private TextView textPilih;


    //untuk keperluan Edit
    private boolean isEdit = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fakultas);

        textPilih= findViewById(R.id.tv1);
//        spinner=findViewById(R.id.spinner);
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //set adapter ke spinner
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                textPilih.setText(list[position]);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        et1 = findViewById(R.id.et1);
        submit= findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanData();
            }



        });
        isEdit =getIntent().getBooleanExtra(Constans.IS_EDIT, false);
        if(isEdit){
            id=getIntent().getIntExtra(Constans.ID, 0);
            String nama= getIntent().getStringExtra(Constans.NAMA);
            et1.setText(nama);
            submit.setText("Update");

        }


    }
    private void simpanData() {
        et1.setError(null);
        boolean cancel = false;
        View focusView = null;
        if(et1.getText().toString().isEmpty()){
            et1.setError("Nama harus diisii");
            cancel=true;
            focusView=et1;
        }
        if (cancel) {

            focusView.requestFocus();
        }else{
            SqlAdapter dbAdapter = new SqlAdapter(getBaseContext());
            Fakultas fakultas = new Fakultas();
            fakultas.setNama_fakultas(et1.getText().toString());

            dbAdapter.open();
            //kondisi edit cek apakah edit atau tambah

            if (isEdit){
                fakultas.setId_fakultas(id);
                boolean hasil = dbAdapter.updateFakultas(fakultas);
                if(hasil){
                    Toast.makeText(this, "Update Data berhasil", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, FakultasActivity2.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this, "Update Gagal", Toast.LENGTH_LONG).show();
                }
                dbAdapter.close();
            }else {
                //kondisi insert
                boolean hasil = dbAdapter.insertFakultas(fakultas);
                if(hasil){
                    Toast.makeText(getBaseContext(), "Simpan", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                    Intent intent= new Intent(getBaseContext(), FakultasActivity2.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getBaseContext(), "Gagal", Toast.LENGTH_LONG).show();
                    dbAdapter.close();
                }


            }
        }
    }
}