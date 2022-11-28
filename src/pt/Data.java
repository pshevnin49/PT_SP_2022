package pt;

import java.util.*;

/**
 * Trida data slouzi pro chraneni a prace s datama cele simulace
 */
public class Data {

    /** GRAF slouzi ke chraneni celeho grafu*/
    private final List<Bod> GRAF;
    /** List vsech velbloudu*/
    private final List<Velbloud> VSICHNI_VELBL;
    /** List vsech skladu*/
    private final List<Sklad> VSICHNI_SKLADY;
    /** List vsech oaz*/
    private final List<Oaza> VSICHNI_OAZY;
    /** List vsech druhu velbloudu*/
    private final List<DruhVelbloudu> DRUHU_VELBL;
    /** List vsech velbloudu ktere aktualne jsiu na ceste*/
    private final Set<Velbloud> VELBL_NA_CESTE;
    /** Vsichni docasne nesplnene pozadavky*/
    private final List<Pozadavek> NESPL_POZ;

    /** Log, ktery vypise v pripade nesplnitelneho pozadavku*/
    private String errLog = null;
    /** Aktualni cas simulace*/
    private double aktualniCas;

    /** Index posledniho vygenerovaneho velbloudu*/
    private int indexVelbloudu;

    /** Velbloud ktery ma stredni rychlost, a stredni max vzdalenost. Slouzi pro testovani cest*/
    private Velbloud stredniVelbl;
    /** Velbloud ktery ma maximalni rychlost, a stredni max vzdalenost toho druhu, ktery ma maximalni rychlost. Slouzi pro testovani cest*/
    private Velbloud rychlejsiVelbl;
    /** Velbloud ma nejvetsi max vzdalenost, a stredni rychlost druhu s max vzdalenosti*/
    private Velbloud nejdelsiVelbl;

    /** Maximalni double cislo*/
    public static final double MAX_VALUE = 1.7976931348623157E308;
    /** Minimalni double cislo*/
    public static final double EPS = 0.00000000001;

    /**
     * Konstruktor tridy data
     */
    public Data(){
        this.GRAF = new ArrayList<>();
        this.DRUHU_VELBL = new ArrayList<>();
        this.NESPL_POZ = new ArrayList<>();
        VSICHNI_VELBL = new ArrayList<>();
        VSICHNI_SKLADY = new ArrayList<>();
        VSICHNI_OAZY = new ArrayList<>();
        VELBL_NA_CESTE = new HashSet<>();
        this.aktualniCas = 0;
        this.indexVelbloudu = 1;
    }

    /**
     * Methoda podle spravne pravdepodobnosti vraci nahodny druh noveho velbloudu
     * @return druhVelbloudu
     */
    public DruhVelbloudu getNahodnyDruhVelbloudu(){

        Random random = new Random();

        DruhVelbloudu druhVelbloudu;
        int randCislo = random.nextInt(100);

        int dolniHranice = 0;
        int horniHranice = 0;

        for(int i = 0; i < DRUHU_VELBL.size(); i++){
            horniHranice += (int) (DRUHU_VELBL.get(i).getPomerDruhuVelbloudu() * 100);

            if(randCislo < horniHranice && randCislo >= dolniHranice){
                druhVelbloudu = DRUHU_VELBL.get(i);
                return druhVelbloudu;
            }

            dolniHranice = horniHranice;

        }
        return null;
    }


    /**
     * Metoda hleda vsichni aktualni (casove) pozadavky, odstranuje z listu nesplnenePozadavky,
     * a vraci list techto aktualnich pozadavek
     * @return
     */
    public List<Pozadavek> getAktualniPozadavky(){

        int index = 0;
        List<Pozadavek> aktualniPozadavky = new ArrayList<>();
        while(index < NESPL_POZ.size()){
            if(NESPL_POZ.get(index).getCasPrichodu() <= aktualniCas){
                aktualniPozadavky.add(NESPL_POZ.get(index));
                NESPL_POZ.remove(index);
            }
            else{
                index++;
            }
        }
        return aktualniPozadavky;
    }

    /**
     * Pridava novy druh velbloudu
     * @param druh
     */
    public void inputDruhVelbloudu(DruhVelbloudu druh){
        this.DRUHU_VELBL.add(druh);
    }

    /**
     * Pridava novy pozadavek
     * @param pozadavek
     */
    public void inputPozadavek(Pozadavek pozadavek){
        this.NESPL_POZ.add(pozadavek);
    }

    /**
     * Metoda prochazi vsichni velbloudy, a vsichni objednavky a hleda nejblizsi akci k aktualnimu casu
     * pak vraci (casAkci - aktualniCas) = casovy posuv od aktualniho casu do casu splneni akci
     * @return krok
     */
    public double getMinKrokCasu(){
        double krok = MAX_VALUE;
        double novyKrok;

        for(Velbloud velbloud : VELBL_NA_CESTE){
            novyKrok = velbloud.getCasPristiAkce();

            if(jeVetsi(krok, novyKrok)){
                krok = novyKrok;
            }
        }

        for(int i = 0; i < NESPL_POZ.size(); i++){
            novyKrok = NESPL_POZ.get(i).getCasPrichodu();

            if(jeVetsi(krok, novyKrok)){
                krok = novyKrok;
            }
        }

        return krok - aktualniCas;
    }

    /**
     * Metoda hleda sklad, ktery ma minimalni casovy krok do navetseni cisla kosu
     * Vyuziva jen tehdy, kdy neexistuje zadny jiny zasovy krok (velbloudy neposouvaji, vsichni pozadavky uz prisli)
     * ale existuji nesplnene pozadavky
     * @return minimalni casovy krok
     */
    public double getMinKrokSkladu(){
        double krok = MAX_VALUE;
        double novyKrok;

        for(Sklad sklad : VSICHNI_SKLADY){
            novyKrok = sklad.getCasovyKrok();

            if(jeVetsi(krok, novyKrok)){
                krok = novyKrok;
            }
        }

        return krok;
    }

    /**
     * Zvetsuje cas cele simulace o zadanou velikost
     * @param cas
     */
    public void zvetseniCasuSimulace(double cas){

        List<Velbloud> velblKOdstr = new ArrayList<>();
        for(int i = 0; i < VSICHNI_SKLADY.size(); i++){
            VSICHNI_SKLADY.get(i).zvetseniCasu(cas);
        }

        for(Velbloud velbloud : VELBL_NA_CESTE){
            if(velbloud.kontrolaCasu()){
                velblKOdstr.add(velbloud);
            }
        }

        for(Velbloud velbloud : velblKOdstr){
            velbloudZkoncilCestu(velbloud);
        }
        aktualniCas += cas;
    }

    /**
     * Prochazi vsichni zastavky, a pripravuje k Dijkstra algoritmu
     */
    public void pripravZastavky(){
        for(int i = 0; i < GRAF.size(); i++){
            GRAF.get(i).setDistance(Data.MAX_VALUE);
            GRAF.get(i).setJeZpracovany(false);
            GRAF.get(i).obnoveniCesty();
        }
    }

    /**
     * Vraci celkovou vzdalenost, kterou usli vsichni velbloudy
     * @return vzdalenost
     */
    public double getCelkovaVzdalenost(){
        double vzdalenost = 0;
        for(int i = 0; i < VSICHNI_VELBL.size(); i++){
            vzdalenost += VSICHNI_VELBL.get(i).getCelkovaVzd();
        }
        return vzdalenost;
    }

    /**
     * Pridava velblouda do aktualne cestujicich
     * @param velbloud
     */
    public void velbloudNaCeste(Velbloud velbloud){
        VELBL_NA_CESTE.add(velbloud);
    }

    public Set<Velbloud> getVelbloudyNaCeste(){
        return VELBL_NA_CESTE;
    }
    /**
     * Odstarnuje velblouda z aktualne cestujicich
     * @param velbloud
     */
    public void velbloudZkoncilCestu(Velbloud velbloud){
        VELBL_NA_CESTE.remove(velbloud);
    }

    /**
     * List vsech zastavek slouzi ke zpracovani sousedu.
     * V listu jsou sklady a oazy v rade, realny index = pozice v listu + 1
     * (na nulove pozici se nachazi prvni prvek)
     * @param zastavka
     */
    public void inputZastavka(Bod zastavka){
        this.GRAF.add(zastavka);
    }

    public void inputOaza(Oaza oaza){
        this.VSICHNI_OAZY.add(oaza);
    }

    public void inputSklad(Sklad sklad){
        this.VSICHNI_SKLADY.add(sklad);
    }

    public String getErrLog(){
        return errLog;
    }

    public void setErrLog(String log){
        errLog = log;
    }

    public List<Sklad> getVsichniSklady(){
        return VSICHNI_SKLADY;
    }

    public List<Velbloud> getVsichniVelbloudy(){
        return VSICHNI_VELBL;
    }

    public List<Oaza> getVsichniOazy(){
        return VSICHNI_OAZY;
    }

    public List<DruhVelbloudu> getDruhyVelbloudu() {
        return DRUHU_VELBL;
    }

    public List<Bod> getGraf(){
        return GRAF;
    }

    public double getAktualniCas(){
        return aktualniCas;
    }
    public int getIndexVelbloudu(){
        return indexVelbloudu;
    }

    public void indexVelblouduInc(){
        indexVelbloudu++;
    }

    public Velbloud getStredniVelbl() {
        return stredniVelbl;
    }

    public void setStredniVelbl(Velbloud stredniVelbl) {
        this.stredniVelbl = stredniVelbl;
    }

    public Velbloud getRychlejsiVelbl() {
        return rychlejsiVelbl;
    }

    public void setRychlejsiVelbl(Velbloud rychlejsiVelbl) {
        this.rychlejsiVelbl = rychlejsiVelbl;
    }

    public Velbloud getNejdelsiVelbl() {
        return nejdelsiVelbl;
    }

    public void setNejdelsiVelbl(Velbloud nejdelsiVelbl) {
        this.nejdelsiVelbl = nejdelsiVelbl;
    }

    /**
     * Prijima dva double cisla, a vrati true pokud x1 je vetsi nez x2 v opacnem pripade vrati false
     * @param x1
     * @param x2
     * @return
     */
    public static boolean jeVetsi(double x1, double x2){
        return (x1 - x2) > EPS;
    }

    /**
     * Vraci true pokud x1 je mensi nez x2
     * @param x1
     * @param x2
     * @return
     */
    public static boolean jeMensi(double x1, double x2){
        return (x1 - x2) < EPS;
    }


}
