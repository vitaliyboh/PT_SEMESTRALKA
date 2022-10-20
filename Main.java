import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        String fileName = "data/tutorial.txt";
        long start = System.nanoTime();
        Svet svet = reader(fileName);
        System.out.println("Duration: " + ((System.nanoTime() - start) / 1000000000.0) + "s");

        while(!svet.pozadavky.isEmpty()){
            Pozadavek aktualni = svet.pozadavky.poll();

            int indexSkladu = svet.mapa.nejblizsiVrchol(aktualni.getOp() + svet.sklady.length - 1);
            System.out.println(indexSkladu);



            System.out.printf("Cas: %d, Pozadavek: %d, Oaza: %d, Pocet kosu: %d, Deadline: %d%n", ((int)(aktualni.getTz() + 0.5)), aktualni.getPoradi(),
                    aktualni.getOp(), aktualni.getKp(), ((int)((aktualni.getTz() + aktualni.getTp()) + 0.5)));

        }

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
            pozice.setX(Double.parseDouble(allUdaje[j]));
            pozice.setY(Double.parseDouble(allUdaje[j+1]));
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
            oazy[i+1] = new Oaza(
                    new Pozice(
                            Double.parseDouble(allUdaje[j]),
                    Double.parseDouble(allUdaje[j+1])));
        }

        // Tvorba grafu
        int indexCest = (pocetOaz*2+indexOaza)  + 1;
        int pocetCest = Integer.parseInt(allUdaje[indexCest]);
        Graph1 graph = new Graph1(pocetOaz+pocetSkladu+1, sklady, oazy);
        for (int i = 0; i < pocetCest; i++) {
            int j = indexCest + 1 + 2*i;
            graph.addEdge(Integer.parseInt(allUdaje[j]),Integer.parseInt(allUdaje[j+1]));
        }
        graph.floydWarshall();

        // Velbloudy (druhy)
        int indexBlouda = (pocetCest*2 + indexCest) + 1;
        int pocetBloudu = Integer.parseInt(allUdaje[indexBlouda]);
        DruhVelblouda[] druhyVelblouda = new DruhVelblouda[pocetBloudu+1];
        for (int i = 0; i < pocetBloudu; i++) {
            int j = indexBlouda + 1 + 8*i;
            druhyVelblouda[i+1] = new DruhVelblouda(allUdaje[j], Double.parseDouble(allUdaje[j+1]),
                    Double.parseDouble(allUdaje[j+2]), Double.parseDouble(allUdaje[j+3]),
                    Double.parseDouble(allUdaje[j+4]), Double.parseDouble(allUdaje[j+5]),
                    Double.parseDouble(allUdaje[j+6]), Double.parseDouble(allUdaje[j+7]));
        }

        // Pozadavky
        int indexPoz = (pocetBloudu*8 + indexBlouda) + 1;
        int pocetPoz = Integer.parseInt(allUdaje[indexPoz]);
        Queue<Pozadavek> pozadavky = new LinkedList<>();
        for (int i = 0; i < pocetPoz; i++) {
            int j = indexPoz + 1 + 4*i;
            pozadavky.add(new Pozadavek(Double.parseDouble(allUdaje[j]), Integer.parseInt(allUdaje[j+1]),
                    Integer.parseInt(allUdaje[j+2]), Double.parseDouble(allUdaje[j+3])));
        }

            return new Svet(oazy,sklady,druhyVelblouda, pozadavky, graph);
    }
}
