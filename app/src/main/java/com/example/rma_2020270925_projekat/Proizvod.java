package com.example.rma_2020270925_projekat;

public class Proizvod {
    Integer id;
    String naziv;
    String proizvodjac;
    Kategorije kategorija;
    Double cena;
    Integer kolicina;

    public Proizvod(String naziv, String proizvodjac, Kategorije kategorija, Double cena, Integer kolicina){
        this.naziv = naziv;
        this.proizvodjac = proizvodjac;
        this.kategorija = kategorija;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Proizvod(Integer id, String naziv, String proizvodjac, Kategorije kategorija, Double cena, Integer kolicina){
        this.id = id;
        this.naziv = naziv;
        this.proizvodjac = proizvodjac;
        this.kategorija = kategorija;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public Kategorije getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorije kategorija) {
        this.kategorija = kategorija;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public enum Kategorije{
        PICE,
        MLECNI_PROIZVODI,
        VOCE_I_POVRCE,
        MESO,
        SLANI_I_SLATKI_KONDITORI,
        HIGIJENA_I_KOZMETIKA,
        KUCNA_HEMIJA
    }
}
