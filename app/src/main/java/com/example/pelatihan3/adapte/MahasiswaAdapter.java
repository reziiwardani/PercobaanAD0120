package com.example.pelatihan3.adapte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pelatihan3.AddFakultasActivity;
import com.example.pelatihan3.AddMahasiswaActivity2;
import com.example.pelatihan3.FakultasActivity2;
import com.example.pelatihan3.Helper.Constans;
import com.example.pelatihan3.Helper.SqlAdapter;
import com.example.pelatihan3.MahasiswaActivity;
import com.example.pelatihan3.R;
import com.example.pelatihan3.models.Jurusan;
import com.example.pelatihan3.models.Mahasiswa;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.Viewholder> {
    private Context context;
    private List<Mahasiswa> mahasiswaList;
    public MahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList){
        this.context= context;
        this.mahasiswaList=mahasiswaList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final Mahasiswa mahasiswa = mahasiswaList.get(position);
        holder.tvUser.setText(mahasiswa.getNama_mahasiswa());
        holder.tvJurusan.setText(mahasiswa.getNama_jurusan());

//        holder.tvJk.setText(mahasiswa.getJenis_kelamin());
//        holder.tvTgl.setText(mahasiswa.getTanggal_lahir_mahasiswa());


        holder.tvNim.setText(mahasiswa.getNim_mahasiswa());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Data " + mahasiswa.getNama_mahasiswa());
                builder.setMessage("Data akan diberi tindakan..");
                builder.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //untuk action hapus
                        //deklarasi db adapternya
                        SqlAdapter dbAdapter = new SqlAdapter(context);
                        //buka koneksi
                        dbAdapter.open();
                        boolean hasil = dbAdapter.deleteDataMahasiswaById(mahasiswa.getId_mahasiswa());
                        //tutup koneksi
                        dbAdapter.close();
                        if(hasil){
                            Toast.makeText(context, "Hapus Berhasil", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Hapus Gagal", Toast.LENGTH_LONG).show();
                        }
                        //refresh data
                        notifyDataSetChanged();
                        ((MahasiswaActivity)context).getData();
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //untuk action edit
                        Intent intent = new Intent(context, AddMahasiswaActivity2.class);
                        intent.putExtra(Constans.IS_EDIT, true);
                        intent.putExtra(Constans.ID_MAHASISWA, mahasiswa.getId_mahasiswa());
                        intent.putExtra(Constans.NAMA, mahasiswa.getNama_mahasiswa());
                        intent.putExtra(Constans.NIM, mahasiswa.getNim_mahasiswa());
                        intent.putExtra(Constans.ID_JURUSAN, mahasiswa.getId_jurusan());

                        intent.putExtra(Constans.TANGGAL_LAHIR, mahasiswa.getTanggal_lahir_mahasiswa());
                        intent.putExtra(Constans.JENIS_KELAMIN, mahasiswa.getJenis_kelamin());

                        context.startActivity(intent);
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        RelativeLayout item;
        TextView tvUser, tvJurusan, tvNim, tvTgl, tvJk;
        public Viewholder(View view){
            super(view);
            item = view.findViewById(R.id.item2);
            tvUser=view.findViewById(R.id.tvUser);
            tvJurusan=view.findViewById(R.id.tvJurusan);
            tvNim =view.findViewById(R.id.tvNim);
//            tvJk =view.findViewById(R.id.tvJk);
//            tvTgl =view.findViewById(R.id.tvTgl);


        }
    }

}
