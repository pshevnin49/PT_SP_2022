package pt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CteniDat {
    public String jmenoSouboru;

    public CteniDat(String jmenoSouboru){
        this.jmenoSouboru = jmenoSouboru;
    }
    public List<String> cteni() throws FileNotFoundException {

        List<String> cisla = new ArrayList<>();
        int hloubkaKomentare = 0; // To je aktualni hloubka komentaru(kolik oteviracih znaku nejsou zavrene v danem miste)

        FileReader read = new FileReader(jmenoSouboru);
        Scanner scn = new Scanner(read);

        while(scn.hasNext()){
            String datovyPrvek = "";
            String slovo = scn.next();
            //System.out.println(slovo);

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

        }
        scn.close();
        return cisla;
    }

}
