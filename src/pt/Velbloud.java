package pt;

import java.util.ArrayList;

public class Velbloud {

    private Sklad domovskaStanice;
    private ArrayList<Stanice> cesta;
    private boolean jeNaCeste;
    private int indexAktStanice; // index aktualni stanici v listu
    private int rychlost;
    private double casBezPiti;
    private int vzdalenost;
    private double aktualniCas;

    public Velbloud(int rychlost, int vzdalenost, DruhVelbloudu druhVelbloudu, Sklad domovskaStanice, int aktualniCas) {
        this.rychlost = rychlost;
        this.vzdalenost = vzdalenost;
        this.domovskaStanice = domovskaStanice;
        this.aktualniCas = aktualniCas;
        this.jeNaCeste = false;
    }

    public Sklad getDomovskaStanice() {
        return domovskaStanice;
    }
    public ArrayList<Stanice> getCesta() {
        return cesta;
    }
    public int getRychlost() {
        return rychlost;
    }
    public int getVzdalenost() {
        return vzdalenost;
    }
    public void zacniCestu(ArrayList<Stanice> cesta){
        casBezPiti = 0;
        indexAktStanice = 0;
        this.cesta = cesta;
        this.jeNaCeste = true;
    }

}


