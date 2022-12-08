/**
 * Trida reprezentujici oazu
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Oaza {
    /** pozice oazy */
    private final Pozice pozice;

    /**
     * Konstruktor vytvori novou instanci oazy
     * @param pozice pozice oazy
     */
    public Oaza(Pozice pozice) {
        this.pozice = pozice;
    }

    /**
     * Vrati pozici oazy
     * @return pozice oazy
     */
    public Pozice getPozice() {
        return pozice;
    }
}
