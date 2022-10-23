package pt;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException, CloneNotSupportedException {

        CteniDat cteniDat = new CteniDat("tutorial.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);

        List<StackCesta> stackCest = dijkstra.getVsichniCesty(2);


//        Velbloud velbloud = new Velbloud(1, 5, 22,
//                baseDat.getNahodnyDruhVelbloudu(), baseDat.getVsichniSklady().get(0), 0);
//
//
//        velbloud.zacniNakladat(2, baseDat.getGraf().get(0).getCestaKeStanici(), baseDat.getPozadavky().get(0));
//
//        for(int i = 0; i < 1000; i++){
//            velbloud.zvetseniCasu(1);
//        }

    }
}
