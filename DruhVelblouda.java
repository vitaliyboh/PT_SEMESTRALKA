public class DruhVelblouda {
    private final String jmeno; // jmeno druhu velblouda
    private final double v_min; // minimalni rychlost
    private final double v_max; // maximalni rychlost
    private final double d_min; // minimalni vzdalenost na jedno napiti
    private final double d_max; // maximalni vzdalenost na jedno napiti
    private final double td; // doba na piti
    private final double kd; // maximalni pocet kosu, ktere velbloud unese
    private final double pd; // pomerne zastoupeni druhu
    public int pocet; // pocet vytvorenych velbloudu daneho druhu

    public DruhVelblouda(String jmeno, double v_min, double v_max,
                         double d_min, double d_max, double td,
                         double kd, double pd) {
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

    public String getJmeno() {
        return jmeno;
    }

    public double getV_min() {
        return v_min;
    }

    public double getV_max() {
        return v_max;
    }

    public double getD_min() {
        return d_min;
    }

    public double getD_max() {
        return d_max;
    }

    public double getTd() {
        return td;
    }

    public double getKd() {
        return kd;
    }

    public double getPd() {
        return pd;
    }

    public int getPocet() {
        return pocet;
    }
}
