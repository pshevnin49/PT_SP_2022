package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private boolean zapsaneCesty = false;
    private List<Cesta> cestyDoOazy;

    public Oaza(int id, double x, double y, Data baseDat){
        super(id, x, y, baseDat);
        cestyDoOazy = new ArrayList<>();
    }

    public List<Cesta> getVsichniCesty() throws CloneNotSupportedException {
        return cestyDoOazy;
    }
    public void zapisCestuDoOazy() throws CloneNotSupportedException {
        Cesta novaCesta = (Cesta) getCestaKeStanici().clone();
        cestyDoOazy.add(novaCesta);
    }

    public List<Cesta> getCestyDoOazy(){
        return cestyDoOazy;
    }

    public void addCestuDoOazy(Cesta cesta){
        cestyDoOazy.add(cesta);
    }

    public void vypisVsihniCesty(){
        System.out.println("Oaza cislo: " + id);
        for(int i = 0; i < cestyDoOazy.size(); i++){
            cestyDoOazy.get(i).vypis();
        }
    }

}
