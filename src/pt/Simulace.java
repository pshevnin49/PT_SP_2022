package pt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class Simulace {

    private DijkstraAlgoritmus dijkstra;
    private Data baseDat;
    private boolean bezi = true;

    public Simulace(Data baseDat){
        this.baseDat = baseDat;
    }

    public void startSimulace() throws CloneNotSupportedException {
        double minKrokCas = baseDat.getMinKrokCasu();
        List<Pozadavek> aktPozList = new ArrayList<>();

        dijkstra = new DijkstraAlgoritmus(baseDat);
        dijkstra.spustAlgoritmus();

        while(minKrokCas != Data.MAX_VALUE && bezi){

            List<Pozadavek> novePozadavky = baseDat.getAktualniPozadavky();

            aktPozList.addAll(novePozadavky);

            if(novePozadavky != null){
                aktPozList.sort(comparing(Pozadavek::getCasDoruceni));
            }

            for(int i = 0; i < novePozadavky.size(); i++ ){
                Pozadavek pozadavek = novePozadavky.get(i);
                System.out.printf("Cas: %d, Pozadavek: %d, Oaza: %d, Pocet kosu: %d, Deadline: %d\n", Math.round(baseDat.getAktualniCas()),
                        pozadavek.getId(), pozadavek.getIdOazy(), pozadavek.getPocetKosu(), Math.round(pozadavek.getCasDoruceni()));
            }

            if(aktPozList.size() > 0){
                zpracovaniPoz(aktPozList);
            }

            baseDat.zvetseniCasuSimulace(minKrokCas);
            minKrokCas = baseDat.getMinKrokCasu();
            if(minKrokCas == Data.MAX_VALUE && aktPozList.size() > 0){
                minKrokCas = baseDat.getMinKrokSkladu();
            }

        }
    }

    /**
     * Prijima vsichni aktualni pozadavky, prochazi a zpracovava jich
     * zastavi pozadavek, pokud najde nejaky pozadavek ktery nelze splnit
     * z duvodu maximalni cesty
     * @param pozadavekList
     * @throws CloneNotSupportedException
     */
    private void zpracovaniPoz(List<Pozadavek> pozadavekList) throws CloneNotSupportedException {

        for(int i = 0; i < pozadavekList.size(); i++){

            Pozadavek pozadavek = pozadavekList.get(i);
            Oaza oaza = baseDat.getVsichniOazy().get(pozadavek.getIdOazy() - 1);

            List<CestaList> cesty = oaza.getCestyDoOazy();
            cesty.sort(comparing(CestaList::getIndexCesty));

            if(kontrolaPoz(pozadavek)){
                int iDCesty = getIDVhodneCesty(pozadavek); // id nejlepsi aktualne dostupne cesty (na sklade je potrebny pocet kosu)
                CestaList prvniCesta = cesty.get(0);

                if(baseDat.getAktualniCas() - pozadavek.getCasDoruceni() > 0){
                    System.out.println("Error 1");
                    nesplnitelnyPoz(pozadavek);
                    break;
                }

                if(prvniCesta.getCasRychlVelbl(pozadavek.getPocetKosu()) != -1 && prvniCesta.getCasRychlVelbl(pozadavek.getPocetKosu()) > pozadavek.getCasDoruceni() - baseDat.getAktualniCas()){ // Kontrola prvni (nejlepsi) cesty, aby byla casove splnitelna
                    System.out.println("Error 2");
                    nesplnitelnyPoz(pozadavek);
                    break;
                }

                if(iDCesty != -1){
                    CestaList cesta = cesty.get(iDCesty);
                    if(iDCesty == 0){

                       if(spusteniVelblouda(pozadavek, cesta)){
                           pozadavekList.remove(i);
                           i--;
                       }

                   }else{
                       if(cesta.zvladneStrVelbl()){

                           if(spusteniVelblouda(pozadavek, cesta)){
                               pozadavekList.remove(i);
                               i--;
                           }

                       } else if (cesta.zvladneNejdelVelbl()) {
                           if(prvniCesta.getCasStrVelbl(pozadavek.getPocetKosu()) == -1){ // kdyz stredni velbloud nezvladne (podle max dalky) ani nejlepsi cestu
                               if((baseDat.getAktualniCas() - pozadavek.getCasDoruceni())/3 < prvniCesta.getCasNejdelVelbl(pozadavek.getPocetKosu())){//kontrola, je li cas na to aby mohli pockat lepsi cestu
                                   if(spusteniVelblouda(pozadavek, cesta)){
                                       pozadavekList.remove(i);
                                       i--;
                                   }
                               }
                           }
                           else if((baseDat.getAktualniCas() - pozadavek.getCasDoruceni())/3 < prvniCesta.getCasStrVelbl(pozadavek.getPocetKosu())){ //Kontrola pokud je cas pockat na uvolneni nejlepsi cesty
                               if(spusteniVelblouda(pozadavek, cesta)){
                                   pozadavekList.remove(i);
                                   i--;
                               }
                           }

                       }
                   }

                }
            }else{
                System.out.println("Error 3");
                nesplnitelnyPoz(pozadavek);
                break;
            }
        }
    }

    private boolean spusteniVelblouda(Pozadavek pozadavek, CestaList cesta) throws CloneNotSupportedException {
        Sklad sklad = (Sklad) cesta.get().getStanice();
        Velbloud velbloud = sklad.getVhodnyVelbl(1, pozadavek.getCasDoruceni(), cesta);

        if(velbloud != null){

            while(pozadavek.getNenalozeneKose() > 0){
                int pocetKosu;

                if(velbloud.getDruhVelbloudu().getMaxZatizeni() < pozadavek.getNenalozeneKose()){
                    pocetKosu = velbloud.getDruhVelbloudu().getMaxZatizeni();
                }else{
                    pocetKosu = pozadavek.getNenalozeneKose();
                }

                velbloud.zacniNakladat(pocetKosu, cesta, pozadavek);
                pozadavek.zvetsiNalozeneKose(pocetKosu);
                velbloud = sklad.getVhodnyVelbl(1, pozadavek.getCasDoruceni(), cesta);
            }
            return true;
        }

        return false;
    }



    private void nesplnitelnyPoz(Pozadavek pozadavek){
        System.out.printf("Cas: %d, Oaza: %d, Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n", Math.round(baseDat.getAktualniCas()),
                pozadavek.getIdOazy());

        baseDat.setErrLog(String.format("Doslo k chybe v case: %.2f, oaza c: %d \n", baseDat.getAktualniCas(), pozadavek.getIdOazy()));
        bezi = false;
    }

    /**
     * Metoda prijima pozadavek, pak prochazi vsichni cesty a hleda prvni cestu sklad ktere
     * ma dostacujici pocet kosu.
     * @param pozadavek
     * @return index vchodne cesty
     */
    private int getIDVhodneCesty(Pozadavek pozadavek) throws CloneNotSupportedException {
        List<CestaList> cesty = baseDat.getVsichniOazy().get(pozadavek.getIdOazy() - 1).getVsichniCesty();


            for(int i = 0; i < cesty.size(); i++){
                Sklad sklad = (Sklad) cesty.get(i).get().getStanice();
                if(sklad.getPocetKosu() >= pozadavek.getPocetKosu()){
                    return i;
                }
            }

        return -1;
    }

    /**
     * Metoda kontroluje vsichni cesty jednoho pozadavku zda lze tuto cestu projit nejdelsim velbloudem
     * @param pozadavek
     * @return true, pokud existuje cesta, kterou lze projit nejdelsim velbloudem false, pokud ne
     */
    private boolean kontrolaPoz(Pozadavek pozadavek) throws CloneNotSupportedException {

        List<CestaList> cesty = baseDat.getVsichniOazy().get(pozadavek.getIdOazy() - 1).getVsichniCesty();

            for(int i = 0; i < cesty.size(); i++){

                if(cesty.get(i).getCasNejdelVelbl(pozadavek.getPocetKosu()) != -1){
                    return true;
                }
            }

            return false;
    }

}
