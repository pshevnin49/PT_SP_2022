package pt;

import java.util.ArrayList;
import java.util.List;

public class LogTrasy {

    private int idTrasy;
    private double casOpusteniSkladu;
    private int idOazy;
    private int pocetKosu;
    private List<String> zastavky;
    private double casNavratu;

    public LogTrasy(int idTrasy, double casOpusteniSkladu, int idOazy, int pocetKosu) {
        this.idTrasy = idTrasy;
        this.casOpusteniSkladu = casOpusteniSkladu;
        this.idOazy = idOazy;
        this.zastavky = new ArrayList<>();
        this.pocetKosu = pocetKosu;
    }

    public void vlozZastavku(String zastavka) {
        zastavky.add(zastavka);
    }

    public void setCasNavratu(double casNavratu) {
        this.casNavratu = casNavratu;
    }

    public String toString(){
        String log = new String();
        log += "    Trasa: " + idTrasy + ", vystoupil v " + Math.round(casOpusteniSkladu) + ", pocet kosu: " + pocetKosu + ", oaza c: " + idOazy + "\n";

        for(int i = 0; i < zastavky.size(); i++){
            log += "        " + zastavky.get(i) + "\n";
        }

        log += "    Cas navratu: " + Math.round(casNavratu) + "\n";

        return log;
    }
}
