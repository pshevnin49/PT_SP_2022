package pt;

import java.util.*;

public class Data {

    private List<Bod> graf;
    private List<Velbloud> vsichniVelbloudy;
    private List<Sklad> vsichniSklady;
    private List<Oaza> vsichniOazy;
    private List<DruhVelbloudu> druhyVelbloudu;
    private Set<Velbloud> velbloudyNaCeste;

    private List<Pozadavek> nesplnennePozadavky;
    private List<Pozadavek> splnenePozadavky;

    private boolean jeSpustenAlgoritmus = false;

    private double aktualniCas;
    private int indexVelbloudu;

    private double maxStrRychlostVelbloudu;
    private int dobaNapiti;
    private double maxStrDalkaVelbloudu;

    private double maxDalka;
    private double maxRychlost;

    private Velbloud stredniVelbl;
    private Velbloud rychlejsiVelbl;
    private Velbloud nejdelsiVelbl;

    public static final double MAX_VALUE = 1.7976931348623157E308;

    public Data(){
        this.graf = new ArrayList<>();
        this.druhyVelbloudu = new ArrayList<>();
        this.nesplnennePozadavky = new ArrayList<>();
        vsichniVelbloudy = new ArrayList<>();
        vsichniSklady = new ArrayList<>();
        vsichniOazy = new ArrayList<>();
        velbloudyNaCeste = new HashSet<>();
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

        for(int i = 0; i < druhyVelbloudu.size(); i++){
            horniHranice += (int) (druhyVelbloudu.get(i).getPomerDruhuVelbloudu() * 100);

            if(randCislo < horniHranice && randCislo >= dolniHranice){
                druhVelbloudu = druhyVelbloudu.get(i);
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
        while(index < nesplnennePozadavky.size()){
            if(nesplnennePozadavky.get(index).getCasPrichodu() <= aktualniCas){
                aktualniPozadavky.add(nesplnennePozadavky.get(index));
                nesplnennePozadavky.remove(index);
            }
            else{
                index++;
            }
        }
        return aktualniPozadavky;
    }
    public void inputDruhVelbloudu(DruhVelbloudu druh){
        this.druhyVelbloudu.add(druh);
    }


    public void inputPozadavka(Pozadavek pozadavek){
        this.nesplnennePozadavky.add(pozadavek);
    }

    /**
     * Metoda prochazi vsichni velbloudy, a vsichni objednavky a hleda nejblizsi akci k aktualnimu casu
     * pak vraci (casAkci - aktualniCas) = casovy posuv od aktualniho casu do casu splneni akci
     * @return krok
     */
    public double getMinKrokCasu(){
        double krok = MAX_VALUE;
        double novyKrok;

        for(Velbloud velbloud : velbloudyNaCeste){
            novyKrok = velbloud.getCasPristiAkce();

            if(jeVetsi(krok, novyKrok)){
                krok = novyKrok;
            }
        }

        for(int i = 0; i < nesplnennePozadavky.size(); i++){
            novyKrok = nesplnennePozadavky.get(i).getCasPrichodu();

            if(jeVetsi(krok, novyKrok)){
                krok = novyKrok;
            }
        }

        return krok - aktualniCas;
    }

    public double getMinKrokSkladu(){
        double krok = MAX_VALUE;
        double novyKrok;

        for(Sklad sklad : vsichniSklady){
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
        for(int i = 0; i < vsichniSklady.size(); i++){
            vsichniSklady.get(i).zvetseniCasu(cas);
        }
       // System.out.println("Pocet skladu: " + vsichniSklady.size());

        for(Velbloud velbloud : velbloudyNaCeste){
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
        for(int i = 0; i < graf.size(); i++){
            graf.get(i).setDistance(Data.MAX_VALUE);
            graf.get(i).setJeZpracovany(false);
            graf.get(i).obnoveniCesty();
        }
    }
    public void velbloudNaCeste(Velbloud velbloud){
        velbloudyNaCeste.add(velbloud);
    }

    public void velbloudZkoncilCestu(Velbloud velbloud){
        velbloudyNaCeste.remove(velbloud);
    }

    /**
     * List vsech zastavek slouzi ke zpracovani sousedu.
     * V listu jsou sklady a oazy v rade, realny index = pozice v listu + 1
     * (na nulove pozici se nachazi prvni prvek)
     * @param zastavka
     */
    public void inputZastavka(Bod zastavka){
        this.graf.add(zastavka);
    }

    public void inputOaza(Oaza oaza){
        this.vsichniOazy.add(oaza);
    }

    public void inputSklad(Sklad sklad){
        this.vsichniSklady.add(sklad);
    }

    public void setMaxDalka(double maxDalka) {
        this.maxDalka = maxDalka;
    }

    public void setMaxRychlost(double maxRychlost) {
        this.maxRychlost = maxRychlost;
    }

    public double getMaxDalka() {
        return maxDalka;
    }

    public double getMaxRychlost() {
        return maxRychlost;
    }

    public void setMaxStrRychlostVelbloudu(double maxRychlost){
        this.maxStrRychlostVelbloudu = maxRychlost;
    }
    public void setMaxStrDalkaVelbloudu(double maxDalka){
        this.maxStrDalkaVelbloudu = maxDalka;
    }
    public double getMaxStrRychlostVelbloudu(){
        return maxStrRychlostVelbloudu;
    }
    public double getMaxStrDalkaVelbloudu(){
        return maxStrDalkaVelbloudu;
    }
    public List<Sklad> getVsichniSklady(){
        return vsichniSklady;
    }

    public List<Velbloud> getVsichniVelbloudy(){
        return vsichniVelbloudy;
    }

    public List<Oaza> getVsichniOazy(){
        return vsichniOazy;
    }

    public List<DruhVelbloudu> getDruhyVelbloudu() {
        return druhyVelbloudu;
    }

    public List<Pozadavek> getPozadavky() {
        return nesplnennePozadavky;
    }
    public List<Bod> getGraf(){
        return graf;
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
        double eps = 0.0000000001;
        return (x1 - x2) > eps;
    }

}
