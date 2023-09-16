package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class KategorijeActivity extends AppCompatActivity {

    Proizvod.Kategorije[] kategorije = Proizvod.Kategorije.values();
    LinearLayout linearLayout;
    Button odjaviSe;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije);

        linearLayout = findViewById(R.id.kategorije_linear);
        kategorije();

        odjaviSe = findViewById(R.id.dugme_odjavi_se);
        odjava();
    }

    public void kategorije(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.topMargin = 20;
        params.leftMargin = 40;
        params.rightMargin = 40;

        for (Proizvod.Kategorije k : kategorije){
            Button kategorijaDugme = new Button(this);
            kategorijaDugme.setBackgroundColor(ContextCompat.getColor(this, R.color.kategorija));
            String kategorijaIme = k.name().replace("_", " ");
            kategorijaDugme.setText(kategorijaIme);

            kategorijaDugme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String naslov = k.name().toString();
                    i = new Intent(KategorijeActivity.this, PrikazProizvoda.class);
                    i.putExtra("naslov", naslov);
                    startActivity(i);
                    finish();
                }
            });
            kategorijaDugme.setLayoutParams(params);
            linearLayout.addView(kategorijaDugme);
        }

        Button sviProizvodi = new Button(this);
        sviProizvodi.setText("SVI PROIZVODI");
        sviProizvodi.setBackgroundColor(ContextCompat.getColor(this, R.color.svi_proizvodi));
        sviProizvodi.setTextColor(ContextCompat.getColor(this, R.color.white));
        sviProizvodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String naslov = "Proizvodi";
                i = new Intent(KategorijeActivity.this, PrikazProizvoda.class);
                i.putExtra("naslov", naslov);
                startActivity(i);
                finish();
            }
        });

        sviProizvodi.setLayoutParams(params);
        linearLayout.addView(sviProizvodi);
    }

    public void odjava(){
        i = new Intent(this, MainActivity.class);
        odjaviSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(KategorijeActivity.this, "Odjava uspesna!", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });
    }
}