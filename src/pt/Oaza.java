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

    public StackCesta getNejlepsiCestu(int pocetKosu, DijkstraAlgoritmus algoritmus) throws CloneNotSupportedException {

        StackCesta cesta;

        if(!spustenAlg){
           cestyDoOazy = algoritmus.getVsichniCesty(getId());
           spustenAlg = true;
        }

        int pocetCest = cestyDoOazy.size();

         for(int i = 0; i < pocetCest; i++){
            Sklad sklad = (Sklad) cestyDoOazy.get(i).getPrvniBod();
            if(sklad.getPocetKosu() >= pocetKosu){
                cesta = cestyDoOazy.get(i);
                if(cesta.get().next == null){
                    return null;
                }
                return cesta;
            }
        }

        return null;
    }

    /**
     * Kontroluje zda byl spousten dijkstra algoritmus pro tuto oazu,
     * pokud byl, vraci vsichni cesty, pokud nebyl, spousti algorithm a vraci cesty.
     * @param algoritmus
     * @return list cest od vswech skladu do oazy
     */
    public List<StackCesta> getVsichniCesty(DijkstraAlgoritmus algoritmus) throws CloneNotSupportedException {

        if(!spustenAlg){
            cestyDoOazy = algoritmus.getVsichniCesty(getId());
            spustenAlg = true;
        }

        return cestyDoOazy;
    }

    public void vypisVsihniCesty(){
        for(int i = 0; i < cestyDoOazy.size(); i++){
            cestyDoOazy.get(i).vypis();
        }
    }

}
