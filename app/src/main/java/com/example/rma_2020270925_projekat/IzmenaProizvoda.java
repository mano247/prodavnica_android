package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class IzmenaProizvoda extends AppCompatActivity {
    EditText naziv, proizvodjac, cena, kolicina;
    Spinner kategorija;
    Proizvod.Kategorije[] kategorije = Proizvod.Kategorije.values();
    Button izmeniProizvod, odustaniOdIzmene;
    Integer id_proizvoda;
    Intent i;
    String naslov;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmena_proizvoda);

        naslov = getIntent().getStringExtra("naslov");

        odustaniOdIzmene = findViewById(R.id.izmena_odustani);
        izmeniProizvod = findViewById(R.id.izmeni_proizvod_dugme);

        naziv = findViewById(R.id.ip_naziv_input);
        proizvodjac = findViewById(R.id.ip_proizvodjac_input);
        kategorija = findViewById(R.id.ip_spinner);
        cena = findViewById(R.id.ip_cena_input);
        kolicina = findViewById(R.id.ip_kolicina_input);

        String proizvod_id = getIntent().getStringExtra("proizvod_id");
        id_proizvoda = Integer.parseInt(proizvod_id);
        String proizvod_naziv = getIntent().getStringExtra("proizvod_naziv");
        String proizvod_proizvodjac = getIntent().getStringExtra("proizvod_proizvodjac");
        String proizvod_kategorija = getIntent().getStringExtra("proizvod_kategorija");
        String proizvod_cena = getIntent().getStringExtra("proizvod_cena");
        int proizvod_kolicina = getIntent().getIntExtra("proizvod_kolicina", 0);

        naziv.setText(proizvod_naziv);
        proizvodjac.setText(proizvod_proizvodjac);

        cena.setText(proizvod_cena);

        if (!containsValue(kategorije, proizvod_kategorija)) {
            kategorija.setSelection(0);
            Proizvod.Kategorije[] newKategorije = new Proizvod.Kategorije[kategorije.length + 1];
            newKategorije[0] = Proizvod.Kategorije.valueOf(proizvod_kategorija);
            System.arraycopy(kategorije, 0, newKategorije, 1, kategorije.length);
            kategorije = newKategorije;
        }
        ArrayAdapter<Proizvod.Kategorije> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kategorije);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kategorija.setAdapter(adapter);

        kategorija.setSelection(getIndex(kategorija, Proizvod.Kategorije.valueOf(proizvod_kategorija)));


        kolicina.setText(String.valueOf(proizvod_kolicina));

        odustaniOdIzmene();
        izmeniProizvod();
    }

    private boolean containsValue(Proizvod.Kategorije[] array, String value) {
        for (Proizvod.Kategorije item : array) {
            if (item.toString().equals(value)) {
                return true;
            }
        }
        return false;
    }


    private int getIndex(Spinner spinner, Proizvod.Kategorije value) {
        ArrayAdapter<Proizvod.Kategorije> adapter = (ArrayAdapter<Proizvod.Kategorije>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i) == value) {
                return i;
            }
        }
        return 0;
    }

    public void izmeniProizvod(){
        izmeniProizvod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noviNaziv = naziv.getText().toString();
                String noviProizvodjac = proizvodjac.getText().toString();
                String novaKategorijaString = kategorija.getSelectedItem().toString();
                String novaCenaString = cena.getText().toString();
                String novaKolicinaString = kolicina.getText().toString();

                if (noviNaziv.isEmpty() || noviProizvodjac.isEmpty() || novaCenaString.isEmpty() || novaKolicinaString.isEmpty()) {
                    Toast.makeText(IzmenaProizvoda.this, "Sva polja moraju biti popunjena.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int novaKolicina;
                try {
                    novaKolicina = Integer.parseInt(novaKolicinaString);
                }catch (NumberFormatException e){
                    return;
                }

                double novaCena = Double.parseDouble(novaCenaString);
                Proizvod.Kategorije novaKategorija = Proizvod.Kategorije.valueOf(novaKategorijaString);

                Proizvod noviProizvod = new Proizvod(id_proizvoda , noviNaziv, noviProizvodjac, novaKategorija, novaCena, novaKolicina);

                ProizvodiDatabaseHelper dbHelper = new ProizvodiDatabaseHelper(getApplicationContext());
                dbHelper.izmeniProizvod(noviProizvod);

                i = new Intent(IzmenaProizvoda.this, PrikazProizvoda.class);
//                String naslov = novaKategorijaString;
                i.putExtra("naslov", naslov);
                startActivity(i);
                Toast.makeText(IzmenaProizvoda.this, "Proizvod uspesno izmenjen.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void odustaniOdIzmene(){
        odustaniOdIzmene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(IzmenaProizvoda.this, PrikazProizvoda.class);
//                String naslov = "Proizvodi";
                i.putExtra("naslov", naslov);
                startActivity(i);
                finish();
            }
        });
    }
}