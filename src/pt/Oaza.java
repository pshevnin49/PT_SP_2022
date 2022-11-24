package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private boolean zapsaneCesty = false;
    private List<StackCesta> cestyDoOazy;

    public Oaza(int id, double x, double y, Data baseDat){
        super(id, x, y, baseDat);
        cestyDoOazy = new ArrayList<>();
    }

    /**
     * Kontroluje zda byl spousten dijkstra algoritmus pro tuto oazu,
     * pokud byl, vraci vsichni cesty, pokud nebyl, spousti algorithm a vraci cesty.
     * @param algoritmus
     * @return list cest od vswech skladu do oazy
     */
    public List<StackCesta> getVsichniCesty(DijkstraAlgoritmus algoritmus) throws CloneNotSupportedException {

        if(!zapsaneCesty){
            cestyDoOazy = algoritmus.getVsichniCesty(getId());
            zapsaneCesty = true;
        }

        return cestyDoOazy;
    }

    public void vypisVsihniCesty(){
        for(int i = 0; i < cestyDoOazy.size(); i++){
            cestyDoOazy.get(i).vypis();
        }
    }

}
