package pt;

public class BodCesty {

    public Stanice zastavka;
    public double vzdalenost;
    public BodCesty next;

    public BodCesty(Stanice zastavka, double vzdalenost, BodCesty next){

        this.zastavka = zastavka;
        this.vzdalenost = vzdalenost;
        this.next = next;

    }

}
