package com.example.rma_2020270925_projekat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText korisnickoIme, lozinka;
    Button prijaviSe;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        korisnickoIme = findViewById(R.id.korisnicko_input);
        lozinka = findViewById(R.id.lozinka_input);

        prijaviSe = findViewById(R.id.prijavi_se_dugme);
        i = new Intent(this, KategorijeActivity.class);
        prijaviSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(korisnickoIme.getText().toString().equals("korisnik") && lozinka.getText().toString().equals("korisnik") ||
                        korisnickoIme.getText().toString().equals("admin") && lozinka.getText().toString().equals("admin")){
                    Toast.makeText(MainActivity.this, "Prijava uspesna.", Toast.LENGTH_SHORT).show();
                    startActivity(i);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Prijava neuspesna! Pokusajte ponovo.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}