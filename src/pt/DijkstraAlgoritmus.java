package pt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class DijkstraAlgoritmus {

    private Data baseDat;
    private List<Bod> nezpracovane;

    public DijkstraAlgoritmus(Data baseDat){
        this.baseDat = baseDat;
    }

    /**
     * Metoda prochazi cely graf pomoci Dijkstruveho algoritmu, a hleda vsichni
     * nejkratsi cesty do oazy daneho indexu. Pak vraci list nejkratsich cest od
     * vsech skladu do teto oazy
     * @param indexOazy
     * @return listCest (serazeny od nejkratsi do nejdelsi)
     * @throws CloneNotSupportedException
     */
    public List<StackCesta> getVsichniCesty(int indexOazy) throws CloneNotSupportedException {

        nezpracovane = new ArrayList<>();

        List<StackCesta> listCest = new ArrayList<>();
        baseDat.pripravZastavky();

        Bod oaza = baseDat.getVsichniOazy().get(indexOazy - 1);
        nezpracovane.add(oaza);

        oaza.setDistance(0);
        StackCesta cestaKOaze = new StackCesta(baseDat);
        cestaKOaze.pridej(oaza, 0);



//        while(oaza != null){
//            zpracujSousedi(oaza);
//            oaza.setJeZpracovany(true);
//            oaza = baseDat.getNezpracovanouStanice();
//        }

        while(!nezpracovane.isEmpty()){
            zpracujSousedi(nezpracovane.get(0));
            nezpracovane.get(0).setJeZpracovany(true);
            nezpracovane.remove(0);
        }

        for(int i = 0; i < baseDat.getVsichniSklady().size(); i++){
            StackCesta novaCesta = baseDat.getVsichniSklady().get(i).getCestaKeStanici();
            listCest.add(novaCesta);
        }

        listCest.sort(comparing(StackCesta::getIndexCesty));


        return listCest;
    }

    /**
     * Prijima stanice a spocita vzdalenosti vsech jeji sousedi,
     * pokud nalezena vzdalenost bude mensi nez aktualni vzdalenost, prepise tuto vzdalenost
     * @param stanice
     */
    private void zpracujSousedi(Bod stanice) throws CloneNotSupportedException {
        List<Hrana> hrany = stanice.getHrany();

        for(int i = 0; i < hrany.size(); i++){

            Hrana hrana = hrany.get(i);
            double vzdalenost = hrana.getVzdalenost() + stanice.getDistance();
            Bod novaStanice = hrana.getStanice();

            if(Data.jeVetsi(hrana.getStanice().getDistance(), vzdalenost)){

                StackCesta cesta = (StackCesta) stanice.getCestaKeStanici().clone();
                cesta.pridej(novaStanice, hrana.getVzdalenost());

                novaStanice.setDistance(vzdalenost);
                novaStanice.setCestaKeStanici(cesta);
            }

            if(!hrana.getStanice().jeZpracovany()){
                nezpracovane.add(hrana.getStanice());
            }


        }
    }
}
