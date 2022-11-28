package pt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static final double MAX_X = 2000;
    private static final double MAX_Y = 2000;
    private static final double MAX_POCET_KOSU = 60;
    private static final double MAX_NALOZ = 1;
    private static final double MAX_DOPLN = 100;
    private static final double MIN_RYCHLOST = 50;
    private static final double MAX_RYCHLOST = 500;
    private static final double MIN_VZDALENOST = 500;
    private static final double MAX_VZDALENOST = 2000;
    private static final double MAX_DOBA_PITI = 1;
    private static final double ODCHYLKA = 100;
    private static final double ODCHYLKA_VZDAL = 50;
    private static final double MAX_ZATIZENI = 50;
    private static final double MAX_ID_OAZY = 50;
    private static final int POCET_KOSU = 1;
    private static final int CAS_OCEKAVANI = 10000;
    private static final Random RAND = new Random();

    public void generatorDat(int pocetSkladu, int pocetOaz, int pocetCest , int pocetDruhuVelbloudu, int pocetPozadavku, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter file = new BufferedWriter(fileWriter);

        file.write(pocetSkladu + "\n");
        for(int i = 0; i < pocetSkladu; i++){
            double x = getRovnomerRozdel(0, MAX_X);
            double y = getRovnomerRozdel(0, MAX_Y);
            double pocetKosu = getRovnomerRozdel(1, MAX_POCET_KOSU);
            double nalozeni = getRovnomerRozdel(1, MAX_NALOZ);
            double doplneni = getRovnomerRozdel(1, MAX_DOPLN);
            file.write(String.format(Locale.US, "%.2f %.2f %d %d %d\n", x, y, (int)pocetKosu, (int)nalozeni, (int)doplneni));
        }

        file.write(pocetOaz + "\n");
        for(int i = 0; i < pocetOaz; i++){
            double x = getRovnomerRozdel(0, MAX_X);
            double y = getRovnomerRozdel(0, MAX_Y);
            file.write(String.format(Locale.US, "%.2f %.2f\n", x, y));
        }

        file.write(pocetCest + "\n");
        for(int i = 0; i < pocetCest; i++){
            double sklad = Math.round(getRovnomerRozdel(1, pocetSkladu));
            double oaza = Math.round(getRovnomerRozdel(1, pocetOaz));file.write(String.format(Locale.US, "%d %d\n",(int)sklad, (int)oaza));
        }

        file.write(pocetDruhuVelbloudu + "\n");
        if(pocetDruhuVelbloudu == 1){
            String druh = "Druh_" + pocetDruhuVelbloudu;
            double minRychlost = getRovnomerRozdel(MIN_RYCHLOST, MIN_RYCHLOST + ODCHYLKA);
            double maxRychlost = getRovnomerRozdel(MAX_RYCHLOST, MAX_RYCHLOST + ODCHYLKA);
            double minVzdal = getRovnomerRozdel(MIN_VZDALENOST, MIN_VZDALENOST + ODCHYLKA_VZDAL);
            double maxVzdal = getRovnomerRozdel(MAX_VZDALENOST, MAX_VZDALENOST + ODCHYLKA_VZDAL);
            double dobaPiti = getRovnomerRozdel(1, MAX_DOBA_PITI);
            double zatizeni = getRovnomerRozdel(MAX_ZATIZENI, MAX_ZATIZENI + 50);
            double pomer = 1;
            file.write(String.format(Locale.US, "%s %.2f %.2f %d %d %d %.2f %.2f\n", druh, minRychlost, maxRychlost, (int)minVzdal, (int)maxVzdal, (int)dobaPiti, zatizeni, pomer));

        }
        else {
            double procSum = 0;
            for (int i = 0; i < pocetDruhuVelbloudu - 1; i++) {
                String druh = "Druh_" + (1 + i);
                double minRychlost = getRovnomerRozdel(MIN_RYCHLOST, MIN_RYCHLOST + ODCHYLKA);
                double maxRychlost = getRovnomerRozdel(MAX_RYCHLOST, MAX_RYCHLOST + ODCHYLKA);
                double minVzdal = getRovnomerRozdel(MIN_VZDALENOST, MIN_VZDALENOST + ODCHYLKA_VZDAL);
                double maxVzdal = getRovnomerRozdel(MAX_VZDALENOST, MAX_VZDALENOST + ODCHYLKA_VZDAL);
                double dobaPiti = getRovnomerRozdel(1, MAX_DOBA_PITI);
                double zatizeni = getRovnomerRozdel(MAX_ZATIZENI, MAX_ZATIZENI + 50);
                double pomer = getRovnomerRozdel(0, 1 - procSum);
                procSum += pomer;
                file.write(String.format(Locale.US, "%s %.2f %.2f %d %d %d %.2f %.2f\n", druh, minRychlost, maxRychlost, (int) minVzdal, (int) maxVzdal, (int) dobaPiti, zatizeni, pomer));
            }


            String druh = "Druh_" + pocetDruhuVelbloudu;
            double minRychlost = getRovnomerRozdel(MIN_RYCHLOST, MIN_RYCHLOST + ODCHYLKA);
            double maxRychlost = getRovnomerRozdel(MAX_RYCHLOST, MAX_RYCHLOST + ODCHYLKA);
            double minVzdal = getRovnomerRozdel(MIN_VZDALENOST, MIN_VZDALENOST + ODCHYLKA_VZDAL);
            double maxVzdal = getRovnomerRozdel(MAX_VZDALENOST, MAX_VZDALENOST + ODCHYLKA_VZDAL);
            double dobaPiti = getRovnomerRozdel(1, MAX_DOBA_PITI);
            double zatizeni = getRovnomerRozdel(MAX_ZATIZENI, MAX_ZATIZENI + 50);
            double pomer = 1 - procSum;
            file.write(String.format(Locale.US, "%s %.2f %.2f %d %d %d %.2f %.2f\n", druh, minRychlost, maxRychlost, (int) minVzdal, (int) maxVzdal, (int) dobaPiti, zatizeni, pomer));
        }
        file.write(pocetPozadavku + "\n");
        double casPrichodu = 0;
        for(int i = 0; i < pocetPozadavku; i++){
           double idOazy = getRovnomerRozdel(1, MAX_ID_OAZY);
           double pocetKosu = getRovnomerRozdel(1, POCET_KOSU);
           double casOcekavani = getRovnomerRozdel(10000, CAS_OCEKAVANI);
           file.write(String.format(Locale.US, "%.2f %d %d %d\n", casPrichodu, (int)idOazy, (int)pocetKosu, (int)casOcekavani));
           casPrichodu += getRovnomerRozdel(1, 10);
        }

        file.flush();
        file.close();
    }

    /**
     * Metoda generuje vlastni data pomoci rovnomerneho rozdeleni
     * @param min dolni hranice
     * @param max horni hranice
     * @return hodnotu v rovnomernem rozdeleni
     */
    private double getRovnomerRozdel(double min, double max){
        double rozdil = max - min;
        double random = RAND.nextDouble() * rozdil;
        return min + random;
    }

}
