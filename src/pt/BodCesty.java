package pt;

public class BodCesty implements Cloneable{

    public Bod stanice;
    public double vzdalenost;
    public boolean musiNapit;
    public BodCesty next;

    public BodCesty(Bod zastavka, double vzdalenost, BodCesty next){

        this.stanice = zastavka;
        this.vzdalenost = vzdalenost;
        this.musiNapit = false;
        this.next = next;

    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
