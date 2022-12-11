import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Trida pro zapis do souboru statistik
 *
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Zapisovac {
    /** pole skladu */
    private final Sklad[] sklady;
    /** pole oaz */
    private final Oaza[] oazy;
    /** pole druhu velbloudu */
    private final DruhVelblouda[] druhyVelbloudu;
    /** celkova doba odpocinku */
    private static int celkovaDobaOdpocinku;
    /** celkem usla vzdalenost */
    private static double celkovaVzdalenost;

    /**
     * Konstruktor pro zapisovac
     *
     * @param sklady         sklady
     * @param oazy           oazy
     * @param druhyVelbloudu druhy velbloudu
     */
    public Zapisovac(Sklad[] sklady, Oaza[] oazy, DruhVelblouda[] druhyVelbloudu) {
        this.sklady = sklady;
        this.oazy = oazy;
        this.druhyVelbloudu = druhyVelbloudu;
    }

    /**
     * Zapise do souboru 'Velbloudi.txt' statistiku o velbloudech
     *
     * @throws FileNotFoundException pokud nenajde soubor
     */
    private void zapisInfoBloudu() throws FileNotFoundException {
        int doba = 0;
        double vzdalenost = 0;
        PrintWriter pw = new PrintWriter("Statistika/Velbloudi.txt");
        for (int i = 1; i < sklady.length; i++) {
            for (Velbloud v : sklady[i].getVelboudi()) {
                pw.println(v + "\n--------------------------------------------------------------------------------");
                doba += v.getCasOdpocinku();
                vzdalenost += v.getCelkVzdalenost();
            }
        }
        celkovaDobaOdpocinku = doba;
        celkovaVzdalenost = vzdalenost;
        pw.close();

    }

    /**
     * Zapise info o skladech do souboru 'Sklady.txt'
     *
     * @throws FileNotFoundException pokud nenajde soubor
     */
    private void zapisInfoSkladu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Sklady.txt");
        for (int i = 1; i < sklady.length; i++) {
            pw.println("Sklad_" + i + "\n" + sklady[i] +
                    "--------------------------------------------------------------------------------");
        }
        pw.close();
    }

    /**
     * Zapise info o vsech oazach do souboru 'Oazy.txt'
     *
     * @throws FileNotFoundException pokud nenajde soubor
     */
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
     * Vypise informace o vsech druzich velbloudu do 'DruhyVelbloudu.txt'
     *
     * @throws FileNotFoundException pokud nenajde soubor
     */
    private void zapisDruhuBloudu() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/DruhyVelbloudu.txt");
        int celkem = 0;
        for (DruhVelblouda d : druhyVelbloudu) {
            celkem += d.pocet;
            pw.println(d);
        }
        pw.println("Celkem: " + celkem + "\n");
        pw.close();
    }

    /**
     * Zapise do souboru 'Obecne.txt' celkove informace o simulaci
     *
     * @throws FileNotFoundException pokdu nenajde soubor
     */
    private void zapisObecneInfo() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter("Statistika/Obecne.txt");
        pw.println("Delka trvani cele simulace: " + (int) Velbloud.getCasPoslednihoPozadavku()
                + "\nCelkova doba odpocinku pouzitych velbloudu: " + celkovaDobaOdpocinku);
        pw.printf(Locale.US, "Celkova usla vzdalenost: %.2f", celkovaVzdalenost);
        pw.close();
    }

    /**
     * Zavola vsechny metody pro zapis do souboru
     *
     * @throws FileNotFoundException pokud nenajde dane soubory
     */
    public void zapisVse() throws FileNotFoundException {
        zapisInfoOaz();
        zapisInfoBloudu();
        zapisInfoSkladu();
        zapisDruhuBloudu();
        zapisObecneInfo();
    }
}
