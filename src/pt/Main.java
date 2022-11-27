package pt;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws IOException, CloneNotSupportedException {

        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generatorDat(10, 10, 15, 3, 20, "mydata.txt");

        long start = System.currentTimeMillis();
        CteniDat cteniDat = new CteniDat("centre_medium.txt");
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
        int milis = (int) ((end % 60000) % 1000);

        System.out.println("Program bezel: " + minuty + "m " + secundy + "s " + milis + "ms ");
    }
}
