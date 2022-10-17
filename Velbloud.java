import java.util.Random;

public class Velbloud {
    private String jmeno;
    private DruhVelblouda druh; // druh velblouda
    private double v; // rychlost
    private double d; // vzdalenost na jedno napiti
    private double td; // doba napiti
    private int kd; // maximalni pocet kosu, ktere bloud unese
    Random r = new Random();

    public Velbloud(DruhVelblouda druh, double v, double d, double td, int kd) {
        this.druh = druh;
        this.v = generateV();
        this.d = generateD();
        this.td = td;
        this.kd = kd;
        druh.pocet++;
        this.jmeno = generateName();
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

}
