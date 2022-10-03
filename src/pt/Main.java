package pt;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String [] args) throws FileNotFoundException {

        CteniDat cteniDat = new CteniDat("tutorial.txt");
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat(baseDat);
        zpracovaniDat.zpracovani(data);


        for(int i = 0; i < baseDat.getVsicniZastavky().size(); i++){

            baseDat.getVsicniZastavky().get(i).vypis();

        }

        baseDat.getVsicniZastavky().get(0).vypis();
        baseDat.getVsicniZastavky().get(0).getNejblizsiSoused().vypis();

    }
}
