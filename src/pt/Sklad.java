package pt;

import java.util.ArrayList;
import java.util.List;

public class Sklad extends Bod {

    private final int PCET_KOSU;
    private final int CAS_OBNOVENI;
    private final int CAS_NALOZENI;
    private final List<Velbloud> DOM_VELBLOUDY;
    private final Data BASE_DAT;
    private final List<String> logList;

    private int aktualniPocetKosu;
    private double casPoObnoveni;

    public Sklad(int id, double x, double y, int pocetKosu, int casObnoveni, int casNalozeni, Data baseDat){
        super(id, x, y, baseDat);
        this.PCET_KOSU = pocetKosu;
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
            String log = String.format("    Doplneni skladu, cas: %.2f; pocet kosu pred: %d; pocet kosu po: %d\n", BASE_DAT.getAktualniCas(), aktualniPocetKosu, aktualniPocetKosu + PCET_KOSU);
            logList.add(log);
            casPoObnoveni = 0;
            aktualniPocetKosu += PCET_KOSU;
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
     * nestihne, zacina generovat nove. Metoda zna stredni rychlost rychlejsiho druhu velblouda,
     * a zna stredni cestu trvalejsiho druhu velblouda. Pokud rychlejsi velbloud nesiha cestu casove, nebo
     * trvalejsi velbloud ma kratsi max vzdalenost, nez nejdelsi usecka cesty, metoda vrati null, protoze pravdepodobnost nalezeni
     * vhodneho velblouda -> 0
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
