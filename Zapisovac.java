import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Zapisovac {
    Sklad[] sklady;
    Oaza[] oazy;

    public Zapisovac(Sklad[] sklady, Oaza[] oazy) {
        this.sklady = sklady;
        this.oazy = oazy;
    }

    void zapisInfoBloudu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Velbloudi.txt");
        for (int i = 1; i < sklady.length; i++){
            for (Velbloud v:sklady[i].getVelboudi()){
                pw.println(v + "\n--------------------------------------------------------------------------------");
            }
        }
        pw.close();


    }

    void zapisInfoSkladu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Sklady.txt");
        for (int i = 1; i < sklady.length; i++) {
            pw.println("Sklad_" + i + "\n" + sklady[i] +
                    "--------------------------------------------------------------------------------");
        }
        pw.close();
    }

    void zapisInfoOaz() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Oazy.txt");
        for (int i = 1; i < oazy.length; i++) {
            if (!oazy[i].getInfo().isEmpty()) {
                pw.println("Oaza_" + i + "\t" + oazy[i]);
            }
        }
        pw.close();
    }

    void zapisVse() throws FileNotFoundException {
        zapisInfoOaz();
        zapisInfoBloudu();
        zapisInfoSkladu();
    }
}
