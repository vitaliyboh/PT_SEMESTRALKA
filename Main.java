import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static Random r;
    static boolean sonic;

    public static void main(String[] args) {


        r = new Random();
        String fileName = "data/centre_medium.txt";
        long start = System.nanoTime();
        Svet svet = reader(fileName);
        System.out.println(((System.nanoTime() - start) / 1000000.0) + " ms\n\n");
        double casPredchozihoPozadavku = 0;
        sonic = true;

        /////////////////////////// VYRIZOVANI POZADAVKU  /////////////////////////////

        while (!svet.pozadavky.isEmpty()) { // jeden pozadavek bude zpracovan v jednom pruchodu while
            Pozadavek aktualni = svet.pozadavky.poll();


            System.out.printf("Cas: %d, Pozadavek: %d, Oaza: %d, Pocet kosu: %d, Deadline: %d%n",
                    ((int) (aktualni.getTz() + 0.5)), aktualni.getPoradi(),
                    aktualni.getOp(), aktualni.getKp(), ((int) ((aktualni.getTz() + aktualni.getTp()) + 0.5)));

            // zde mame sklady serazene od nejblizsiho po nejvzdalenejsi
            ArrayList<Integer> list = svet.mapa.nejblizsiVrcholy(aktualni.getOp() + svet.sklady.length - 1);

            ArrayList<Integer> pomocnyList = new ArrayList<>(list);

            // obnoveni kosu
            for (Integer skladyIndex : list) {
                Sklad sklad = svet.sklady[skladyIndex];
                if (aktualni.getTz() < sklad.getTs() || sklad.getAktualniPocetKosu() == sklad.getKs()) {
                    continue;
                }
                int nasobek1 = (int) (aktualni.getTz() / sklad.getTs());
                if (casPredchozihoPozadavku <= sklad.getTs() * nasobek1 && sklad.getTs() * nasobek1 <= aktualni.getTz()) {
                    sklad.setAktualniPocetKosu(sklad.getKs());
                    sklad.setNasobek(nasobek1);
                }
            }
            casPredchozihoPozadavku = aktualni.getTz();

            // z listu sklad odeberu, pokud nema dostatecny pocet kosu
            for (int i = list.size() - 1; i >= 0; i--) {
                if (svet.sklady[list.get(i)].getAktualniPocetKosu() < aktualni.getKp()) {
                    list.remove(i);
                }
            }


            // seznam nejblizsich skladu je prazdny -> kazdej sklad ma malo kosu
            // overim, zda se nevyplati pockat na dalsi obnoveni (pridam pripadny sklad zpet do seznamu)
            if (list.isEmpty()) {
                cekaniNaObnovuSkladu(svet, aktualni, list, pomocnyList);
            }

            // pokud bloud je na ceste ale stihne aktualni pozadavek az se vrati,
            // tak nastavime ze neni na ceste(tj mame s nim pocitat)
            for (Integer indexSkladu : list) {
                for (Velbloud velbloud : svet.sklady[indexSkladu].getVelboudi()) {
                    if (velbloud.isNaCeste() && velbloud.getCasNavratu() <= aktualni.getTz()) {
                        velbloud.setNaCeste(false);
                    }
                }
            }


            Velbloud velbloudFinalni = null; // finalni bloud, ktery vyrazi na cestu
            ArrayList<Velbloud> finalniBloudi = new ArrayList<>();
            double cas = -1; // nejrychlejsi mozny cas, za ktery to dany velbloud ujde
            int pocetKosu = aktualni.getKp();


            // projizdim sklady a jejich (jiz existujici)velbloudy
            // TENTO WHILE ZABIRAL POKAZDY 12 SEC, upravil jsem ho
            pocetKosu = kontrolaExisBloudu(svet, aktualni, list, velbloudFinalni, finalniBloudi, pocetKosu);

            // tento while zabiral 15 sec v centre_medium


            

            int err = 0;
            if (pocetKosu > 0) {
                err = generovaniBloudu(svet, aktualni, list, finalniBloudi, pocetKosu);
            }

            // pokud nejrychlejsi cas, ktery lze ujit je -1 -> pozadavek nelze splnit
            if (err == 1 || finalniBloudi.isEmpty()) {
                System.out.printf("Cas: %d, Oaza: %d, Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n",
                        (int) (aktualni.getTz() + 0.5), aktualni.getOp());
                System.out.println("Duvod: Nenasel se vhodny velbloud");
                System.exit(1);
            }

            
            finalniCestaBlouda(svet, aktualni, finalniBloudi);
            System.out.println();
        }

        System.out.println("Simulace probehla uspesne :) *dab*");

    }

    private static void finalniCestaBlouda(Svet svet, Pozadavek aktualni, ArrayList<Velbloud> finalniBloudi) {
        for (Velbloud velbloud : finalniBloudi) {
            if (velbloud.isNaCeste()) aktualni.setTz(velbloud.getCasNavratu());

            // odecteme pocet kosu ze skladu, ktere bloud nalozil na sebe
            Sklad skladVelbloudaFinalniho = svet.sklady[velbloud.getIndexSkladu()];
            skladVelbloudaFinalniho.setAktualniPocetKosu(skladVelbloudaFinalniho.getAktualniPocetKosu() - velbloud.getKd());


            ArrayList<Integer> finalniCesta = svet.mapa.cesta(velbloud.getIndexSkladu(),
                    aktualni.getOp() + svet.sklady.length - 1, velbloud);

            System.out.printf("Cas: %d, Velbloud: %d, Sklad: %d, Nalozeno kosu: %d, Odchod v: %d\n",
                    (int) (aktualni.getTz() + 0.5), velbloud.getPoradi(),
                    velbloud.getIndexSkladu(), velbloud.getKd(),
                    (int) (aktualni.getTz() + velbloud.getKd() * svet.sklady[velbloud.getIndexSkladu()].getTn() + 0.5));

            try {
                svet.mapa.vypisCestyVelblouda(velbloud, finalniCesta, aktualni);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private static int generovaniBloudu(Svet svet, Pozadavek aktualni, ArrayList<Integer> list, ArrayList<Velbloud> finalniBloudi, int pocetKosu) {
        Velbloud velbloudFinalni;
        double cas;
        int pomocna = 0;


        // kontrola, zda vubec bloud s maximalnimi atributy zvladne ujit cestu
        boolean zvladne = false;
        for (DruhVelblouda druh : svet.druhyVelbloudu) {

            Velbloud superBloud = new Velbloud(druh, list.get(0), r);
            superBloud.makeSuper();

            cas = svet.mapa.cestaVelblouda(superBloud, svet.mapa.cesta(superBloud.getIndexSkladu(),
                    aktualni.getOp() + svet.sklady.length - 1, superBloud), aktualni);
            if (cas != -1) {
                zvladne = true;
                break;
            }
        }

        if (!zvladne) {
            return 1;
        }; // pokud ani super bloud nezvladne cestu - nema cenu generovat
            
        
        
        while (pocetKosu > 0) {
//            if (pomocna > 1000) break; // pokud vygenerujem 1000 zbytecnych bloudu -> konec
            int celkovyPocetBloudu = Velbloud.getPocet();
            // generovani bloudu

            for (DruhVelblouda druh : svet.druhyVelbloudu) {
                if (druh.getPocet() <= celkovyPocetBloudu * druh.getPd() || celkovyPocetBloudu == 0) {
                    velbloudFinalni = new Velbloud(druh, list.get(0), r);
                    svet.sklady[list.get(0)].getVelboudi().add(velbloudFinalni);

                    if (svet.sklady[list.get(0)].getMaxVzdalenostBloud() == null) {
                        svet.sklady[list.get(0)].setMaxVzdalenostBloud(velbloudFinalni);
                    }
                    else if (svet.sklady[list.get(0)].getMaxVzdalenostBloud().getD() < velbloudFinalni.getD()) {
                            svet.sklady[list.get(0)].setMaxVzdalenostBloud(velbloudFinalni);
                    }

                    cas = svet.mapa.cestaVelblouda(velbloudFinalni, svet.mapa.cesta(velbloudFinalni.getIndexSkladu(),
                            aktualni.getOp() + svet.sklady.length - 1, velbloudFinalni), aktualni);
                    if (cas != -1) {
                        if ((pocetKosu - velbloudFinalni.getDruh().getKd()) >= 0) {
                            velbloudFinalni.setKd(velbloudFinalni.getDruh().getKd());
                            pocetKosu -= velbloudFinalni.getKd();
                        } else {
                            velbloudFinalni.setKd(pocetKosu);
                            pocetKosu = 0;
                        }
                        finalniBloudi.add(velbloudFinalni);
                    }

                    break;
                    // pocet tohoto druhu je mensi nez by mel byt => generuj
                }
                // pokud je vetsi => jedem dal
            }
        }
        return 0;
    }

    private static int kontrolaExisBloudu(Svet svet, Pozadavek aktualni, ArrayList<Integer> list,
                                          Velbloud velbloudFinalni, ArrayList<Velbloud> finalniBloudi,
                                          int pocetKosu) {
        double cas;
        while (pocetKosu > 0) {

            for (Integer indexSkladu : list) {
                // tuhle podminku posunul na uplne nahoru a pridal pokud je list empty tak continue
                // aby to skiplo ty sklady kde nejsou zadny bloudi
                if (svet.sklady[indexSkladu].getVelboudi() == null
                        || svet.sklady[indexSkladu].getVelboudi().isEmpty()) continue;
                ArrayList<Integer> cesta = svet.mapa.cesta(indexSkladu, aktualni.getOp() + svet.sklady.length - 1, svet.sklady[indexSkladu].getMaxVzdalenostBloud());
                double pomocna = Double.POSITIVE_INFINITY;

                for (Velbloud velbloud : svet.sklady[indexSkladu].getVelboudi()) {
                    //ArrayList<Integer> cesta = svet.mapa.cesta(indexSkladu, aktualni.getOp() + svet.sklady.length - 1, velbloud);

                    if (finalniBloudi.contains(velbloud)) continue;
                    cas = svet.mapa.cestaVelblouda(velbloud, cesta, aktualni);

                    if (velbloud.isNaCeste()) {
                        if (cas == -1 || (velbloud.getCasNavratu() + cas) > (aktualni.getTz() + aktualni.getTp()))
                            continue;
                    }

                    if (cas < pomocna && cas != -1) {
                        pomocna = cas;
                        velbloudFinalni = velbloud;
                    }
                }
            }

            if (velbloudFinalni == null || finalniBloudi.contains(velbloudFinalni)) break;
            if ((pocetKosu - velbloudFinalni.getDruh().getKd()) >= 0) {
                velbloudFinalni.setKd(velbloudFinalni.getDruh().getKd());
                pocetKosu -= velbloudFinalni.getKd();
            } else {
                velbloudFinalni.setKd(pocetKosu);
                pocetKosu = 0;
            }
            finalniBloudi.add(velbloudFinalni);
        }
        return pocetKosu;
    }

    private static void cekaniNaObnovuSkladu(Svet svet, Pozadavek aktualni, ArrayList<Integer> list,
                                             ArrayList<Integer> pomocnyList) {
        for (Integer indexSkladu : pomocnyList) {
            if (svet.sklady[indexSkladu].getKs() == 0)
                continue; // nema cenu obnovovat kose u skladu s poctem kosu 0
            int nasobek = (int) (aktualni.getTz() / svet.sklady[indexSkladu].getTs() + 1);
            if (nasobek == svet.sklady[indexSkladu].getNasobek()) {
                nasobek++;
            }
            if (aktualni.getTp() + aktualni.getTz() <= svet.sklady[indexSkladu].getTs() * nasobek) {
                continue;
            }
            list.add(indexSkladu);
            aktualni.setTz(svet.sklady[indexSkladu].getTs() * nasobek);
            svet.sklady[indexSkladu].setNasobek(nasobek);
            svet.sklady[indexSkladu].setAktualniPocetKosu(svet.sklady[indexSkladu].getKs());
            break;
        }
        // pokud i presto je seznam prazdny, nenalezli jsme vhodny sklad
        if (list.isEmpty()) {
            System.out.printf("Cas: %d, Oaza: %d, Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n",
                    (int) (aktualni.getTz() + 0.5), aktualni.getOp());
            System.out.println("Duvod: Nenasel se vhodny sklad");
            System.exit(1);
        }
    }

    private static void vypisVelbloudu(Svet svet) {
        int celkem = 0;
        for (DruhVelblouda d : svet.druhyVelbloudu){
            celkem += d.pocet;
            System.out.println(d);
        }
        System.out.println("Celkem: " + celkem + "\n");
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
                int iBloudPomocna = iBloud;
                while (iBloud != -1) { // jedem dokud na radce jsou bloudi
                    bloudi++;
                    iPoust = line.indexOf("\uD83C\uDFDC");
                    if (iPoust != -1) bloudi--; // pokud jsem nasel poust odstranim jednoho blouda

                    iBloudPomocna = iBloud;

                    line = line.substring(0, iBloudPomocna) + " " + line.substring(iPoust + 2);

                    int indexPousteNovyString = line.indexOf("\uD83C\uDFDC");
                    if (indexPousteNovyString != -1) { //tenhle if to vyresil :D
                        int indexBloudaNovyString = line.indexOf("\uD83D\uDC2A");
                        if (indexBloudaNovyString < indexPousteNovyString && indexBloudaNovyString != -1) {
                            iBloud = indexBloudaNovyString;
                        }
                        continue;
                    }
                    iBloud = line.indexOf("\uD83D\uDC2A");

                }

                if (bloudi == 0 && !line.isBlank()) { // v line je(jsou) validni udaj(e)
                    String str = line.replaceAll("[\\s|\\u00A0]+", " "); // nahradi vsechny whitespaces jednou mezerou
                    String[] split = str.split(" "); // nasoupe hodnoty do pole

                    for (String s : split) {
                        if (!s.isBlank()) {
                            allUdaje1.add(s);
                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] allUdaje = allUdaje1.toArray(new String[0]);

        // Sklady
        int pocetSkladu = Integer.parseInt(allUdaje[0]);
        Sklad[] sklady = new Sklad[pocetSkladu + 1];
        for (int i = 0; i < pocetSkladu; i++) {
            Pozice pozice = new Pozice(0, 0);
            int j = i * 5 + 1;
            pozice.setX(Double.parseDouble(allUdaje[j]));
            pozice.setY(Double.parseDouble(allUdaje[j + 1]));
            int ks = Integer.parseInt(allUdaje[j + 2]);
            double ts = Double.parseDouble(allUdaje[j + 3]);
            double tn = Double.parseDouble(allUdaje[j + 4]);
            sklady[i + 1] = new Sklad(pozice, ks, ts, tn);
        }

        // Oazy
        int indexOaza = pocetSkladu * 5 + 1;
        int pocetOaz = Integer.parseInt(allUdaje[indexOaza]);
        Oaza[] oazy = new Oaza[pocetOaz + 1];
        for (int i = 0; i < pocetOaz; i++) {
            int j = indexOaza + 1 + 2 * i;
            oazy[i + 1] = new Oaza(
                    new Pozice(
                            Double.parseDouble(allUdaje[j]),
                            Double.parseDouble(allUdaje[j + 1])));
        }

        // Tvorba grafu
        int indexCest = (pocetOaz * 2 + indexOaza) + 1;
        int pocetCest = Integer.parseInt(allUdaje[indexCest]);
        Graph graph = new Graph(pocetOaz + pocetSkladu + 1, sklady, oazy);

        for (int i = 0; i < pocetCest; i++) {
            int j = indexCest + 1 + 2 * i;
            graph.addEdge(Integer.parseInt(allUdaje[j]), Integer.parseInt(allUdaje[j + 1]));
        }
        


        // Velbloudy (druhy)
        int indexBlouda = (pocetCest * 2 + indexCest) + 1;
        int pocetBloudu = Integer.parseInt(allUdaje[indexBlouda]);
        DruhVelblouda[] druhyVelblouda = new DruhVelblouda[pocetBloudu];
        for (int i = 0; i < pocetBloudu; i++) {
            int j = indexBlouda + 1 + 8 * i;
            druhyVelblouda[i] = new DruhVelblouda(allUdaje[j], Double.parseDouble(allUdaje[j + 1]),
                    Double.parseDouble(allUdaje[j + 2]), Double.parseDouble(allUdaje[j + 3]),
                    Double.parseDouble(allUdaje[j + 4]), Double.parseDouble(allUdaje[j + 5]),
                    Integer.parseInt(allUdaje[j + 6]), Double.parseDouble(allUdaje[j + 7]));
        }

        // Pozadavky
        int indexPoz = (pocetBloudu * 8 + indexBlouda) + 1;
        int pocetPoz = Integer.parseInt(allUdaje[indexPoz]);
        Queue<Pozadavek> pozadavky = new LinkedList<>();
        for (int i = 0; i < pocetPoz; i++) {
            int j = indexPoz + 1 + 4 * i;
            pozadavky.add(new Pozadavek(Double.parseDouble(allUdaje[j]), Integer.parseInt(allUdaje[j + 1]),
                    Integer.parseInt(allUdaje[j + 2]), Double.parseDouble(allUdaje[j + 3])));
        }

        return new Svet(oazy, sklady, druhyVelblouda, pozadavky, graph);

    }

}


