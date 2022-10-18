import java.util.ArrayList;

public class Graph1 {
    double[][] matice;
    Sklad[] sklady;
    Oaza[] oazy;

    public Graph1(int pocetVrcholu, Sklad[] sklady, Oaza[] oazy) {
        this.matice = new double[pocetVrcholu][pocetVrcholu];
        this.sklady = sklady;
        this.oazy = oazy;
    }

    void addEdge(int i, int j) {
        double value;
        if (j >= sklady.length && i >= sklady.length) {
            value = oazy[i - sklady.length+1].getPozice().getDistance(oazy[j - sklady.length+1].getPozice());
        }
        else if (j >= sklady.length) {
            value = sklady[i].getPozice().getDistance(oazy[j - sklady.length+1].getPozice());
        }
        else if (i >= sklady.length) {
            value = oazy[i - sklady.length+1].getPozice().getDistance(sklady[j].getPozice());
        }
        else {
            value = sklady[i].getPozice().getDistance(sklady[j].getPozice());
        }
        matice[i][j] = value;
        matice[j][i] = value;
    }

    ArrayList<Integer> neighbours(int v){
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0;i<matice[0].length;i++)
            if (matice[v][i] == 1) result.add(i);
        return result;
    }

    boolean isNeighbour(int i, int j){
        return matice[i][j]==1;
    }
}
