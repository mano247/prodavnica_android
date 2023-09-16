package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText korisnickoIme, lozinka;
    Button prijaviSe, registrujSe;
    Intent i, i2;
    List<Korisnik> korisnici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KorisnikDatabaseHelper korisnikDatabaseHelper = new KorisnikDatabaseHelper(this);
        korisnikDatabaseHelper.dodajKorisnika(new Korisnik("korisnik", "korisnik"));
        korisnici = korisnikDatabaseHelper.getKorisnici();


        korisnickoIme = findViewById(R.id.korisnicko_input);
        lozinka = findViewById(R.id.lozinka_input);

        prijaviSe = findViewById(R.id.prijavi_se_dugme);
        i = new Intent(this, KategorijeActivity.class);
        prijaviSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unetoKorisnickoIme = korisnickoIme.getText().toString();
                String unetaLozinka = lozinka.getText().toString();

                if (unetoKorisnickoIme.isEmpty() || unetaLozinka.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Unesite sve podatke.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean uspesnaPrijava = false;

                    for (Korisnik k : korisnici) {
                        if (unetoKorisnickoIme.equals(k.getKorisnickoIme()) && unetaLozinka.equals(k.getLozinka())) {
                            uspesnaPrijava = true;
                            break;
                        }
                    }

                    if (uspesnaPrijava) {
                        Toast.makeText(MainActivity.this, "Prijava uspesna!", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Prijava neuspesna! Pokusajte ponovo. Ako nemate nalog registrujte se", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        registrujSe = findViewById(R.id.dugme_registracija);
        i2 = new Intent(this, Registracija.class);
        registrujSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i2);
                finish();
            }
        });

    }
}