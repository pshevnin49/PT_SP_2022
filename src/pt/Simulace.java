package pt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class Simulace {

    private Data baseDat;

    public Simulace(Data baseDat){
        this.baseDat = baseDat;
    }

    public void startSimulace() throws CloneNotSupportedException {

        double minKrokCas = baseDat.getMinKrokCasu();
        List<Pozadavek> aktPozList = new ArrayList<>();
        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);

        while(minKrokCas != Data.MAX_VALUE){

            List<Pozadavek> novePozadavky = baseDat.getAktualniPozadavky();
            aktPozList.addAll(novePozadavky);

            for(int i = 0; i < novePozadavky.size(); i++ ){
                Pozadavek pozadavek = novePozadavky.get(i);
                System.out.printf("Cas: %d, Pozadavek: %d, Oaza: %d, Pocet kosu: %d, Deadline: %d\n", Math.round(baseDat.getAktualniCas()),
                        pozadavek.getId(), pozadavek.getIdOazy(), pozadavek.getPocetKosu(), Math.round(pozadavek.getCasDoruceni()));
            }

            if(aktPozList.size() > 0){
                zpracovaniPoz(aktPozList, dijkstra);
            }
            baseDat.zvetseniCasuSimulace(minKrokCas);
            minKrokCas = baseDat.getMinKrokCasu();
        }
    }

    private void zpracovaniPoz(List<Pozadavek> pozadavekList, DijkstraAlgoritmus dijkstra) throws CloneNotSupportedException {
        pozadavekList.sort(comparing(Pozadavek::getCasDoruceni));

        for(int i = 0; i < pozadavekList.size(); i++){
            Pozadavek pozadavek = pozadavekList.get(i);
            Oaza oaza = baseDat.getVsichniOazy().get(pozadavek.getIdOazy());
            StackCesta cesta = oaza.getNejlepsiCestu(pozadavek.getPocetKosu(), dijkstra);
            Sklad sklad = (Sklad) cesta.get().stanice;
            Velbloud velbloud = sklad.getVhodnyVelbl(pozadavek.getPocetKosu(), pozadavek.getCasDoruceni(), cesta);

            if(velbloud != null){
                //System.out.println("Velbloud != null");
                pozadavekList.remove(i);
                velbloud.zacniNakladat(pozadavek.getPocetKosu(), cesta, pozadavek);
            }else{
                //System.out.println("Velbloud == null");
            }

        }
    }






}
