package pt;

import java.util.ArrayList;
import java.util.List;

/**
 * Trida ktera slouzi pro chraneni logu jedne trasy velbloudu
 *
 */
public class LogTrasy {

    private final int ID_TRASY;
    private final double CAS_OPUST_SKL;
    private final int ID_SKLADU;
    private final int POCET_KOSU;
    private final List<String> ZASTAVKY;
    private double casNavratu;

    /**
     * Konstruktor tridy LogTrasy
     * @param idTrasy
     * @param casOpusteniSkladu
     * @param idOazy
     * @param pocetKosu
     */
    public LogTrasy(int idTrasy, double casOpusteniSkladu, int idOazy, int pocetKosu) {
        this.ID_TRASY = idTrasy;
        this.CAS_OPUST_SKL = casOpusteniSkladu;
        this.ID_SKLADU = idOazy;
        this.ZASTAVKY = new ArrayList<>();
        this.POCET_KOSU = pocetKosu;
    }

    public void vlozZastavku(String zastavka) {
        ZASTAVKY.add(zastavka);
    }

    public void setCasNavratu(double casNavratu) {
        this.casNavratu = casNavratu;
    }

    /**
     * Vrci String trasy ve spravnem formatu
     * @return
     */
    public String toString(){
        String log = "    Trasa: " + ID_TRASY + ", vystoupil v " + Math.round(CAS_OPUST_SKL) + ", pocet kosu: " + POCET_KOSU + ", oaza c: " + ID_SKLADU + "\n";

        for(int i = 0; i < ZASTAVKY.size(); i++){
            log += "        " + ZASTAVKY.get(i) + "\n";
        }

        log += "    Cas navratu: " + Math.round(casNavratu) + "\n";

        return log;
    }
}
