import java.util.*;

/**
 * Trida predstavuje graf reprezentovany seznamem sousedu
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Graph {
    /** pole seznamu sousedu pro kazdy vrchol */
    Link[] edges;
    /** pole skladu */
    Sklad[] sklady;
    /** pole oaz */
    Oaza[] oazy;

    /**
     * Konstruktor vytvori instanci grafu
     *
     * @param delka  pocet vsech vrcholu
     * @param sklady pole skladu
     * @param oazy   pole oaz
     */
    public Graph(int delka, Sklad[] sklady, Oaza[] oazy) {
        this.sklady = sklady;
        this.oazy = oazy;
        edges = new Link[delka];
    }

    /**
     * Metoda prida hranu z vrcholu i do vrcholu j (a obracene, mame neorientovany graf)
     *
     * @param i index vrcholu
     * @param j index sousedniho vrcholu
     */
    public void addEdge(int i, int j) {
        double value;
        if (j >= sklady.length && i >= sklady.length) {
            value = oazy[i - sklady.length + 1].getPozice().getDistance(oazy[j - sklady.length + 1].getPozice());
        } else if (j >= sklady.length) {
            value = sklady[i].getPozice().getDistance(oazy[j - sklady.length + 1].getPozice());
        } else if (i >= sklady.length) {
            value = oazy[i - sklady.length + 1].getPozice().getDistance(sklady[j].getPozice());
        } else {
            value = sklady[i].getPozice().getDistance(sklady[j].getPozice());
        }
        Link link = new Link(i, edges[j], value);
        edges[j] = link;
        link = new Link(j, edges[i], value);
        edges[i] = link;
    }

    /**
     * Metoda najde nejkratsi cestu kterou ujde dany velbloud z vrcholu s do vrchlo d
     *
     * @param s        pocatecni vrchol
     * @param d        koncovy vrchol
     * @param velbloud velbloud pro kteryho hledame nejkratsi cestu
     * @return seznam vsech vrcholu pres ktere vede cesta, nebo null pokud jsme nenasli cestu kterou ujde dany velbloud
     */
    List<Integer> cesta(int s, int d, Velbloud velbloud) {
        int[] arr = new int[edges.length];
        List<Integer> cesta = new ArrayList<>();
        if (s == d) { // pokud je to cesta z a do a, cesta bude pres a
            cesta.add(s);
            return cesta;
        }
        Stack<Integer> stack = new Stack<>(); // stack pro potreby zjisteni cesty

        int[] mark = new int[edges.length]; // pocet vrcholu
        mark[s] = 1;
        double[] result = new double[edges.length];

        // naplnim pole vzdalenosti z S nekonecny
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.POSITIVE_INFINITY;
        }

        result[s] = 0; // z s do s je to za cenu 0

        PriorityQueue<PQNode> q = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(PQNode o1, PQNode o2) {
                return Double.compare(o1.vzdalenost, o2.vzdalenost);
            }
        }); // prioritni fronta

        q.add(new PQNode(s, 0)); // pridam prvek na haldu s prioritou 0

        while (q.peek() != null && q.peek().vzdalenost >= 0) { // dokud je v halde neco a pokud je vzdalenost >=0
            int v = q.peek().index; // index vrcholu
            q.remove(); // odstranim z haldy
            Link nbLink = edges[v]; // vemu jeho sousedy
            while (nbLink != null) {
                int n = nbLink.neighbour; // index souseda
                if (mark[n] != 2) { // pokud neni oznacen jako definitivni
                    double newDistance = result[v] + nbLink.edgeValue; // nova vzdalenost
                    PQNode node = new PQNode(n, newDistance); // vytvorim node do haldy
                    if (mark[n] == 0) { // pokud jsem zde jeste nebyl, oznacim na 1
                        mark[n] = 1;
                        if (nbLink.edgeValue <= velbloud.getD()) { // pokud jsem zde nebyl a zaroven to bloud ujde
                            arr[n] = v;
                            result[n] = newDistance;
                            q.add(node);
                        }
                    }
                    // pokud nova vzdalenost je mensi nez aktualni a zaroven to velbloud ujde
                    else if (newDistance < result[n] && velbloud.getD() >= nbLink.edgeValue) {
                        result[n] = newDistance;
                        arr[n] = v;
                        q.remove(node);
                        node.vzdalenost = newDistance;
                        q.add(node);
                    }
                }
                nbLink = nbLink.next; // jdu na dalsiho souseda
            }
            mark[v] = 2; // trvale oznaceni
        }
        if (Double.isInfinite(result[d])) { // pokud neexistuje vhodna cesta metodu koncim vracenim null
            return cesta;
        }

        // algoritmus pro hledani cesty
        int pomocna = d;
        stack.add(pomocna);
        while (arr[pomocna] != s) {
            stack.add(arr[pomocna]);
            pomocna = arr[pomocna];
        }

        while (!stack.isEmpty()) {
            cesta.add(stack.pop());
        }
        return cesta;
    }

    /**
     * Metoda najde a seradi vsechny sklady od nejblizsich po nejvzdalenejsi od vrcholu s
     *
     * @param s index oazy pro kterou hledame nejblizsi sklady
     * @return seznam indexu vsech skladu od nejblizsich po nejvzdalenejsi
     */
    List<Integer> nejblizsiVrcholy(int s) {

        ArrayList<Integer> vrcholy = new ArrayList<>();
        int[] mark = new int[edges.length]; // pocet vrcholu
        mark[s] = 1;
        double[] result = new double[edges.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.POSITIVE_INFINITY;
        }
        result[s] = 0;

        PriorityQueue<PQNode> q = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(PQNode o1, PQNode o2) {
                return Double.compare(o1.vzdalenost, o2.vzdalenost);
            }
        });

        q.add(new PQNode(s, 0));

        while (q.peek() != null && q.peek().vzdalenost >= 0) {
            int v = q.peek().index;
            q.remove();
            Link nbLink = edges[v];
            while (nbLink != null) {
                int n = nbLink.neighbour;
                if (mark[n] != 2) {
                    double newDistance = result[v] + nbLink.edgeValue;
                    PQNode node = new PQNode(n, newDistance);
                    if (mark[n] == 0) {
                        mark[n] = 1;
                        result[n] = newDistance;
                        q.add(node);
                    } else if (newDistance < result[n]) {
                        result[n] = newDistance;
                        q.remove(node);
                        node.vzdalenost = newDistance;
                        q.add(node);
                    }
                }
                nbLink = nbLink.next;
            }
            mark[v] = 2;
        }

        double pomocna1 = Double.MAX_VALUE;

        // do seznamu dam pouze sklady
        for (int i = 1; i < result.length; i++) {
            if (s == i) {
                continue;
            }
            double pomocna = result[i];
            if ((pomocna < pomocna1 && i <= sklady.length - 1)) {
                vrcholy.add(i);
            }
        }

        // serazeni vrcholu
        vrcholy.sort(new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (int) (result[o1] - result[o2]);
            }
        });

        return vrcholy;
    }

    /**
     * Metoda spocita cas, jak dlouho  bude trvat danemu velbloudovi obslouzit dany pozadavek
     *
     * @param velbloud  zkoumany velbloud
     * @param cesta     cesta kterou ma velbloud ujit
     * @param pozadavek pozadavek, ktery ma velbloud obslouzit
     * @return cas, jak dlouho bude trvat velbloudovi obslouzit dany pozadavek, nebo -1 pokud pozadavek nestihne obslouzit vcas
     */
    public double cestaVelblouda(Velbloud velbloud, List<Integer> cesta, Pozadavek pozadavek) {
        if (cesta.isEmpty()) {
            return -1;
        }

        int i = velbloud.getIndexSkladu(); // index velbloudovo skladu, zde zacinam
        double cas = 0;

        for (Integer j : cesta) { // for pres indexy cesty
            if (i == j) {
                continue;
            }
            Link n = edges[i]; // sousedi domovskeho skladu
            while (n.neighbour != j) { // najdu link pro index daneho mista v ceste
                n = n.next;
            }

            double vzdalenost = n.edgeValue;
            if (vzdalenost > velbloud.getD()) { // pokud je dana vzdalenost mensi nez co zvladne velbloud ujit,
                // vracim -1
                return -1;
            }
            cas += vzdalenost / velbloud.getV(); // pricitam do citace casu

            velbloud.setEnergie(velbloud.getEnergie() - vzdalenost); // uberu mu energii
            if (velbloud.getEnergie() <= 0) { // pokud ma malo energie, napije se
                cas += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            }
            i = j; // posuneme se na index dal
        }
        cas += 2 * velbloud.getKd() * sklady[velbloud.getIndexSkladu()].getTn(); // cestu zpatky pripoctu do citace
        velbloud.setEnergie(velbloud.getD()); // velbloud se vratil, doplnim mu energii
        // pokud to bloud nestihne do deadline, vracim -1, pokud ano tak dobu za kterou to usel
        return (cas + pozadavek.getTz()) > (pozadavek.getTp() + pozadavek.getTz()) ? -1 : cas;
    }

    /**
     * Metoda prubezne vypisuje cestu velblouda podle zadaneho formatu (tj pres ktere oazy/sklady prochazi, kde se musi napit atd)
     *
     * @param velbloud  velbloud, ktery obsluhuje pozadavek
     * @param cesta     cesta, kterou ma velbloud ujit
     * @param pozadavek pozadavek, ktery ma velbloud obslouzit
     * @throws InterruptedException vyhozeni vyjimky pri chybe uspani vlakna
     */
    public int vypisCestyVelblouda(Velbloud velbloud, List<Integer> cesta,
                                   Pozadavek pozadavek) throws InterruptedException {
        int result = 0;
        int i = velbloud.getIndexSkladu(); // zacinam v domovskem skladu
        velbloud.setNaCeste(true); // nastavim jeho stav
        // zaciname simulovat cestu - zaciname v Tz + doba nalozeni
        // a postupne po vypisech pricitame do tohohle casu cas cesty, dobu napiti atd
        double casAktualni = pozadavek.getTz() + velbloud.getKd() * sklady[i].getTn();
        double celkVzdalenost = 0; // pro ucely statistiky
        for (Integer j : cesta) {
            Link n = edges[i];
            while (n.neighbour != j) {
                n = n.next;
            }
            double vzdalenost = n.edgeValue;
            celkVzdalenost += vzdalenost;
            velbloud.setEnergie(velbloud.getEnergie() - vzdalenost);

            if (j == pozadavek.getOp() + sklady.length - 1) { // pokud jsem dorazil do oazy ktera zadala pozadavek
                int casDorazu = (int) (casAktualni + vzdalenost / velbloud.getV() + 0.5);
                int casVylozeni = (int) (casDorazu + sklady[velbloud.getIndexSkladu()].getTn() * velbloud.getKd() + 0.5);
                int casovaRezerva = (int) (pozadavek.getTp() - casVylozeni + 0.5);
                if (!Main.sonic) {
                    Thread.sleep(1000);
                }
                System.out.printf("Cas: %d, Velbloud: %d, Oaza: %d, Vylozeno kosu: %d, Vylozeno v: %d, Casova rezerva: %d\n",
                        casDorazu, velbloud.getPoradi(), pozadavek.getOp(), velbloud.getKd(), casVylozeni, casovaRezerva);
                result = cestaVelbloudaZpet(velbloud, cesta, pozadavek, casVylozeni);
                // pro ucely statistiky
                oazy[pozadavek.getOp()].getInfo().peek().getListVelbloudu().add(velbloud);
                if (oazy[pozadavek.getOp()].getInfo().peek().getCasDoruceni() < casVylozeni) {
                    oazy[pozadavek.getOp()].getInfo().peek().setCasDoruceni(casVylozeni);
                }
                velbloud.getInfo().peek().setCasDoruceni(casVylozeni);
                velbloud.addToCelkVzdalnost(celkVzdalenost);
                return result;
            }
            String misto;

            // pojmenuju dane misto podle indexu
            if (j > sklady.length - 1) {
                misto = "Oaza";
            } else {
                misto = "Sklad";
            }
            casAktualni += vzdalenost / velbloud.getV();

            if (!Main.sonic) {
                Thread.sleep(1000);
            }
            int indexOazy = j;
            if (misto.equals("Oaza")) {
                indexOazy = j - sklady.length + 1;
            }
            if (velbloud.getEnergie() <= 0) { // bloud se jde napit na danem miste
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Ziznivy %s, Pokracovani mozne v: %d\n",
                        (int) (casAktualni + 0.5), velbloud.getPoradi(),
                        misto, indexOazy, velbloud.getDruh().getJmeno(), (int) (casAktualni + velbloud.getTd() + 0.5));
                velbloud.getInfo().peek().getZastavky().add(misto + "_" + indexOazy);
                velbloud.getInfo().peek().getCasyZastavek().add((int) (casAktualni + 0.5));

                casAktualni += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            } else { // bloud prochazi mistem
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Kuk na velblouda\n",
                        (int) (casAktualni + 0.5), velbloud.getPoradi(), misto, indexOazy);
            }
            i = j;
        }
        return result;

    }

    /**
     * Metoda vypisuje cestu velblouda zpet v danem formatu
     *
     * @param velbloud     velbloud, ktery obsluhuje pozadavek
     * @param cesta        cesta, kterou ma velbloud ujit
     * @param pozadavek    pozadavek, ktery ma velbloud obslouzit
     * @param casAktualni1 cas kdy velbloud splnil pozadavek a muze jit zpatky
     * @throws InterruptedException vyhozeni vyjimky pri chybe uspani vlakna
     */
    public int cestaVelbloudaZpet(Velbloud velbloud, List<Integer> cesta,
                                  Pozadavek pozadavek, double casAktualni1) throws InterruptedException {
        int result = 0;
        double casAktualni = casAktualni1;
        cesta.add(0, velbloud.getIndexSkladu()); // pridam index domovskeho skladu blouda
        cesta.remove(cesta.size() - 1); // odstranim posledni prvek
        int i = pozadavek.getOp() + sklady.length - 1;

        for (int j = cesta.size() - 1; j >= 0; j--) { // jdu od konce cesty
            Link n = edges[i];
            while (n.neighbour != cesta.get(j)) {
                n = n.next;
            }
            double vzdalenost = n.edgeValue;
            int indexDo = cesta.get(j);
            if (indexDo == velbloud.getIndexSkladu()) { // dorazil jsem do skladu
                int cas = (int) (casAktualni + vzdalenost / velbloud.getV() + 0.5);
                if (!Main.sonic) {
                    Thread.sleep(1000);
                }
                System.out.printf("Cas: %d, Velbloud: %d, Navrat do skladu: %d\n", cas, velbloud.getPoradi(), velbloud.getIndexSkladu());
                velbloud.setCasNavratu(cas /*+ velbloud.getTd()*/);
                velbloud.getInfo().peek().setCasNavratu((int) (velbloud.getCasNavratu() + 0.5));
                velbloud.setEnergie(velbloud.getD());
                result = cas;
                return result;
            }
            String misto;

            if (indexDo > sklady.length - 1) {
                misto = "Oaza";
            } else {
                misto = "Sklad";
            }
            casAktualni += vzdalenost / velbloud.getV();

            if (!Main.sonic) {
                Thread.sleep(1000);
            }
            int indexOazy = indexDo;
            if (misto.equals("Oaza")) {
                indexOazy = indexDo - sklady.length + 1;
            }
            if (velbloud.getEnergie() <= 0) { // bloud se jde napit na danem miste
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Ziznivy %s, Pokracovani mozne v: %d\n",
                        (int) (casAktualni + 0.5), velbloud.getPoradi(),
                        misto, indexOazy, velbloud.getDruh().getJmeno(), (int) (casAktualni + velbloud.getTd() + 0.5));
                velbloud.getInfo().peek().getZastavky().add(misto + "_" + indexOazy);
                velbloud.getInfo().peek().getCasyZastavek().add((int) (casAktualni + 0.5));
                casAktualni += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            } else { // bloud prochazi mistem
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Kuk na velblouda\n",
                        (int) (casAktualni + 0.5), velbloud.getPoradi(), misto, indexOazy);
            }
            i = indexDo;

        }
        return result;
    }
}
