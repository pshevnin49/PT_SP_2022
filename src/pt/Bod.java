package pt;

import java.util.*;

public abstract class Bod {

    protected int id;
    protected double x;
    protected double y;
    protected boolean jeZpracovany; // pro algoritmus hledani kratsi cesty(stanice je zpracovana kdyz jsou spoctene cesty ke vsem jeji dousedim)

    protected StackCesta kratsiCesta;//nejkratsi cesta do dane stanici pri prochazeni Dejkstra alg.
    protected List<Hrana> hrany;
    private double distance;
    private Data baseDat;

    public Bod(int id, double x, double y, Data baseDat){
        this.id = id;
        this.x = x;
        this.y = y;
        this.jeZpracovany = false;
        this.baseDat = baseDat;
        this.distance = Data.MAX_VALUE;
        this.hrany = new ArrayList<>();
        this.kratsiCesta = new StackCesta(baseDat);
        kratsiCesta.pridej(this, 0);
    }

    public void obnoveniCesty(){
        kratsiCesta = new StackCesta(baseDat);
        kratsiCesta.pridej(this, 0);
    }

    public boolean jeZpracovany() {
        return jeZpracovany;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public StackCesta getCestaKeStanici(){
        return kratsiCesta;
    }
    public void setCestaKeStanici(StackCesta cestaKeStanici){
        this.kratsiCesta = cestaKeStanici;
    }
    public void setJeZpracovany(boolean jeZpracovany){
        this.jeZpracovany = jeZpracovany;
    }

    public int getId(){
        return id;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public List<Hrana> getHrany(){
        return hrany;
    }

    public void vlozHranu(Bod soused){
        Hrana hrana = new Hrana(soused, spocitejVzdalenost(soused));
        hrany.add(hrana);
    }
    void vypis(){
        System.out.println(id + ". x:" + x + " y:" + y);
        System.out.println("Sousedi: ");

        for(int i = 0; i < hrany.size(); i++){
            hrany.get(i).vypis();
        }
    }
    public double spocitejVzdalenost(Bod stanice) {
        return Math.sqrt((x - stanice.getX()) * (x - stanice.getX()) + (y - stanice.getY()) * (y - stanice.getY()));
    }
}
