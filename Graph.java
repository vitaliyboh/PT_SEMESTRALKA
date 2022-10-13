public class Graph {
    Link[] edges;

    public Graph(int delka) {
        edges = new Link[delka];
    }

    public void addEdge(int i, int j) {
        Link link = new Link(i,edges[j]);
        edges[j] = link;
        link = new Link(j, edges[i]);
        edges[i] = link;
    }

}
