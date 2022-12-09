import java.util.Random;
import java.util.Stack;

/**
 * Trida reprezentujici velblouda
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Velbloud {
    /** jmeno velblouda */
    private final String jmeno;
    /** druh velblouda */
    private final DruhVelblouda druh;
    /** rychlost */
    private double v;
    /** vzdalenost na jedno napiti */
    private double d;
    /** doba napiti */
    private final double td;
    /** index domovskeho skladu */
    private final  int indexSkladu;
    /**
     * na zacatku ma velbloud energii stejnou jako vzdalenost na jedno napiti,
     * pak ji prubezne modifikujeme podle vzdalenosti kterou urazil
     */
    private double energie;
    /** booleovska promena indikujici zda je ci neni velbloud na ceste */
    private boolean naCeste;
    /** cas navratu velblouda do domovskeho skladu po vykonani pozadavku */
    private double casNavratu;
    /** celkovy pocet velbloudu (pocitame dohromady i velbloudy ruznych druhu) */
    private static int pocet = 0;
    /** poradi velblouda */
    private final int poradi = ++pocet;
    /** generator nahodnych cisel */
    Random r;
    /** aktualni zatizeni velblouda(tj kolik ma na sobe nalozeno kosu, nemusi byt vzdy rovno max hodnote) */
    private int kd;
    /** info velblouda */
    private Stack<Trasa> info = new Stack<>();

    /**
     * Konstruktor vytvori novou instanci velblouda
     * nastavi druh a index skladu
     * dale vygeneruje podle druhu jeho rychlost a vzdalenost kterou ujd na jedno napiti,
     * vygeneruje jeho jmeno a zvysi pocet velboudu daneho druhu a zaroven celkovy pocet velbloudu
     * @param druh druh velblouda
     * @param indexSkladu index skladu kde byl velbloud vytvoreny
     * @param r generator nahodnych cisel
     */
    public Velbloud(DruhVelblouda druh, int indexSkladu, Random r) {
        this.druh = druh;
        this.r = r;
        this.v = generateV(); // vygenerujeme rychlost
        this.d = generateD(); // vygenerujeme vzdalenost na jedno napiti
        this.td = druh.getTd();
        druh.pocet++;
        this.jmeno = generateName();
        this.indexSkladu = indexSkladu;
        this.energie = d;
    }

    /**
     * Metoda vygeneruje rychlost velblouda v rozsahu min a max hodnoty jeho druhu
     * @return vygenerovana rychlost
     */
    public final double generateV() {
        if (druh.getV_min() == druh.getV_max()) {
            return druh.getV_min();
        }
        return r.nextDouble(druh.getV_max() + Double.MIN_VALUE - druh.getV_min()) + druh.getV_min();
    }

    /**
     * Metoda vygeneruje vzdalenost velblouda na jedno napiti v rozsahu min a max hodnoty jeho druhu
     * @return vygenerovana vzdalenost na jedno napiti
     */
    public final double generateD() {
        return r.nextGaussian((druh.getD_min() + druh.getD_max())/2.0,(druh.getD_max() - druh.getD_min())/4.0);
    }

    /**
     * Metoda vygeneruej jmeno velblouda
     * jmeno je ve formatu Velbloud_(poradi daneho velblouda)
     * @return nazev velblouda
     */
    public final String generateName() {
        return "Velboud_" + pocet;
    }

    /**
     * Vrati celkovy pocet velbloudu
     * @return pocet velbloudu
     */
    public static int getPocet() {
        return pocet;
    }

    /**
     * Vrati jmeno velblouda
     * @return jmeno velblouda
     */
    public String getJmeno() {
        return jmeno;
    }

    /**
     * Vrati druh velblouda
     * @return druh velblouda
     */
    public DruhVelblouda getDruh() {
        return druh;
    }

    /**
     * Vrati rychlost velblouda
     * @return rychlost velblouda
     */
    public double getV() {
        return v;
    }

    /**
     * Metoda zjisti jestli je velbloud na ceste
     * @return true - je na ceste, false - neni na ceste(je ve svem domovskym skladu)
     */
    public boolean isNaCeste() {
        return naCeste;
    }

    /**
     * Vrati poradi velblouda
     * @return poradi velblouda
     */
    public int getPoradi() {
        return poradi;
    }

    /**
     * Nastavi booleovskou promennou indikujici zda je velbloud na ceste nebo ne
     * @param naCeste bud true - je na ceste, nebo false - neni na ceste
     */
    public void setNaCeste(boolean naCeste) {
        this.naCeste = naCeste;
    }

    /**
     * Vrati vzdalenost velblouda kterou ujde na jedno napiti
     * @return vzdalenost na jedno napiti
     */
    public double getD() {
        return d;
    }

    /**
     * Vrati cas na piti
     * @return cas na piti
     */
    public double getTd() {
        return td;
    }

    /**
     * Vrati aktualni zatizeni velblouda (tj kolik ma na sobe nalozeno kosu, nemusi byt max pocet kosu ktery unese)
     * @return aktualni zatizeni velblouda
     */
    public int getKd() {
        return kd;
    }

    /**
     * Vrati index domovskeho skladu
     * @return index domovskeho skladu
     */
    public int getIndexSkladu() {
        return indexSkladu;
    }

    /**
     * Nastavi energii velblouda na danou hodnotu
     * nastavime v pripade kdy velbloud usel nejakou cestu, to znamena ze jeho energie se zmensila
     * @param energie pozadovana hodnota energie
     */
    public void setEnergie(double energie) {
        this.energie = energie;
    }

    /**
     * Vrati aktualni energii velblouda
     * @return aktualni energie velblouda
     */
    public double getEnergie() {
        return energie;
    }

    /**
     * Nastavi atribut kdy se velbloud vrati do sveho skladu na danou hodnotu
     * @param casNavratu cas kdy se velbloud vrati do skladu
     */
    public void setCasNavratu(double casNavratu) {
        this.casNavratu = casNavratu;
    }

    /**
     * Vrati cas navratu velblouda do skladu
     * @return cas navratu velblouda do skladu
     */
    public double getCasNavratu() {
        return casNavratu;
    }

    /**
     * Nastavi aktualni zatizeni velblouda (tj pocet kosu ktery byl na nej nalozen)
     * @param kd pocet kosu nalozeny na velblouda
     */
    public void setKd(int kd) {
        this.kd = kd;
    }

    /**
     * Udela z velblouda tzv superVelblouda,
     * tj nastavi jeho rychlost a vzdalenost kterou ujde na jedno napiti na maximalni mozne hodnoty
     */
    public void makeSuper(){
        this.v = druh.getV_max();
        this.d = druh.getD_max();
    }

    public Stack<Trasa> getInfo() {
        return info;
    }

}
