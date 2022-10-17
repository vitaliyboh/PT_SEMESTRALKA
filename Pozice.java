import java.util.Random;

public class Pozice {
    private double x;
    private double y;

    public Pozice(double x, double y){
        this.x = x;
        this.y = y;

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistance(Pozice other){
        return Math.sqrt((this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y));
    }
}
