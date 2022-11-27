package pt;

public class Hrana implements Cloneable{

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

    public void setVzdalenost(double vzdalenost) {
        this.vzdalenost = vzdalenost;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
