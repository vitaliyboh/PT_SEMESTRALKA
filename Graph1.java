import java.util.ArrayList;
import java.util.Scanner;

public class Graph1 {
    double[][] matice;
    Sklad[] sklady;
    Oaza[] oazy;

    int[][] naslednici;
    double[][] matice_vzdalenosti;

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

    void floydWarshall() {

        int nV = matice[0].length;

        int i, j, k;

        setMaticiVzdalenosti(nV);
        setMaticiNasledniku(nV);


        // Kontroluju zda neni cesta lepsi pres vrchol k
        for (k = 0; k < nV; k++) {
            for (i = 0; i < nV; i++) {
                for (j = 0; j < nV; j++) {
                    if (matice_vzdalenosti[i][k] + matice_vzdalenosti[k][j] < matice_vzdalenosti[i][j]) {
                        matice_vzdalenosti[i][j] = matice_vzdalenosti[i][k] + matice_vzdalenosti[k][j];
                        naslednici[i][j] = k;
                    }
                }
            }
        }

    }

    private void setMaticiVzdalenosti(int nV) {
        this.matice_vzdalenosti = new double[nV][nV];
        int j;
        int i;
        for (i = 0; i < nV; i++) {
            for (j = 0; j < nV; j++) {
                if (i!=j){
                    if (matice[i][j] == 0){
                        matice_vzdalenosti[i][j] = Double.POSITIVE_INFINITY;
                    }
                    else matice_vzdalenosti[i][j] = matice[i][j];
                }
                else matice_vzdalenosti[i][j] = 0;
            }
        }
    }

    private void setMaticiNasledniku(int nV) {
        this.naslednici = new int[nV][nV];
        int i;
        int j;
        for (i = 0; i < nV; i++) {
            for (j = 0; j < nV; j++) {
                if (matice_vzdalenosti[i][j] != Double.POSITIVE_INFINITY && i!=j){
                    naslednici[i][j] = i;
                }
                else naslednici[i][j] = -1;
            }
        }
    }

    public void cestaPresVrcholy(int a, int b) {
        if (naslednici[a][b] != a) {
            int c = naslednici[a][b];
            while (naslednici[c][b] != c){
                System.out.println(c);
                c = naslednici[c][b];
            }
            System.out.println(c);
        }
        System.out.println(b);
    }

    public int nejblizsiVrchol(int vstupniVrchol) {
        int index = 0;
        double pomocna;
        double pomocna1 = Double.MAX_VALUE;

        for (int i = 1; i < matice[0].length; i++) {
            if (vstupniVrchol == i) continue;
            pomocna = matice_vzdalenosti[vstupniVrchol][i];
            if (pomocna < pomocna1 && i <= sklady.length) {
                index = i;
                pomocna1 = pomocna;
            }
        }
        return index;
    }
}
