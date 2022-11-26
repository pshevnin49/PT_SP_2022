package pt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class DijkstraAlgoritmus {

    private Data baseDat;
    private List<Bod> nezpracovane;

    private Bod minStanice = null;
    private double minDalka = 0;

    public DijkstraAlgoritmus(Data baseDat){
        this.baseDat = baseDat;
    }

    /**
     * Metoda prochazi vsichni sklady, a
     */
    public void spustAlgoritmus() throws CloneNotSupportedException {

        for(int i = 0; i < baseDat.getVsichniSklady().size(); i++){
            System.out.println("Sklad cislo: " + i);
            spoctiCestyOdSkladu(baseDat.getVsichniSklady().get(i));
        }
    }

    /**
     * Metoda prochazi cely graf pomoci Dijkstruveho algoritmu, a hleda vsichni
     * nejkratsi cesty do oazy daneho indexu. Pak vraci list nejkratsich cest od
     * vsech skladu do teto oazy
     * @param sklad
     * @return listCest (serazeny od nejkratsi do nejdelsi)
     * @throws CloneNotSupportedException
     */
    private void spoctiCestyOdSkladu(Bod sklad) throws CloneNotSupportedException {

        nezpracovane = new ArrayList<>();
        baseDat.pripravZastavky();
        sklad.setDistance(0);

        nezpracovane.add(sklad);

        while(!nezpracovane.isEmpty()){
            int index = 0;
            if(minStanice != null){
                index = nezpracovane.indexOf(minStanice);
            }

            nezpracovane.get(index).setJeZpracovany(true);
            zpracujSousedi(nezpracovane.get(index));
            nezpracovane.remove(index);

        }

        for(int i = 0; i < baseDat.getVsichniOazy().size(); i++){
            baseDat.getVsichniOazy().get(i).zapisCestuDoOazy();
        }
    }

    /**
     * Prijima stanice a spocita vzdalenosti vsech jeji sousedi,
     * pokud nalezena vzdalenost bude mensi nez aktualni vzdalenost, prepise tuto vzdalenost
     * @param stanice
     */
    private void zpracujSousedi(Bod stanice) throws CloneNotSupportedException {

        List<Hrana> hrany = stanice.getHrany();
        minStanice = null;
        minDalka = 0;


        for(int i = 0; i < hrany.size(); i++){

            Hrana hrana = hrany.get(i);
            double vzdalenost = hrana.getVzdalenost() + stanice.getDistance();
            Bod novaStanice = hrana.getStanice();

            if(!hrana.getStanice().jeZpracovany()){
                if(Data.jeVetsi(hrana.getStanice().getDistance(), vzdalenost)){

                    //Cesta cesta = (Cesta) stanice.getCestaKeStanici().clone();

                    CestaList cesta = (CestaList) stanice.getCestaKeStanici().cloneSPoslednim();

                    novaStanice.setDistance(vzdalenost);
                    novaStanice.setCestaKeStanici(cesta);

                    cesta.pridej(novaStanice, hrana.getVzdalenost());

                }

                if(!hrana.getStanice().getZpracovava()){
                    if(Data.jeMensi(hrana.getStanice().getDistance(), minDalka)){
                        minDalka = hrana.getStanice().getDistance();
                        minStanice = hrana.getStanice();
                    }
                    nezpracovane.add(hrana.getStanice());
                    hrana.getStanice().setZpracovava(true);
                }
            }

        }
    }
}
