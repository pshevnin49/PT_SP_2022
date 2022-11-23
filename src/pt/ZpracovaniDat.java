package pt;

import java.util.List;

public class ZpracovaniDat {

    private Data baseDat;

    public void zpracovani(List<String> data, Data baseDat){

        this.baseDat = baseDat;
        int pocetSkladu = Integer.parseInt(data.get(0));
        int indexSkladu = 1;
        int indexPoslSkladu = pocetSkladu * 5;

        for(int i = 1; i < indexPoslSkladu; i += 5){
            double x = Double.parseDouble(data.get(i));
            double y = Double.parseDouble(data.get(i + 1));
            int pocetKusu = Integer.parseInt(data.get(i + 2));
            int casObnoveni = Integer.parseInt(data.get(i + 3));
            int casNalozeni = Integer.parseInt(data.get(i + 4));
            Bod sklad = new Sklad(indexSkladu, x, y, pocetKusu, casObnoveni, casNalozeni, baseDat);

            indexSkladu++;
            baseDat.inputSklad((Sklad) sklad);
            baseDat.inputZastavka(sklad);
        }

        int pocetOaz = Integer.parseInt(data.get(indexPoslSkladu + 1));
        int indexOaz = 1;
        int indexPoslOazy = indexPoslSkladu + (pocetOaz * 2) + 1;

        for(int i = indexPoslSkladu + 2; i < indexPoslOazy; i += 2){

            double x = Double.parseDouble(data.get(i));
            double y = Double.parseDouble(data.get(i + 1));

            Bod oaza = new Oaza(indexOaz, x, y, baseDat);

            baseDat.inputOaza((Oaza) oaza);
            baseDat.inputZastavka(oaza);

            indexSkladu++;
            indexOaz++;
        }

        int pocetCest = Integer.parseInt(data.get(indexPoslOazy + 1));
        int indexPoslCesty = indexPoslOazy + (pocetCest * 2) + 1;

        for(int i = indexPoslOazy + 2; i < indexPoslCesty; i += 2){

            int indexZastavky = Integer.parseInt(data.get(i));
            int indexSousedu = Integer.parseInt(data.get(i + 1));

            Bod zastavka = baseDat.getGraf().get(indexZastavky - 1); // -1 proto, ze v listu vsech zastavek pocet jde od 0
            Bod soused = baseDat.getGraf().get(indexSousedu - 1);

            zastavka.vlozHranu(soused);
            soused.vlozHranu(zastavka);

        }

        int pocetDruhu = Integer.parseInt(data.get(indexPoslCesty + 1));
        int indexPoslDruhu = indexPoslCesty + (pocetDruhu * 8) + 1;

        double strVelblRychlost = 0;
        double strVelblVzdal = 0;

        double maxVelblRychlost = 0;
        double vzdNejrychlVelbl = 0;

        double maxVelblVzdal = 0;
        double rychlNejdelsVelbl = 0;

        double stredniDobaPiti = 0;

        for(int i = indexPoslCesty + 2; i < indexPoslDruhu; i += 8){

            String nazev = data.get(i);
            double minRychlost = Double.parseDouble(data.get(i + 1));
            double maxRychlost = Double.parseDouble(data.get(i + 2));
            double minVzdalenost = Double.parseDouble(data.get(i + 3));
            double maxVzdalenost = Double.parseDouble(data.get(i + 4));
            int dobaPiti = Integer.parseInt(data.get(i + 5));

            int maxZatizeni = Integer.parseInt(data.get(i + 6));
            double pomerDruhu = Double.parseDouble(data.get(i + 7));

            double strRychlost = (minRychlost + maxRychlost)/2;
            double strVzdalenost = (minVzdalenost + maxVzdalenost)/2;

            if(Data.jeVetsi(maxRychlost, maxVelblRychlost)){
                maxVelblRychlost = maxRychlost;
                vzdNejrychlVelbl = strVzdalenost;
            }

            if(Data.jeVetsi(maxVzdalenost, maxVelblVzdal)){
                maxVelblVzdal = maxVzdalenost;
                rychlNejdelsVelbl = strRychlost;
            }

            strVelblRychlost += strRychlost;
            strVelblVzdal += strVzdalenost * pomerDruhu;

            stredniDobaPiti += dobaPiti;

            DruhVelbloudu druhVelbloudu = new DruhVelbloudu(nazev, minRychlost, maxRychlost, minVzdalenost, maxVzdalenost, dobaPiti, maxZatizeni, pomerDruhu);
            baseDat.inputDruhVelbloudu(druhVelbloudu);
        }

        strVelblRychlost /= baseDat.getDruhyVelbloudu().size();
        stredniDobaPiti /= baseDat.getDruhyVelbloudu().size();

        Velbloud stredniVelbloud = new Velbloud(0, baseDat, strVelblRychlost, strVelblVzdal, stredniDobaPiti);
        baseDat.setStredniVelbl(stredniVelbloud);

        Velbloud rychlejsiVelbl = new Velbloud(0, baseDat, maxVelblRychlost, vzdNejrychlVelbl, stredniDobaPiti);
        baseDat.setRychlejsiVelbl(rychlejsiVelbl);

        Velbloud nejdelsiVelbl = new Velbloud(0, baseDat, rychlNejdelsVelbl, maxVelblVzdal, stredniDobaPiti);
        baseDat.setNejdelsiVelbl(nejdelsiVelbl);

        int idPozadavku = 1;
        int pocetPozadavku = Integer.parseInt(data.get(indexPoslDruhu + 1));
        int indexPoslPozadavku = indexPoslDruhu + (pocetPozadavku * 4) + 1;

        for(int i = indexPoslDruhu + 2; i < indexPoslPozadavku; i += 4){

            double casPrichodu = Double.parseDouble(data.get(i));
            int indexOazy = Integer.parseInt(data.get(i + 1));
            int mnozstviKosu = Integer.parseInt(data.get(i + 2));
            int casCekani = Integer.parseInt(data.get(i + 3));

            Pozadavek pozadavek = new Pozadavek(idPozadavku, casPrichodu, indexOazy, mnozstviKosu, casCekani);
            baseDat.inputPozadavka(pozadavek);
            idPozadavku++;

        }
    }
}
