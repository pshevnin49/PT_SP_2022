package pt;

import java.util.ArrayList;

public class Velbloud {

    private Sklad domovskaStanice;
    private StackCesta cesta;
    private boolean jeNaCeste;
    private int indexAktStanice; // index aktualni stanici v listu
    private int rychlost;
    private double casBezPiti;

    private Pozadavka actualniPozadavka;
    private int vzdalenost;
    private double aktualniCas;
    private double pristiAkce;
    public Velbloud(int rychlost, int vzdalenost, DruhVelbloudu druhVelbloudu, Sklad domovskaStanice, double aktualniCas) {
        this.rychlost = rychlost;
        this.vzdalenost = vzdalenost;
        this.domovskaStanice = domovskaStanice;
        this.aktualniCas = aktualniCas;
        this.jeNaCeste = false;
    }

    public void zvetseniCasu(double cas){

    }

    public double getPristiAkce(){
        return pristiAkce;
    }
    public Sklad getDomovskaStanice() {
        return domovskaStanice;
    }
    public StackCesta getCesta() {
        return cesta;
    }
    public int getRychlost() {
        return rychlost;
    }
    public int getVzdalenost() {
        return vzdalenost;
    }
    public void zacniCestu(StackCesta cesta){
        casBezPiti = 0;
        indexAktStanice = 0;
        this.cesta = cesta;
        this.jeNaCeste = true;
    }

}


