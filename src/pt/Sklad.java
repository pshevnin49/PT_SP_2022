package pt;

import java.util.ArrayList;
import java.util.List;

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

    public Sklad(int id, double x, double y, int pocetKosu, int casObnoveni, int casNalozeni, Data baseDat){
        super(id, x, y);
        this.pocetKosu = pocetKosu;
        this.casObnoveni = casObnoveni;
        this.casNalozeni = casNalozeni;
        this.domVelbloudy = new ArrayList<>();
        this.aktualniPocetKosu = pocetKosu;
        this.casPoObnoveni = 0;
        this.actualniCas = 0;
        this.rezervovaneKose = 0;
        this.baseDat = baseDat;
    }
    public void zvetseniCasu(double cas){

        double doubleCasObnoveni = (double) casObnoveni;
        casPoObnoveni += cas;
        actualniCas += cas;

        if(Data.jeVetsi(casPoObnoveni, casObnoveni)){
            casPoObnoveni = 0;
            aktualniPocetKosu += pocetKosu;
        }

    }
    public void setRezervovaneKose(int pocetKosu){
        rezervovaneKose = pocetKosu;
    }
    public void odstranKose(int pocetKosu){
        rezervovaneKose -= pocetKosu;
        this.pocetKosu -= pocetKosu;
    }
    public void pridejVelblouda(Velbloud velbloud){
        domVelbloudy.add(velbloud);
    }
    public int getPocetKosu() {
        return pocetKosu - rezervovaneKose;
    }
    public int getCasObnoveni() {
        return casObnoveni;
    }
    public int getCasNalozeni() {
        return casNalozeni;
    }

    /**
     * Metoda prochazi vsichni
     * @param pocetKosu
     * @param casDoruceni
     * @param cesta
     * @return
     */
    public Velbloud getVhodnyVelbl(int pocetKosu, double casDoruceni, StackCesta cesta){ //cas doruceni - cas prichodu poz + casCekani
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
            int stihneLi = cesta.stihneCestuVelbloud(velbloud, casDoruceni, pocetKosu);

            if(stihneLi == 0){
                return velbloud;
            }
            else if(stihneLi == 1){
                domVelbloudy.add(velbloud);
                if(bylNejdelsiVelbl){
                    return velbloudPrazdny;
                }
            }else{
                domVelbloudy.add(velbloud);
                if(bylRychlejsiVelbl){
                    return velbloudPrazdny;
                }
            }
            if(velbloud.getRychlost() >= baseDat.getMaxRychlostVelbloudu()){
                bylRychlejsiVelbl = true;
            }
            if(velbloud.getVzdalenostMax() >= baseDat.getMaxDalkaVelbloudu()){
                bylRychlejsiVelbl = true;
            }
        }
        return velbloudPrazdny;

    }

    public Velbloud getNovyVelbloud(DruhVelbloudu druh){
        return new Velbloud(baseDat.getIndexVelbloudu(),
                druh, this, baseDat.getAktualniCas());
    }
}
