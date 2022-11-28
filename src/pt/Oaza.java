package pt;

import java.util.ArrayList;
import java.util.List;

public class Oaza extends Bod {

    private final List<CestaList> CESTY_DO_OAZY;
    private final List<Pozadavek> VSICHNI_POZ;

    public Oaza(int id, double x, double y, Data baseDat){
        super(id, x, y, baseDat);
        CESTY_DO_OAZY = new ArrayList<>();
        VSICHNI_POZ = new ArrayList<>();
    }

    public void addPoz(Pozadavek pozadavek){
        VSICHNI_POZ.add(pozadavek);
    }

    public List<CestaList> getVsichniCesty() throws CloneNotSupportedException {
        return CESTY_DO_OAZY;
    }
    public void zapisCestuDoOazy() throws CloneNotSupportedException {
        CestaList novaCesta = (CestaList) getCestaKeStanici().clone();
        CESTY_DO_OAZY.add(novaCesta);
    }

    public List<CestaList> getCestyDoOazy(){
        return CESTY_DO_OAZY;
    }

    public String getLog(){
        String log = String.format("Oaza id: %d \n", id);
        for(int i = 0; i < VSICHNI_POZ.size(); i++){
            log += VSICHNI_POZ.get(i).getLog();
        }
        return log;
    }


}
