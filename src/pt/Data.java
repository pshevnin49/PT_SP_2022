package pt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Data {
    private List<Bod> graf;
    private List<Velbloud> vsichniVelbloudy;
    private List<Sklad> vsichniSklady;
    private List<Oaza> vsichniOazy;
    private List<DruhVelbloudu> druhyVelbloudu;
    private List<Pozadavka> nesplnennePozadavky;
    private List<Pozadavka> splnenePozadavky;
    private double aktualniCas;

    public static final double MAX_VALUE = 1.7976931348623157E308;

    public Data(){
        this.graf = new ArrayList<>();
        this.druhyVelbloudu = new ArrayList<>();
        this.nesplnennePozadavky = new ArrayList<>();
        vsichniVelbloudy = new ArrayList<>();
        vsichniSklady = new ArrayList<>();
        vsichniOazy = new ArrayList<>();
        this.aktualniCas = 0;
    }

    /**
     * Methoda podle spravne pravdepodobnosti vraci nahodny druh noveho velbloudu
     * @return druhVelbloudu
     */
    public DruhVelbloudu getNahodnyDruhVelbloudu(){

        Random random = new Random();

        DruhVelbloudu druhVelbloudu;
        int randCislo = random.nextInt(99);

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

    public void casIncrement(){
        aktualniCas++;
    }

    /**
     * Metoda hleda vsichni aktualni (casove) pozadavky, odstranuje z listu nesplnenePozadavky,
     * a vraci list techto aktualnich pozadavek
     * @return
     */
    public List<Pozadavka> getAktualniPozadavky(){

        int index = 0;
        List<Pozadavka> aktualniPozadavky = new ArrayList<>();

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

    /**
     * Vraci stanice, ktera jeste nebyla zpracovana to jest (jeji sousedi nemaji spoctenou vzdalenost
     * a promnenna jeZpracovana == False) ale uz ma vlastni vzdalenost od zacatku cesty
     * @return Stanice nezprStanice
     */
    public Bod getNezpracovanouStanice(){
        Bod stanice = null;

        for(int i = 0; i < graf.size(); i++){
            if(!graf.get(i).jeZpracovany && jeVetsi(Data.MAX_VALUE, graf.get(i).getDistance()) && jeVetsi(graf.get(i).getDistance(), 0)){
                stanice = graf.get(i);
                return stanice;
            }
        }
        return stanice;
    }

    public void inputPozadavka(Pozadavka pozadavka){
        this.nesplnennePozadavky.add(pozadavka);
    }

    /**
     * Metoda prochazi vsichni velbloudy, a vsichni objednavky a hleda nejblizsi akci k aktualnimu casu
     * pak vraci (casAkci - aktualniCas) = casovy posuv od aktualniho casu do casu splneni akci
     * @return krok
     */
    public double getMinKrokCasu(){
        double krok = MAX_VALUE;
        double novyKrok;

        for(int i = 0; i < vsichniVelbloudy.size(); i++){
            novyKrok = vsichniVelbloudy.get(i).getPristiAkce();

            if(novyKrok < krok){
                krok = novyKrok;
            }
        }

        for(int i = 0; i < nesplnennePozadavky.size(); i++){

            novyKrok = nesplnennePozadavky.get(i).getCasPrichodu();

            if(novyKrok < krok){
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

        for(int i = 0; i < vsichniSklady.size(); i++){
            vsichniSklady.get(i).zvetseniCasu(cas);
        }

        for(int i = 0; i < vsichniSklady.size(); i++){
            vsichniSklady.get(i).zvetseniCasu(cas);
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
        }
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

    public List<Pozadavka> getPozadavky() {
        return nesplnennePozadavky;
    }
    public List<Bod> getGraf(){
        return graf;
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
