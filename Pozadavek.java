public class Pozadavek {
    private int tz; // cas prichodu pozadavku
    private int op; // index oazy do ktere ma byt dorucen
    private int kp; // mnozstvi kosu ktere oaza potrebuje
    private int tp; // udávající, za jak dlouho po příchodu požadavku musí být koše doručeny
    private static int pocet = 0; // pocet instanci
    private int poradi; // poradi instance

    public int getTz() {
        return tz;
    }

    public int getOp() {
        return op;
    }

    public int getKp() {
        return kp;
    }

    public int getTp() {
        return tp;
    }

    public static int getPocet() {
        return pocet;
    }

    public int getPoradi() {
        return poradi;
    }

    public Pozadavek(int tz, int op, int kp, int tp) {
        this.tz = tz;
        this.op = op;
        this.kp = kp;
        this.tp = tp;
        pocet += 1;
        poradi = pocet;
    }
}
