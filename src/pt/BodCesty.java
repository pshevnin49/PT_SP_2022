package pt;

public class BodCesty {

    private Stanice zastavka;
    private int vzdalenost;

    public BodCesty(Stanice zastavka, int vzdalenost) {
        this.zastavka = zastavka;
        this.vzdalenost = vzdalenost;
    }

    public Stanice getZastavka() {
        return zastavka;
    }

    public int getVzdalenost() {
        return vzdalenost;
    }

//    public int getCasDorazeni() {
//        return casDorazeni;
//    }


}
