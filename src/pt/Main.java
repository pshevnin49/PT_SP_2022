package pt;

import java.io.IOException;
import java.util.List;

/**
 * Hlavni trida programu
 */
public class Main {

    public static void main(String [] args) throws IOException, CloneNotSupportedException {

        DataGenerator generator = new DataGenerator();
        CteniDat cteniDat = new CteniDat("sparse_bit_small.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);
        dijkstra.spustAlgoritmus();

        Simulace simulace = new Simulace(baseDat);
        simulace.startSimulace();

        SouborStatistiky statistiky = new SouborStatistiky(baseDat);
        try {
            statistiky.genSoubrStatistik();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
