package com.example.rma_2020270925_projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProizvodiDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "proizvodi_baza";

    private static final String TABLE_NAME = "PROIZVODI";

    public static final String ID = "id";
    public static final String NAZIV = "naziv";
    public static final String PROIZVODJAC = "proizvodjac";
    public static final String KATEGORIJA = "kategorija";
    public static final String CENA = "cena";
    public static final String KOLICINA = "kolicina";

    private SQLiteDatabase sqLiteDatabase;


    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + NAZIV + " TEXT NOT NULL," +
            PROIZVODJAC + " TEXT NOT NULL," + KATEGORIJA + " TEXT NOT NULL," +
            CENA + " TEXT NOT NULL," + KOLICINA + " TEXT NOT NULL);";

    public ProizvodiDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void dodajProizvod(Proizvod proizvod) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProizvodiDatabaseHelper.NAZIV, proizvod.getNaziv());
        contentValues.put(ProizvodiDatabaseHelper.PROIZVODJAC, proizvod.getProizvodjac());
        contentValues.put(ProizvodiDatabaseHelper.KATEGORIJA, String.valueOf(proizvod.getKategorija()));
        contentValues.put(ProizvodiDatabaseHelper.CENA, proizvod.getCena());
        contentValues.put(ProizvodiDatabaseHelper.KOLICINA, proizvod.getKolicina());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(ProizvodiDatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public List<Proizvod> getProizvodi() {
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<Proizvod> proizvodi = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String naziv = cursor.getString(1);
                String proizvodjac = cursor.getString(2);
                Proizvod.Kategorije kategorija = Proizvod.Kategorije.valueOf(cursor.getString(3));
                double cena = Double.parseDouble(cursor.getString(4));
                int kolicina = Integer.parseInt(cursor.getString(5));
                proizvodi.add(new Proizvod(id, naziv, proizvodjac, kategorija, cena, kolicina));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return proizvodi;
    }

    public void izmeniProizvod(Proizvod proizvod){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProizvodiDatabaseHelper.NAZIV, proizvod.getNaziv());
        contentValues.put(ProizvodiDatabaseHelper.PROIZVODJAC, proizvod.getProizvodjac());
        contentValues.put(ProizvodiDatabaseHelper.KATEGORIJA, String.valueOf(proizvod.getKategorija()));
        contentValues.put(ProizvodiDatabaseHelper.CENA, proizvod.getCena());
        contentValues.put(ProizvodiDatabaseHelper.KOLICINA, proizvod.getKolicina());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(ProizvodiDatabaseHelper.TABLE_NAME, contentValues, ID + " =?", new String[]{String.valueOf(proizvod.getId())});
    }

    public void ukloniProizvod(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(ProizvodiDatabaseHelper.TABLE_NAME, ID + " =?", new String[]{String.valueOf(id)});
    }

    public List<Proizvod> getProizvodiPoKategoriji(String kategorija) {
        String sql = "select * from " + TABLE_NAME + " where " + KATEGORIJA + " = ?";
        sqLiteDatabase = this.getReadableDatabase();
        List<Proizvod> proizvodi = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{kategorija});
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String naziv = cursor.getString(1);
                String proizvodjac = cursor.getString(2);
                Proizvod.Kategorije kategorijaEnum = Proizvod.Kategorije.valueOf(cursor.getString(3));
                double cena = Double.parseDouble(cursor.getString(4));
                int kolicina = Integer.parseInt(cursor.getString(5));
                proizvodi.add(new Proizvod(id, naziv, proizvodjac, kategorijaEnum, cena, kolicina));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return proizvodi;
    }
}
