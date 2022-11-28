package pt;

public class Pozadavek {

    private final double CAS_PRICHODU;
    private final int ID_OAZY;
    private final int POCET_KOSU;
    private final int ID;
    private final double CAS_OCEKAVANI;
    private final double CAS_DORUCENI;

    private int vylozeneKose;
    private double casFactickehoDoruceni;
    private int nalozeneKose;

    private Velbloud velbloud;

    public Pozadavek(int id, double casPrichodu, int idOazy, int pocetKosu, double casOcekavani) {
        this.ID = id;
        this.CAS_PRICHODU = casPrichodu;
        this.ID_OAZY = idOazy;
        this.POCET_KOSU = pocetKosu;
        this.CAS_OCEKAVANI = casOcekavani;
        this.CAS_DORUCENI = casPrichodu + casOcekavani;
        this.vylozeneKose = 0;
        this.nalozeneKose = 0;
    }

    public int getNenalozeneKose(){
        return POCET_KOSU - nalozeneKose;
    }
    public void zvetsiNalozeneKose(int pocet){
        nalozeneKose += pocet;
    }
    public void zvetsiVylozeneKose(int pocet){
        vylozeneKose += pocet;
    }
    public int getNevylozeneKose(){
        return POCET_KOSU - vylozeneKose;
    }

    public double getCasPrichodu() {
        return CAS_PRICHODU;
    }
    public int getIdOazy() {
        return ID_OAZY;
    }
    public int getId(){
        return ID;
    }
    public int getPocetKosu() {
        return POCET_KOSU;
    }
    public double getCasOcekavani() {
        return CAS_OCEKAVANI;
    }
    public double getCasDoruceni(){
        return CAS_DORUCENI;
    }

    public void setVelbloud(Velbloud velbloud) {
        this.velbloud = velbloud;

    }

    public void setCasFactickehoDoruceni(double casFactickehoDoruceni) {
        this.casFactickehoDoruceni = casFactickehoDoruceni;
    }

    public String getLog(){
        String log = String.format("    Pozadavek id: %d; nejpozdejsi cas doruceni: %.2f; pocet kosu: %d; cas faktickeho doruceni %.2f; id skladu: %d; id velbloudu %d \n", ID, CAS_DORUCENI
                , POCET_KOSU, casFactickehoDoruceni, velbloud.getDomovskaStanice().getId(), velbloud.getId());
        return log;
    }
}
