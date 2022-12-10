/**
 * Trida reprezentujici druh velblouda
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class DruhVelblouda {
    /** jmeno druhu velblouda */
    private final String jmeno;
    /** minimalni rychlost */
    private final double v_min;
    /** maximalni rychlost */
    private final double v_max;
    /** minimalni vzdalenost na jedno napiti */
    private final double d_min;
    /** maximalni vzdalenost na jedno napiti */
    private final double d_max;
    /** doba na piti */
    private final double td;
    /** maximalni pocet kosu, ktere velbloud unese */
    private final int kd;
    /** pomerne zastoupeni druhu */
    private final double pd;
    /** pocet vytvorenych velbloudu daneho druhu */
    public int pocet;

    /**
     * Konstruktor vytvori instanci druhu velblouda
     *
     * @param jmeno jmeno druhu
     * @param v_min min rychlost
     * @param v_max max rychlost
     * @param d_min min vzdalenost na jedno napiti
     * @param d_max max vzdalenost na jedno napiti
     * @param td    doba na piti
     * @param kd    max pocet kosu, ktere velbloud unese
     * @param pd    pomerne zastoupeni druhu
     */
    public DruhVelblouda(String jmeno, double v_min, double v_max,
                         double d_min, double d_max, double td,
                         int kd, double pd) {
        this.jmeno = jmeno;
        this.v_min = v_min;
        this.v_max = v_max;
        this.d_min = d_min;
        this.d_max = d_max;
        this.td = td;
        this.kd = kd;
        this.pd = pd;
        this.pocet = 0;
    }

    /**
     * Vrati jmeno druhu
     *
     * @return jmeno druhu
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     * Vrati min rychlost
     *
     * @return min rychlost
     */
    public double getV_min() {
        return v_min;
    }

    /**
     * Vrati max rychlost
     *
     * @return max rychlost
     */
    public double getV_max() {
        return v_max;
    }

    /**
     * Vrati min vzdalenost
     *
     * @return min vzdalenost
     */
    public double getD_min() {
        return d_min;
    }

    /**
     * Vrati max vzdalenost
     *
     * @return max vzdalenost
     */
    public double getD_max() {
        return d_max;
    }

    /**
     * Vrati dobu na piti
     *
     * @return doba na piti
     */
    public double getTd() {
        return td;
    }

    /**
     * Vrati max pocet kosu, ktere unese
     *
     * @return max pocet kosu, ktere unese
     */
    public int getKd() {
        return kd;
    }

    /**
     * Vrati pomerne zastoupeni druhu
     *
     * @return pomerne zastoupeni druhu
     */
    public double getPd() {
        return pd;
    }

    /**
     * Vrati pocet vytvorenych velbloudu daneho druhu
     *
     * @return pocet vytvorenych velbloudu daneho druhu
     */
    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    /**
     * Metoda zajisti srozumitelny popis druhu velblouda
     *
     * @return retezcova reprezentace instance
     */
    @Override
    public String toString() {
        return "Druh velblouda: " + jmeno + ", pocet = " + pocet + ", pomer: " + pd;
    }
}
