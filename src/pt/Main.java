package pt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException, CloneNotSupportedException {

        long start = System.currentTimeMillis();
        CteniDat cteniDat = new CteniDat("centre_small.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        Simulace simulace = new Simulace(baseDat);
        simulace.startSimulace();

        SouborStatistiky statistiky = new SouborStatistiky(baseDat);
        try {
            statistiky.zapisStatistiku();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long stop = System.currentTimeMillis();
        long end = stop - start;

        int minuty = (int) (end/60000);
        int secundy = (int)((end % 60000)/1000);
        int milis = (int) ((end % 60000) % 1000);

        System.out.println("Program bezel: " + minuty + "m " + secundy + "s " + milis + "ms ");
    }
}
