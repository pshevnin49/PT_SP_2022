package pt;

import java.util.ArrayList;
import java.util.List;

public class Sklad extends Bod {

    private int pocetKosu;
    private int casObnoveni;
    private int casNalozeni;
    private int aktualniPocetKosu;
    private int rezervovaneKose;
    private double actualniCas;
    private int casPoslObnoveni;
    private double casPoObnoveni;
    private List<Velbloud> domVelbloudy;

    public Sklad(int id, int x, int y, int pocetKosu, int casObnoveni, int casNalozeni){

        super(id, x, y);
        this.pocetKosu = pocetKosu;
        this.casObnoveni = casObnoveni;
        this.casNalozeni = casNalozeni;
        this.domVelbloudy = new ArrayList<>();
        this.aktualniPocetKosu = pocetKosu;
        this.casPoObnoveni = 0;
        this.rezervovaneKose = 0;
        this.actualniCas = 0;
    }
    public void zvetseniCasu(double cas){

        double doubleCasObnoveni = (double) casObnoveni;
        casPoObnoveni += cas;
        actualniCas += cas;

        if(Data.jeVetsi(casPoObnoveni, casObnoveni)){
            casPoObnoveni = 0;
            aktualniPocetKosu += pocetKosu;
        }

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


//    public Velbloud getNovyVelbloud(DruhVelbloudu druh){
//
//        return new Velbloud(druh, this);
//    }
}
