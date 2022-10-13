public class Sklad{
    private Pozice pozice;
    private int ks; // pocet kosu
    private int ts; // uplynuta doba na doplneni
    private int tn; // doba nalozeni/vylozeni kose

    public Sklad(Pozice pozice, int ks, int ts, int tn) {
        this.pozice = pozice;
        this.ks = ks;
        this.ts = ts;
        this.tn = tn;
    }

    public Pozice getPozice() {
        return pozice;
    }


    public int getKs() {
        return ks;
    }

    public void setKs(int ks) {
        this.ks = ks;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public int getTn() {
        return tn;
    }

    public void setTn(int tn) {
        this.tn = tn;
    }

}
