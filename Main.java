import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        try {
            List<String> list = Files.readAllLines(Paths.get("parser.txt"));
            int bloudi = 0;
            for(String line : list) { // for each pres vsechny radky v seznamu

                int iBloud = line.indexOf("\uD83D\uDC2A");
                int iPoust = line.indexOf("\uD83C\uDFDC");
                if (bloudi != 0 && iBloud == -1){ // bloudi>0 => jsem v blokovym komentari a zaroven indexBlouda neni na aktualni radce
                    while(iPoust != -1){ // jedem pres indexy pouste
                        bloudi--;
                        line = line.substring(iPoust+2);
                        iPoust = line.indexOf("\uD83C\uDFDC");
                    }
                }
                while (iBloud != -1) { // jedem dokud na radce jsou bloudi
                    bloudi++;
                    iPoust = line.indexOf("\uD83C\uDFDC");
                    if (iPoust != -1) bloudi--; // pokud jsem nasel poust odstranim jednoho blouda

                    line = line.substring(0, iBloud) + " " + line.substring(iPoust + 2);
                    iBloud = line.indexOf("\uD83D\uDC2A");

                    if(line.indexOf("\uD83C\uDFDC") != -1) { //tenhle if to vyresil :D
                        iBloud = 0;
                    }

                }
                if (bloudi == 0 && !line.isBlank()) { // v line je(jsou) validni udaj(e)
                    String str = line.replaceAll("[\\s|\\u00A0]+"," "); // nahradi vsechny whitespaces jednou mezerou
                    String[] split = str.split(" "); // nasoupe hodnoty do pole
                    int n = 0;
                    for (String s : split){ // pokud string vypadal takto " 1" v poli je ["",1], toho se zbavime kontrolou zda neni nahodou prvek pole prazdny
                        if(!s.isBlank()) n++; // vysledne pole bez prazdnych prvku bude mit velikost n
                    }
                    String[] udaje = new String[n]; // zde budou uz orezany, neprazdny, osetreny validni udaje
                    int pom = 0; // prekopirovani validnich, neprazdnych udaju do novyho pole
                    for (String s : split){
                        if(!s.isBlank()) {
                            udaje[pom] = s;
                            pom++;
                        }
                    }

                    System.out.println(Arrays.toString(udaje));

                }
            }
        }
            catch (IOException e) {
                e.printStackTrace();
        }
    }

}
