public class DruhVelblouda {
    private final String jmeno;
    private final double v_min;
    private final double v_max;
    private final double d_min;
    private final double d_max;
    private final double td;
    private final double kd;
    private final double pd;
    public int pocet;

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
