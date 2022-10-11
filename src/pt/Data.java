package pt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Data {
    private List<Stanice> graf;
    private List<Velbloud> vsichniVelbloudy;
    private List<Sklad> vsichniSklady;
    private List<Oaza> vsichniOazy;
    private List<DruhVelbloudu> druhyVelbloudu;
    private List<Pozadavka> nesplnennePozadavky;
    private List<Pozadavka> splnenePozadavky;
    private int aktualniCas;

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
    public Stanice getNezpracovanouStanice(){
        Stanice stanice = null;

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

    public double getMinKrokCasu(){
        double krok = -1;
        return krok;
    }


    /**
     * List vsech zastavek slouzi ke zpracovani sousedu.
     * V listu jsou sklady a oazy v rade, realny index = pozice v listu + 1
     * (na nulove pozici se nachazi prvni prvek)
     * @param zastavka
     */
    public void inputZastavka(Stanice zastavka){
        this.graf.add(zastavka);
    }

    public void pripravZastavky(){
        for(int i = 0; i < graf.size(); i++){
            graf.get(i).setDistance(Data.MAX_VALUE);
            graf.get(i).setJeZpracovany(false);
        }
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
    public List<Stanice> getGraf(){
        return graf;
    }

    public static boolean jeVetsi(double x1, double x2){
        double eps = 0.0000000001;
        return (x1 - x2) > eps;
    }
}
