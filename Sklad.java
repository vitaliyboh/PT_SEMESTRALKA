import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Trida reprezentujici skald
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Sklad{
    /** pozice skladu */
    private final Pozice pozice;
    /** pocet kosu na sklade */
    private final int ks;
    /** doba potrebna pro doplneni kosu */
    private final double ts;
    /** doba nalozeni/vylozeni 1 kose */
    private final double tn;
    /** list velbloudu, ktere maji tento sklad jako domovsky */
    private final List<Velbloud> velboudi;
    /** velboud z daneho skladu ktery ma maximalni vzdalenost kterou ujde na jedno napiti */
    Velbloud maxVzdalenostBloud;
    /** aktualni pocet kosu na sklade */
    private int aktualniPocetKosu;
    /** pocet, kolikrat se doplnoval sklad */
    private int nasobek;
    /** info skladu */
    private final Stack<InfoSklad> info = new Stack<>();

    /**
     * Konstruktor vytvori novou instanci skladu
     *
     * @param pozice pozice skladu
     * @param ks     pocet kosu na sklade
     * @param ts     doba potrebna pro doblneni kosu
     * @param tn     doba nalozeni/vylozeni 1 kose
     */
    public Sklad(Pozice pozice, int ks, double ts, double tn) {
        this.pozice = pozice;
        this.ks = ks;
        this.ts = ts;
        this.tn = tn;
        velboudi = new ArrayList<>();
        aktualniPocetKosu = this.ks;
    }

    /**
     * Vrati pozici skladu
     *
     * @return pozice skladu
     */
    public Pozice getPozice() {
        return pozice;
    }

    /**
     * Vrati pocet kosu na sklade
     *
     * @return pocet kosu na sklade
     */
    public int getKs() {
        return ks;
    }

    /**
     * Vrati pocet, kolikrat se doplnoval sklad
     *
     * @return pocet, kolikrat se doplnoval sklad
     */
    public int getNasobek() {
        return nasobek;
    }

    /**
     * Nastavi pocet, kolikrat se doplnoval skald
     *
     * @param nasobek dany pocet
     */
    public void setNasobek(int nasobek) {
        this.nasobek = nasobek;
    }

    /**
     * Nastavi aktualni pocet kosu na sklade
     *
     * @param aktualniPocetKosu pocet kosu na sklade
     */
    public void setAktualniPocetKosu(int aktualniPocetKosu) {
        this.aktualniPocetKosu = aktualniPocetKosu;
    }

    /**
     * Vrati aktualni pocet kosu na sklade
     *
     * @return aktualni pocet kosu na sklade
     */
    public int getAktualniPocetKosu() {
        return aktualniPocetKosu;
    }

    /**
     * Vrati dobu potrebnu na doplneni skladu
     *
     * @return doba potrebna na doplneni skladu
     */
    public double getTs() {
        return ts;
    }

    /**
     * Vrati dobu nalozeni/vylozeni 1 kose
     *
     * @return doba nalozeni/vylozeni 1 kose
     */
    public double getTn() {
        return tn;
    }

    /**
     * Vrati list vsehc velbloudu, kteri patri tomuto skladu
     *
     * @return list velbloudu, kteri patri tomuto skladu
     */
    public List<Velbloud> getVelboudi() {
        return velboudi;
    }

    /**
     * Nastavi velblouda, ktery ma maximalni vzdalenost kterou ujde na jedno napiti
     *
     * @param maxVzdalenostBloud velbloud, ktery ma maximalni vzdalenost kterou ujde na jedno napiti
     */
    public void setMaxVzdalenostBloud(Velbloud maxVzdalenostBloud) {
        this.maxVzdalenostBloud = maxVzdalenostBloud;
    }

    /**
     * Vrati velblouda, ktery ma maximalni vzdalenost kterou ujde na jedno napiti
     *
     * @return velbloud, ktery ma maximalni vzdalenost kterou ujde na jedno napiti
     */
    public Velbloud getMaxVzdalenostBloud() {
        return maxVzdalenostBloud;
    }

    public Stack<InfoSklad> getInfo() {
        return info;
    }

    @Override
    public String toString() {
        StringBuilder vypis = new StringBuilder();
        if (info.isEmpty()) {
            vypis.append("-nedoslo k zadnemu doplneni skladu-");
        } else {
            while (!info.empty()) {
                vypis.append(info.pop().toString());
            }
        }
        vypis.append("\n");
        return vypis.toString();
    }
}
