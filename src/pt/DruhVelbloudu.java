package pt;

import java.util.Random;

public class DruhVelbloudu {

    private final String NAZEV;
    private final double MAX_RYCHLOST;
    private final double MIN_RYCHLOST;
    private final double MAX_VZDALENOST;
    private final double MIN_VZDALENOST;
    private final int DOBA_PITI;
    private final int MAX_ZATIZENI;
    private final double POMER_DRUHU_VELBL;
    private final Random RAND;
    private int POCET_VELBL = 0;

    public DruhVelbloudu(String nazev, double minRychlost, double maxRychlost, double minVzdalenost, double maxVzdalenost, int dobaPiti, int maxZatizeni, double pomerDruhuVelbloudu) {
        this.NAZEV = nazev;
        this.MAX_RYCHLOST = maxRychlost;
        this.MIN_RYCHLOST = minRychlost;
        this.MAX_VZDALENOST = maxVzdalenost;
        this.MIN_VZDALENOST = minVzdalenost;
        this.DOBA_PITI = dobaPiti;
        this.MAX_ZATIZENI = maxZatizeni;
        this.POMER_DRUHU_VELBL = pomerDruhuVelbloudu;
        RAND = new Random();
    }

    public int getDobaPiti() {
        return DOBA_PITI;
    }

    public int getMaxZatizeni() {
        return MAX_ZATIZENI;
    }

    public double getPomerDruhuVelbloudu() {
        return POMER_DRUHU_VELBL;
    }

    public String getNazev() {
        return NAZEV;
    }

    public int getPocetVelbloudu(){
        return POCET_VELBL;
    }

    public void vypis(){
        System.out.println(NAZEV + " max rychlost:" + MAX_RYCHLOST + " min rychlost:" + MIN_RYCHLOST + " max vzd.:" + MAX_VZDALENOST + " min vzd.: " + MIN_VZDALENOST);
    }

    public double randRych() {
        return MIN_RYCHLOST + (MAX_RYCHLOST - MIN_RYCHLOST) * RAND.nextDouble();
    }

    public void pocetVelblIncr(){
        POCET_VELBL++;
    }

    public double randVzdal() {
        double stredniHodn = (MIN_VZDALENOST + MAX_VZDALENOST)/2;
        double odchylka = (MAX_VZDALENOST - MIN_VZDALENOST)/4;
        return odchylka * RAND.nextGaussian() + stredniHodn;
    }
}
