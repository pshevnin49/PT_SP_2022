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
            nezpracovane.get(0).setJeZpracovany(true);
            zpracujSousedi(nezpracovane.get(0));
            nezpracovane.remove(0);
            //nezpracovane.sort(comparing(Bod::getDistance));
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

        //System.out.println("Zpracovava stanice cislo: " + stanice.getId());

        for(int i = 0; i < hrany.size(); i++){

            Hrana hrana = hrany.get(i);
            double vzdalenost = hrana.getVzdalenost() + stanice.getDistance();
            Bod novaStanice = hrana.getStanice();

            if(!hrana.getStanice().jeZpracovany()){
                if(Data.jeVetsi(hrana.getStanice().getDistance(), vzdalenost)){

                    Cesta cesta = (Cesta) stanice.getCestaKeStanici().clone();
//                    Bod clonePrvni = cesta.getPrvniBod();
//                    double cloneDalka = cesta.get().vzdalenost;

//                    if(clonePrvni != null){
//                        cesta.nahradPrvni(clonePrvni, cloneDalka);
//                    }

                    cesta.pridej(novaStanice, hrana.getVzdalenost());

                    novaStanice.setDistance(vzdalenost);
                    novaStanice.setCestaKeStanici(cesta);
                }
                if(!hrana.getStanice().getZpracovava()){
                    nezpracovane.add(hrana.getStanice());
                    hrana.getStanice().setZpracovava(true);

                }

            }

        }
    }
}
