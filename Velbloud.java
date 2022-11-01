import java.util.Random;

public class Velbloud {
    private String jmeno; //jmeno velblouda
    private DruhVelblouda druh; // druh velblouda
    private double v; // rychlost
    private double d; // vzdalenost na jedno napiti
    private double td; // doba napiti
    private double kd; // maximalni pocet kosu, ktere bloud unese
    private  int indexSkladu; // index domovskeho skladu
    private double energie;  // na zacatku ma velbloud energii stejnou jako vzdalenost na jedno napiti,
                             // pak ji prubezne modifikujeme podle vzdalenosti kterou urazil
    private boolean naCeste;  // booleovska promena indikujici zda je ci neni velbloud na ceste
    private double casNavratu; // cas navratu velblouda do domovskeho skladu po vykonani pozadavku
    private static int pocet = 0;
    private int poradi; //  poradi velblouda
    Random r;


    public Velbloud(DruhVelblouda druh, int indexSkladu, Random r) {
        this.druh = druh;
        this.r = r;
        this.v = generateV(); // vygenerujeme rychlost
        this.d = generateD(); // vygenerujeme vzdalenost na jedno napiti
        this.td = druh.getTd();
        this.kd = druh.getKd();
        pocet++;
        druh.pocet++;
        poradi = pocet;
        this.jmeno = generateName();
        this.indexSkladu = indexSkladu;
        this.energie = d;
    }

    public double generateV() {
        if (druh.getV_min() == druh.getV_max()) {
            return druh.getV_min();
        }
        return r.nextDouble(druh.getV_max() + Double.MIN_VALUE - druh.getV_min()) + druh.getV_min();
    }

    public double generateD() {
        return r.nextGaussian((druh.getD_min() + druh.getD_max())/2.0,(druh.getD_max() - druh.getD_min())/4.0);
    }

    public String generateName() {
        return "Velboud_" + pocet;
    }

    public static int getPocet() {
        return pocet;
    }

    public String getJmeno() {
        return jmeno;
    }

    public DruhVelblouda getDruh() {
        return druh;
    }

    public double getV() {
        return v;
    }
    public boolean isNaCeste() {
        return naCeste;
    }
    public int getPoradi() {
        return poradi;
    }

    public void setNaCeste(boolean naCeste) {
        this.naCeste = naCeste;
    }

    public double getD() {
        return d;
    }

    public double getTd() {
        return td;
    }

    public double getKd() {
        return kd;
    }

    public int getIndexSkladu() {
        return indexSkladu;
    }

    public Random getR() {
        return r;
    }

    public void setEnergie(double energie) {
        this.energie = energie;
    }

    public double getEnergie() {
        return energie;
    }

    public void setCasNavratu(double casNavratu) {
        this.casNavratu = casNavratu;
    }

    public double getCasNavratu() {
        return casNavratu;
    }
}
