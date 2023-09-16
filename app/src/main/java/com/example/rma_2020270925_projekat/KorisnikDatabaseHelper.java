package com.example.rma_2020270925_projekat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class KorisnikDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="korisnici_baza";

    private static final String TABLE_NAME = "KORISNICI";
    public static final String ID = "id";
    public static final String KORISNICKO_IME = "korisnickoIme";
    public static final String LOZINKA = "lozinka";

    private SQLiteDatabase sqLiteDatabase;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + KORISNICKO_IME + " TEXT NOT NULL, " +
            LOZINKA + " TEXT NOT NULL);";

    public KorisnikDatabaseHelper(Context context) {
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

    public void dodajKorisnika(Korisnik korisnik) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KorisnikDatabaseHelper.KORISNICKO_IME, korisnik.getKorisnickoIme());
        contentValues.put(KorisnikDatabaseHelper.LOZINKA, korisnik.getLozinka());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(KorisnikDatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public List<Korisnik> getKorisnici(){
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<Korisnik> korisnici = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String korisnickoIme = cursor.getString(1);
                String lozinka = cursor.getString(2);
                korisnici.add(new Korisnik(id, korisnickoIme, lozinka));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return korisnici;
    }

}
