/**
 * Trida reprezentujici pozadavek
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Pozadavek {
    /** cas prichodu pozadavku */
    private double tz;
    /** index oazy do ktere ma byt dorucen */
    private final int op;
    /** mnozstvi kosu ktere oaza potrebuje */
    private final int kp;
    /** cas udávající, za jak dlouho po příchodu požadavku musí být koše doručeny */
    private final double tp;
    /** pocet instanci */
    private static int pocet = 0;
    /** poradi instance */
    private final int poradi;

    /**
     * Konstruktor vytvori instanci pozadavku
     *
     * @param tz cas prichodu pozadavku
     * @param op index oazy
     * @param kp pocet kosu
     * @param tp cas, za jak dlouho po prichodu pozadavku musi byt kose doruceny
     */
    public Pozadavek(double tz, int op, int kp, double tp) {
        this.tz = tz;
        this.op = op;
        this.kp = kp;
        this.tp = tp;
        pocet += 1;
        poradi = pocet;
    }

    /**
     * Vrati cas prichodu pozadavku
     *
     * @return cas prichodu pozadavku
     */
    public double getTz() {
        return tz;
    }

    /**
     * Vrati index oazy
     *
     * @return index oazy
     */
    public int getOp() {
        return op;
    }

    /**
     * Vrati pocet kosu
     *
     * @return pocet kosu
     */
    public int getKp() {
        return kp;
    }

    /**
     * Vrati cas, za jak dlouho od prichodu pozadavku musi byt kose doruceny
     *
     * @return cas, za jak dlouho od prichodu pozadavku musi byt kose doruceny
     */
    public double getTp() {
        return tp;
    }

    /**
     * Vrati poradi pozadavku
     *
     * @return poradi pozadavku
     */
    public int getPoradi() {
        return poradi;
    }

    /**
     * Nastavi cas prichodu pozadavku
     *
     * @param tz cas prichodu pozadavku
     */
    public void setTz(double tz) {
        this.tz = tz;
    }
}
