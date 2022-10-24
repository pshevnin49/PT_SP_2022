package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private boolean spustenAlg = false;
    private List<StackCesta> cestyDoOazy;
    public Oaza(int id, double x, double y){
        super(id, x, y);
        cestyDoOazy = new ArrayList<>();
    }

    public StackCesta getNejlepsiCestu(int pocetKosu, DijkstraAlgoritmus algoritmus) throws CloneNotSupportedException {

        if(!spustenAlg){
           cestyDoOazy = algoritmus.getVsichniCesty(getId());
           spustenAlg = true;
        }

        for(int i = 0; i < cestyDoOazy.size(); i++){
            Sklad sklad = (Sklad)cestyDoOazy.get(i).getPrvniBod();
            if(sklad.getPocetKosu() >= pocetKosu){
                StackCesta cesta = cestyDoOazy.get(i);
                return cesta;
            }
        }
        return null;
    }

    public int getPocetCest(){
        return cestyDoOazy.size();
    }

}
