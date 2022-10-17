public class Pozadavek {
    private int tz; // cas prichodu pozadavku
    private int op; // index oazy do ktere ma byt dorucen
    private int kp; // mnozstvi kosu ktere oaza potrebuje
    private int tp; // udávající, za jak dlouho po příchodu požadavku musí být koše doručeny

    public Pozadavek(int tz, int op, int kp, int tp) {
        this.tz = tz;
        this.op = op;
        this.kp = kp;
        this.tp = tp;
    }
}
