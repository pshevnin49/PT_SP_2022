package pt;

import java.util.List;

public class ZpracovaniDat {

    private Data baseDat;

//    public ZpracovaniDat(Data baseDat){
//        this.baseDat = baseDat;
//    }

    public void zpracovani(List<String> data, Data baseDat){

        int maxStredniRychlost = 0; // maximalni stredni rychlost
        //int dobaNapitiRychlejsiho = 0;
        int maxStredniVzdal = 0; // maximalni stredni dalka

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

            Bod oaza = new Oaza(indexOaz, x, y);

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

        for(int i = indexPoslCesty + 2; i < indexPoslDruhu; i += 8){

            String nazev = data.get(i);
            int minRychlost = Integer.parseInt(data.get(i + 1));
            int maxRychlost = Integer.parseInt(data.get(i + 2));
            int minVzdalenost = Integer.parseInt(data.get(i + 3));
            int maxVzdalenost = Integer.parseInt(data.get(i + 4));
            int dobaPiti = Integer.parseInt(data.get(i + 5));
            int maxZatizeni = Integer.parseInt(data.get(i + 6));
            double pomerDruhu = Double.parseDouble(data.get(i + 7));

            int stredniRychlost = (minRychlost + maxRychlost)/2;
            if(stredniRychlost > maxStredniRychlost){
                maxStredniRychlost = stredniRychlost;
            }

            int stredniVzdalenost = (minVzdalenost + maxVzdalenost)/2;
            if(stredniVzdalenost > maxStredniVzdal){
                maxStredniVzdal = stredniVzdalenost;
            }

            DruhVelbloudu druhVelbloudu = new DruhVelbloudu(nazev, minRychlost, maxRychlost, minVzdalenost, maxVzdalenost, dobaPiti, maxZatizeni, pomerDruhu);
            baseDat.inputDruhVelbloudu(druhVelbloudu);

        }

        baseDat.setMaxDalkaVelbloudu(maxStredniVzdal);
        baseDat.setMaxRychlostVelbloudu(maxStredniRychlost);

        int idPozadavku = 1;
        int pocetPozadavku = Integer.parseInt(data.get(indexPoslDruhu + 1));
        int indexPoslPozadavku = indexPoslDruhu + (pocetPozadavku * 4) + 1;

        for(int i = indexPoslDruhu + 2; i < indexPoslPozadavku; i += 4){

            int casPrichodu = Integer.parseInt(data.get(i));
            int indexOazy = Integer.parseInt(data.get(i + 1));
            int mnozstviKosu = Integer.parseInt(data.get(i + 2));
            int casCekani = Integer.parseInt(data.get(i + 3));


            Pozadavek pozadavek = new Pozadavek(idPozadavku, casPrichodu, indexOazy, mnozstviKosu, casCekani);
            baseDat.inputPozadavka(pozadavek);
            idPozadavku++;

        }
    }
}
