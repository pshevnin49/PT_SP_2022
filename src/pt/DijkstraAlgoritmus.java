package pt;

import java.util.ArrayList;
import java.util.List;

public class DijkstraAlgoritmus {

    private Data baseDat;
    private List<Bod> nezpracovane;

    private Bod minStanice = null;
    private double minDalka = 0;

    public DijkstraAlgoritmus(Data baseDat){
        this.baseDat = baseDat;
    }
    int idOazy = 0;

    /**
     * Metoda prochazi vsichni sklady, a
     */
    public void spustAlgoritmus() throws CloneNotSupportedException {
        for(int i = 0; i < baseDat.getVsichniSklady().size(); i++){
            spoctiCestyOdSkladu(baseDat.getVsichniSklady().get(i));
            idOazy++;
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
    public void spoctiCestyOdSkladu(Bod sklad) throws CloneNotSupportedException {

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
            zpracujBod(nezpracovane.get(index));
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
    private void zpracujBod(Bod stanice) throws CloneNotSupportedException {

        List<Hrana> hrany = stanice.getHrany();
        minStanice = null;
        minDalka = 0;

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
