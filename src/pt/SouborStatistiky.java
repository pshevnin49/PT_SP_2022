package pt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Trida urcena ke generaci souboru se statistikou, kterou podarilo sebrat
 * za behu programu
 */
public class SouborStatistiky {

    private final Data BASE_DAT;
    private final String FILE_NAME = "statistika.txt";

    public SouborStatistiky(Data baseDat){
        this.BASE_DAT = baseDat;
    }

    /**
     * Generuje soubor a pomoci metody zapisStatistiku vklada do neho
     * statistiku
     * @throws IOException
     */
    public void genSoubrStatistik() throws IOException {

        FileWriter fileWriter = new FileWriter(FILE_NAME);
        BufferedWriter file = new BufferedWriter(fileWriter);

        try{
            if(BASE_DAT.getErrLog() == null){
                zapisStatistiku(file);
            }else{
                file.write(BASE_DAT.getErrLog());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            file.flush();
            file.close();
            fileWriter.close();
        }
    }

    /**
     * Zapisuje statistiku do souboru
     * @param file
     */
    private void zapisStatistiku(BufferedWriter file){
        String vzdalenost = String.format("Celkova vzdalenost: %.2f\n", BASE_DAT.getCelkovaVzdalenost());

        try {
            for(int i = 0; i < BASE_DAT.getVsichniVelbloudy().size(); i++){
                file.write("\n" + BASE_DAT.getVsichniVelbloudy().get(i).getLog() + "\n");
            }
            file.write("\n");

            for(int i = 0; i < BASE_DAT.getVsichniOazy().size(); i++){
                file.write("\n" + BASE_DAT.getVsichniOazy().get(i).getLog() + "\n");
            }
            file.write("\n");
            for(int i = 0; i < BASE_DAT.getVsichniSklady().size(); i++){
                file.write("\n" + BASE_DAT.getVsichniSklady().get(i).getLog() + "\n");
            }
            file.write("\n");

            file.write(String.format("Cas cele simulace: %.2f\n", BASE_DAT.getAktualniCas()));
            file.write(vzdalenost);

            file.write("Vsichni druhy: \n");
            for(int i = 0; i < BASE_DAT.getDruhyVelbloudu().size(); i++){
                file.write("    Druh: " + BASE_DAT.getDruhyVelbloudu().get(i).getNazev() + ", pocet jedincu: " + BASE_DAT.getDruhyVelbloudu().get(i).getPocetVelbloudu() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
