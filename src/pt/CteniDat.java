package pt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CteniDat {
    public String jmenoSouboru;
    public CteniDat(String jmenoSouboru){
        this.jmenoSouboru = jmenoSouboru;
    }

    /**
     * Cte data a ignoruje vsichni komentare, vraci List Stringu s uzitecnyma datama
     * @return cisla
     * @throws FileNotFoundException
     */
    public List<String> cteni() throws IOException {

        List<String> cisla = new ArrayList<>();

        FileReader read = null;
        Scanner scn = null;

        try {
            read = new FileReader(jmenoSouboru);
            scn = new Scanner(read);
            while(scn.hasNext()){
                String slovo = scn.next();
                cisla.addAll(getNextCisla(slovo));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            scn.close();
            read.close();
        }

        return cisla;
    }

    /**
     * Metoda prijima jeden radek bez mezer, a vraci vsichni uzitecna cisla z tohoto radku a odstranuje komentare
     * @param slovo
     * @return list uzitecnych cisel
     */
    private List<String> getNextCisla(String slovo){
        List<String> cisla = new ArrayList<>();
        String datovyPrvek = "";
        int hloubkaKomentare = 0; // To je aktualni hloubka komentaru(kolik oteviracih znaku nejsou zavrene v danem miste)

        for(int i = 0; i < slovo.length(); i++){
            Character prvniChar = slovo.charAt(i);

            if(prvniChar.equals('\uD83D')){
                Character druhyCharEmoji = slovo.charAt(i + 1);
                if(druhyCharEmoji.equals('\uDC2A')){
                    //velbloud
                    hloubkaKomentare++;
                    if(!datovyPrvek.equals("")){
                        cisla.add(datovyPrvek);
                        datovyPrvek = "";
                    }
                    i++;
                }
            } else if (prvniChar.equals('\uD83C')) {
                Character druhyCharEmoji = slovo.charAt(i + 1);
                if (druhyCharEmoji.equals('\uDFDC')) {
                    //poust
                    hloubkaKomentare--;
                }
                i++;
            } else if (hloubkaKomentare == 0){
                datovyPrvek = datovyPrvek + prvniChar;

            }
        }
        if(!datovyPrvek.equals("")){
            cisla.add(datovyPrvek);
        }
        return cisla;
    }
}
