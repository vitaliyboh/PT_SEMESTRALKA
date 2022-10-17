public class Link {
    int neighbour;
    Link next;
    double edgeValue;

    public Link(int neighbour, Link next, double edgeValue) {
        this.neighbour = neighbour;
        this.next = next;
        this.edgeValue = edgeValue;
    }
}
