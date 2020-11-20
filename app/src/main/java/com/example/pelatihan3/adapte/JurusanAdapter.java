package com.example.pelatihan3.adapte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelatihan3.AddFakultasActivity;
import com.example.pelatihan3.AddJurusanActivity;
import com.example.pelatihan3.FakultasActivity2;
import com.example.pelatihan3.Helper.Constans;
import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.JurusanActivity;
import com.example.pelatihan3.R;
import com.example.pelatihan3.models.Jurusan;

import java.util.ArrayList;
import java.util.List;

public class JurusanAdapter extends RecyclerView.Adapter<JurusanAdapter.Viewholder> {
    private Context context;
    private List<Jurusan> jurusanList = new ArrayList<>();
    public JurusanAdapter (Context context, List<Jurusan> jurusanList){
        this.context= context;
        this.jurusanList= jurusanList;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jurusan, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Jurusan jurusan = jurusanList.get(position);
        holder.txJurusan.setText(jurusan.getNama_jurusan());
        holder.txFakultas.setText(jurusan.getNama_fakultas());
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Edit Jurusan"+ jurusan.getNama_jurusan());
                final ArrayAdapter<String> adapterMenu = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item);
                adapterMenu.add("Edit");
                adapterMenu.add("Hapus");

                //masukan array ke dalam dialog
                builder.setAdapter(adapterMenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //pilih menu yang diklik
                        switch (which){
                            case 0:
                                //edit jurusan
                                Intent intent = new Intent(context, AddJurusanActivity.class);
                                intent.putExtra(Constans.IS_EDIT, true);
                                intent.putExtra(Constans.ID_JURUSAN, jurusan.getId_jurusan());
                                intent.putExtra(Constans.ID, jurusan.getId_fakultas());
                                intent.putExtra(Constans.NAMA, jurusan.getNama_jurusan());
                                context.startActivity(intent);
                                break;

                            case 1:
                                //hapus data jurusan
                                SqlAdapter dbAdapter = new SqlAdapter(context);
                                //buka koneksi
                                dbAdapter.open();
                                boolean hasil = dbAdapter.deleteJurusanById(jurusan.getId_jurusan());
                                dbAdapter.close();
                                if(hasil){
                                    Toast.makeText(context, "Hapus Berhasil", Toast.LENGTH_LONG).show();
                                    ((JurusanActivity)context).getData();
                                }else{
                                    Toast.makeText(context, "Hapus Gagal", Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                //penting!!!
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return jurusanList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        //item jurusan
        LinearLayout item;
        TextView txJurusan, txFakultas;

        public Viewholder(View view){
            super(view);
            item = view.findViewById(R.id.itemJurusan);
            txJurusan=view.findViewById(R.id.txJurusan);
            txFakultas=view.findViewById(R.id.txFakultas);

        }
    }

}
