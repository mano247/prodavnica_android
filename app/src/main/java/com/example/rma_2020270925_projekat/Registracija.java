package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Registracija extends AppCompatActivity {

    EditText korisnicko, sifra;
    Button registracija, nazad;
    List<Korisnik> korisnici;
    private List<String> korisnickaImena;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        KorisnikDatabaseHelper korisnikDatabaseHelper = new KorisnikDatabaseHelper(this);
        korisnici = korisnikDatabaseHelper.getKorisnici();

        korisnickaImena = new ArrayList<>();

        for(Korisnik k: korisnici){
            korisnickaImena.add(k.getKorisnickoIme());
        }

        korisnicko = findViewById(R.id.reg_korisnicko_input);
        sifra = findViewById(R.id.reg_lozinka_input);

        registracija = findViewById(R.id.dugme_registrujSe);
        nazad = findViewById(R.id.dugme_reg_odustani);

        i = new Intent(Registracija.this, MainActivity.class);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unesenoKorisnickoIme = korisnicko.getText().toString();
                String unesenaLozinka = sifra.getText().toString();

                if (unesenoKorisnickoIme.isEmpty() || unesenaLozinka.isEmpty()){
                    Toast.makeText(Registracija.this, "Unesite sve podatke!", Toast.LENGTH_SHORT).show();
                } else if (korisnickaImena.contains(unesenoKorisnickoIme)){
                    Toast.makeText(Registracija.this, "Uneto korisnicko ime vec postoji u sistemu. Izaberite novo korisnicko ime.", Toast.LENGTH_LONG).show();
                }else {
                    Korisnik noviKorisnik = new Korisnik(unesenoKorisnickoIme, unesenaLozinka);
                    korisnikDatabaseHelper.dodajKorisnika(noviKorisnik);
                    Toast.makeText(Registracija.this, "Registracija uspesna!", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
                }

            }
        });

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
                finish();
            }
        });

    }
}