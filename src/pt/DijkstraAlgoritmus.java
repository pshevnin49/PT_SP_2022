package pt;

import java.util.ArrayList;
import java.util.List;

public class DijkstraAlgoritmus {

    private Data baseDat;

    public DijkstraAlgoritmus(Data baseDat){
        this.baseDat = baseDat;
    }

    public void getVsichniCesty(int indexOazy) throws CloneNotSupportedException {
        baseDat.pripravZastavky();

        Stanice oaza = baseDat.getVsichniOazy().get(indexOazy - 1);
        oaza.setDistance(0);
        StackCesta cestaKOaze = new StackCesta();
        cestaKOaze.pridej(oaza, 0);

        while(oaza != null){
            zpracujSousedi(oaza);
            oaza.setJeZpracovany(true);
            oaza = baseDat.getNezpracovanouStanice();
        }
    }

    /**
     * Prijima stanice a spocita vzdalenosti vsech jeji sousedi,
     * pokud nalezena vzdalenost bude mensi nez aktualni vzdalenost, prepise tuto vzdalenost
     * @param stanice
     */
    private void zpracujSousedi(Stanice stanice) throws CloneNotSupportedException {
        List<Hrana> hrany = stanice.getHrany();

        for(int i = 0; i < hrany.size(); i++){

            Hrana hrana = hrany.get(i);
            double vzdalenost = hrana.getVzdalenost() + stanice.getDistance();
            Stanice novaStanice = hrana.getStanice();

            if(Data.jeVetsi(hrana.getStanice().getDistance(), vzdalenost)){

                StackCesta cesta = (StackCesta) stanice.getCestaKeStanici().clone();
                cesta.pridej(novaStanice, hrana.getVzdalenost());

                //System.out.println(vzdalenost + " vzdalenost");

                novaStanice.setDistance(vzdalenost);
                novaStanice.setCestaKeStanici(cesta);
            }

        }
    }



}
