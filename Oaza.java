import java.util.Stack;

/**
 * Trida reprezentujici oazu
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Oaza {
    /** pozice oazy */
    private final Pozice pozice;
    /** info oazy */
    private final Stack<InfoOazy> info = new Stack<>();

    /**
     * Konstruktor vytvori novou instanci oazy
     *
     * @param pozice pozice oazy
     */
    public Oaza(Pozice pozice) {
        this.pozice = pozice;
    }

    /**
     * Vrati pozici oazy
     *
     * @return pozice oazy
     */
    public Pozice getPozice() {
        return pozice;
    }

    /**
     * Vrati instanci infa o oaze
     *
     * @return info pro statistiku
     */
    public Stack<InfoOazy> getInfo() {
        return info;
    }

    @Override
    public String toString() {
        StringBuilder vypis = new StringBuilder();
        if (info.isEmpty()) {
            vypis.append("-oaza nemela zadny pozadavek-");
        } else {
            while (!info.empty()) {
                vypis.append(info.pop().toString());
            }
        }
        vypis.append("\n");
        return vypis.toString();
    }
}
