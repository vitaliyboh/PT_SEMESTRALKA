import java.util.ArrayList;
import java.util.Random;

public class Velbloud {
    private String jmeno;
    private DruhVelblouda druh; // druh velblouda
    private double v; // rychlost
    private double d; // vzdalenost na jedno napiti
    private double td; // doba napiti
    private int kd; // maximalni pocet kosu, ktere bloud unese
    private  int indexSkladu;
    private double energie;
    private boolean naCeste;

    Random r = new Random();


    public Velbloud(DruhVelblouda druh, double v, double d, double td, int kd, int indexSkladu) {
        this.druh = druh;
        this.v = generateV();
        this.d = generateD();
        this.td = td;
        this.kd = kd;
        druh.pocet++;
        this.jmeno = generateName();
        this.indexSkladu = indexSkladu;
        this.energie = d;
    }

    public double generateV() {
        return r.nextDouble(druh.getV_max() + Double.MIN_VALUE - druh.getV_min()) + druh.getV_min();
    }

    public double generateD() {
        return r.nextGaussian((druh.getD_min() + druh.getD_max())/2.0,(druh.getD_max() - druh.getD_min())/4.0);
    }

    public String generateName() {
        return "Velboud_" + druh.pocet;
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

    public void setNaCeste(boolean naCeste) {
        this.naCeste = naCeste;
    }

    public double getD() {
        return d;
    }

    public double getTd() {
        return td;
    }

    public int getKd() {
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
}
