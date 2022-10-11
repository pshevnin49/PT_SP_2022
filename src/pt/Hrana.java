package pt;

public class Hrana {

    private double vzdalenost;
    private Stanice stanice;
    public Hrana(Stanice stanice, double vzdalenost){

        this.stanice = stanice;
        this.vzdalenost = vzdalenost;

    }
    public double getVzdalenost(){
        return vzdalenost;
    }
    public Stanice getStanice(){
        return stanice;
    }

    public void vypis(){
        System.out.println("    Soused: " + stanice.getId() + " dalka: " + vzdalenost);
    }

}
