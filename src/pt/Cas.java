package pt;

import java.util.List;

public class Cas {

    private double aktualniCas;
    private Data baseDat;

    public Cas(Data baseDat){
        aktualniCas = 0;
        this.baseDat = baseDat;
    }

    public void zvetseniCasuSimulace(double cas){

        List<Sklad> sklady = baseDat.getVsichniSklady();
        List<Velbloud> velbloudy = baseDat.getVsichniVelbloudy();

        for(int i = 0; i < sklady.size(); i++){
            sklady.get(i).zvetseniCasu(cas);
        }

        for(int i = 0; i < velbloudy.size(); i++){
            velbloudy.get(i).zvetseniCasu(cas);
        }

    }

}
