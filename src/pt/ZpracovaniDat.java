package pt;

import java.util.List;

public class ZpracovaniDat {

    private Data baseDat;

    public ZpracovaniDat(Data baseDat){
        this.baseDat = baseDat;
    }

    public void zpracovani(List<String> data){

        int pocetSkladu = Integer.parseInt(data.get(0));
        int index = 1;
        int indexPoslSkladu = pocetSkladu * 5;

        for(int i = 1; i < indexPoslSkladu; i += 5){

            int x = Integer.parseInt(data.get(i));
            int y = Integer.parseInt(data.get(i + 1));
            int pocetKusu = Integer.parseInt(data.get(i + 2));
            int casObnoveni = Integer.parseInt(data.get(i + 3));
            int casNalozeni = Integer.parseInt(data.get(i + 4));
            Stanice sklad = new Sklad(index, x, y, pocetKusu, casObnoveni, casNalozeni);
            index++;
            //baseDat.inputSklad(sklad);
            baseDat.inputZastavka(sklad);

        }

        int pocetOaz = Integer.parseInt(data.get(indexPoslSkladu + 1));
        int indexPoslOazy = indexPoslSkladu + (pocetOaz * 2) + 1;


        for(int i = indexPoslSkladu + 2; i < indexPoslOazy; i += 2){

            int x = Integer.parseInt(data.get(i));
            int y = Integer.parseInt(data.get(i + 1));

            Stanice oaza = new Oaza(index, x, y);

            //baseDat.inputOaza(oaza);
            baseDat.inputZastavka(oaza);

            index++;
        }

        int pocetCest = Integer.parseInt(data.get(indexPoslOazy + 1));
        int indexPoslCesty = indexPoslOazy + (pocetCest * 2) + 1;
        System.out.println(pocetCest + " pocet cest");

        for(int i = indexPoslOazy + 2; i < indexPoslCesty; i += 2){

            int indexZastavky = Integer.parseInt(data.get(i));
            int indexSousedu = Integer.parseInt(data.get(i + 1));

            Stanice zastavka = baseDat.getVsicniZastavky().get(indexZastavky - 1); // -1 proto, ze v listu vsech zastavek pocet jde od 0
            Stanice soused = baseDat.getVsicniZastavky().get(indexSousedu - 1);

            zastavka.vlozSouseda(soused);
            soused.vlozSouseda(soused);

            //System.out.println( index + ", " + x + ", " + y);

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

            DruhVelbloudu druhVelbloudu = new DruhVelbloudu(nazev, minRychlost, maxRychlost, minVzdalenost, maxVzdalenost, dobaPiti, maxZatizeni, pomerDruhu);
            baseDat.inputDruhVelbloudu(druhVelbloudu);

        }

        int pocetPozadavku = Integer.parseInt(data.get(indexPoslDruhu + 1));
        int indexPoslPozadavku = indexPoslDruhu + (pocetPozadavku * 4) + 1;

        for(int i = indexPoslDruhu + 2; i < indexPoslPozadavku; i += 4){

            int casPrichodu = Integer.parseInt(data.get(i));
            int indexOazy = Integer.parseInt(data.get(i + 1));
            int mnozstviKosu = Integer.parseInt(data.get(i + 2));
            int casCekani = Integer.parseInt(data.get(i + 3));


            Pozadavka pozadavka = new Pozadavka(casPrichodu, indexOazy, mnozstviKosu, casCekani);
            baseDat.inputPozadavka(pozadavka);

        }
    }
}
