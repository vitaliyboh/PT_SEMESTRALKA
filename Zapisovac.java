import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Zapisovac {
    Sklad[] sklady;
    Oaza[] oazy;
    DruhVelblouda[] druhyVelbloudu;

    public Zapisovac(Sklad[] sklady, Oaza[] oazy, DruhVelblouda[] druhyVelbloudu) {
        this.sklady = sklady;
        this.oazy = oazy;
        this.druhyVelbloudu = druhyVelbloudu;
    }

    private void zapisInfoBloudu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Velbloudi.txt");
        for (int i = 1; i < sklady.length; i++){
            for (Velbloud v:sklady[i].getVelboudi()){
                pw.println(v + "\n--------------------------------------------------------------------------------");
            }
        }
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



    void zapisVse() throws FileNotFoundException {
        zapisInfoOaz();
        zapisInfoBloudu();
        zapisInfoSkladu();
        zapisDruhuBloudu();
    }
}
