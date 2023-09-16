package com.example.rma_2020270925_projekat;

public class Korisnik {
    Integer id;
    String korisnickoIme;
    private String lozinka;

    public Korisnik(String korisnickoIme, String lozinka){
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public Korisnik(Integer id, String korisnickoIme, String lozinka){
        this.id = id;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
