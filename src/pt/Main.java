package pt;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException, CloneNotSupportedException {

        CteniDat cteniDat = new CteniDat("sparse_very_small.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        Simulace simulace = new Simulace(baseDat);

        simulace.startSimulace();

        for(int i = 0; i < baseDat.getVsichniVelbloudy().size(); i++){
            baseDat.getVsichniVelbloudy().get(i).vypisPoSimulace();
        }

    }
}
