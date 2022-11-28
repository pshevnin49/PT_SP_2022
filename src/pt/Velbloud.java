package pt;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Velbloud {

    private Sklad domovskaStanice;
    private CestaList cesta;
    private boolean jeNaCesteZpatky;

    private CestaList cestaZpatky;
    private DruhVelbloudu druhVelbloudu = null;
    private StavVelbloudu stav;

    private int id;
    private double rychlost;
    private double vzdalenostBezPiti;
    private int aktualniPocetKosu;

    private Pozadavek aktualniPozadavek;
    private double vzdalenostMax;
    private Data baseDat;
    private double casSplneniAkce;

    private List<LogTrasy> logyTras;
    private LogTrasy aktualniLog;

    private double casPoslVystupu;

    private double celkovaVzdalenost = 0;
    private double casVytvoreni;
    private double celyCasCesty;
    private double maxVzdalBezPiti = 0;
    private int trasaID = 0;

    private int vsichniKose;
    private int vsichniPoz;

    private int dobaPiti;

    public Velbloud(int id, DruhVelbloudu druhVelbloudu, Sklad domovskaStanice, Data baseDat) {
        this.id = id;
        this.casVytvoreni = baseDat.getAktualniCas();
        this.rychlost = druhVelbloudu.randRych();
        this.vzdalenostMax = druhVelbloudu.randVzdal();
        this.domovskaStanice = domovskaStanice;
        this.druhVelbloudu = druhVelbloudu;
        this.baseDat = baseDat;
        this.stav = StavVelbloudu.CEKA;
        this.aktualniPocetKosu = 0;
        this.dobaPiti = druhVelbloudu.getDobaPiti();
        this.logyTras = new ArrayList<>();
    }

    /**
     * Tento konstruktor slouzi k generaci merice velbloudy (jako stredni, rychlejsi a t.d velbloudy)
     * @param id
     * @param baseDat
     * @param rychlost
     * @param vzdalenost
     * @param dobaPiti
     */
    public Velbloud(int id, Data baseDat, double rychlost, double vzdalenost, double dobaPiti) {
        this.id = id;
        this.rychlost = rychlost;
        this.vzdalenostMax = vzdalenost;
        this.domovskaStanice = null;
        this.druhVelbloudu = null;
        this.baseDat = baseDat;
        this.stav = StavVelbloudu.CEKA;
        this.aktualniPocetKosu = 0;
        this.dobaPiti = (int) dobaPiti;
    }

    /**
     * Hlavni ridici metoda velbloudu. Kazdy krok kontroluje ma li byt splnena akce kterou aktualne dela velbloud
     * Pokud velbloud este nevratil domu vraci false, pokud vratil, vraci true;
     * @return
     */
    public boolean kontrolaCasu(){
        if(!Data.jeVetsi(casSplneniAkce, baseDat.getAktualniCas())){
            switch(stav){
                case CEKA:
                    //System.out.println("break v switch case zvetseniCasu");
                    break;

                case PIJE:
                    posuvDoDalsiSt();
                    break;

                case POSOUVA:
                    if(prijelDoStanici()){
                        return true;
                    }
                    break;

                case NAKLADA:
                    zacniCestu();
                    break;

                case VYKLADA:
                    zacniCestuZpatky();
                    break;
            }
        }
        return false;
    }

    public DruhVelbloudu getDruhVelbloudu(){
        return druhVelbloudu;
    }

    /**
     * Velbloud zacina cestu, po spusteni teto metody stav velbloudu je:
     * posouva z domovskeho skladu do prvniho bodu cesty.
     *
     */
    private void zacniCestu(){
        vzdalenostBezPiti = 0;
        posuvDoDalsiSt();
    }

    private void zacniCestuZpatky(){
        cesta = cestaZpatky;
        vzdalenostBezPiti = 0;
        jeNaCesteZpatky = true;
        posuvDoDalsiSt();
    }

    /**
     * Zpracovava prijezd velbloudu do jakekoliv stanici
     * kdyz vratil domu, vraci true, v opacnem pripade - false
     */
    private boolean prijelDoStanici(){
        double aktualniCas = baseDat.getAktualniCas();

        if(cesta.getList().size() == 1){
            if(!jeNaCesteZpatky){

                double casVylozeni = aktualniCas + domovskaStanice.getCasNalozeni() * aktualniPocetKosu;

                System.out.printf("Cas: %d, Velbloud: %d, Oaza: %d, Vylozeno kosu: %d, Vylozeno v: %d, Casova rezerva: %d\n",
                        Math.round(aktualniCas), id, cesta.get().getStanice().getId(), aktualniPocetKosu, Math.round(casVylozeni),
                        Math.round((aktualniPozadavek.getCasPrichodu() + aktualniPozadavek.getCasOcekavani()) - casVylozeni)
                );
                zacniVykladat();
            }
            else{
                System.out.printf("Cas: %d, Velbloud: %d, Navrat do skladu: %d\n", Math.round(aktualniCas), id, domovskaStanice.getId());
                prijelDomu();
                return true;
            }
        }
        else{

            String druhStanice;
            String pitiStanice;
            if(cesta.get().getStanice().getClass() == Sklad.class){//jakeho druhu je stanice
                druhStanice = "Sklad";
                pitiStanice = "na sklade";
            }else{
                druhStanice = "Oaza";
                pitiStanice = "v oaze";
            }

            if(Data.jeVetsi(getCasCesty(cesta.get().getVzdalenost()), (vzdalenostMax - vzdalenostBezPiti))){

                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Kuk na velblouda\n", Math.round(aktualniCas), id, druhStanice,
                        cesta.get().getStanice().getId());
                posuvDoDalsiSt();
            }
            else{

                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Ziznivy %s, Pokracovani mozne v: %d\n", Math.round(aktualniCas), id, druhStanice,
                        cesta.get().getStanice().getId(), druhVelbloudu.getNazev(), Math.round(aktualniCas + druhVelbloudu.getDobaPiti()));

                vlozLogPiti(pitiStanice);
                zacniPiti();
            }
        }
        return false;
    }

    /**
     * Ulozeni logu piti na zastavce
     * @param pitiStanice
     */
    private void vlozLogPiti(String pitiStanice){
        double aktualniCas = baseDat.getAktualniCas();
        String logPiti = String.format("Zastavil pro piti %s, c: %d cas: %.2f", pitiStanice, cesta.get().getStanice().getId(), aktualniCas);
        aktualniLog.vlozZastavku(logPiti);
    }

    /**
     * Prijezd do domovske stanici
     */
    private void prijelDomu(){
        aktualniLog.setCasNavratu(baseDat.getAktualniCas());
        logyTras.add(aktualniLog);
        jeNaCesteZpatky = false;
        stav = StavVelbloudu.CEKA;
        domovskaStanice.pridejVelblouda(this);
        casSplneniAkce = Data.MAX_VALUE;
    }

    /**
     * V teto metode velbloud zacina nakladat v domocske oaze. (take toto metodou zacina splneni objednavky)
     * @param pocetKosu
     * @param cesta
     */
    public void zacniNakladat(int pocetKosu, CestaList cesta, Pozadavek pozadavek) throws CloneNotSupportedException {

        System.out.printf("Cas: %d, Velbloud: %d, Sklad: %d, Nalozeno kosu: %d, Odchod v: %d\n", Math.round(baseDat.getAktualniCas()),
                id, domovskaStanice.getId(), pocetKosu, Math.round(baseDat.getAktualniCas() + domovskaStanice.getCasNalozeni() * pocetKosu));

        pozadavek.setVelbloud(this);

        celkovaVzdalenost += cesta.getCelaDalka() * 2;
        aktualniPocetKosu = pocetKosu;
        aktualniPozadavek = pozadavek;

        trasaID++;
        aktualniLog = new LogTrasy(trasaID, baseDat.getAktualniCas(), pozadavek.getIdOazy(), pocetKosu);

        baseDat.velbloudNaCeste(this);
        domovskaStanice.odstranKose(pocetKosu);
        this.cesta = (CestaList) cesta.clone();
        cestaZpatky = getCestuZpatky();
        stav = StavVelbloudu.NAKLADA;
        casSplneniAkce = baseDat.getAktualniCas() + domovskaStanice.getCasNalozeni() * pocetKosu;
    }

    /**
     * Odstranuje z listu posledni stanice a posouva velblouda do pristi stanice.
     */
    private void posuvDoDalsiSt(){
        stav = StavVelbloudu.POSOUVA;
        Hrana bodCesty = cesta.get();
        if(Data.jeVetsi(getCasCesty(bodCesty.getVzdalenost()), maxVzdalBezPiti)){
            maxVzdalBezPiti = getCasCesty(bodCesty.getVzdalenost());
        }
        casSplneniAkce = baseDat.getAktualniCas() + getCasCesty(bodCesty.getVzdalenost());
        celyCasCesty += getCasCesty(bodCesty.getVzdalenost());
        vzdalenostBezPiti += cesta.get().getVzdalenost();
        cesta.odstran();
    }

    public double getAktualniCas(){
        return baseDat.getAktualniCas();
    }
    public double getCasPristiAkce(){
        return casSplneniAkce;
    }
    public Sklad getDomovskaStanice() {
        return domovskaStanice;
    }
    public double getRychlost() {
        return rychlost;
    }
    public double getVzdalenostMax() {
        return vzdalenostMax;
    }
    public int getId(){
        return id;
    }


    /**
     * Metoda urcena k tomu, aby velbloud zacal splnovat akci: piti
     */
    private void zacniPiti(){
        if(Data.jeVetsi(vzdalenostBezPiti, maxVzdalBezPiti)){
            maxVzdalBezPiti = vzdalenostBezPiti;
        }
        vzdalenostBezPiti = 0;
        casSplneniAkce = baseDat.getAktualniCas() + druhVelbloudu.getDobaPiti();
        stav = StavVelbloudu.PIJE;
    }

    public int getDobaPiti(){
        return dobaPiti;
    }

    private void zacniVykladat(){
        vsichniPoz += 1;
        vsichniKose += aktualniPocetKosu;
        aktualniPozadavek.zvetsiVylozeneKose(aktualniPocetKosu);
        stav = StavVelbloudu.VYKLADA;
        aktualniPocetKosu = 0;
        casSplneniAkce = baseDat.getAktualniCas() + domovskaStanice.getCasNalozeni() * aktualniPocetKosu;
        if(aktualniPozadavek.getNevylozeneKose() <= 0){
            aktualniPozadavek.setCasFactickehoDoruceni(casSplneniAkce);
        }
    }


    private double getCasCesty(double dalka){
        double cas;
        cas = dalka / rychlost;
        return cas;
    }

    public double getCelkovaVzd(){
        return  celkovaVzdalenost;
    }

    /**
     * Prijima stack kam za behu programu zapisuje cesta zpatky pro velbloud
     * a prevadi do formatu fronty, ktery potrebuje velbloud
     * @return fronta z cestou zpatky
     */
    private CestaList getCestuZpatky(){

        CestaList cestaZpatky = new CestaList(baseDat);
        int indexZpatky = 0;

        for(int i = (cesta.getList().size() - 1); i >= 0; i--){
            Hrana bod = new Hrana(cesta.getList().get(i).getStanice(), 0);
            if(i - 1 >= 0){
                bod.setVzdalenost(cesta.getList().get(i - 1).getVzdalenost());
            }
            cestaZpatky.getList().add(indexZpatky, bod);
            indexZpatky++;
        }

        return cestaZpatky;
    }

    public String getLog(){
        String log = String.format("  Velbloud c: %d; druh: %s;  rychlost: %.2f;  maxVzdalBezPiti: %.2f \n", id, druhVelbloudu.getNazev(), rychlost, maxVzdalBezPiti);

        for(int i = 0; i < logyTras.size(); i++){
            log += logyTras.get(i).toString();
        }


        log += String.format("    Odpocival celkem: %.2f; cela vzdalenost: %.2f", (baseDat.getAktualniCas() - casVytvoreni - celyCasCesty), celkovaVzdalenost);

        return log;
    }

}


