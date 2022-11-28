package pt;

public class Pozadavek {

    private double casPrichodu;
    private int idOazy;
    private int pocetKosu;
    private int nalozeneKose;
    private int vylozeneKose;
    private int id;
    private double casFactickehoDoruceni;
    private double casOcekavani;
    private double casDoruceni;
    private Velbloud velbloud;


    public Pozadavek(int id, double casPrichodu, int idOazy, int pocetKosu, double casOcekavani) {
        this.id = id;
        this.casPrichodu = casPrichodu;
        this.idOazy = idOazy;
        this.pocetKosu = pocetKosu;
        this.casOcekavani = casOcekavani;
        this.casDoruceni = casPrichodu + casOcekavani;
        this.vylozeneKose = 0;
        this.nalozeneKose = 0;
    }

    public int getNenalozeneKose(){
        return pocetKosu - nalozeneKose;
    }
    public void zvetsiNalozeneKose(int pocet){
        nalozeneKose += pocet;
    }
    public void zvetsiVylozeneKose(int pocet){
        vylozeneKose += pocet;
    }
    public int getNevylozeneKose(){
        return pocetKosu - vylozeneKose;
    }


    public double getCasPrichodu() {
        return casPrichodu;
    }
    public int getIdOazy() {
        return idOazy;
    }
    public int getId(){
        return id;
    }
    public int getPocetKosu() {
        return pocetKosu;
    }
    public double getCasOcekavani() {
        return casOcekavani;
    }
    public double getCasDoruceni(){
        return casDoruceni;
    }

    public double getCasFactickehoDoruceni() {
        return casFactickehoDoruceni;
    }

    public Velbloud getVelbloud() {
        return velbloud;
    }

    public void setVelbloud(Velbloud velbloud) {
        this.velbloud = velbloud;

    }

    public void setCasFactickehoDoruceni(double casFactickehoDoruceni) {
        this.casFactickehoDoruceni = casFactickehoDoruceni;
    }

    public void vypis(){
        System.out.println("Cas prichodu: " + casPrichodu + ". id oazy:" + idOazy + " pocet kosu:" + pocetKosu + " cas ocekavani:" + casOcekavani);
    }

    public String getLog(){
        String log = String.format("    Pozadavek id: %d; nejpozdejsi cas doruceni: %.2f; pocet kosu: %d; cas faktickeho doruceni %.2f; id skladu: %d; id velbloudu %d \n", id, casDoruceni
                , pocetKosu, casFactickehoDoruceni, velbloud.getDomovskaStanice().getId(), velbloud.getId());
        return log;
    }
}
