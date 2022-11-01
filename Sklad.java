import java.util.ArrayList;

public class Sklad{
    private Pozice pozice;
    private int ks; // pocet kosu
    private int ts; // uplynuta doba na doplneni
    private int tn; // doba nalozeni/vylozeni kose
    private ArrayList<Velbloud> velboudi; // list velbloudu, ktere maji tento sklad jako domovsky

    private int aktualniPocetKosu;

    private int nasobek;

    public Sklad(Pozice pozice, int ks, int ts, int tn) {
        this.pozice = pozice;
        this.ks = ks;
        this.ts = ts;
        this.tn = tn;
        velboudi = new ArrayList<Velbloud>();
        aktualniPocetKosu = this.ks;
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

    public int getNasobek() {
        return nasobek;
    }

    public void setNasobek(int nasobek) {
        this.nasobek = nasobek;
    }

    public void setAktualniPocetKosu(int aktualniPocetKosu) {
        this.aktualniPocetKosu = aktualniPocetKosu;
    }

    public int getAktualniPocetKosu() {
        return aktualniPocetKosu;
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

    public ArrayList<Velbloud> getVelboudi() {
        return velboudi;
    }
}
