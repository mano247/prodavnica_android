package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DodajProizvod extends AppCompatActivity {

    Proizvod.Kategorije[] kategorije = Proizvod.Kategorije.values();

    EditText naziv, proizvodjac, cena, kolicina;
    Spinner kategorijaSpinner;

    Button dodaj, odustani;
    String naslov;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_proizvod);

        naslov = getIntent().getStringExtra("naslov");

        naziv = findViewById(R.id.dp_naziv_input);
        proizvodjac = findViewById(R.id.dp_proizvodjac_input);
        kategorijaSpinner = findViewById(R.id.dp_spinner);
        cena = findViewById(R.id.dp_cena_input);
        kolicina = findViewById(R.id.dp_kolicina_input);

        spinner();

        dodaj();
        odustani();
    }

    public void spinner(){
        ArrayAdapter<Proizvod.Kategorije> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategorije);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategorijaSpinner.setAdapter(adapter);
        naslov.replace(" ", "_");

        if ("Proizvodi".equals(naslov)) {
            kategorijaSpinner.setSelection(0);
        } else {
            Proizvod.Kategorije kategorija = Proizvod.Kategorije.valueOf(naslov);
            int index = adapter.getPosition(kategorija);
            kategorijaSpinner.setSelection(index);
        }
    }

    public void dodaj(){
        dodaj = findViewById(R.id.dodaj_proizvod_dugme);
        i = new Intent(this, PrikazProizvoda.class);
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nazivStr = naziv.getText().toString();
                String proizvodjacStr = proizvodjac.getText().toString();
                Proizvod.Kategorije kategorija = (Proizvod.Kategorije) kategorijaSpinner.getSelectedItem();
                String cenaStr = cena.getText().toString();
                String kolicinaStr = kolicina.getText().toString();

                Double cenaD = null;
                try {
                    cenaD = Double.valueOf(cenaStr);
                } catch (NumberFormatException e){
                    Toast.makeText(DodajProizvod.this, "Unesite sve podatke", Toast.LENGTH_SHORT).show();
                }

                Integer kolicinaI = null;
                try {
                    kolicinaI = Integer.valueOf(kolicinaStr);
                } catch (NumberFormatException e){
                    Toast.makeText(DodajProizvod.this, "Unesite sve podatke", Toast.LENGTH_SHORT).show();
                }


                if (nazivStr.length() == 0 || proizvodjacStr.length() == 0 || cenaStr.length() == 0 || kolicinaStr.length() == 0){
                    Toast.makeText(DodajProizvod.this, "Unesite sve podatke", Toast.LENGTH_SHORT).show();
                }else{
                    ProizvodiDatabaseHelper proizvodiDatabaseHelper = new ProizvodiDatabaseHelper(DodajProizvod.this);
                    Proizvod proizvod = new Proizvod(nazivStr, proizvodjacStr, kategorija, cenaD, kolicinaI);
                    proizvodiDatabaseHelper.dodajProizvod(proizvod);
                    Toast.makeText(DodajProizvod.this, "Proizvod uspesno dodat", Toast.LENGTH_SHORT).show();

                    i.putExtra("naslov", naslov);
                    finish();
                    startActivity(i);

                }
            }

        });
    }

    public void odustani(){
        odustani = findViewById(R.id.odustani_dugme);
        i = new Intent(this, PrikazProizvoda.class);
        odustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String naslov = "Proizvodi";
                i.putExtra("naslov", naslov);
                startActivity(i);
                finish();
            }
        });
    }
}