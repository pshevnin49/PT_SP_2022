package pt;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Hlavni trida programu
 */
public class Main {

    public static void main(String [] args) {
        vypisMenu();
    }

    /**
     * Menu pro obsluhu celeho programu
     */
    private static void vypisMenu(){
        Scanner scn = new Scanner(System.in);
        System.out.println("\t******Main Menu*******");

        System.out.println("----------------------------------------------------");
        System.out.println("|Prikaz:   1 - Generovani dat                      |");
        System.out.println("|          2 - Nacist data a pustit simulaci   	   |");
        System.out.println("|          3 - Spustit v rezimu debug              |");
        System.out.println("----------------------------------------------------");

        int vstup;
        do {
            System.out.println("Zadejte cislo prikazu od 1 az 4:");
            vstup = scn.nextInt();
        } while (vstup < 1 || vstup > 4);

        try {

            if(vstup == 1){
                System.out.println("Zadejte jmeno souboru koncici na .txt:");
                String jmenoSouboru = scn.next();
                System.out.println("Zadejte pocet skladu:");
                int pocetSkladu = scn.nextInt();
                System.out.println("Zadejte pocet oaz:");
                int pocetOaz = scn.nextInt();
                System.out.println("Zadejte pocet cest:");
                int pocetCest = scn.nextInt();
                System.out.println("Zadejte pocet druhu velbloudu:");
                int pocetDruhu = scn.nextInt();
                System.out.println("Zadejte pocet pozadavek:");
                int pocetPozadavek = scn.nextInt();

                if(pocetSkladu <= 0|| pocetOaz <= 0 || pocetCest <= 0 || pocetDruhu <= 0 || pocetPozadavek <= 0){
                    System.out.println("Spatny format dat pro generace, ukonceni programu.");
                    scn.close();
                    return;
                }
                generovaniDat(jmenoSouboru, pocetSkladu, pocetOaz, pocetCest, pocetDruhu, pocetPozadavek);

            }
            else if(vstup == 2){
                System.out.println("Zadejte jmeno souboru koncici na .txt:");
                String jmenoSouboru = scn.next();
                spusteniSimulace(jmenoSouboru);
            }
            else if(vstup == 3){
                System.out.println("Zadejte jmeno souboru koncici na .txt:");
                String jmenoSouboru = scn.next();
                Simulace simulace;

                simulace = spusteniDebug(jmenoSouboru);
                boolean bezi;
                do {
                    System.out.println("Zmacknete 1 aby posunout simulace o jeden krok");
                    System.out.println("Zmacknete libovlone cislo aby nechat dobehnout do konce");
                    vstup = scn.nextInt();

                    simulace.debugRezim();

                    if(vstup == 1){
                        bezi = simulace.debugRezim();

                    }
                    else{
                        simulace.startSimulace();
                        bezi = false;
                    }
                } while (bezi);
            }

        } catch (IOException e) {
            System.out.println("Soubor neexistuje nebo jina chyba.");
            throw new RuntimeException(e);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }finally {
            scn.close();
        }

        scn.close();
    }

    private static Simulace spusteniDebug(String jmenoSouboru) throws CloneNotSupportedException, IOException {
        CteniDat cteniDat = new CteniDat(jmenoSouboru);
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);
        dijkstra.spustAlgoritmus();

        Simulace simulace = new Simulace(baseDat);
        return simulace;
    }

    private static void spusteniSimulace(String jmenoSouboru) throws IOException, CloneNotSupportedException {
        CteniDat cteniDat = new CteniDat(jmenoSouboru);
        Data baseDat = new Data();
        List<String> data = cteniDat.cteni();

        ZpracovaniDat zpracovaniDat = new ZpracovaniDat();
        zpracovaniDat.zpracovani(data, baseDat);

        DijkstraAlgoritmus dijkstra = new DijkstraAlgoritmus(baseDat);
        dijkstra.spustAlgoritmus();

        Simulace simulace = new Simulace(baseDat);
        simulace.startSimulace();

        SouborStatistiky statistiky = new SouborStatistiky(baseDat);
        statistiky.genSoubrStatistik();

    }

    private static void generovaniDat(String jmenoSouboru, int pocetSkladu, int pocetOaz, int pocetCest, int pocDruhu, int pocPoz) throws IOException {

        DataGenerator generator = new DataGenerator();
        generator.generatorDat(pocetSkladu, pocetOaz, pocetCest, pocDruhu, pocPoz, jmenoSouboru);

    }


}
