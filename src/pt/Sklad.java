package pt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sklad extends Bod {

    private int pocetKosu;
    private int casObnoveni;
    private int casNalozeni;
    private int aktualniPocetKosu;
    private int rezervovaneKose;
    private double actualniCas;
    private int casPoslObnoveni;
    private double casPoObnoveni;
    private List<Velbloud> domVelbloudy;
    private Data baseDat;
    private List<String> logList;

    public Sklad(int id, double x, double y, int pocetKosu, int casObnoveni, int casNalozeni, Data baseDat){
        super(id, x, y, baseDat);
        this.pocetKosu = pocetKosu;
        this.casObnoveni = casObnoveni;
        this.casNalozeni = casNalozeni;
        this.domVelbloudy = new ArrayList<>();
        this.aktualniPocetKosu = pocetKosu;
        this.casPoObnoveni = 0;
        this.actualniCas = 0;
        this.rezervovaneKose = 0;
        this.baseDat = baseDat;
        this.logList = new ArrayList<>();
    }

    public void zvetseniCasu(double cas){
        double doubleCasObnoveni = (double) casObnoveni;
        casPoObnoveni += cas;
        actualniCas += cas;

        if(Data.jeVetsi(casPoObnoveni, doubleCasObnoveni)){
            String log = String.format("    Doplneni skladu, cas: %.2f; pocet kosu pred: %d; pocet kosu po: %d\n", baseDat.getAktualniCas(), aktualniPocetKosu, aktualniPocetKosu + pocetKosu);
            logList.add(log);
            casPoObnoveni = 0;
            aktualniPocetKosu += pocetKosu;
        }
    }

    public double getCasovyKrok(){
        return casObnoveni;
    }

    public void odstranKose(int pocetKosu){
        this.aktualniPocetKosu -= pocetKosu;
    }

    public void pridejVelblouda(Velbloud velbloud){
        domVelbloudy.add(velbloud);
    }
    public int getPocetKosu() {
        return aktualniPocetKosu;
    }
    public int getCasObnoveni() {
        return casObnoveni;
    }
    public int getCasNalozeni() {
        return casNalozeni;
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

        boolean bylRychlejsiVelbl = false;
        boolean bylNejdelsiVelbl = false;
        Velbloud velbloudPrazdny = null;

        if(domVelbloudy.size() > 0){
            for(int i = 0; i < domVelbloudy.size(); i++){
                Velbloud velbloud = domVelbloudy.get(i);
                if(cesta.stihneCestuVelbloud(velbloud, casDoruceni, pocetKosu) == 0){
                    domVelbloudy.remove(i);
                    return velbloud;
                }
            }
        }
        while(!bylNejdelsiVelbl || !bylRychlejsiVelbl){
            Velbloud velbloud = getNovyVelbloud(baseDat.getNahodnyDruhVelbloudu());
            velbloud.getDruhVelbloudu().pocetVelblIncr();
            int stihneLi = cesta.stihneCestuVelbloud(velbloud, casDoruceni, pocetKosu);

            if(stihneLi == 0){
                return velbloud;
            }
            else if(stihneLi == 1){
                domVelbloudy.add(velbloud);
                if(velbloud.getVzdalenostMax() >= baseDat.getNejdelsiVelbl().getVzdalenostMax()){
                    return velbloudPrazdny;
                }
            }else{
                domVelbloudy.add(velbloud);
                if(velbloud.getRychlost() >= baseDat.getRychlejsiVelbl().getRychlost()){
                    return velbloudPrazdny;
                }
            }
            if(velbloud.getRychlost() >= baseDat.getRychlejsiVelbl().getRychlost()){
                bylRychlejsiVelbl = true;
            }
            if(velbloud.getVzdalenostMax() >= baseDat.getNejdelsiVelbl().getVzdalenostMax()){
                bylRychlejsiVelbl = true;
            }

        }
        return velbloudPrazdny;

    }

    private Velbloud getNovyVelbloud(DruhVelbloudu druh){

        Velbloud velbloud = new Velbloud(baseDat.getIndexVelbloudu(),
                druh, this, baseDat);

        baseDat.getVsichniVelbloudy().add(velbloud);
        baseDat.indexVelblouduInc();
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
