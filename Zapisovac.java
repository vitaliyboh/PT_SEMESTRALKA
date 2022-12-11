import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Zapisovac {
    Sklad[] sklady;
    Oaza[] oazy;
    DruhVelblouda[] druhyVelbloudu;
    private static int celkovaDobaOdpocinku;
    private static double celkovaVzdalenost;

    public Zapisovac(Sklad[] sklady, Oaza[] oazy, DruhVelblouda[] druhyVelbloudu) {
        this.sklady = sklady;
        this.oazy = oazy;
        this.druhyVelbloudu = druhyVelbloudu;
    }

    private void zapisInfoBloudu() throws FileNotFoundException {
        int doba = 0;
        double vzdalenost = 0;
        PrintWriter pw = new PrintWriter("Statistika/Velbloudi.txt");
        for (int i = 1; i < sklady.length; i++){
            for (Velbloud v:sklady[i].getVelboudi()){
                pw.println(v + "\n--------------------------------------------------------------------------------");
                doba += v.getCasOdpocinku();
                vzdalenost += v.getCelkVzdalenost();
            }
        }
        celkovaDobaOdpocinku = doba;
        celkovaVzdalenost = vzdalenost;
        pw.close();

    }

    private void zapisInfoSkladu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Sklady.txt");
        for (int i = 1; i < sklady.length; i++) {
            pw.println("Sklad_" + i + "\n" + sklady[i] +
                    "--------------------------------------------------------------------------------");
        }
        pw.close();
    }

    private void zapisInfoOaz() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Oazy.txt");
        for (int i = 1; i < oazy.length; i++) {
            if (!oazy[i].getInfo().isEmpty()) {
                pw.println("Oaza_" + i + "\t" + oazy[i]);
            }
        }
        pw.close();
    }

    /**
     * Vypise informace o vsech druzich velbloudu
     */
    private void zapisDruhuBloudu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/DruhyVelbloudu.txt");
        int celkem = 0;
        for (DruhVelblouda d : druhyVelbloudu){
            celkem += d.pocet;
            pw.println(d);
        }
        pw.println("Celkem: " + celkem + "\n");
        pw.close();
    }

    private void zapisObecneInfo() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Obecne.txt");
        pw.println("Delka trvani cele simulace: " + (int)Velbloud.getCasPoslednihoPozadavku());
        pw.println("Celkova doba odpocinku pouzitych velbloudu: " + celkovaDobaOdpocinku);
        pw.printf("Celkova usla vzdalenost: %.2f", celkovaVzdalenost);
        pw.close();
    }


    void zapisVse() throws FileNotFoundException {
        zapisInfoOaz();
        zapisInfoBloudu();
        zapisInfoSkladu();
        zapisDruhuBloudu();
        zapisObecneInfo();
    }
}
