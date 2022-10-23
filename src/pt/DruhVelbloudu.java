package pt;

import java.util.Random;

public class DruhVelbloudu {
    private String nazev;
    private int maxRychlost;
    private int minRychlost;
    private int rychlost;
    private int maxVzdalenost;
    private int minVzdalenost;
    private int vzdalenost;
    private int dobaPiti;
    private int maxZatizeni;
    private double pomerDruhuVelbloudu;
    private Random rand = new Random();

    public DruhVelbloudu(String nazev, int maxRychlost, int minRychlost, int maxVzdalenost, int minVzdalenost, int dobaPiti, int maxZatizeni, double pomerDruhuVelbloudu) {
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

    public int getMaxRychlost() {
        return maxRychlost;
    }

    public int getMinRychlost() {
        return minRychlost;
    }

    public int getMaxVzdalenost() {
        return maxVzdalenost;
    }

    public int getMinVzdalenost() {
        return minVzdalenost;
    }

    public void vypis(){
        System.out.println(nazev + " max rychlost:" + maxRychlost + " min rychlost:" + minRychlost + " max vzd.:" + maxVzdalenost + " min vzd.: " + minVzdalenost);
    }

    public int randRych() {
        return minRychlost + rand.nextInt(maxRychlost - minRychlost + 1);
    }

    public int randVzdal() {
        return minVzdalenost + rand.nextInt(maxVzdalenost - minVzdalenost + 1);
    }
}
