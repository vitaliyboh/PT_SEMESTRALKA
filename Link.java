/**
 * Trida reprezentujici jeden spojovy prvek
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Link {
    /** index sousedniho vrcholu */
    int neighbour;
    /** odkaz na dalsi prvek */
    Link next;
    /** vzdalenost k vrcholu */
    double edgeValue;

    /**
     * Konstruktor vytvori instanci spojoveho prvku
     * @param neighbour index sousedniho vrcholu
     * @param next odkaz na dalsi prvek
     * @param edgeValue vzdalenost k vrcholu
     */
    public Link(int neighbour, Link next, double edgeValue) {
        this.neighbour = neighbour;
        this.next = next;
        this.edgeValue = edgeValue;
    }
}
