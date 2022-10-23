package pt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class Simulace {

    private Data baseDat;

    public Simulace(Data baseDat){
        this.baseDat = baseDat;
    }

    public void startSimulace(){

        double minKrokCas = baseDat.getMinKrokCasu();
        List<Pozadavka> aktualniPoz = new ArrayList<>();

        while(minKrokCas != Data.MAX_VALUE){
            baseDat.zvetseniCasuSimulace(minKrokCas);
            aktualniPoz.addAll(baseDat.getAktualniPozadavky());

            if(aktualniPoz.size() > 0){
                aktualniPoz.sort(comparing(Pozadavka::getCasDoruceni));

            }


            minKrokCas = baseDat.getMinKrokCasu();
        }


    }




}
