package pt;

public class Hrana {

    private double vzdalenost;
    private Bod stanice;
    public Hrana(Bod stanice, double vzdalenost){

        this.stanice = stanice;
        this.vzdalenost = vzdalenost;

    }
    public double getVzdalenost(){
        return vzdalenost;
    }
    public Bod getStanice(){
        return stanice;
    }

    public void vypis(){
        System.out.println("    Soused: " + stanice.getId() + " dalka: " + vzdalenost);
    }

}
