package pt;

import java.util.Random;

public class DruhVelbloudu {
    private String nazev;
    private double maxRychlost;
    private double minRychlost;
    private int rychlost;
    private double maxVzdalenost;
    private double minVzdalenost;
    private int vzdalenost;
    private int dobaPiti;
    private int maxZatizeni;
    private double pomerDruhuVelbloudu;
    private Random rand = new Random();

    public DruhVelbloudu(String nazev, double minRychlost, double maxRychlost, double minVzdalenost, double maxVzdalenost, int dobaPiti, int maxZatizeni, double pomerDruhuVelbloudu) {
        this.nazev = nazev;
        this.maxRychlost = maxRychlost;
        this.minRychlost = minRychlost;
        this.maxVzdalenost = maxVzdalenost;
        this.minVzdalenost = minVzdalenost;
        this.dobaPiti = dobaPiti;
        this.maxZatizeni = maxZatizeni;
        this.pomerDruhuVelbloudu = pomerDruhuVelbloudu;
    }

    public int getDobaPiti() {
        return dobaPiti;
    }

    public int getMaxZatizeni() {
        return maxZatizeni;
    }

    public double getPomerDruhuVelbloudu() {
        return pomerDruhuVelbloudu;
    }

    public String getNazev() {
        return nazev;
    }

    public double getMaxRychlost() {
        return maxRychlost;
    }

    public double getMinRychlost() {
        return minRychlost;
    }

    public double getMaxVzdalenost() {
        return maxVzdalenost;
    }

    public double getMinVzdalenost() {
        return minVzdalenost;
    }

    public void vypis(){
        System.out.println(nazev + " max rychlost:" + maxRychlost + " min rychlost:" + minRychlost + " max vzd.:" + maxVzdalenost + " min vzd.: " + minVzdalenost);
    }

    public double randRych() {

        return minRychlost + (maxRychlost - minRychlost) * rand.nextDouble();

    }

    public double randVzdal() {

        double stredniHodn = (minVzdalenost + maxVzdalenost)/2;
        double odchylka = (maxVzdalenost - minVzdalenost)/4;

        return odchylka * rand.nextGaussian() + stredniHodn;


    }
}
