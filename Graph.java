public class Graph {
    Link[] edges;
    Sklad[] sklady;
    Oaza[] oazy;

    public Graph(int delka) {
        edges = new Link[delka];
    }

    public void addEdge(int i, int j) {
        double value = 0;
        Link link = new Link(i,edges[j], value);
        edges[j] = link;
        link = new Link(j, edges[i], value);
        edges[i] = link;
    }

}
