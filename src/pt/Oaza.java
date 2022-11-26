package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private boolean zapsaneCesty = false;
    private List<CestaList> cestyDoOazy;

    public Oaza(int id, double x, double y, Data baseDat){
        super(id, x, y, baseDat);
        cestyDoOazy = new ArrayList<>();
    }

    public List<CestaList> getVsichniCesty() throws CloneNotSupportedException {
        return cestyDoOazy;
    }
    public void zapisCestuDoOazy() throws CloneNotSupportedException {
        CestaList novaCesta = (CestaList) getCestaKeStanici().clone();
        cestyDoOazy.add(novaCesta);
    }

    public List<CestaList> getCestyDoOazy(){
        return cestyDoOazy;
    }

    public void addCestuDoOazy(CestaList cesta){
        cestyDoOazy.add(cesta);
    }

    public void vypisVsihniCesty(){
        System.out.println("Oaza cislo: " + id);
        for(int i = 0; i < cestyDoOazy.size(); i++){
            cestyDoOazy.get(i).vypis();
        }
    }

}
