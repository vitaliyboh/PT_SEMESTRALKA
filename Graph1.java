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

    public ArrayList<Integer> cesta(int a, int b) {
        ArrayList<Integer> cesta = new ArrayList<>();
        cesta.add(a);
        if (naslednici[a][b] == a) {
            cesta.add(b);
            return cesta;
        }

        int c = naslednici[a][b];
        cesta.add(c);
        while (naslednici[c][b] != c) {
            cesta.add(c);
            c = naslednici[c][b];
        }

        cesta.add(b);
        return cesta;
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
        if (index == 0) {
            System.out.println("Pozadavek nelze vykonat!");
            System.exit(1);
        }
        return index;
    }

    public ArrayList<Integer> nejblizsiVrcholy(int vstupniVrchol) {
        int index = 0;
        double pomocna;
        double pomocna1 = Double.MAX_VALUE;
        ArrayList<Integer> vrcholy = new ArrayList<>(matice[0].length - 1);


        for (int i = 1; i < matice[0].length; i++) {
            if (vstupniVrchol == i) continue;
            pomocna = matice_vzdalenosti[vstupniVrchol][i];
            if ((pomocna < pomocna1 && i <= sklady.length-1) ) {
                vrcholy.add(i);
               // pomocna1 = pomocna;
            }
        }
        if(vrcholy.size()>1) {
            bubbleSort(vrcholy, vstupniVrchol);
        }

        if (vrcholy.isEmpty()) {
            System.out.println("Pozadavek nelze vykonat!");
            System.exit(1);
        }
        return vrcholy;
    }

    public void bubbleSort (ArrayList<Integer> list, int vstupniVrchol) {
        int n = list.size();
        int temp = 0;
        for(int i=0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (matice_vzdalenosti[vstupniVrchol][list.get(j - 1)] > matice_vzdalenosti[vstupniVrchol][list.get(j)]) {
                    //swap elements
                    temp = list.get(j - 1);
                    list.add(j - 1, list.get(j));
                    list.remove(j);
                    list.add(j, temp);
                    list.remove(j+1);
                }

            }
        }
    }

    public double cestaVelblouda (Velbloud velbloud, ArrayList<Integer> cesta, Pozadavek pozadavek) {
        int i = velbloud.getIndexSkladu();
        double cas = 0;

        for (Integer j: cesta) {
            if (matice_vzdalenosti[i][j] > velbloud.getD()){
                return -1;
            }
            cas += velbloud.getV()*matice_vzdalenosti[i][j];
            velbloud.setEnergie(velbloud.getEnergie() - matice_vzdalenosti[i][j]);
            if (velbloud.getEnergie() <= 0) {
                cas += velbloud.getTd();
                velbloud.setEnergie(velbloud.getD());
            }
            i = j;
        }
        cas += pozadavek.getKp()* sklady[velbloud.getIndexSkladu()].getTn();

        return cas > (pozadavek.getTp() + pozadavek.getTz()) ? -1 : cas;
    }

}
