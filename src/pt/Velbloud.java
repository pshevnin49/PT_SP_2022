package pt;

public class Velbloud {

    private Sklad domovskaStanice;
    private StackCesta cesta;
    private StackCesta cestaZpatky;

    private boolean jeNaCeste;
    private boolean jeNaCesteZpatky;

    private DruhVelbloudu druhVelbloudu;
    private StavVelbloudu stav;

    private int id;
    private int rychlost;
    private double vzdalenostBezPiti;
    private double predchoziVzdalenost; // pro cestu zpatky
    private int aktualniPocetKosu;

    private Pozadavka aktualniPozadavka;
    private int vzdalenostMax;
    private double aktualniCas;
    private double casSplneniAkce;

    public Velbloud(int id, int rychlost, int vzdalenostMax, DruhVelbloudu druhVelbloudu, Sklad domovskaStanice, double aktualniCas) {
        this.id = id;
        this.rychlost = rychlost;
        this.vzdalenostMax = vzdalenostMax;
        this.domovskaStanice = domovskaStanice;
        this.aktualniCas = aktualniCas;
        this.cestaZpatky = new StackCesta();
        this.druhVelbloudu = druhVelbloudu;
        this.jeNaCeste = false;
        this.stav = StavVelbloudu.CEKA;
        this.aktualniPocetKosu = 0;
    }

    /**
     * Hlavni ridici metoda velbloudu. Kazdy krok kontroluje ma li byt splnena akce kterou aktualne dela velbloud
     * @param cas
     */
    public void zvetseniCasu(double cas){
        aktualniCas += cas;

        //System.out.println("Cas: " + aktualniCas);

        if(!Data.jeVetsi(casSplneniAkce, aktualniCas)){
            switch(stav){
                case CEKA:
                    //System.out.println("break v switc case zvetseniCasu");
                    break;

                case PIJE:
                    posuvDoDalsiSt();
                    break;

                case POSOUVA:

                    prijelDoStanici();
                    break;

                case NAKLADA:
                    zacniCestu();
                    break;

                case VYKLADA:
                    zacniCestuZpatky();
                    break;
            }
        }
    }

    public double getCasPristiAkce(){
        return casSplneniAkce;
    }
    public Sklad getDomovskaStanice() {
        return domovskaStanice;
    }
    public StackCesta getCesta() {
        return cesta;
    }
    public int getRychlost() {
        return rychlost;
    }
    public int getVzdalenostMax() {
        return vzdalenostMax;
    }

    public double getAktualniCas(){
        return aktualniCas;
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
        predchoziVzdalenost = 0;
        cestaZpatky = new StackCesta();
        vzdalenostBezPiti = 0;
        jeNaCeste = true;
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
     */
    private void prijelDoStanici(){
        if(cesta.get().next == null){
            if(!jeNaCesteZpatky){

                double casVylozeni = aktualniCas + domovskaStanice.getCasNalozeni() * aktualniPocetKosu;

                System.out.printf("Cas: %d, Velbloud: %d, Oaza: %d, Vylozeno kosu: %d, Vylozeno v: %d, Casova rezerva: %d\n",
                        Math.round(aktualniCas), id, cesta.get().stanice.getId(), aktualniPocetKosu, Math.round(casVylozeni),
                        Math.round((aktualniPozadavka.getCasPrichodu() + aktualniPozadavka.getCasOcekavani()) - casVylozeni)
                );
                pridejBodCestyZpatky();
                zacniVykladat();
            }
            else{
                System.out.printf("Cas: %d, Velbloud: %d, Navrat do skladu: %d\n", Math.round(aktualniCas), id, domovskaStanice.getId());
                prijelDomu();
            }
        }
        else{

            String druhStanice;
            if(cesta.get().stanice.getClass() == Sklad.class){//jakeho druhu je stanice
                druhStanice = "Sklad";
            }else{
                druhStanice = "Oaza";
            }

            if(Data.jeVetsi(getCasCesty(cesta.get().vzdalenost), (vzdalenostMax - vzdalenostBezPiti))){

                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Kuk na velblouda\n", Math.round(aktualniCas), id, druhStanice,
                        cesta.get().stanice.getId());
                posuvDoDalsiSt();
            }
            else{

                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Ziznivy %s, Pokracovani mozne v: %d\n", Math.round(aktualniCas), id, druhStanice,
                        cesta.get().stanice.getId(), druhVelbloudu.getNazev(), Math.round(aktualniCas + druhVelbloudu.getDobaPiti()) );
                zacniPiti();
            }
        }
    }

    /**
     * Prijezd do domovske stanici
     */
    private void prijelDomu(){
        jeNaCeste = false;
        jeNaCesteZpatky = false;
        stav = StavVelbloudu.CEKA;
    }

    /**
     * V teto metode velbloud zacina nakladat v domocske oaze. (take toto metodou zacina splneni objednavky)
     * @param pocetKosu
     * @param cesta
     */
    public void zacniNakladat(int pocetKosu, StackCesta cesta, Pozadavka pozadavka){


        System.out.printf("Cas: %d, Velbloud: %d, Sklad: %d, Nalozeno kosu: %d, Odchod v: %d\n", Math.round(aktualniCas),
                id, domovskaStanice.getId(), pocetKosu, Math.round(aktualniCas + domovskaStanice.getCasNalozeni() * pocetKosu));

        aktualniPocetKosu = pocetKosu;
        aktualniPozadavka = pozadavka;
        domovskaStanice.odstranKose(pocetKosu);
        this.cesta = cesta;
        stav = StavVelbloudu.NAKLADA;
        casSplneniAkce = aktualniCas + domovskaStanice.getCasNalozeni() * pocetKosu;
    }

    /**
     * Odstranuje ze stacku posledni stanice a posouva velblouda do pristi stanice.
     */
    private void posuvDoDalsiSt(){
        stav = StavVelbloudu.POSOUVA;
        BodCesty bodCesty = cesta.get();
        casSplneniAkce = aktualniCas + getCasCesty(bodCesty.vzdalenost);

        if(!jeNaCesteZpatky){
            pridejBodCestyZpatky();
        }
        vzdalenostBezPiti += cesta.get().vzdalenost;
        predchoziVzdalenost = cesta.get().vzdalenost;
        cesta.odstran();

    }

    /**
     * Metoda urcena k tomu, aby velbloud zacal splnovat akci: piti
     */
    private void zacniPiti(){
        vzdalenostBezPiti = 0;
        casSplneniAkce = aktualniCas + druhVelbloudu.getDobaPiti();
        stav = StavVelbloudu.PIJE;
    }

    private void zacniVykladat(){
        stav = StavVelbloudu.VYKLADA;
        aktualniPocetKosu = 0;
        casSplneniAkce = aktualniCas + domovskaStanice.getCasNalozeni() * aktualniPocetKosu;
    }

    public void pridejBodCestyZpatky(){// pridava bod do cesty zpatky pridava
        cestaZpatky.pridej(cesta.get().stanice, predchoziVzdalenost);
    }

    private double getCasCesty(double dalka){
        double cas;
        cas = dalka/rychlost;
        return cas;
    }

}


