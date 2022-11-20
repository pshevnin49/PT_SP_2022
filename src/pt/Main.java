package pt;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException, CloneNotSupportedException {

        long start = System.currentTimeMillis();
        CteniDat cteniDat = new CteniDat("sparse_bit_large.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

//        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);
//
//        for(int i = 0; i < baseDat.getVsichniOazy().size(); i++){  //pro testovani
//            baseDat.getVsichniOazy().get(i).getNejlepsiCestu(0, dijkstra);
//        }
//
//        for(int i = 0; i < baseDat.getVsichniOazy().size(); i++){  //pro testovani
//            baseDat.getVsichniOazy().get(i).vypisVsivhniCesty();
//        }

        Simulace simulace = new Simulace(baseDat);
        simulace.startSimulace();

        long stop = System.currentTimeMillis();

        long end = stop - start;

        int minuty = (int) (end/60000);
        int secundy = (int)((end % 60000)/1000);



        System.out.println("Program bezel: " + minuty + "m " + secundy + "s ");

//        for(int i = 0; i < baseDat.getVsichniVelbloudy().size(); i++){
//            baseDat.getVsichniVelbloudy().get(i).vypisPoSimulace();
//        }


    }
}
