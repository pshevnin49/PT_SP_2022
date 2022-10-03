package pt;

public class Pozadavka {

    private int casPrichodu;
    private int idOazy;
    private int pocetKosu;
    private  int casOcekavani;

    public Pozadavka(int casPrichodu, int idOazy, int pocetKosu, int casOcekavani) {
        this.casPrichodu = casPrichodu;
        this.idOazy = idOazy;
        this.pocetKosu = pocetKosu;
        this.casOcekavani = casOcekavani;
    }

    public Pozadavka() {

    }

    public int getCasPrichodu() {
        return casPrichodu;
    }

    public int getIdOazy() {
        return idOazy;
    }

    public int getPocetKosu() {
        return pocetKosu;
    }

    public int getCasOcekavani() {
        return casOcekavani;
    }

    public void vypis(){
        System.out.println("Cas prichodu: " + casPrichodu + ". id oazy:" + idOazy + " pocet kosu:" + pocetKosu + " cas ocekavani:" + casOcekavani);
    }
}
