package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private boolean zapsaneCesty = false;
    private List<FrontaCesta> cestyDoOazy;

    public Oaza(int id, double x, double y, Data baseDat){
        super(id, x, y, baseDat);
        cestyDoOazy = new ArrayList<>();
    }


    public List<FrontaCesta> getVsichniCesty() throws CloneNotSupportedException {
        return cestyDoOazy;
    }
    public void zapisCestuDoOazy() throws CloneNotSupportedException {
        FrontaCesta novaCesta = (FrontaCesta) getCestaKeStanici().clone();
        cestyDoOazy.add(novaCesta);
    }

    public List<FrontaCesta> getCestyDoOazy(){
        return cestyDoOazy;
    }

    public void addCestuDoOazy(FrontaCesta cesta){
        cestyDoOazy.add(cesta);
    }

    public void vypisVsihniCesty(){
        System.out.println("Oaza cislo: " + id);
        for(int i = 0; i < cestyDoOazy.size(); i++){
            cestyDoOazy.get(i).vypis();
        }
    }

}
