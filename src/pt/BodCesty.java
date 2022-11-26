package pt;

public class BodCesty implements Cloneable{

    public Bod stanice;
    public double vzdalenost;
    public BodCesty next = null;

    public BodCesty(Bod zastavka){
        this.stanice = zastavka;

    }
    public BodCesty(Bod zastavka, double vzdalenost){
        this.stanice = zastavka;
        this.vzdalenost = vzdalenost;

    }

    public BodCesty(Bod zastavka, double vzdalenost, BodCesty next){
        this.stanice = zastavka;
        this.vzdalenost = vzdalenost;
        this.next = next;
    }


    public void setNext(BodCesty novyBod){
        this.next = novyBod;
    }

    public BodCesty getNext(){
        return next;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
