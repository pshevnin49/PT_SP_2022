package pt;

public class BodCesty {

    public Bod bod;
    public double vzdalenost;
    public BodCesty next;

    public BodCesty(Bod zastavka, double vzdalenost, BodCesty next){

        this.bod = zastavka;
        this.vzdalenost = vzdalenost;
        this.next = next;

    }

}
