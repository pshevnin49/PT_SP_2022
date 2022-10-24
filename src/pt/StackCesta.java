package pt;

import java.util.List;

public class StackCesta implements Cloneable{ // treba se udelat stack cesta tak, aby spoustel vypocty jenom jednou, pak vyuzival promenne

    private BodCesty top = null;
    private boolean jeSpoctenaCesta = false;
    private double maxUsecka;
    private double dalkaCesty;
    private List<Double> vsichniDelkyCesty;
    private Bod stanice;

    public void pridej(Bod novaStanice, double vzdalenost){

        BodCesty novyBod = new BodCesty(novaStanice, vzdalenost, top);

        if(top == null){
            top = novyBod;
            stanice = novyBod.stanice;
        }
        else{
            novyBod.next = top;
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

    public Bod getPosledniStanice(){
        return stanice;
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
     * @return vraci 0, pokud velbloud stihne cestu, vraci 1 pokud velbloud
     * ma mensi vzdalenost nez nejdelsi hrana cesty
     * vraci 2, pokud velbloud nestihne cestu casove
     */
    public int stihneCestuVelbloud(Velbloud velbloud, double casDoruceni, int pocetKosu){
        BodCesty bodCesty = top;
        double maxDalka = 0;
        double cestaBezPiti = 0;
        double celyCasCesty = 0;
        double celaDalkaCesty = 0;

        //if(!jeSpoctenaCesta){
            while(bodCesty != null){
                if(Data.jeVetsi(bodCesty.vzdalenost, maxDalka)){
                    maxDalka = bodCesty.vzdalenost;
                }

                if(Data.jeVetsi(velbloud.getVzdalenostMax(), cestaBezPiti + bodCesty.vzdalenost)){
                    cestaBezPiti = 0;
                    celyCasCesty += velbloud.getDruhVelbloudu().getDobaPiti();
                }

                celyCasCesty += bodCesty.vzdalenost / velbloud.getRychlost();
                celaDalkaCesty += bodCesty.vzdalenost;
                cestaBezPiti += bodCesty.vzdalenost;
                bodCesty = bodCesty.next;

            }

            jeSpoctenaCesta = true;
            maxUsecka = maxDalka;
            dalkaCesty = celaDalkaCesty;


//        }else{
//            maxDalka = maxUsecka;
//            celaDalkaCesty = dalkaCesty;
//            celyCasCesty = celaDalkaCesty / velbloud.getRychlost();
//        }

        double realnyCasDoruceni = celyCasCesty + velbloud.getAktualniCas() + (2 * pocetKosu * velbloud.getDomovskaStanice().getCasNalozeni()); // cely cas
        if(Data.jeVetsi(casDoruceni, realnyCasDoruceni)){
            if(Data.jeVetsi(velbloud.getVzdalenostMax(), maxDalka)){
                return 0;
            }
            else{
                return 1;
            }
        }
        else{
            return 2;
        }

    }

    public Bod getPrvniBod(){
        return top.stanice;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
