package pt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SouborStatistiky {

    private Data baseDat;
    private final String FILE_NAME = "statistika.txt";

    public SouborStatistiky(Data baseDat){
        this.baseDat = baseDat;
    }

    public void zapisStatistiku() throws IOException {
        FileWriter fileWriter = new FileWriter(FILE_NAME);
        BufferedWriter file = new BufferedWriter(fileWriter);
        String vzdalenost = String.format("Celkova vzdalenost: %.2f\n", baseDat.getCelkovaVzdalenost());

        if(baseDat.getErrLog() == null){
            for(int i = 0; i < baseDat.getVsichniVelbloudy().size(); i++){
                file.write("\n" + baseDat.getVsichniVelbloudy().get(i).getLog() + "\n");
            }
            file.write("\n");

            for(int i = 0; i < baseDat.getVsichniOazy().size(); i++){
                file.write("\n" + baseDat.getVsichniOazy().get(i).getLog() + "\n");
            }
            file.write("\n");
            for(int i = 0; i < baseDat.getVsichniSklady().size(); i++){
                file.write("\n" + baseDat.getVsichniSklady().get(i).getLog() + "\n");
            }
            file.write("\n");

            file.write("Cas cele simulace: " + baseDat.getAktualniCas() + "\n");
            file.write(vzdalenost);

            file.write("Vsichni druhy: \n");
            for(int i = 0; i < baseDat.getDruhyVelbloudu().size(); i++){
                file.write("    Druh: " + baseDat.getDruhyVelbloudu().get(i).getNazev() + ", pocet jedincu: " + baseDat.getDruhyVelbloudu().get(i).getPocetVelbloudu() + "\n");
            }
        }else{
            file.write(baseDat.getErrLog());
        }


        file.flush();
        file.close();
    }
}
