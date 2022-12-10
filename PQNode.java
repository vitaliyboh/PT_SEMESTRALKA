/**
 * Trida reprezentujici uzel do prioritni fronty
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class PQNode {
    /** index vrcholu */
    int index;
    /** priorita */
    double vzdalenost;

    /**
     * Konstruktor vytvori novou instanci
     *
     * @param index      index vrcholu
     * @param vzdalenost priorita
     */
    public PQNode(int index, double vzdalenost) {
        this.index = index;
        this.vzdalenost = vzdalenost;
    }
}
