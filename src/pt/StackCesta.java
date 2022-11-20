package pt;

import java.util.List;

public class StackCesta implements Cloneable{ // treba se udelat stack cesta tak, aby spoustel vypocty jenom jednou, pak vyuzival promenne

    private BodCesty top = null;
    private Bod stanice;

    private boolean jeSpoctenIndex = false;
    private double maxUsecka = 0;
    private double dalkaCesty = 0;
    private int indexNejlepsiCesty = 0;

    private boolean jeCasRychlVelbl = false;
    private double casRychlVelbl = 0;

    private boolean jeCasNejdelVelbl = false;
    private double casNejdelVelbl = 0;

    private Data baseDat;

    public StackCesta(Data baseDat){
        this.baseDat = baseDat;
    }

    public void pridej(Bod novaStanice, double vzdalenost){

        BodCesty novyBod = new BodCesty(novaStanice, vzdalenost, top);

        if(top == null){
            top = novyBod;
            stanice = novyBod.stanice;
        }
        else{
            novyBod.next = top;
            top = novyBod;
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
        BodCesty bodCesty = top;

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
            indexNejlepsiCesty = (int) (dalka + pocetStanic * maxUsecka);
            jeSpoctenIndex = true;
        }

    }

    public void odstran(){
        if(top == null){
            return;
        }
        else{
            top = top.next;
        }
    }

    public void vypis(){
        BodCesty bodCesty = top;

        while(bodCesty != null){
            System.out.print("from: " + bodCesty.stanice.getId()  + " -> " + bodCesty.vzdalenost + " -> ");
            bodCesty = bodCesty.next;
        }
        System.out.println(getCelaDalka() + "O");
    }

    /**
     * Spocita kolik casu potrebuje rychlejsi velbloud na tuto cestu pokud to zvladne
     * z pohledu maximalni delky, pokud to nezvladne, cas bude se rovnat -1
     */
    public void casRychlVelbloud(){
        BodCesty bodCesty = top;
        double maxDalka = 0;
        double cestaBezPiti = 0;
        double celyCasCesty = 0;
        Velbloud velbloud = baseDat.getRychlejsiVelbl();

        if(!jeCasRychlVelbl){
            while(bodCesty != null){
                if(Data.jeVetsi(bodCesty.vzdalenost, maxDalka)){
                    maxDalka = bodCesty.vzdalenost;
                }

                if(Data.jeVetsi(velbloud.getVzdalenostMax(), cestaBezPiti + bodCesty.vzdalenost)){
                    cestaBezPiti = 0;
                    celyCasCesty += velbloud.getDruhVelbloudu().getDobaPiti();
                }

                celyCasCesty += bodCesty.vzdalenost / velbloud.getRychlost();
                cestaBezPiti += bodCesty.vzdalenost;
                bodCesty = bodCesty.next;

            }

            jeCasRychlVelbl = true;

            double realnyCasDoruceni = celyCasCesty + velbloud.getAktualniCas();
            if(Data.jeVetsi(maxDalka, velbloud.getVzdalenostMax())){
                realnyCasDoruceni = -1;
            }
            casRychlVelbl = realnyCasDoruceni;
        }
    }

    /**
     * Spocita kolik casu potrebuje nejdelsi velbloud na tuto cestu pokud to zvladne
     * z pohledu maximalni delky, pokud to nezvladne, cas bude se rovnat -1
     */
    public void casNejdelVelbloud(){
        BodCesty bodCesty = top;
        double maxDalka = 0;
        double cestaBezPiti = 0;
        double celyCasCesty = 0;
        Velbloud velbloud = baseDat.getNejdelsiVelbl();

        if(!jeCasNejdelVelbl){
            while(bodCesty != null){
                if(Data.jeVetsi(bodCesty.vzdalenost, maxDalka)){
                    maxDalka = bodCesty.vzdalenost;
                }

                if(Data.jeVetsi(velbloud.getVzdalenostMax(), cestaBezPiti + bodCesty.vzdalenost)){
                    cestaBezPiti = 0;
                    celyCasCesty += velbloud.getDruhVelbloudu().getDobaPiti();
                }

                celyCasCesty += bodCesty.vzdalenost / velbloud.getRychlost();
                cestaBezPiti += bodCesty.vzdalenost;
                bodCesty = bodCesty.next;

            }

            jeCasNejdelVelbl = true;

            double realnyCasDoruceni = celyCasCesty + velbloud.getAktualniCas();
            if(Data.jeVetsi(maxDalka, velbloud.getVzdalenostMax())){
                realnyCasDoruceni = -1;
            }
            casNejdelVelbl = realnyCasDoruceni;
        }
    }

    /**
     * Prijima velblouda, pocet kusu, a spocita stihne li velbloud projit danou cestu v cas
     * @param velbloud
     * @param casDoruceni - cas prichodu pozadavku + doba cekani
     * @param pocetKosu
     * @return vraci 0, pokud velbloud stihne cestu, vraci 1 pokud velbloud
     * ma mensi vzdalenost nez nejdelsi hrana cesty
     * vraci 2, pokud velbloud nestihne cestu casove
     */
    public int stihneCestuVelbloud(Velbloud velbloud, double casDoruceni, int pocetKosu){
        BodCesty bodCesty = top;
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
                celyCasCesty += velbloud.getDruhVelbloudu().getDobaPiti();
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
     * Vraci cas ktery potrebuje rychlejsi velbloud na danou cestu
     * pokud tuto cestu nedokaze zvladnout, vraci -1
     * @param pocetKosu
     * @return
     */
    public double getCasRychlVelbl(int pocetKosu){
        casRychlVelbloud();

        Sklad sklad = (Sklad) top.stanice;
        double casNalozeni = sklad.getCasNalozeni();

        return casRychlVelbl + 2 * pocetKosu * casNalozeni;
    }

    /**
     * Vraci cas ktery potrebuje nejdelsi velbloud na danou cestu
     * pokud tuto cestu nedokaze zvladnout, vraci -1
     * @param pocetKosu
     * @return
     */
    public double getCasNejdelVelbl(int pocetKosu){
        casNejdelVelbloud();

        Sklad sklad = (Sklad) top.stanice;
        double casNalozeni = sklad.getCasNalozeni();

        return casNejdelVelbl + 2 * pocetKosu * casNalozeni;
    }

    public int getIndexNejlepsiCesty(){
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

    public Bod getPosledniStanice(){
        return stanice;
    }
    public BodCesty get(){
        return top;
    }

    public Bod getPrvniBod(){
        return top.stanice;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
