package pt;

import java.util.*;

public abstract class Bod {

    protected int id;
    protected int x;
    protected int y;
    protected boolean jeZpracovany; // pro algoritmus hledani kratsi cesty(stanice je zpracovana kdyz jsou spoctene cesty ke vsem jeji dousedim)

    protected StackCesta cestaKeStanici;
    protected List<Hrana> hrany;
    private double distance;

    public Bod(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.jeZpracovany = false;
        this.distance = Data.MAX_VALUE;
        this.hrany = new ArrayList<>();
        this.cestaKeStanici = new StackCesta();
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
        return cestaKeStanici;
    }
    public void setCestaKeStanici(StackCesta cestaKeStanici){
        this.cestaKeStanici = cestaKeStanici;
    }
    public void setJeZpracovany(boolean jeZpracovany){
        this.jeZpracovany = jeZpracovany;
    }
    public int getId(){
        return id;
    }
    public int getX() {
        return x;
    }
    public int getY() {
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
    }
    public double spocitejVzdalenost(Bod stanice) {
        return Math.sqrt((x - stanice.getX()) * (x - stanice.getX()) + (y - stanice.getY()) * (y - stanice.getY()));
    }
}
