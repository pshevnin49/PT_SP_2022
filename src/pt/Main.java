package pt;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException, CloneNotSupportedException {

        long start = System.currentTimeMillis();
        CteniDat cteniDat = new CteniDat("weird_small.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        Simulace simulace = new Simulace(baseDat);
        simulace.startSimulace();

        long stop = System.currentTimeMillis();

        long end = stop - start;

        int minuty = (int) (end/60000);
        int secundy = (int)((end % 60000)/1000);

        System.out.println("Program bezel: " + minuty + "m " + secundy + "s ");

    }
}
