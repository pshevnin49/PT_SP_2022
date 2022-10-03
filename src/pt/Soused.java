package pt;

public class Soused {

    private double vzdalenost;
    private Stanice stanice;

    public Soused(Stanice stanice, double vzdalenost){

        this.stanice = stanice;
        this.vzdalenost = vzdalenost;

    }

    public double getVzdalenost(){
        return vzdalenost;
    }

    public Stanice getStanice(){
        return stanice;
    }
}
