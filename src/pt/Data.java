package pt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Data {
    private List<Stanice> vsicniZastavky;

    private List<Velbloud> vsichniVelbloudy;
    private List<DruhVelbloudu> druhyVelbloudu;
    private List<Pozadavka> nesplnennePozadavky;

    private List<Pozadavka> splnenePozadavky;
    private int oznaceniDeikstreho;
    private int aktualniCas;

    public Data(){

        this.vsicniZastavky = new ArrayList<>();
        this.druhyVelbloudu = new ArrayList<>();
        this.nesplnennePozadavky = new ArrayList<>();
        vsichniVelbloudy = new ArrayList<>();
        this.aktualniCas = 0;
        this.oznaceniDeikstreho = 0;

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

//            TODO: posmotri 2 str zadani a pozadavkach, aktualni cas by nemel byt zahrnout, takze podminka bez =, proste < aktualniCas
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

    public void inputPozadavka(Pozadavka pozadavka){

        this.nesplnennePozadavky.add(pozadavka);

    }

    /**
     * List vsech zastavek slouzi ke zpracovani sousedu.
     * V listu jsou sklady a oazy v rade, realny index = pozice v listu + 1
     * (na nulove pozici se nachazi prvni prvek)
     * @param zastavka
     */
    public void inputZastavka(Stanice zastavka){

        this.vsicniZastavky.add(zastavka);

    }

    public List<DruhVelbloudu> getDruhyVelbloudu() {
        return druhyVelbloudu;
    }

    public List<Pozadavka> getPozadavky() {
        return nesplnennePozadavky;
    }
    public List<Stanice> getVsicniZastavky(){
        return  vsicniZastavky;
    }
}
