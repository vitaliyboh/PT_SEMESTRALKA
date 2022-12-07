import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/*
    Trida predstavuje graf reprezentovany seznamem sousedu
 */
public class Graph {
    Link[] edges;
    Sklad[] sklady;
    Oaza[] oazy;


    public Graph(int delka, Sklad[] sklady, Oaza[] oazy) {
        this.sklady = sklady;
        this.oazy = oazy;
        edges = new Link[delka];
    }

    public void addEdge(int i, int j) {
        double value;
        if (j >= sklady.length && i >= sklady.length) {
            value = oazy[i - sklady.length+1].getPozice().getDistance(oazy[j - sklady.length+1].getPozice());
        }
        else if (j >= sklady.length) {
            value = sklady[i].getPozice().getDistance(oazy[j - sklady.length+1].getPozice());
        }
        else if (i >= sklady.length) {
            value = oazy[i - sklady.length+1].getPozice().getDistance(sklady[j].getPozice());
        }
        else {
            value = sklady[i].getPozice().getDistance(sklady[j].getPozice());
        }
        Link link = new Link(i,edges[j], value);
        edges[j] = link;
        link = new Link(j, edges[i], value);
        edges[i] = link;
    }

    /*
    double Dijkstra(int s, int d) {
        int[] mark = new int[edges.length]; // pocet vrcholu!
        mark[s] = 1;
        double[] result = new double[edges.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Double.POSITIVE_INFINITY;
        result[s] = 0;

        PriorityQueue<PQNode> q = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(PQNode o1, PQNode o2) {
                return Double.compare(o1.vzdalenost,o2.vzdalenost);
            }
        });

        q.add(new PQNode(s,0));

        while(q.peek() != null && q.peek().vzdalenost>=0) {
            int v = q.peek().index;
            if(v == d) return result[d];
            q.remove();
            Link nbLink = edges[v];
            while(nbLink!=null) {
                int n = nbLink.neighbour;
                if (mark[n] != 2) {
                    double newDistance = result[v] + nbLink.edgeValue;
                    PQNode node = new PQNode(n, newDistance);
                    if (mark[n] == 0) {
                        mark[n] = 1;
                        result[n] = newDistance;
                        q.add(node);
                    }
                    else if (newDistance<result[n]) {
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
        return result[d];
    }
    */

    ArrayList<Integer> cesta(int s, int d, Velbloud velbloud) {
        int[] arr = new int[edges.length];
        ArrayList<Integer> cesta = new ArrayList<>();
        if (s == d) {
            cesta.add(s);
            return cesta;
        }
        Stack<Integer> stack = new Stack<>();
        int[] mark = new int[edges.length]; // pocet vrcholu!
        mark[s] = 1;
        double[] result = new double[edges.length];

        for (int i = 0; i < result.length; i++) result[i] = Double.POSITIVE_INFINITY;

        result[s] = 0;


        PriorityQueue<PQNode> q = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(PQNode o1, PQNode o2) {
                return Double.compare(o1.vzdalenost, o2.vzdalenost);
            }
        });

        q.add(new PQNode(s, 0));

        while(q.peek() != null && q.peek().vzdalenost>=0) {
            int v = q.peek().index;
            q.remove();
            Link nbLink = edges[v];
            while(nbLink!=null) {
                int n = nbLink.neighbour;
                if (mark[n] != 2) {
                    double newDistance = result[v] + nbLink.edgeValue;
                    PQNode node = new PQNode(n, newDistance);
                    if (mark[n] == 0) {
                        mark[n] = 1;
                        arr[n] = v;
                        if (nbLink.edgeValue <= velbloud.getD()) {
                            result[n] = newDistance;
                            q.add(node);
                        }
                    }
                    else if (newDistance<result[n] && velbloud.getD() >= nbLink.edgeValue) {
                        result[n] = newDistance;
                        arr[n] = v;
                        q.remove(node);
                        node.vzdalenost = newDistance;
                        q.add(node);
                    }
                }
                nbLink = nbLink.next;
            }
            mark[v] = 2;
            //if(v == d) break;
        }
        if (Double.isInfinite(result[d])) return null;

        int pomocna = d;
        stack.add(pomocna);
        while(arr[pomocna] != s) {
            stack.add(arr[pomocna]);
            pomocna = arr[pomocna];
        }

        while (!stack.isEmpty()) {
            cesta.add(stack.pop());
        }

        return cesta;
    }


    ArrayList<Integer> nejblizsiVrcholy(int s) {


        ArrayList<Integer> vrcholy = new ArrayList<>();
        int[] mark = new int[edges.length]; // pocet vrcholu
        mark[s] = 1;
        double[] result = new double[edges.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Double.POSITIVE_INFINITY;
        result[s] = 0;

        PriorityQueue<PQNode> q = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(PQNode o1, PQNode o2) {
                return Double.compare(o1.vzdalenost,o2.vzdalenost);
            }
        });

        q.add(new PQNode(s,0));

        while(q.peek() != null && q.peek().vzdalenost>=0) {
            int v = q.peek().index;
            q.remove();
            Link nbLink = edges[v];
            while(nbLink!=null) {
                int n = nbLink.neighbour;
                if (mark[n] != 2) {
                    double newDistance = result[v] + nbLink.edgeValue;
                    PQNode node = new PQNode(n, newDistance);
                    if (mark[n] == 0) {
                        mark[n] = 1;
                        result[n] = newDistance;
                        q.add(node);
                    }
                    else if (newDistance<result[n]) {
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

        //edges[s].poleVzdalenoti = result;


        double pomocna1 = Double.MAX_VALUE;
        for (int i = 1; i < result.length; i++) {
            if (s == i) continue;
            double pomocna = result[i];
            if ((pomocna < pomocna1 && i <= sklady.length - 1)) {
                vrcholy.add(i);
            }
        }

        vrcholy.sort(new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return (int)(result[o1] - result[o2]);
            }
        });

        /* Puvodni bubblesort rucne napsany
        int n = vrcholy.size();
        int temp = 0;
        for(int i=0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (result[j - 1] > result[j]) {
                    //swap elements
                    temp = vrcholy.get(j - 1);
                    vrcholy.add(j - 1, vrcholy.get(j));
                    vrcholy.remove(j);
                    vrcholy.add(j, temp);
                    vrcholy.remove(j+1);
                }

            }
        }
        */




        
        return vrcholy;
    }



    public double cestaVelblouda (Velbloud velbloud, ArrayList<Integer> cesta, Pozadavek pozadavek) {
        if (cesta == null) return -1;
        int i = velbloud.getIndexSkladu();
        double cas = 0;

        for (Integer j: cesta) {
            if (i==j) continue;
            Link n = edges[i];
            while (n.neighbour != j) {
                n = n.next;
            }
            double vzdalenost = n.edgeValue;
            if (vzdalenost > velbloud.getD()){
                return -1;
            }
            cas += vzdalenost/velbloud.getV();

            // cas += velbloud.getV()*vzdalenost;
            velbloud.setEnergie(velbloud.getEnergie() - vzdalenost);
            if (velbloud.getEnergie() <= 0) {
                cas += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            }
            i = j;
        }
        cas += 2*velbloud.getKd()* sklady[velbloud.getIndexSkladu()].getTn();
        velbloud.setEnergie(velbloud.getD());
        return (cas + pozadavek.getTz()) > (pozadavek.getTp() + pozadavek.getTz()) ? -1 : cas;
    }

    public void vypisCestyVelblouda (Velbloud velbloud, ArrayList<Integer> cesta,
                                     Pozadavek pozadavek) throws InterruptedException {
        int i = velbloud.getIndexSkladu();
        velbloud.setNaCeste(true);
        double casAktualni = pozadavek.getTz() + velbloud.getKd()*sklady[i].getTn();// zaciname simulovat cestu - zaciname v Tz
        // a postupne po vypisech pricitame do tohohle casu cas cesty, dobu napiti atd

        for (Integer j: cesta) {
            Link n = edges[i];
            while (n.neighbour != j) {
                n = n.next;
            }
            double vzdalenost = n.edgeValue;
            velbloud.setEnergie(velbloud.getEnergie() - vzdalenost);

            if (j == pozadavek.getOp()+ sklady.length-1){
                int casDorazu = (int) (casAktualni + vzdalenost/velbloud.getV() + 0.5);
                int casVylozeni = (int) (casDorazu + sklady[velbloud.getIndexSkladu()].getTn() * velbloud.getKd() + 0.5);
                int casovaRezerva = (int) (pozadavek.getTp() - casVylozeni + 0.5);
                if (!Main.sonic) Thread.sleep(1000);
                System.out.printf("Cas: %d, Velbloud: %d, Oaza: %d, Vylozeno kosu: %d, Vylozeno v: %d, Casova rezerva: %d\n",
                        casDorazu, velbloud.getPoradi(), pozadavek.getOp(), velbloud.getKd(), casVylozeni, casovaRezerva);
                cestaVelbloudaZpet(velbloud,cesta, pozadavek, casVylozeni);
                return;
            }
            String misto = "";

            if (j > sklady.length - 1) misto = "Oaza";
            else misto = "Sklad";
            casAktualni += vzdalenost/velbloud.getV();

            if (!Main.sonic)Thread.sleep(1000);
            int indexOazy = j;
            if( misto.equals("Oaza")) indexOazy = j - sklady.length +1;
            if (velbloud.getEnergie() <= 0) { // bloud se jde napit na danem miste
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Ziznivy %s, Pokracovani mozne v: %d\n",
                        (int)(casAktualni +0.5), velbloud.getPoradi(),
                        misto, indexOazy, velbloud.getDruh().getJmeno(), (int)(casAktualni + velbloud.getTd()+0.5));
                casAktualni += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            }
            else { // bloud prochazi mistem
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Kuk na velblouda\n",
                        (int)(casAktualni+0.5), velbloud.getPoradi(), misto, indexOazy);
            }
            i = j;
        }
    }

    public void cestaVelbloudaZpet(Velbloud velbloud, ArrayList<Integer> cesta,
                                   Pozadavek pozadavek, double casAktualni) throws InterruptedException {
        cesta.add(0, velbloud.getIndexSkladu());
        cesta.remove(cesta.size()-1);
        int i = pozadavek.getOp()+ sklady.length - 1;

        for (int j = cesta.size()-1; j >= 0; j--) {
            Link n = edges[i];
            while (n.neighbour != cesta.get(j)) {
                n = n.next;
            }
            double vzdalenost = n.edgeValue;
            int indexDo = cesta.get(j);
            if (indexDo == velbloud.getIndexSkladu()) {
                int cas = (int) (casAktualni + vzdalenost/velbloud.getV() + 0.5);
                if (!Main.sonic)Thread.sleep(1000);
                System.out.printf("Cas: %d, Velbloud: %d, Navrat do skladu: %d\n", cas, velbloud.getPoradi(), velbloud.getIndexSkladu());
                velbloud.setCasNavratu(cas + velbloud.getTd());
                velbloud.setEnergie(velbloud.getD());
                return;
            }
            String misto = "";

            if (indexDo > sklady.length - 1) misto = "Oaza";
            else misto = "Sklad";
            casAktualni += vzdalenost/velbloud.getV();

            if (!Main.sonic)Thread.sleep(1000);
            int indexOazy = indexDo;
            if( misto.equals("Oaza")) indexOazy = indexDo - sklady.length +1;
            if (velbloud.getEnergie() <= 0) { // bloud se jde napit na danem miste
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Ziznivy %s, Pokracovani mozne v: %d\n",
                        (int)(casAktualni +0.5), velbloud.getPoradi(),
                        misto, indexOazy, velbloud.getDruh().getJmeno(), (int)(casAktualni + velbloud.getTd()+0.5));
                casAktualni += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            }
            else { // bloud prochazi mistem
                System.out.printf("Cas: %d, Velbloud: %d, %s: %d, Kuk na velblouda\n",
                        (int)(casAktualni+0.5), velbloud.getPoradi(), misto, indexOazy);
            }
            i = indexDo;

        }
    }
}
