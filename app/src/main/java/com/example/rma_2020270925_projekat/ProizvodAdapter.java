package com.example.rma_2020270925_projekat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProizvodAdapter extends RecyclerView.Adapter<ProizvodAdapter.ViewHolder>{

    List<Proizvod> proizvodi;
    Context context;
    ProizvodiDatabaseHelper proizvodiDatabaseHelper;


    public ProizvodAdapter(List<Proizvod> proizvodi, Context context) {
        this.proizvodi = proizvodi;
        this.context = context;
        proizvodiDatabaseHelper = new ProizvodiDatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.proizvod, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Proizvod proizvod = proizvodi.get(position);

        holder.id.setText(Integer.toString(proizvod.getId()));
        holder.naziv.setText(proizvod.getNaziv());
        holder.proizvodjac.setText(proizvod.getProizvodjac());
        holder.kategorija.setText(proizvod.getKategorija().toString());
        holder.cena.setText(Double.toString(proizvod.getCena()));
        holder.kolicina.setText(Integer.toString(proizvod.getKolicina()));

        holder.izmeni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, IzmenaProizvoda.class);
                i.putExtra("proizvod_id", proizvod.getId().toString());
                i.putExtra("proizvod_naziv", proizvod.getNaziv());
                i.putExtra("proizvod_proizvodjac", proizvod.getProizvodjac());
                i.putExtra("proizvod_kategorija", proizvod.getKategorija().toString());
                i.putExtra("proizvod_cena", proizvod.getCena().toString());
                i.putExtra("proizvod_kolicina", proizvod.getKolicina());
                context.startActivity(i);
                ((Activity) context).finish();

            }
        });

        holder.ukloni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proizvodiDatabaseHelper.ukloniProizvod(proizvod.getId());
                proizvodi.remove(position);
                Toast.makeText(context, "Proizvod uspesno uklonjen.", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return proizvodi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, naziv, proizvodjac, kategorija, cena, kolicina;
        Button izmeni, ukloni;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.p_id);
            naziv = itemView.findViewById(R.id.p_naziv);
            proizvodjac = itemView.findViewById(R.id.p_proizvodjac);
            kategorija = itemView.findViewById(R.id.p_kategorija);
            cena = itemView.findViewById(R.id.p_cena);
            kolicina = itemView.findViewById(R.id.p_kolicina);

            izmeni = itemView.findViewById(R.id.p_izmeni);
            ukloni = itemView.findViewById(R.id.p_ukloni);

        }
    }
}
