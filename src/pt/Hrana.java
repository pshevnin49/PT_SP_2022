package pt;

/**
 * Trida reprezentuje hranu grafu
 * Ma odkaz na pristi stanice (bod grafu) a vzdalenost do
 * toho bodu
 */
public class Hrana implements Cloneable{

    private double vzdalenost;
    private final Bod FINAL;

    /**
     * Konstruktor tridy hrana
     * @param stanice odkaz na bod
     * @param vzdalenost vzdalenost do toho bodu
     */
    public Hrana(Bod stanice, double vzdalenost){
        this.FINAL = stanice;
        this.vzdalenost = vzdalenost;
    }

    public double getVzdalenost(){
        return vzdalenost;
    }

    public Bod getStanice(){
        return FINAL;
    }

    public void setVzdalenost(double vzdalenost) {
        this.vzdalenost = vzdalenost;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
