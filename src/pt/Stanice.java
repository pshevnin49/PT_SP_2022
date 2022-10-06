package pt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Stanice {
    protected int id;
    protected int x;
    protected int y;
    protected boolean jeZpracovany; // pro algoritmus hledani kratsi cesty
    protected List<Soused> sousedi;

    public Stanice(int id, int x, int y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.jeZpracovany = false;
        this.sousedi = new ArrayList<>();
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

    public boolean getJeZpracovany(){
        return jeZpracovany;
    }

    public void vlozSouseda(Stanice sousedStanice){

        Soused soused = new Soused(sousedStanice, spocitejVzdalenost(sousedStanice));
        this.sousedi.add(soused);

    }

    public void setJeZpracovany(boolean jeZpracovany){
        this.jeZpracovany = jeZpracovany;
    }
    void vypis(){
        System.out.println(id + ". x:" + x + " y:" + y);
    }

    public double spocitejVzdalenost(Stanice stanice) {
        return Math.sqrt((x - stanice.getX()) * (x - stanice.getX()) + (y - stanice.getY()) * (y - stanice.getY()));
    }

    public Stanice getNejblizsiSoused(){

        Collections.sort(sousedi, Comparator.comparing(Soused::getVzdalenost));
        return sousedi.get(0).getStanice();

    }

}
