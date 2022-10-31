public class Pozadavek {
    private double tz; // cas prichodu pozadavku
    private int op; // index oazy do ktere ma byt dorucen
    private int kp; // mnozstvi kosu ktere oaza potrebuje
    private double tp; // udávající, za jak dlouho po příchodu požadavku musí být koše doručeny
    private static int pocet = 0; // pocet instanci
    private int poradi; // poradi instance


    public Pozadavek(double tz, int op, int kp, double tp) {
        this.tz = tz;
        this.op = op;
        this.kp = kp;
        this.tp = tp;
        pocet += 1;
        poradi = pocet;
    }

    public double getTz() {
        return tz;
    }

    public int getOp() {
        return op;
    }

    public int getKp() {
        return kp;
    }

    public double getTp() {
        return tp;
    }

    public static int getPocet() {
        return pocet;
    }

    public int getPoradi() {
        return poradi;
    }

    public void setTz(double tz) {
        this.tz = tz;
    }
}
