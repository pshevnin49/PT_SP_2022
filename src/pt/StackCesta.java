package pt;

public class StackCesta implements Cloneable{

    private BodCesty top = null;

    public void pridej(Bod novaStanice, double vzdalenost){

        BodCesty novyBod = new BodCesty(novaStanice, vzdalenost, top);

        if(top == null){
            this.top = novyBod;
        }
        else{
            top = novyBod;
        }
    }

    BodCesty get(){
        return top;
    }
    public double getCelaDalka(){
        double dalka = 0;
        BodCesty bodCesty = top;

        while (bodCesty != null){
            dalka += bodCesty.vzdalenost;
            bodCesty = bodCesty.next;
        }
        return dalka;
    }
    public void odstran(){
        if(top == null){
            return;
        }
        else{
            top = top.next;
        }
    }

    public void vypis(){
        BodCesty bodCesty = top;

        while(bodCesty != null){
            System.out.print("from: " + bodCesty.stanice.getId()  + " -> " + bodCesty.vzdalenost + " -> ");
            bodCesty = bodCesty.next;
        }
        System.out.println(getCelaDalka() + "O");
    }

    /**
     * Prijima velblouda, pocet kusu, a spocita stihne li velbloud projit danou cestu v cas
     * @param velbloud
     * @param casDoruceni - cas prichodu pozadavku + doba cekani
     * @param pocetKosu
     * @return
     */
    public boolean stihneCestuVelbloud(Velbloud velbloud, double casDoruceni, int pocetKosu){
        BodCesty bodCesty = top;
        double maxDalka = 0;
        double cestaBezPiti = 0;
        double celyCasCesty = 0;

        while(bodCesty != null){
            if(Data.jeVetsi(bodCesty.vzdalenost, maxDalka)){
                maxDalka = bodCesty.vzdalenost;
            }

            if(Data.jeVetsi(velbloud.getVzdalenostMax(), cestaBezPiti + bodCesty.vzdalenost)){
                cestaBezPiti = 0;
                celyCasCesty += velbloud.getDruhVelbloudu().getDobaPiti();
            }

            celyCasCesty += bodCesty.vzdalenost / velbloud.getRychlost();
            cestaBezPiti += bodCesty.vzdalenost;
        }

        double realnyCasDoruceni = celyCasCesty + velbloud.getAktualniCas() + (2 * pocetKosu * velbloud.getDomovskaStanice().getCasNalozeni()); // cely cas

        if(Data.jeVetsi(casDoruceni, realnyCasDoruceni) && Data.jeVetsi(velbloud.getVzdalenostMax(), maxDalka)){
            return true;
        }
        return false;
    }

    public Bod getPrvniBod(){
        return top.stanice;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
