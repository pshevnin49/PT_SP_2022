package pt;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class Simulace {

    private final Data BASE_DAT;
    private boolean bezi = true;

    public Simulace(Data baseDat){
        this.BASE_DAT = baseDat;
    }

    public void startSimulace() throws CloneNotSupportedException {
        double minKrokCas = BASE_DAT.getMinKrokCasu();
        List<Pozadavek> aktPozList = new ArrayList<>();

        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(BASE_DAT);
        dijkstra.spustAlgoritmus();

        while(minKrokCas != Data.MAX_VALUE && bezi){

            List<Pozadavek> novePozadavky = BASE_DAT.getAktualniPozadavky();

            aktPozList.addAll(novePozadavky);

            if(novePozadavky != null){
                aktPozList.sort(comparing(Pozadavek::getCasDoruceni));
            }

            for(int i = 0; i < novePozadavky.size(); i++ ){
                Pozadavek pozadavek = novePozadavky.get(i);
                System.out.printf("Cas: %d, Pozadavek: %d, Oaza: %d, Pocet kosu: %d, Deadline: %d\n", Math.round(BASE_DAT.getAktualniCas()),
                        pozadavek.getId(), pozadavek.getIdOazy(), pozadavek.getPocetKosu(), Math.round(pozadavek.getCasDoruceni()));
            }

            if(aktPozList.size() > 0){
                zprVsechPoz(aktPozList);
            }

            BASE_DAT.zvetseniCasuSimulace(minKrokCas);
            minKrokCas = BASE_DAT.getMinKrokCasu();
            if(minKrokCas == Data.MAX_VALUE && aktPozList.size() > 0){
                minKrokCas = BASE_DAT.getMinKrokSkladu();
            }

        }
    }

    /**
     * Prijima list pozadavku, a na zacatku pomoci metody kontrolPoz()
     * kontroluje, zda vsichni pozadavky maji aspon jednu cestu, kterou da splnit aspon jednim velbloudem
     * @param pozadavekList
     * @throws CloneNotSupportedException
     */
    private void zprVsechPoz(List<Pozadavek> pozadavekList) throws CloneNotSupportedException {

        for(int i = 0; i < pozadavekList.size(); i++){
            Pozadavek pozadavek = pozadavekList.get(i);
            Oaza oaza = BASE_DAT.getVsichniOazy().get(pozadavek.getIdOazy() - 1);

            List<CestaList> cesty = oaza.getCestyDoOazy();
            cesty.sort(comparing(CestaList::getIndexCesty));
            if(kontrolaPoz(pozadavek)){
                CestaList cesta = getCesta(pozadavek, cesty);

                if(cesta != null && spusteniVelblouda(pozadavek, cesta)){
                    pozadavekList.remove(i);
                    i--;
                }

            }else{
                nesplnitelnyPoz(pozadavek);
                break;
            }
        }
    }

    /**
     * Metoda prijima pozadavek, a vsichni jeho cesty, pokud existuje cesta
     * kterou da projit alespon jednim z velbloudu vcas, vrati tuto cestu, pokud takova cesta ted chybi, vrati null
     * @param pozadavek
     * @param cesty
     * @return
     * @throws CloneNotSupportedException
     */
    private CestaList getCesta(Pozadavek pozadavek, List<CestaList> cesty) throws CloneNotSupportedException {
        int iDCesty = getIDVhodneCesty(pozadavek); // id nejlepsi aktualne dostupne cesty (na sklade je potrebny pocet kosu)
        CestaList prvniCesta = cesty.get(0);

        if(iDCesty != -1){
            CestaList cesta = cesty.get(iDCesty);
            if(iDCesty == 0){
                return cesta;
            }else {
                if(cesta.zvladneStrVelbl()){
                    return cesta;

                } else if (cesta.zvladneNejdelVelbl()) {
                    if(prvniCesta.getCasStrVelbl(pozadavek.getPocetKosu()) == -1){ // kdyz stredni velbloud nezvladne (podle max dalky) ani nejlepsi cestu
                        if((BASE_DAT.getAktualniCas() - pozadavek.getCasDoruceni())/3 < prvniCesta.getCasNejdelVelbl(pozadavek.getPocetKosu())){//kontrola, je li cas na to aby mohli pockat lepsi cestu
                            return cesta;
                        }
                    }
                    else if((BASE_DAT.getAktualniCas() - pozadavek.getCasDoruceni())/3 < prvniCesta.getCasStrVelbl(pozadavek.getPocetKosu())){ //Kontrola pokud je cas pockat na uvolneni nejlepsi cesty
                        return cesta;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Metoda prijima pozadavek, a vhodnou na dany moment cestu pro dany pozadavek
     * a zacina hledat vhodneho velblouda na sklade, pomoci metody getVhodnyVelbl()
     * pokud vhodny velbloud byl nalezen, posila toho velblouda na cestu
     * @param pozadavek
     * @param cesta
     * @return
     * @throws CloneNotSupportedException
     */
    private boolean spusteniVelblouda(Pozadavek pozadavek, CestaList cesta) throws CloneNotSupportedException {
        Sklad sklad = (Sklad) cesta.get().getStanice();
        Velbloud velbloud;

        while(pozadavek.getNenalozeneKose() > 0){
            int pocetKosu;
            velbloud = sklad.getVhodnyVelbl(1, pozadavek.getCasDoruceni(), cesta);

            if(velbloud == null){
                return false;
            }

            if(velbloud.getDruhVelbloudu().getMaxZatizeni() < pozadavek.getNenalozeneKose()){
                pocetKosu = velbloud.getDruhVelbloudu().getMaxZatizeni();
            }else{
                pocetKosu = pozadavek.getNenalozeneKose();
            }

            velbloud.zacniNakladat(pocetKosu, cesta, pozadavek);
            pozadavek.zvetsiNalozeneKose(pocetKosu);

        }
        return true;

    }



    private void nesplnitelnyPoz(Pozadavek pozadavek){
        System.out.printf("Cas: %d, Oaza: %d, Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n", Math.round(BASE_DAT.getAktualniCas()),
                pozadavek.getIdOazy());

        BASE_DAT.setErrLog(String.format("Doslo k chybe v case: %.2f; Oaza c: %d \n", BASE_DAT.getAktualniCas(), pozadavek.getIdOazy()));
        bezi = false;
    }

    /**
     * Metoda prijima pozadavek, pak prochazi vsichni cesty a hleda prvni cestu sklad ktere
     * ma dostacujici pocet kosu.
     * @param pozadavek
     * @return index vchodne cesty
     */
    private int getIDVhodneCesty(Pozadavek pozadavek) throws CloneNotSupportedException {
        List<CestaList> cesty = BASE_DAT.getVsichniOazy().get(pozadavek.getIdOazy() - 1).getVsichniCesty();


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

        List<CestaList> cesty = BASE_DAT.getVsichniOazy().get(pozadavek.getIdOazy() - 1).getVsichniCesty();
        CestaList prvniCesta = cesty.get(0);

        if(BASE_DAT.getAktualniCas() - pozadavek.getCasDoruceni() > 0){
            return false;
        }
        if(prvniCesta.getCasRychlVelbl(pozadavek.getPocetKosu()) != -1 && prvniCesta.getCasRychlVelbl(pozadavek.getPocetKosu()) > pozadavek.getCasDoruceni() - BASE_DAT.getAktualniCas()){ // Kontrola prvni (nejlepsi) cesty, aby byla casove splnitelna
            return false;
        }

        for(int i = 0; i < cesty.size(); i++){
            if(cesty.get(i).getCasNejdelVelbl(pozadavek.getPocetKosu()) != -1){
                return true;
            }
        }

        return false;
    }

}
