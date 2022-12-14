import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * Hlavni trida simulace obsahujici main
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Main {
    /** generator nahodnych cisel */
    static Random r;
    /** promena pro zrychleni simulace
     * sonic=true - neprovadime uspani mezi vypisy cesty
     * sonic=false - mezi vypisy je uspani 1sec
     */
    static boolean sonic;
    /**  */
    static Zapisovac zapisovac;

    /**
     * Vstupni bod programu, spusteni simulace
     *
     * @param args nazev souboru
     */
    public static void main(String[] args) {
        
        if (args.length != 1){
            System.out.println("Neplatny pocet parametru. " +
                    "Zadejte jeden parametr jako nazev souboru ve tvaru '<nazev_souboru>.txt'");
            System.exit(1);
        }

        r = new Random();
        String fileName = "data/" + args[0];
        Svet svet = reader(fileName); // precteme ze souboru data, vytvorime instanci svet
        zapisovac = new Zapisovac(svet.sklady, svet.oazy, svet.druhyVelbloudu);
        sonic = true;
        int casPoslednihoPozadavku = 0;

        /////////////////////////// VYRIZOVANI POZADAVKU  /////////////////////////////

        while (!svet.pozadavky.isEmpty()) { // jeden pozadavek bude zpracovan v jednom pruchodu while
            Pozadavek aktualni = svet.pozadavky.poll();

            InfoOazy infoOazy = new InfoOazy(); // pro potreby statistiky
            svet.oazy[aktualni.getOp()].getInfo().add(infoOazy);
            infoOazy.setCasPozadavku((int) (aktualni.getTz() + 0.5));
            infoOazy.setPocetKosu(aktualni.getKp());
            infoOazy.setDeadline((int) (aktualni.getTz() + aktualni.getTp() + 0.5));

            System.out.printf("Cas: %d, Pozadavek: %d, Oaza: %d, Pocet kosu: %d, Deadline: %d%n",
                    ((int) (aktualni.getTz() + 0.5)), aktualni.getPoradi(),
                    aktualni.getOp(), aktualni.getKp(), ((int) ((aktualni.getTz() + aktualni.getTp()) + 0.5)));

            // zde mame sklady serazene od nejblizsiho po nejvzdalenejsi
            List<Integer> list = svet.mapa.nejblizsiVrcholy(aktualni.getOp() + svet.sklady.length - 1);

            ArrayList<Integer> pomocnyList = new ArrayList<>(list); // pokud se vyprazdni list,
            // projdu sklady jeste jednou, tentokrat budu cekat do jejich obnoveni

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


            Velbloud velbloudFinalni = null; //TODO chyba - zapomneli jsme vymazat po uprave kodu
            List<Velbloud> finalniBloudi = new ArrayList<>(); // zde budou vsichni bloudi, kteri obslouzi pozadavek
            int pocetKosu = aktualni.getKp(); // pocet kosu ktery zbyva donest do oazy

            // projizdim sklady a jejich (jiz existujici)velbloudy
            pocetKosu = kontrolaExisBloudu(svet, aktualni, list, velbloudFinalni, finalniBloudi, pocetKosu);

            int err = 0; // slouzi pro zjisteni jak metoda dopadla (mohlo by byt boolean pro usetreni pameti)
            if (pocetKosu > 0) { // pokud zbyvaji nejaky kose jeste, dogeneruju blouda
                err = generovaniBloudu(svet, aktualni, list, finalniBloudi, pocetKosu);
            }

            // pokud nejrychlejsi cas, ktery lze ujit je -1 -> pozadavek nelze splnit
            if (err == 1 || finalniBloudi.isEmpty()) {
                System.out.printf("Cas: %d, Oaza: %d, Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n",
                        (int) (aktualni.getTz() + 0.5), aktualni.getOp());
                System.out.println("Duvod: Nenasel se vhodny velbloud");
                try {
                    zapisovac.zapisVse();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.exit(1);
            }
            int casPozadavku = finalniCestaBlouda(svet, aktualni, finalniBloudi);  // cas kdy se vratil do skladu
            if (casPozadavku > casPoslednihoPozadavku) { // pro ucely statistika - kdy byl nejpozdejc obslouzen pozadavek
                Velbloud.setCasPoslednihoPozadavku(casPozadavku);
                casPoslednihoPozadavku = casPozadavku;
            }
            System.out.println();
        }
        System.out.println("\nSimulace probehla uspesne :) *dab*");


        try {
            zapisovac.zapisVse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metoda zajisti vypis obsluhovani pozadavku
     *
     * @param svet          svet
     * @param aktualni      pozadavek, ktery musime obslouzit
     * @param finalniBloudi vsechny velbloudy, ktere budou obsluhovat dany pozadavek
     */
    private static int finalniCestaBlouda(Svet svet, Pozadavek aktualni, List<Velbloud> finalniBloudi) {
        int result = 0;
        for (Velbloud velbloud : finalniBloudi) {

            if (velbloud.isNaCeste()) { // pokud je na ceste nastavim cas na cas jeho navratu a poslu ho
                aktualni.setTz(velbloud.getCasNavratu());
            }

            // odecteme pocet kosu ze skladu, ktere bloud nalozil na sebe
            Sklad skladVelbloudaFinalniho = svet.sklady[velbloud.getIndexSkladu()];
            skladVelbloudaFinalniho.setAktualniPocetKosu(
                    skladVelbloudaFinalniho.getAktualniPocetKosu() - velbloud.getKd());

            // pres ktere indexy bloud pujde
            List<Integer> finalniCesta = svet.mapa.cesta(velbloud.getIndexSkladu(),
                    aktualni.getOp() + svet.sklady.length - 1, velbloud);
            List<String> pojmCesta = vratPojmenovanouCestu(finalniCesta, svet); // pro ucely statistiky
            int casOdchodu = (int) (aktualni.getTz() + velbloud.getKd()
                    * svet.sklady[velbloud.getIndexSkladu()].getTn() + 0.5); // kdy bloud vyrazi ze skladu
            // pro ucely statistiky
            Trasa trasa = new Trasa(casOdchodu, pojmCesta,
                    velbloud.getKd(), (finalniCesta.get(finalniCesta.size() - 1) - svet.sklady.length + 1));
            velbloud.getInfo().push(trasa);

            System.out.printf("Cas: %d, Velbloud: %d, Sklad: %d, Nalozeno kosu: %d, Odchod v: %d\n",
                    (int) (aktualni.getTz() + 0.5), velbloud.getPoradi(),
                    velbloud.getIndexSkladu(), velbloud.getKd(), casOdchodu);

            try {
                // uprava oproti odevzdani
                int pomocna = svet.mapa.vypisCestyVelblouda(velbloud, finalniCesta, aktualni);
                if (pomocna > result) {
                    result = pomocna;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    /**
     * Metoda zajisti generovani velbloudu
     *
     * @param svet          svet
     * @param aktualni      pozadavek, ktery potrebujeme obslouzit
     * @param list          list indexu skladu, ktere jsou serazene od nejblizsich po nejvzdalenejsi od dane oazy z pozadvku
     * @param finalniBloudi list vsech velbloudu, kteri budou dany pozadavek obsluhovat
     * @param pocetKosu1    pocet kosu, ktere potrebujeme jeste dorucit
     * @return 1 - pokud nemuzeme vygenerovat velblouda, ktery oblsouzi pozadavek,
     *          0 - pokud vygenerujeme velblouda, ktery zvladne oblouzit pozadavek
     */
    private static int generovaniBloudu(Svet svet, Pozadavek aktualni, List<Integer> list, List<Velbloud> finalniBloudi, int pocetKosu1) {
        Velbloud velbloudFinalni;
        double cas;
        int pocetKosu = pocetKosu1;

        // kontrola, zda vubec bloud s maximalnimi atributy zvladne ujit cestu
        boolean zvladne = false;
        for (DruhVelblouda druh : svet.druhyVelbloudu) {

            Velbloud superBloud = new Velbloud(druh, list.get(0), r);
            Velbloud.pocet -= 1;
            druh.setPocet(druh.getPocet() - 1);
            superBloud.makeSuper();

            cas = svet.mapa.cestaVelblouda(superBloud,
                    svet.mapa.cesta(superBloud.getIndexSkladu(),
                            aktualni.getOp() + svet.sklady.length - 1, superBloud), aktualni);
            if (cas != -1) {
                zvladne = true;
                break;
            }
        }

        if (!zvladne) {
            return 1;
        } // pokud ani super bloud nezvladne cestu - nema cenu generovat


        while (pocetKosu > 0) {
            int celkovyPocetBloudu = Velbloud.getPocet();
            // generovani bloudu

            for (DruhVelblouda druh : svet.druhyVelbloudu) {
                // pokud pocet bloudu daneho druhu je mensi nebo roven poctu,
                // ktery odpovida z jeho procentualniho zastoupeni (ocekavaneho) nebo pocet bloudu je nula
                if (druh.getPocet() <= celkovyPocetBloudu * druh.getPd() || celkovyPocetBloudu == 0) {
                    velbloudFinalni = new Velbloud(druh, list.get(0), r); // vygeneruju blouda
                    svet.sklady[list.get(0)].getVelboudi().add(velbloudFinalni); // pridam do skladu

                    // vedu si udaj o bloudovi s maximalni vzdalenosti v danem sklade
                    if (svet.sklady[list.get(0)].getMaxVzdalenostBloud() == null) {
                        svet.sklady[list.get(0)].setMaxVzdalenostBloud(velbloudFinalni);
                    } else if (svet.sklady[list.get(0)].getMaxVzdalenostBloud().getD() < velbloudFinalni.getD()) {
                        svet.sklady[list.get(0)].setMaxVzdalenostBloud(velbloudFinalni);
                    }


                    cas = svet.mapa.cestaVelblouda(velbloudFinalni, svet.mapa.cesta(velbloudFinalni.getIndexSkladu(),
                            aktualni.getOp() + svet.sklady.length - 1, velbloudFinalni), aktualni);
                    // pokud cestu ujde
                    if (cas != -1) {
                        // nastavim jeho pocet kosu ktery ponese
                        if ((pocetKosu - velbloudFinalni.getDruh().getKd()) >= 0) {
                            velbloudFinalni.setKd(velbloudFinalni.getDruh().getKd());
                            pocetKosu -= velbloudFinalni.getKd();
                        } else {
                            velbloudFinalni.setKd(pocetKosu);
                            pocetKosu = 0;
                        }
                        finalniBloudi.add(velbloudFinalni); // pridam do seznamu bloudu ktere poslu na cestu
                    }
                    break; // pokud jsem vygeneroval, vyskocim z generovani a whilem checknu jestli uz
                    // jsem poslal dost velbloudu nebo jeste zbyvaji nejake kose
                }
            }
        }
        return 0;
    }

    /**
     * Metoda kontroluje zda pozadavek muze oblouzit jiz existujici velbloud
     *
     * @param svet             svet
     * @param aktualni         pozadavek, ktery musime olouzit
     * @param list             list indexu skladu, ktere jsou serazene od nejblizsich po nejvzdalenejsi od dane oazy z pozadvku
     * @param velbloudFinalni1 list vsech velbloudu, kteri budou dany pozadavek obsluhovat
     * @param finalniBloudi    list vsech velbloudu, kteri budou dany pozadavek obsluhovat
     * @param pocetKosu1       pocet kosu, ktere potrebujeme dorucit
     * @return 0 - pokud existujici velbloudi muzou dorucit vsechny kose, ktere potrebuje oaza
     * pocetKosu - pocet kosu, ktery jeste potrebujeme dorucit(tj dorucili jsme bud 0 nebo nejakou cast kosu, ktere potrebuje oaza)
     */
    private static int kontrolaExisBloudu(Svet svet, Pozadavek aktualni, List<Integer> list,
                                          Velbloud velbloudFinalni1, List<Velbloud> finalniBloudi,
                                          int pocetKosu1) {
        int pocetKosu = pocetKosu1;
        Velbloud velbloudFinalni = velbloudFinalni1;
        double cas;
        while (pocetKosu > 0) { // dokud zbyvaj kose k doruceni

            for (Integer indexSkladu : list) {
                // pokud sklad nema zadny bloudy preskakuju sklad
                if (svet.sklady[indexSkladu].getVelboudi() == null
                        || svet.sklady[indexSkladu].getVelboudi().isEmpty()) {
                    continue;
                }
                // vytvorim cestu takovou, jakou ujde bloud s maximalni vzdalenosti
                List<Integer> cesta = svet.mapa.cesta(indexSkladu, aktualni.getOp() + svet.sklady.length - 1, svet.sklady[indexSkladu].getMaxVzdalenostBloud());
                double pomocna = Double.POSITIVE_INFINITY;

                // projedu vsechny bloudy ve skladu
                for (Velbloud velbloud : svet.sklady[indexSkladu].getVelboudi()) {
                    // pokud uz jsem ho poslal, preskakuju ho
                    if (finalniBloudi.contains(velbloud)) {
                        continue;
                    }
                    // cas, za ktery ujde bloud danou cestu (pokud -1 tak neujde)
                    cas = svet.mapa.cestaVelblouda(velbloud, cesta, aktualni);

                    // pokud je na ceste a zaroven to neujde nebo pokud i po casu jeho navratu to nestihnu tak ho preskocim
                    if (velbloud.isNaCeste() && (cas == -1 || (velbloud.getCasNavratu() + cas) > (aktualni.getTz() + aktualni.getTp()))) {
                        continue;
                    }

                    // hledame velblouda, ktery to ujde za nejkratsi cas
                    if (cas < pomocna && cas != -1) {
                        pomocna = cas;
                        velbloudFinalni = velbloud;
                    }
                }
            }
            // pokud jsem zadneho optimalniho velblouda i presto nenasel tak breaknu
            if (velbloudFinalni == null || finalniBloudi.contains(velbloudFinalni)) {
                break;
            }
            // nastavim velbloudovi pocet kosu ktery ma nalozit - pokud po odecteni jsem furt na cisle vetsim
            // nebo rovno nule - musim ho nalozit maximalnim poctem
            if ((pocetKosu - velbloudFinalni.getDruh().getKd()) >= 0) {
                velbloudFinalni.setKd(velbloudFinalni.getDruh().getKd());
                pocetKosu -= velbloudFinalni.getKd();
            }
            // v opacnem pripade nalozim jenom potrebny pocet kosu aby nenesl zbytecne moc kosu
            else {
                velbloudFinalni.setKd(pocetKosu);
                pocetKosu = 0;
            }
            // pridam ho do seznamu velbloudu, ktere poslu na cestu
            finalniBloudi.add(velbloudFinalni);
        }
        return pocetKosu;
    }

    /**
     * Metoda ceka na obnoveni kosu ve skladech,
     * pokud se nestihnou obnovit vcas(tj nestihnem obslouzit pozadavek) koncime simulaci
     *
     * @param svet        svet
     * @param aktualni    pozadavek, ktery potrebujeme obslouzit
     * @param list        list indexu skladu, ktere jsou serazene od nejblizsich po nejvzdalenejsi od dane oazy z pozadvku
     * @param pomocnyList pomocny list, stejny jako list
     */
    private static void cekaniNaObnovuSkladu(Svet svet, Pozadavek aktualni, List<Integer> list,
                                             List<Integer> pomocnyList) {
        for (Integer indexSkladu : pomocnyList) {
            Sklad sklad = svet.sklady[indexSkladu];
            // TODO zde byla chyba
            if (sklad.getKs() == 0) {
                continue; // nema cenu obnovovat kose u skladu s poctem kosu 0
            }
            aktualni.setTz(sklad.getTs() * sklad.getNasobek()); // nastavim cas na dobu jeho obnoveni
            int nasobek = (int) (aktualni.getTz() / sklad.getTs() + 1); // nasobek zvysim
            if (nasobek == sklad.getNasobek()) {
                nasobek++;
            }
            // pokud doba obnoveni je moc pozde do deadlinu, preskakuju sklad
            if (aktualni.getTp() + aktualni.getTz() <= sklad.getTs() * nasobek) {
                continue;
            }
            list.add(indexSkladu); // pridam ho do seznamu nejblizsich skladu

            // statistika
            sklad.getInfo().push(new InfoSklad((int) (nasobek * sklad.getTs() + 0.5), sklad.getAktualniPocetKosu(), sklad.getKs()));

            aktualni.setTz(sklad.getTs() * nasobek);
            sklad.setNasobek(nasobek);
            sklad.setAktualniPocetKosu(sklad.getKs());
            break;
        }
        // pokud i presto je seznam prazdny, nenalezli jsme vhodny sklad
        if (list.isEmpty()) {
            System.out.printf("Cas: %d, Oaza: %d, Vsichni vymreli, Harpagon zkrachoval, Konec simulace\n",
                    (int) (aktualni.getTz() + 0.5), aktualni.getOp());
            System.out.println("Duvod: Nenasel se vhodny sklad");
            try {
                zapisovac.zapisVse();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.exit(1);
        }
    }


    /**
     * Metoda nacte data ze souboru
     *
     * @param fileName  nazev souboru
     * @return Svet     Obsahuje informace o aktualni simulaci (oazy, sklady, velbloudy...)
     */
    public static Svet reader(String fileName) {
        ArrayList<String> allUdaje1 = null;
        try {
            allUdaje1 = new ArrayList<>();

            List<String> list = Files.readAllLines(Paths.get(fileName)); // vsechny radky souboru
            cteniUdaju(allUdaje1, list);


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

    /**
     * Cteni udaju
     *
     * @param allUdaje1 list vybranych validinich udaju
     * @param list      vstupni string
     */
    private static void cteniUdaju(List<String> allUdaje1, List<String> list) {
        int bloudi = 0;
        for (String line : list) { // for each pres vsechny radky v seznamu

            int iBloud = line.indexOf("\uD83D\uDC2A"); // index blouda
            int iPoust = line.indexOf("\uD83C\uDFDC"); // index pouste
            if (bloudi != 0) {// bloudi>0 => jsem v blokovym komentari
                // pokud jsem v blok komentari a na radce neni ani bloud ani poust = preskakuju radku
                if (iPoust == -1 && iBloud == -1) {
                    continue;
                }
                while (iPoust != -1 && bloudi != 0) { // osekavam radku dokud tam jsou pouste a jsem v blok.kom.
                    bloudi--;
                    // pokud v casti kterou useknu jsou bloudi, prictu je do citace
                    bloudi += (int) line.substring(0, iPoust).chars().filter(ch -> ch == '\uD83D').count();
                    line = line.substring(iPoust + 2);
                    iPoust = line.indexOf("\uD83C\uDFDC");
                }
            }
            // TODO chyba - stasnejsi by bylo to presunout na konec ifu nad touto radkou
            iBloud = line.indexOf("\uD83D\uDC2A"); // pokud projel whilem, tak pro jistotu znova spoctu index znovu

            // pokud jsem v blok komentari a na radce neni ani bloud ani poust = preskakuju radku
            if (bloudi != 0 && iBloud == -1) {
                continue;
            }

            while (iBloud != -1) { // jedem dokud na radce jsou bloudi
                bloudi++; // nasel jsem blouda - zvednu citac
                iPoust = line.indexOf("\uD83C\uDFDC");
                if (iPoust != -1) {
                    bloudi--; // pokud jsem nasel poust odstranim jednoho blouda
                }

                // pokud cast kterou osekavam je ohranicena bloudem a pousti
                // spoctu kolik bloudu je v osekavany casti a prictu je do citace
                if (iBloud + 2 < iPoust) {
                    String dummy = line.substring(iBloud + 2, iPoust);
                    bloudi += (int) dummy.chars().filter(ch -> ch == '\uD83D').count();
                }
                // pokud cast kterou osekavam je ohranicena bloudem a blok.kom. pokracuje na dalsi radce
                // spoctu kolik bloudu je v osekavany casti a prictu je do citace
                else if (iPoust == -1) {
                    String dummy = line.substring(iBloud + 2);
                    bloudi += (int) dummy.chars().filter(ch -> ch == '\uD83D').count();
                }

                int pocetPousti = (int) line.chars().filter(ch -> ch == '\uD83C').count();
                int res = bloudi - pocetPousti;

                // pocetBloudi-pocetPousti > 0, tak po odstraneni komentare jsem stale v blok.kom.
                // oriznu radku do prvniho blouda
                if (res > 0) {
                    line = line.substring(0, iBloud);
                }
                // normalni pripad - vsechny komentare se vyrusi v jedne radce
                else {
                    line = line.substring(0, iBloud) + " " + line.substring(iPoust + 2);
                }

                // vnorene komentare
                int indexPousteNovyString = line.indexOf("\uD83C\uDFDC");
                if (indexPousteNovyString != -1) {
                    int indexBloudaNovyString = line.indexOf("\uD83D\uDC2A");
                    // na dane radce je vnoreny komentar
                    if (indexBloudaNovyString < indexPousteNovyString && indexBloudaNovyString != -1) {
                        iBloud = indexBloudaNovyString;
                    }
                    // pokud je zde pouze poust - odectu z citace bloudu
                    else {
                        bloudi--;
                    }
                }
                else {
                    iBloud = line.indexOf("\uD83D\uDC2A");
                }
            }

            if (!line.isBlank()) { // v line je(jsou) validni udaj(e)
                String str = line.replaceAll("[\\s|\\u00A0]+", " "); // nahradi vsechny whitespaces jednou mezerou
                String[] split = str.split(" "); // nasoupe hodnoty do pole

                for (String s : split) {
                    if (!s.isBlank()) {
                        allUdaje1.add(s);
                    }
                }
            }
        }
    }

    /**
     * Vrati pojmenovanou cestu na zaklade indexu
     *
     * @param cesta cesta s indexy
     * @param svet  svet
     * @return pojmenovana cesta (napr.'Oaza_1' atd.)
     */
    private static List<String> vratPojmenovanouCestu(List<Integer> cesta, Svet svet) {
        List<String> pojmCesta = new ArrayList<>();
        for (Integer i : cesta) {
            if (i < svet.sklady.length) {
                pojmCesta.add("Sklad_" + i);
            } else {
                pojmCesta.add("Oaza_" + (i - svet.sklady.length + 1));
            }
        }
        return pojmCesta;
    }
}


