import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String fileName = "tutorial.txt";
        Svet svet = reader(fileName);
    }
    public static Svet reader(String fileName) {
        ArrayList<String> allUdaje1 = null;
        try {
            allUdaje1 = new ArrayList<String>();

            List<String> list = Files.readAllLines(Paths.get(fileName));
            int bloudi = 0;
            for (String line : list) { // for each pres vsechny radky v seznamu

                int iBloud = line.indexOf("\uD83D\uDC2A");
                int iPoust = line.indexOf("\uD83C\uDFDC");
                if (bloudi != 0 && iBloud == -1) { // bloudi>0 => jsem v blokovym komentari a zaroven indexBlouda neni na aktualni radce
                    while (iPoust != -1) { // jedem pres indexy pouste
                        bloudi--;
                        line = line.substring(iPoust + 2);
                        iPoust = line.indexOf("\uD83C\uDFDC");
                    }
                }
                while (iBloud != -1) { // jedem dokud na radce jsou bloudi
                    bloudi++;
                    iPoust = line.indexOf("\uD83C\uDFDC");
                    if (iPoust != -1) bloudi--; // pokud jsem nasel poust odstranim jednoho blouda

                    line = line.substring(0, iBloud) + " " + line.substring(iPoust + 2);
                    iBloud = line.indexOf("\uD83D\uDC2A");

                    if (line.indexOf("\uD83C\uDFDC") != -1) { //tenhle if to vyresil :D
                        iBloud = 0;
                    }

                }

                if (bloudi == 0 && !line.isBlank()) { // v line je(jsou) validni udaj(e)
                    String str = line.replaceAll("[\\s|\\u00A0]+", " "); // nahradi vsechny whitespaces jednou mezerou
                    String[] split = str.split(" "); // nasoupe hodnoty do pole
                    int n = 0;
                    for (String s : split) { // pokud string vypadal takto " 1" v poli je ["",1], toho se zbavime kontrolou zda neni nahodou prvek pole prazdny
                        if (!s.isBlank()) n++; // vysledne pole bez prazdnych prvku bude mit velikost n
                    }
                    String[] udaje = new String[n]; // zde budou uz orezany, neprazdny, osetreny validni udaje
                    int pom = 0; // prekopirovani validnich, neprazdnych udaju do novyho pole
                    for (String s : split) {
                        if (!s.isBlank()) {
                            udaje[pom] = s;
                            pom++;
                        }
                    }
                    for (String s : udaje) {
                        allUdaje1.add(s);
                    }
                    //System.out.println(Arrays.toString(udaje));
                }
            }
           // System.out.println(allUdaje1.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] allUdaje = allUdaje1.toArray(new String[0]);
        // Sklady
        int pocetSkladu = Integer.parseInt(allUdaje[0]);
        Sklad[] sklady = new Sklad[pocetSkladu+1];
        for (int i = 0; i < pocetSkladu; i++) {
            Pozice pozice = new Pozice(0,0 );
            int j = i*5 +1;
            pozice.setX(Integer.parseInt(allUdaje[j]));
            pozice.setY(Integer.parseInt(allUdaje[j+1]));
            int ks = Integer.parseInt(allUdaje[j+2]);
            int ts = Integer.parseInt(allUdaje[j+3]);
            int tn = Integer.parseInt(allUdaje[j+4]);
            sklady[i+1] = new Sklad(pozice,ks,ts,tn);
        }

        // Oazy
        int indexOaza = pocetSkladu*5 + 1;
        int pocetOaz = Integer.parseInt(allUdaje[indexOaza]);
        Oaza[] oazy = new Oaza[pocetOaz+1];
        for (int i = 0; i < pocetOaz; i++) {
            int j = indexOaza + 1 + 2*i;
            oazy[i+1] = new Oaza(new Pozice(Integer.parseInt(allUdaje[j]),Integer.parseInt(allUdaje[j+1])));
        }






            return null;
    }
}
