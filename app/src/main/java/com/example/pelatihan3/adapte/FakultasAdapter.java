package com.example.pelatihan3.adapte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelatihan3.AddFakultasActivity;
import com.example.pelatihan3.FakultasActivity2;
import com.example.pelatihan3.Helper.Constans;
import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.R;
import com.example.pelatihan3.models.Fakultas;

import java.util.List;

public class FakultasAdapter extends RecyclerView.Adapter<FakultasAdapter.Viewholder> {
    private Context context;
    private List<Fakultas> fakultasList;

    public FakultasAdapter(Context context, List<Fakultas> fakultasList){
        this.context= context;
        this.fakultasList=fakultasList;
    }
    @NonNull
    @Override
    //custom Adapter
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fakultas, parent, false);
        return new Viewholder(view);
    }

    @Override
    //buat panggil data namun looping
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Fakultas fakultas= fakultasList.get(position);
        holder.textNama.setText(fakultas.getNama_fakultas());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Edit Fakultas" + fakultas.getNama_fakultas());
                builder.setMessage("Action Data Fakultas");
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //untuk action hapus
                        //deklarasi db adapternya
                        SqlAdapter dbAdapter = new SqlAdapter(context);
                        //buka koneksi
                        dbAdapter.open();
                        boolean hasil = dbAdapter.deleteFakultasById(fakultas.getId_fakultas());
                        //tutup koneksi
                        dbAdapter.close();
                        if(hasil){
                            Toast.makeText(context, "Hapus Berhasil", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Hapus Gagal", Toast.LENGTH_LONG).show();
                        }
                        //refresh data
                        notifyDataSetChanged();
                        ((FakultasActivity2)context).getData();
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //untuk action edit
                        Intent intent = new Intent(context, AddFakultasActivity.class);
                        intent.putExtra(Constans.IS_EDIT, true);
                        intent.putExtra(Constans.ID, fakultas.getId_fakultas());
                        intent.putExtra(Constans.NAMA, fakultas.getNama_fakultas());
                        context.startActivity(intent);

                    }
                });
                builder.setNeutralButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // action batal
                        dialog.dismiss();
                    }

                });

                builder.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return fakultasList.size();
//        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView textNama;
        RelativeLayout item;
        public Viewholder(View view){
            super(view);
            textNama= view.findViewById(R.id.textNama);
            item = view.findViewById(R.id.item);
        }

    }
}
