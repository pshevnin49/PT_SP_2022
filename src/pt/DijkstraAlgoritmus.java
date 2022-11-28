package pt;

import java.util.ArrayList;
import java.util.List;

/**
 * Trida ve ktere je implementovan Dijkstreho algoritmus pro hledani cest od jedneho bodu grafu
 * ke vsem ostatnim
 */
public class DijkstraAlgoritmus {

    private final Data BASE_DAT;
    private List<Bod> nezpracovane;

    /**
     * Konstruktor tridy
     * @param baseDat
     */
    public DijkstraAlgoritmus(Data baseDat){
        this.BASE_DAT = baseDat;
    }
    int idOazy = 0;

    /**
     * Metoda prochazi vsichni sklady, a spousti pro nich hledani
     * cesto do vsech
     */
    public void spustAlgoritmus() throws CloneNotSupportedException {
        for(int i = 0; i < BASE_DAT.getVsichniSklady().size(); i++){
            spoctiCestyOdSkladu(BASE_DAT.getVsichniSklady().get(i));
            idOazy++;
        }
    }

    /**
     * Metoda priijima sklad, a spocita cesty od daneho skladu do vsech
     * ostatnich bodu grafu. Na zacatku vklada sklad do listu nezpracovane, a zacina volat metodu
     * zpracuj bod pro nezpracovany bod s nejmensi vzdalenosti, dokud tento list nebude prazdny
     * @param sklad
     * @throws CloneNotSupportedException
     */
    public void spoctiCestyOdSkladu(Bod sklad) throws CloneNotSupportedException {

        nezpracovane = new ArrayList<>();
        BASE_DAT.pripravZastavky();
        sklad.setDistance(0);

        nezpracovane.add(sklad);

        while(!nezpracovane.isEmpty()){
            double minDalka = 0;
            int index = 0;

            for(int i = 0; i < nezpracovane.size(); i++){
                if(Data.jeMensi(nezpracovane.get(i).getDistance(), minDalka)){
                    minDalka = nezpracovane.get(i).getDistance();
                    index = i;
                }
            }

            nezpracovane.get(index).setJeZpracovany(true);
            zpracujBod(nezpracovane.get(index));
            nezpracovane.remove(index);
        }

        for(int i = 0; i < BASE_DAT.getVsichniOazy().size(); i++){
            BASE_DAT.getVsichniOazy().get(i).zapisCestuDoOazy();
        }
    }

    /**
     * Prijima stanice a spocita vzdalenosti vsech jeji sousedi,
     * pokud nalezena vzdalenost bude mensi nez aktualni vzdalenost, prepise tuto vzdalenost
     * Pak kontroluje, jstli bod neni zpracovan (vsichni jeho sousedi uz maji spoctenou dalku)
     * kdyz ne, pridava bod do listu nezpracovane
     * @param stanice
     */
    private void zpracujBod(Bod stanice) throws CloneNotSupportedException {

        List<Hrana> hrany = stanice.getHrany();
        //minStanice = null;
        double minDalka = 0;

        for(int i = 0; i < hrany.size(); i++){

            Hrana hrana = hrany.get(i);
            double vzdalenost = hrana.getVzdalenost() + stanice.getDistance();
            Bod novaStanice = hrana.getStanice();
            if(!hrana.getStanice().jeZpracovany()){

                if(Data.jeVetsi(hrana.getStanice().getDistance(), vzdalenost)){
                    CestaList cesta = (CestaList) stanice.getCestaKeStanici().clone();
                    novaStanice.setDistance(vzdalenost);
                    novaStanice.setCestaKeStanici(cesta);
                    cesta.pridej(novaStanice, hrana.getVzdalenost());
                }

                if(Data.jeMensi(hrana.getStanice().getDistance(), minDalka) && !hrana.getStanice().getZpracovava()){
                    minDalka = hrana.getStanice().getDistance();
                }

                if(!hrana.getStanice().getZpracovava()){
                    nezpracovane.add(hrana.getStanice());
                    hrana.getStanice().setZpracovava(true);
                }
            }
        }
    }
}
