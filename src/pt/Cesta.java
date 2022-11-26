package pt;

import java.util.Stack;

public class Cesta implements Cloneable{

    private BodCesty prvni = null;
    private BodCesty posledni = null;

    private boolean jeSpoctenIndex = false;
    private double maxUsecka = 0;
    private double dalkaCesty = 0;
    private int indexNejlepsiCesty = 0;

    private boolean spoctenCasVelbl = false;
    private double casRychlVelbl = 0;
    private double casNejdelVelbl = 0;
    private double casStrVelbl = 0;

    private BodCesty predposledni = null;

    private final Data baseDat;

    public Cesta(Data baseDat){
        this.baseDat = baseDat;
    }

    public void pridej(Bod novaStanice, double vzdalenost){

        BodCesty novyBod = new BodCesty(novaStanice, 0);

//        if(predposledni != null){
//            predposledni.vzdalenost = vzdalenost;
//        }
        if(prvni == null){
            prvni = novyBod;
            posledni = novyBod;
        }
        else{
            posledni.next = novyBod;
            predposledni = posledni;
            posledni = novyBod;
            predposledni.vzdalenost = vzdalenost;
        }

    }

    public void pridejBodCesty(BodCesty novyBod){

        if(prvni == null){
            prvni = novyBod;
            posledni = novyBod;
        }
        else{
            posledni.next = novyBod;
            posledni = novyBod;
        }

    }



    /**
     * Metoda spocita dalku, cesty, max usecku, a generuje z techto dat
     * "index nejlepsi cesty" tento index slouzi k razeni cest od nejlepsi k nejhorsi.
     * formula tohoto indexu je: cela dalka + pocet usecek * delku max usecky;
     */
    public void spocitejIndexCesty(){
        double dalka = 0;
        double pocetStanic = 0;
        BodCesty bodCesty = prvni;

        if(jeSpoctenIndex){
            while (bodCesty != null){
                pocetStanic++;

                dalka += bodCesty.vzdalenost;
                bodCesty = bodCesty.next;

                if(Data.jeVetsi(bodCesty.vzdalenost, maxUsecka)){
                    maxUsecka = bodCesty.vzdalenost;
                }
            }
            dalkaCesty = dalka;

            if(Data.jeVetsi(maxUsecka, baseDat.getStredniVelbl().getVzdalenostMax())){ // pokud cestu nelze splnit strednim velbloudem
                indexNejlepsiCesty = (int) (dalkaCesty * 1.5); // nebo 2

                if(Data.jeVetsi(maxUsecka, baseDat.getNejdelsiVelbl().getVzdalenostMax())){
                    indexNejlepsiCesty = (int) (dalkaCesty * 7);; // mozna jeste zmenim
                }

            }
            else{
                indexNejlepsiCesty = (int) (dalkaCesty);
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
        BodCesty bodCesty = prvni;

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
            while(bodCesty != null){
                if(Data.jeVetsi(bodCesty.vzdalenost, maxDalka)){
                    maxDalka = bodCesty.vzdalenost;
                }

                if(Data.jeVetsi(velblStr.getVzdalenostMax(), bezPitiStr + bodCesty.vzdalenost)){
                    bezPitiStr = 0;
                    casCestyStr += velblStr.getDobaPiti();
                }

                casCestyStr += bodCesty.vzdalenost / velblStr.getRychlost();
                bezPitiStr += bodCesty.vzdalenost;

                if(Data.jeVetsi(velblNejdel.getVzdalenostMax(), bezPitiNejdel + bodCesty.vzdalenost)){
                    bezPitiNejdel = 0;
                    casCestyNejdel += velblNejdel.getDobaPiti();
                }

                casCestyNejdel += bodCesty.vzdalenost / velblNejdel.getRychlost();
                bezPitiNejdel += bodCesty.vzdalenost;

                if(Data.jeVetsi(velblRychl.getVzdalenostMax(), bezPitiRychl + bodCesty.vzdalenost)){
                    bezPitiRychl = 0;
                    casCestyRychl += velblRychl.getDobaPiti();
                }

                casCestyRychl += bodCesty.vzdalenost / velblRychl.getRychlost();
                bezPitiRychl += bodCesty.vzdalenost;

                bodCesty = bodCesty.next;

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
        BodCesty bodCesty = prvni;
        double maxDalka = 0;
        double cestaBezPiti = 0;
        double celyCasCesty = 0;
        double celaDalkaCesty = 0;


        while(bodCesty != null){
            if(Data.jeVetsi(bodCesty.vzdalenost, maxDalka)){
                maxDalka = bodCesty.vzdalenost;
            }

            if(Data.jeVetsi(velbloud.getVzdalenostMax(), cestaBezPiti + bodCesty.vzdalenost)){
                cestaBezPiti = 0;
                celyCasCesty += velbloud.getDobaPiti();
            }

            celyCasCesty += bodCesty.vzdalenost / velbloud.getRychlost();
            celaDalkaCesty += bodCesty.vzdalenost;
            cestaBezPiti += bodCesty.vzdalenost;
            bodCesty = bodCesty.next;

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
     * Metoda otoci frontu cesty pro cestu zpatky
     * @return cesta zpatky
     */
    public Cesta getCestaZpatky(){
        Stack<BodCesty> cesta = new Stack<>();
        Cesta cestaZpatky = new Cesta(baseDat);
        double predchoziDalka = 0;
        BodCesty predchoziBod = null;

        BodCesty aktualniBod = prvni;

        while(aktualniBod != null){

            BodCesty novyBod = new BodCesty(aktualniBod.stanice);
            novyBod.vzdalenost = predchoziDalka;
            predchoziDalka = aktualniBod.vzdalenost;
            novyBod.next = predchoziBod;

            cesta.push(novyBod);
            predchoziBod = novyBod;
            aktualniBod = prvni.next;

        }

        while(!cesta.isEmpty()){

            BodCesty bodCesty = cesta.pop();
            cestaZpatky.pridej(bodCesty.stanice, bodCesty.vzdalenost);

        }

        return cestaZpatky;
    }

    /**
     * Vraci cas ktery potrebuje rychlejsi velbloud na danou cestu
     * pokud tuto cestu nedokaze zvladnout, vraci -1
     * @param pocetKosu
     * @return
     */
    public double getCasRychlVelbl(int pocetKosu){

        casVelbloudu();

        Sklad sklad = (Sklad) prvni.stanice;
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

        Sklad sklad = (Sklad) prvni.stanice;
        double casNalozeni = sklad.getCasNalozeni();

        if(casNejdelVelbl == -1){
            return -1;
        }

        return casNejdelVelbl + 2 * pocetKosu * casNalozeni;
    }

    public double getCasStrVelbl(int pocetKosu){
        casVelbloudu();

        Sklad sklad = (Sklad) prvni.stanice;
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

    public BodCesty getPosledni(){
        return posledni;
    }

    public int getIndexCesty(){
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
        BodCesty bodCesty = prvni;

        while(bodCesty != null){
            System.out.print("from: " + bodCesty.stanice.getId()  + " -> " + bodCesty.vzdalenost + " -> ");
            bodCesty = bodCesty.next;
        }
        System.out.println(getCelaDalka() + "O");
    }

    BodCesty get(){
        return prvni;
    }

    void odstran(){
        if(prvni != null){
            prvni = prvni.next;
        }
    }

    public BodCesty getPredposledni() {
        return predposledni;
    }

    public void setPredposledni(BodCesty predposledni) {
        this.predposledni = predposledni;
    }

    public Bod getPrvniBod(){
        return prvni.stanice;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Cesta clone = new Cesta(baseDat);

        BodCesty bod = prvni;

        while(bod != null){
            clone.pridejBodCesty((BodCesty) bod.clone());
            bod = bod.getNext();
        }

        return clone;

    }
}
