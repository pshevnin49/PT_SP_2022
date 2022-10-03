package pt;

import java.util.ArrayList;

public class Velbloud {

    private Sklad domovskaStanice;
    private Sklad cilovaStanice;
    private ArrayList<Stanice> cesta;
    private boolean jeNaCeste;
    private int indexAktStanice; // index aktualni stanici v listu
    private int rychlost;
    private int vzdalenost;
    private int aktualniCas;
    private DruhVelbloudu druhVelbloudu;

    public Velbloud(int rychlost, int vzdalenost, DruhVelbloudu druhVelbloudu, Sklad domovskaStanice, int aktualniCas) {

        this.rychlost = rychlost;
        this.vzdalenost = vzdalenost;
        this.druhVelbloudu = druhVelbloudu;
        this.domovskaStanice = domovskaStanice;
        this.aktualniCas = aktualniCas;
        this.jeNaCeste = false;

    }

    public Sklad getDomovskaStanice() {
        return domovskaStanice;
    }

    public Sklad getCilovaStanice() {
        return cilovaStanice;
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

    public DruhVelbloudu getDruhVelbloudu() {
        return druhVelbloudu;
    }

    public void zacniCestu(ArrayList<Stanice> cesta){

        this.cesta = cesta;
        this.jeNaCeste = true;

    }

}


