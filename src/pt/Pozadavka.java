package pt;

public class Pozadavka {

    private double casPrichodu;
    private int idOazy;
    private int pocetKosu;
    private double casOcekavani;
    private double casDoruceni;

    public Pozadavka(double casPrichodu, int idOazy, int pocetKosu, double casOcekavani) {
        this.casPrichodu = casPrichodu;
        this.idOazy = idOazy;
        this.pocetKosu = pocetKosu;
        this.casOcekavani = casOcekavani;
        this.casDoruceni = casPrichodu + casOcekavani;
    }


    public double getCasPrichodu() {
        return casPrichodu;
    }
    public int getIdOazy() {
        return idOazy;
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
