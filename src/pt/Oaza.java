package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private boolean spustenAlg = false;
    private List<StackCesta> cestyDoOazy;

    public Oaza(int id, double x, double y, Data baseDat){
        super(id, x, y, baseDat);
        cestyDoOazy = new ArrayList<>();
    }

    public StackCesta getNejlepsiCestu(int pocetKosu, DijkstraAlgoritmus algoritmus, Data baseDat) throws CloneNotSupportedException {

        StackCesta cesta = null;

        if(!spustenAlg){
           cestyDoOazy = algoritmus.getVsichniCesty(getId());
           spustenAlg = true;
        }

        int pocetCest = cestyDoOazy.size();

         for(int i = 0; i < pocetCest; i++){//Odstranit vsichni cesty, ktere neda splnit podle max vzdalenosti velbloudu. Pak napsat metodu, ktera vraci delku listu cest
            Sklad sklad = (Sklad) cestyDoOazy.get(i).getPrvniBod();
            if(sklad.getPocetKosu() >= pocetKosu){
                cesta = cestyDoOazy.get(i);
                return cesta;
            }
        }
        return cesta;

    public List<StackCesta> getVsichniCesty(){
        return cestyDoOazy;
    }

    public void vypisVsihniCesty(){
        for(int i = 0; i < cestyDoOazy.size(); i++){
            cestyDoOazy.get(i).vypis();
        }
    public int getPocetCest(){
        return cestyDoOazy.size();
    }

}
