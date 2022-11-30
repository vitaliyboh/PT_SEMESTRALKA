import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

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

    double Dijkstra(int s, int d) {
        int[] mark = new int[edges.length]; // pocet vrcholu!
        mark[s] = 1;
        double[] result = new double[edges.length];
        for (int i = 0; i < result.length; i++)
            result[i] = Double.POSITIVE_INFINITY;
        result[s] = 0;

        PriorityQueue<PQNode> q = new PriorityQueue<>(new Comparator<PQNode>() {
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
        return result[d];
    }

}
