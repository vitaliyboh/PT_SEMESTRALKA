/**
 * Prepravka reprezentujici pozici
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Pozice {
    /** x-ova souradnice */
    private double x;
    /** y-ova souradnice */
    private double y;

    /**
     * Konstruktor vytvori novou instanci
     *
     * @param x x-ova souradnice
     * @param y y-ova souradnice
     */
    public Pozice(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Nastavi x-vou souradnici
     *
     * @param x x-ova souradnice
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Nastavi y-ovu souradnici
     *
     * @param y y-ova souradnice
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Metoda spocita vzdalenost dvou pozic
     *
     * @param other pozice, ke ktery chceme spocitat vzdalenost
     * @return vzdalenost k dane pozici
     */
    public double getDistance(Pozice other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }
}
