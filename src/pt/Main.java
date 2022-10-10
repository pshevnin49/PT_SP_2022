package pt;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException, CloneNotSupportedException {

        CteniDat cteniDat = new CteniDat("tutorial.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat(baseDat);
        zpracovaniDat.zpracovani(data);


        baseDat.getGraf().get(0).vypis();
        System.out.println("vypis");

        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);

        dijkstra.getVsichniCesty(2);

        for(int i = 0; i < baseDat.getGraf().size(); i++){
            System.out.println((i+1) + ". " + baseDat.getGraf().get(i).getDistance());
        }

        for(int i = 0; i < baseDat.getGraf().size(); i++){
            baseDat.getGraf().get(i).getCestaKeStanici().vypis();
        }

    }
}
