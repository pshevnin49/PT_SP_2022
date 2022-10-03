package pt;

import java.util.ArrayList;
import java.util.List;

public class Sklad extends Stanice {
    private int pocetKosu;
    private int casObnoveni;
    private int casNalozeni;
    private int aktualniPocetKosu;

    private int rezervovaneKose;

    private int actualniCas;

    private int casPoslObnoveni;

    private int casPoObnoveni;

    private List<Velbloud> domVelbloudy;

    public Sklad(int id, int x, int y, int pocetKosu, int casObnoveni, int casNalozeni){

        super(id, x, y);
        this.pocetKosu = pocetKosu;
        this.casObnoveni = casObnoveni;
        this.casNalozeni = casNalozeni;
        this.sousedi = new ArrayList<>();
        this.domVelbloudy = new ArrayList<>();
        this.aktualniPocetKosu = pocetKosu;
        this.casPoObnoveni = 0;
        this.rezervovaneKose = 0;

    }

    public void pridejVelblouda(Velbloud velbloud){
        domVelbloudy.add(velbloud);
    }

    public int getPocetKosu() {
        return pocetKosu;
    }

    public int getCasObnoveni() {
        return casObnoveni;
    }

    public int getCasNalozeni() {
        return casNalozeni;
    }


}
