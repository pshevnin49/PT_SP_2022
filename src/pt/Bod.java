package pt;

import java.util.*;

/**
 * Abstractni trida bod reprezentuje bod grafu
 */
public abstract class Bod {
    /** Index bodu */
    protected int id;
    /** Souradnice x */
    protected double x;
    /** Souradnice y */
    protected double y;
    /** Je li tento bod spracovan Dijkstra algoritmem*/
    protected boolean jeZpracovany; // pro algoritmus hledani kratsi cesty(stanice je zpracovana kdyz jsou spoctene cesty ke vsem jeji dousedim)
    /** Zpracovava li ted tento bod nebo ne*/
    private boolean zpracovava;

    /** Nejkratsi nalezena do teto doby cesta od skladu, ktery soucasne zpracovava Dijkstra alg. do tohoto bodu*/
    private CestaList kratsiCesta;//nejkratsi cesta do dane stanici pri prochazeni Dejkstra alg.
    /** Sousedi thohto bodu v grafe*/
    private final List<Hrana> HRANY;

    /** Dalka nejkratsi docasne nalezene cesty*/
    private double distance;
    /** Data celeho programu*/
    private final Data baseDat;

    /**
     * Konstruktor tridy Bod
     * @param id
     * @param x
     * @param y
     * @param baseDat
     */
    public Bod(int id, double x, double y, Data baseDat){
        this.id = id;
        this.x = x;
        this.y = y;
        this.jeZpracovany = false;
        this.zpracovava = false;
        this.baseDat = baseDat;
        this.distance = Data.MAX_VALUE;
        this.HRANY = new ArrayList<>();
        this.kratsiCesta = new CestaList(baseDat);

    }

    /**
     * Vynuluje nalezenou cestu pu kazdem uspesnem dobehnuti Dijkstreho algoritmu
     */
    public void obnoveniCesty(){
        kratsiCesta = new CestaList(baseDat);
        kratsiCesta.pridej(this, 0);
        jeZpracovany = false;
        zpracovava = false;
    }

    public boolean jeZpracovany() {
        return jeZpracovany;
    }

    public boolean getZpracovava(){
        return zpracovava;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public CestaList getCestaKeStanici(){
        return kratsiCesta;
    }
    public void setCestaKeStanici(CestaList cestaKeStanici){
        this.kratsiCesta = cestaKeStanici;
    }
    public void setJeZpracovany(boolean jeZpracovany){
        this.jeZpracovany = jeZpracovany;
    }

    public void setZpracovava(boolean zpracovava){
        this.zpracovava = zpracovava;
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
        return HRANY;
    }

    /**
     * Pridava hranu souseda
     * @param soused
     */
    public void vlozHranu(Bod soused){
        Hrana hrana = new Hrana(soused, spocitejVzdalenost(soused));
        HRANY.add(hrana);
    }

    /**
     * Spocita vzdalenost souseda
     * @param stanice
     * @return
     */
    public double spocitejVzdalenost(Bod stanice) {
        return Math.sqrt((x - stanice.getX()) * (x - stanice.getX()) + (y - stanice.getY()) * (y - stanice.getY()));
    }
}
