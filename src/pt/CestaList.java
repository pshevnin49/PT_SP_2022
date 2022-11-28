package pt;


import java.util.ArrayList;
import java.util.List;

public class CestaList{

    private boolean jeSpoctenIndex = false;
    private double maxUsecka = 0;
    private double dalkaCesty = 0;
    private double indexNejlepsiCesty = 0;

    private boolean spoctenCasVelbl = false;
    private double casRychlVelbl = 0;
    private double casNejdelVelbl = 0;
    private double casStrVelbl = 0;

    private final Data baseDat;

    private List<Hrana> cesta;

    public CestaList(Data baseDat){
        this.baseDat = baseDat;
        this.cesta = new ArrayList<>();
    }

    public CestaList(Data baseDat, List<Hrana> cesta){
        this.baseDat = baseDat;
        this.cesta = cesta;
    }

    public void pridej(Bod novaStanice, double vzdalenost){

        if(cesta.size() > 0){
            cesta.get(cesta.size() - 1).setVzdalenost(vzdalenost);
        }

        Hrana hrana = new Hrana(novaStanice, 0);
        cesta.add(hrana);
    }


    /**
     * Metoda spocita dalku, cesty, max usecku, a generuje z techto dat
     * "index nejlepsi cesty" tento index slouzi k razeni cest od nejlepsi k nejhorsi.
     * formula tohoto indexu je: cela dalka + pocet usecek * delku max usecky;
     */
    public void spocitejIndexCesty(){
        double dalka = 0;
        double pocetStanic = 0;

        if(!jeSpoctenIndex){
            for (Hrana bodCesty : cesta){
                pocetStanic++;

                dalka += bodCesty.getVzdalenost();

                if(Data.jeVetsi(bodCesty.getVzdalenost(), maxUsecka)){
                    maxUsecka = bodCesty.getVzdalenost();
                }

            }
            dalkaCesty = dalka;

            if(Data.jeVetsi(maxUsecka, baseDat.getStredniVelbl().getVzdalenostMax())){ // pokud cestu nelze splnit strednim velbloudem
                indexNejlepsiCesty = dalkaCesty * 1.5; // nebo 2

                if(Data.jeVetsi(maxUsecka, baseDat.getNejdelsiVelbl().getVzdalenostMax())){
                    indexNejlepsiCesty = dalkaCesty * 7;; // mozna jeste zmenim
                }

            }
            else{
                indexNejlepsiCesty = dalkaCesty;
            }

            jeSpoctenIndex = true;
        }
    }

    /**
     * Metoda spocita cas pro vsichni "standardni" velbloudy jako rychlejsi, stredni, a nejdelsi.
     * Pokud velbloud zvlada tuto cestu, ulozi cas potrebny tomuto velbloudovi pro zvladnuti cesty,
     * pokud ne - zapise -1
     */
    public void casVelbloudu(){

        double maxDalka = 0;
        double bezPitiStr = 0;
        double bezPitiRychl = 0;
        double bezPitiNejdel = 0;

        double casCestyStr = 0;
        double casCestyRychl = 0;
        double casCestyNejdel = 0;

        Velbloud velblStr = baseDat.getStredniVelbl();
        Velbloud velblRychl = baseDat.getRychlejsiVelbl();
        Velbloud velblNejdel = baseDat.getNejdelsiVelbl();

        if(!spoctenCasVelbl){
            for (Hrana bodCesty : cesta){
                if(Data.jeVetsi(bodCesty.getVzdalenost(), maxDalka)){
                    maxDalka = bodCesty.getVzdalenost();
                }

                if(Data.jeVetsi(velblStr.getVzdalenostMax(), bezPitiStr + bodCesty.getVzdalenost())){
                    bezPitiStr = 0;
                    casCestyStr += velblStr.getDobaPiti();
                }

                casCestyStr += bodCesty.getVzdalenost() / velblStr.getRychlost();
                bezPitiStr += bodCesty.getVzdalenost();

                if(Data.jeVetsi(velblNejdel.getVzdalenostMax(), bezPitiNejdel + bodCesty.getVzdalenost())){
                    bezPitiNejdel = 0;
                    casCestyNejdel += velblNejdel.getDobaPiti();
                }

                casCestyNejdel += bodCesty.getVzdalenost() / velblNejdel.getRychlost();
                bezPitiNejdel += bodCesty.getVzdalenost();

                if(Data.jeVetsi(velblRychl.getVzdalenostMax(), bezPitiRychl + bodCesty.getVzdalenost())){
                    bezPitiRychl = 0;
                    casCestyRychl += velblRychl.getDobaPiti();
                }

                casCestyRychl += bodCesty.getVzdalenost() / velblRychl.getRychlost();
                bezPitiRychl += bodCesty.getVzdalenost();

            }

            spoctenCasVelbl = true;
            if(Data.jeVetsi(maxDalka, velblRychl.getVzdalenostMax())){
                casCestyRychl = -1;
            }
            casRychlVelbl = casCestyRychl;

            if(Data.jeVetsi(maxDalka, velblNejdel.getVzdalenostMax())){
                casCestyNejdel = -1;
                System.out.println(maxDalka + " maxDalka");
                System.out.println(velblNejdel.getVzdalenostMax() + " velblNejdel.getVzdalenostMax()");
            }
            casNejdelVelbl = casCestyNejdel;

            if(Data.jeVetsi(maxDalka, velblStr.getVzdalenostMax())){
                casCestyStr = -1;
            }
            casStrVelbl = casCestyStr;
        }
    }

    /**
     * Prijima velblouda, pocet kusu, a spocita stihne li velbloud projit danou cestu v cas
     * @param velbloud
     * @param casDoruceni - cas prichodu pozadavku + doba cekani
     * @param pocetKosu - pocet kosu v danem doruceni
     * @return vraci 0, pokud velbloud stihne cestu, vraci 1 pokud velbloud
     * ma mensi vzdalenost nez nejdelsi hrana cesty
     * vraci 2, pokud velbloud nestihne cestu casove
     */
    public int stihneCestuVelbloud(Velbloud velbloud, double casDoruceni, int pocetKosu){
        double maxDalka = 0;
        double cestaBezPiti = 0;
        double celyCasCesty = 0;
        double celaDalkaCesty = 0;

        for (Hrana bodCesty : cesta){

            if(Data.jeVetsi(bodCesty.getVzdalenost(), maxDalka)){
                maxDalka = bodCesty.getVzdalenost();
            }

            if(Data.jeVetsi(velbloud.getVzdalenostMax(), cestaBezPiti + bodCesty.getVzdalenost())){
                cestaBezPiti = 0;
                celyCasCesty += velbloud.getDobaPiti();
            }

            celyCasCesty += bodCesty.getVzdalenost() / velbloud.getRychlost();
            celaDalkaCesty += bodCesty.getVzdalenost();
            cestaBezPiti += bodCesty.getVzdalenost();

        }

        maxUsecka = maxDalka;
        dalkaCesty = celaDalkaCesty;

        double realnyCasDoruceni = celyCasCesty + velbloud.getAktualniCas() + (2 * pocetKosu * velbloud.getDomovskaStanice().getCasNalozeni()); // cely cas
        if(Data.jeVetsi(casDoruceni, realnyCasDoruceni)){
            if(Data.jeVetsi(velbloud.getVzdalenostMax(), maxDalka)){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{
            return 2;
        }
    }

    /**
     * Vraci cas ktery potrebuje rychlejsi velbloud na danou cestu
     * pokud tuto cestu nedokaze zvladnout, vraci -1
     * @param pocetKosu
     * @return
     */
    public double getCasRychlVelbl(int pocetKosu){

        casVelbloudu();
        Sklad sklad = (Sklad) cesta.get(0).getStanice();
        double casNalozeni = sklad.getCasNalozeni();

        if(casRychlVelbl == -1){
            return -1;
        }
        return casRychlVelbl + 2 * pocetKosu * casNalozeni;
    }

    /**
     * Vraci cas ktery potrebuje nejdelsi velbloud na danou cestu
     * pokud tuto cestu nedokaze zvladnout, vraci -1
     * @param pocetKosu
     * @return
     */
    public double getCasNejdelVelbl(int pocetKosu){

        casVelbloudu();
        Sklad sklad = (Sklad) cesta.get(0).getStanice();

        double casNalozeni = sklad.getCasNalozeni();

        if(casNejdelVelbl == -1){
            return -1;
        }

        return casNejdelVelbl + 2 * pocetKosu * casNalozeni;
    }

    public double getCasStrVelbl(int pocetKosu){
        casVelbloudu();

        Sklad sklad = (Sklad) cesta.get(0).getStanice();
        double casNalozeni = sklad.getCasNalozeni();

        if(casStrVelbl == -1){
            return -1;
        }

        return casStrVelbl + 2 * pocetKosu * casNalozeni;
    }

    public boolean zvladneNejdelVelbl(){
        casVelbloudu();

        if(casNejdelVelbl == -1){
            return false;
        }
        return true;
    }

    public boolean zvladneStrVelbl(){
        casVelbloudu();

        if(Data.jeVetsi(baseDat.getStredniVelbl().getVzdalenostMax(), maxUsecka)){
            return true;
        }
        return false;
    }

    public double getIndexCesty(){
        spocitejIndexCesty();
        return indexNejlepsiCesty;
    }

    public double getCelaDalka(){
        spocitejIndexCesty();
        return dalkaCesty;
    }

    public double getMaxUsecka(){
        spocitejIndexCesty();
        return maxUsecka;
    }

    public void vypis(){


        for (Hrana bodCesty : cesta){
            System.out.print("from: " + bodCesty.getStanice().getId()  + " -> " + bodCesty.getVzdalenost() + " -> ");

        }
        System.out.println(getCelaDalka() + "O");
    }

    Hrana get(){
        return cesta.get(0);
    }

    void odstran(){
        if(cesta.size() > 0){
            cesta.get(0).getVzdalenost();
            cesta.remove(0);
        }
    }

    public List<Hrana> getList(){
        return cesta;
    }

    protected Object clone() throws CloneNotSupportedException {

        List<Hrana> cloneList = new ArrayList<>();
        CestaList clone = new CestaList(baseDat, cloneList);

        for(int i = 0; i < cesta.size(); i++){
            Hrana novyBod;

            if(i == cesta.size() - 1){
                novyBod = (Hrana) cesta.get(i).clone();
            }else{
                novyBod = cesta.get(i);
            }

            cloneList.add(i, novyBod);
        }

        return clone;
    }

}
