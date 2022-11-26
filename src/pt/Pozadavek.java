package pt;

public class Pozadavek {

    private double casPrichodu;
    private int idOazy;

    private int pocetKosu;
    private int prevezeneKose;

    private int id;
    private double casOcekavani;
    private double casDoruceni;

    public Pozadavek(int id, double casPrichodu, int idOazy, int pocetKosu, double casOcekavani) {
        this.id = id;
        this.casPrichodu = casPrichodu;
        this.idOazy = idOazy;
        this.pocetKosu = pocetKosu;
        this.casOcekavani = casOcekavani;
        this.casDoruceni = casPrichodu + casOcekavani;
        prevezeneKose = 0;
    }

    public int getNeprevezeneKose(){
        return pocetKosu - prevezeneKose;
    }
    public void zvetsiPrevezeneKose(int pocet){
        prevezeneKose += pocet;
    }

    public double getCasPrichodu() {
        return casPrichodu;
    }
    public int getIdOazy() {
        return idOazy;
    }
    public int getId(){
        return id;
    }
    public int getPocetKosu() {
        return pocetKosu;
    }

    public double getCasOcekavani() {
        return casOcekavani;
    }

    public double getCasDoruceni(){
        return casDoruceni;
    }

    public void vypis(){
        System.out.println("Cas prichodu: " + casPrichodu + ". id oazy:" + idOazy + " pocet kosu:" + pocetKosu + " cas ocekavani:" + casOcekavani);
    }
}
