package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//import android.widget.Toolbar;

public class PrikazProizvoda extends AppCompatActivity {

    Button dodajProizvod, nazad, odjava, pretragaDugme, resetPretrage;
    Intent i;
    LinearLayout linearLayout;
    String naslov;
    Toolbar tb;
    RecyclerView recyclerView;
    EditText pretragaInput;
    List<Proizvod> proizvodi;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_proizvoda);
        naslov = getIntent().getStringExtra("naslov");
        tb = findViewById(R.id.toolbar3);
        tb.setTitle(naslov);
        setSupportActionBar(tb);

        ProizvodiDatabaseHelper proizvodiDatabaseHelper = new ProizvodiDatabaseHelper(this);
        if (naslov.equals("Proizvodi")) {
            proizvodi = proizvodiDatabaseHelper.getProizvodi();
        } else {
            proizvodi = proizvodiDatabaseHelper.getProizvodiPoKategoriji(naslov);
        }

        recyclerView = findViewById(R.id.recycler_view_proizvodi);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        pretragaInput = findViewById(R.id.pretraga);
        pretragaDugme = findViewById(R.id.dugme_pretraga);
        resetPretrage = findViewById(R.id.dugme_reset_pretrage);

        generisiProizvode();

        dodajP();
        nazad();
        odjaviSe();

        pretraga();

    }

    public void generisiProizvode(){
        LinearLayout subLayout;
        TextView id, naziv, proizvodjac, kategorija, cena, kolicina;
        ProizvodiDatabaseHelper proizvodiDatabaseHelper = new ProizvodiDatabaseHelper(this);
        List<Proizvod> proizvodi = null;
        if (naslov.equals("Proizvodi")) {
            proizvodi = proizvodiDatabaseHelper.getProizvodi();
        }else{
            proizvodi = proizvodiDatabaseHelper.getProizvodiPoKategoriji(naslov);
        }

        if (proizvodi.size() > 0){
            ProizvodAdapter proizvodAdapter = new ProizvodAdapter(proizvodi, PrikazProizvoda.this);
            recyclerView.setAdapter(proizvodAdapter);

        }else {
            Toast.makeText(this, "Nema proizvoda u bazi.", Toast.LENGTH_SHORT).show();
        }
    }

    public void dodajP(){
        dodajProizvod = findViewById(R.id.dugme_dodaj_proizvod);
        dodajProizvod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(PrikazProizvoda.this, DodajProizvod.class);
                i.putExtra("naslov", naslov);
                startActivity(i);
                finish();
            }
        });
    }

    public void nazad(){
        nazad = findViewById(R.id.nazad_dugme);
        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(PrikazProizvoda.this, KategorijeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void odjaviSe(){
        odjava = findViewById(R.id.odjava_dugme);
        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(PrikazProizvoda.this, MainActivity.class);
                Toast.makeText(PrikazProizvoda.this, "Odjava uspesna!", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });
    }

    public void pretraga(){
        pretragaDugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tekstPretrage = pretragaInput.getText().toString().trim().toLowerCase();
                List<Proizvod> filtriraniProizvodi = new ArrayList<>();

                for (Proizvod p : proizvodi){
                    String naziv = p.getNaziv().toLowerCase();
                    String proizvodjac = p.getProizvodjac().toLowerCase();

                    if (naziv.contains(tekstPretrage) || proizvodjac.contains(tekstPretrage)){
                        filtriraniProizvodi.add(p);
                    }
                }

                if(filtriraniProizvodi.size() > 0){
                    ProizvodAdapter proizvodAdapter = new ProizvodAdapter(filtriraniProizvodi, PrikazProizvoda.this);
                    recyclerView.setAdapter(proizvodAdapter);
                }else {
                    Toast.makeText(PrikazProizvoda.this, "Nema rezultata pretrage", Toast.LENGTH_SHORT).show();
                }
            }
        });
        resetPretrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pretragaInput.setText("");
                ProizvodAdapter adapter = new ProizvodAdapter(proizvodi, PrikazProizvoda.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }





}