package pt;

import java.util.ArrayList;
import java.util.List;

/**
 * Trida reprezentuje sklad je dedena od abstraktni tridy bod
 */
public class Sklad extends Bod {

    private final int POCET_KOSU;
    private final int CAS_OBNOVENI;
    private final int CAS_NALOZENI;
    /** Domovske velbloudi, velbloudi ktere aktualne jsou na tomto sklade*/
    private final List<Velbloud> DOM_VELBLOUDY;
    private final Data BASE_DAT;
    private final List<String> logList;

    private int aktualniPocetKosu;
    private double casPoObnoveni;

    /**
     * Konstruktor tridy sklad
     * @param id
     * @param x
     * @param y
     * @param pocetKosu
     * @param casObnoveni
     * @param casNalozeni
     * @param baseDat
     */
    public Sklad(int id, double x, double y, int pocetKosu, int casObnoveni, int casNalozeni, Data baseDat){
        super(id, x, y, baseDat);
        this.POCET_KOSU = pocetKosu;
        this.CAS_OBNOVENI = casObnoveni;
        this.CAS_NALOZENI = casNalozeni;
        this.DOM_VELBLOUDY = new ArrayList<>();
        this.aktualniPocetKosu = pocetKosu;
        this.casPoObnoveni = 0;
        this.BASE_DAT = baseDat;
        this.logList = new ArrayList<>();
    }

    public void zvetseniCasu(double cas){
        double doubleCasObnoveni = (double) CAS_OBNOVENI;
        casPoObnoveni += cas;

        if(Data.jeVetsi(casPoObnoveni, doubleCasObnoveni)){

            int increment = (int) (casPoObnoveni / doubleCasObnoveni);
            String log = String.format("    Doplneni skladu, cas: %.2f; pocet kosu pred: %d; pocet kosu po: %d\n", BASE_DAT.getAktualniCas(), aktualniPocetKosu, aktualniPocetKosu + POCET_KOSU * increment);
            logList.add(log);
            casPoObnoveni = 0;

            aktualniPocetKosu += POCET_KOSU * increment;
        }
    }

    public double getCasovyKrok(){
        return CAS_OBNOVENI;
    }

    public void odstranKose(int pocetKosu){
        this.aktualniPocetKosu -= pocetKosu;
    }

    public void pridejVelblouda(Velbloud velbloud){
        DOM_VELBLOUDY.add(velbloud);
    }
    public int getPocetKosu() {
        return aktualniPocetKosu;
    }
    public int getCasObnoveni() {
        return CAS_OBNOVENI;
    }
    public int getCasNalozeni() {
        return CAS_NALOZENI;
    }

    /**
     * Metoda prochazi vsichni velbloudy, ktere nachazi na sklade, kdyz zadny z nich cestu
     * nestihne, zacina generovat nove pomoci metody generujNovyVelbloud.
     * @param pocetKosu
     * @param casDoruceni
     * @param cesta
     * @return
     */
    public Velbloud getVhodnyVelbl(int pocetKosu, double casDoruceni, CestaList cesta){ //cas doruceni - cas prichodu poz + casCekani

        if(DOM_VELBLOUDY.size() > 0){
            for(int i = 0; i < DOM_VELBLOUDY.size(); i++){
                Velbloud velbloud = DOM_VELBLOUDY.get(i);
                if(cesta.stihneCestuVelbloud(velbloud, casDoruceni, pocetKosu) == 0){
                    DOM_VELBLOUDY.remove(i);
                    return velbloud;
                }
            }
        }
        return generujNovyVelbloud(pocetKosu, casDoruceni, cesta);

    }

    /**
     * Metoda generuje noveho velbloud, ktery urcite dokaze zvladnout cestu v cas.
     * Metoda generuji velbloudi dokud nepochopi, ze vsichni nejlepsi velbloudi uz byli vygenerovane,
     * a vhodny velbloud jeste nalezen nebyl. Vyuziva k tomu prumnerne velbloudy jako nejdelsiVelbloud a rychlejsiVelbloud
     * velbloudi, ktere byli vygenerovane ale nejsou vhodne vkladaji do listu domovske velbloudy
     * @param pocetKosu
     * @param casDoruceni
     * @param cesta
     * @return
     */
    private Velbloud generujNovyVelbloud(int pocetKosu, double casDoruceni, CestaList cesta){
        boolean bylRychlejsiVelbl = false;
        boolean bylNejdelsiVelbl = false;
        Velbloud velbloudPrazdny = null;

        while(!bylNejdelsiVelbl || !bylRychlejsiVelbl){
            Velbloud velbloud = getNovyVelbloud(BASE_DAT.getNahodnyDruhVelbloudu());
            velbloud.getDruhVelbloudu().pocetVelblIncr();
            int stihneLi = cesta.stihneCestuVelbloud(velbloud, casDoruceni, pocetKosu);

            if(stihneLi == 0){
                return velbloud;
            }
            else if(stihneLi == 1){
                DOM_VELBLOUDY.add(velbloud);
                if(velbloud.getVzdalenostMax() >= BASE_DAT.getNejdelsiVelbl().getVzdalenostMax()){
                    return velbloudPrazdny;
                }
            }else{
                DOM_VELBLOUDY.add(velbloud);
                if(velbloud.getRychlost() >= BASE_DAT.getRychlejsiVelbl().getRychlost()){
                    return velbloudPrazdny;
                }
            }
            if(velbloud.getRychlost() >= BASE_DAT.getRychlejsiVelbl().getRychlost()){
                bylRychlejsiVelbl = true;
            }
            if(velbloud.getVzdalenostMax() >= BASE_DAT.getNejdelsiVelbl().getVzdalenostMax()){
                bylRychlejsiVelbl = true;
            }

        }
        return velbloudPrazdny;
    }

    /**
     * Vraci nahodneho
     * @param druh
     * @return
     */
    private Velbloud getNovyVelbloud(DruhVelbloudu druh){

        Velbloud velbloud = new Velbloud(BASE_DAT.getIndexVelbloudu(),
                druh, this, BASE_DAT);

        BASE_DAT.getVsichniVelbloudy().add(velbloud);
        BASE_DAT.indexVelblouduInc();
        return velbloud;
    }

    public String getLog(){
        String log = String.format("Sklad c: %d\n", id);

        for(int i = 0; i < logList.size(); i++){
            log += logList.get(i);
        }
        return log;
    }

}
