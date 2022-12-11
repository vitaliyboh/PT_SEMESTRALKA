import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro zaznam jedne trasy velblouda
 *
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Trasa {
    /** cas opusteni skladu */
    private int casOpusteni;
    /** cesta s nazvy mist */
    private List<String> cesta;
    /** pocet kosu ktery velbloud nesl */
    private int pocetKosu;
    /** index oazy do ktere sel */
    private int indexOazy;
    /** cas doruceni */
    private int casDoruceni;
    /** mista, kde se napil */
    private List<String> zastavky = new ArrayList<>();
    /** casy, kdy se napil */
    private List<Integer> casyZastavek = new ArrayList<>();
    /** cas navratu velblouda do skladu */
    private int casNavratu;

    /**
     * Konstruktor pro trasu velblouda
     *
     * @param casOpusteni cas opusteni skladu
     * @param cesta       cesta s nazvy mist
     * @param pocetKosu   pocet kosu
     * @param indexOazy   index oazy do ktere sel
     */
    public Trasa(int casOpusteni, List<String> cesta, int pocetKosu, int indexOazy) {
        this.casOpusteni = casOpusteni;
        this.cesta = cesta;
        this.pocetKosu = pocetKosu;
        this.indexOazy = indexOazy;
    }

    /**
     * getter pro cas opusteni
     *
     * @return cas opusteni
     */
    public int getCasOpusteni() {
        return casOpusteni;
    }

    /**
     * setter pro cas opusteni
     *
     * @param casOpusteni cas opusteni
     */
    public void setCasOpusteni(int casOpusteni) {
        this.casOpusteni = casOpusteni;
    }

    /**
     * getter pro cestu
     *
     * @return cesta
     */
    public List<String> getCesta() {
        return cesta;
    }

    /**
     * setter pro cestu
     *
     * @param cesta cesta
     */
    public void setCesta(List<String> cesta) {
        this.cesta = cesta;
    }

    /**
     * getter pro pocet kosu
     *
     * @return pocet kosu
     */
    public int getPocetKosu() {
        return pocetKosu;
    }

    /**
     * setter pro pocet kosu
     *
     * @param pocetKosu pocet kosu
     */
    public void setPocetKosu(int pocetKosu) {
        this.pocetKosu = pocetKosu;
    }

    /**
     * getter pro index finalni oazy
     *
     * @return index oazy
     */
    public int getIndexOazy() {
        return indexOazy;
    }

    /**
     * setter pro index finalni oazy
     *
     * @param indexOazy index oazy
     */
    public void setIndexOazy(int indexOazy) {
        this.indexOazy = indexOazy;
    }

    /**
     * getter pro cas dorucei
     *
     * @return cas doruceni
     */
    public int getCasDoruceni() {
        return casDoruceni;
    }

    /**
     * setter pro cas dorucei
     *
     * @param casDoruceni cas doruceni
     */
    public void setCasDoruceni(int casDoruceni) {
        this.casDoruceni = casDoruceni;
    }

    /**
     * getter pro seznam mist kde se velbloud napil
     *
     * @return seznam zastavek
     */
    public List<String> getZastavky() {
        return zastavky;
    }

    /**
     * setter pro seznam mist kde se velbloud napil
     *
     * @param zastavky seznam zastavek
     */
    public void setZastavky(List<String> zastavky) {
        this.zastavky = zastavky;
    }

    /**
     * getter pro casy kdy se velbloud napil
     *
     * @return cas zastavek
     */
    public List<Integer> getCasyZastavek() {
        return casyZastavek;
    }

    /**
     * setter pro casy kdy se velbloud napil
     *
     * @param casyZastavek zastavek
     */
    public void setCasyZastavek(List<Integer> casyZastavek) {
        this.casyZastavek = casyZastavek;
    }

    /**
     * getter pro cas navratu velblouda do skladu
     *
     * @return cas navratu
     */
    public int getCasNavratu() {
        return casNavratu;
    }

    /**
     * setter pro cas navratu velblouda do skladu
     *
     * @param casNavratu cas navratu
     */
    public void setCasNavratu(int casNavratu) {
        this.casNavratu = casNavratu;
    }

    /**
     * Sestavi retezec pro vypis do statistik
     *
     * @return retezec pro vypis
     */
    @Override
    public String toString() {
        StringBuilder vypis = new StringBuilder(" <> Cas odchodu: " + casOpusteni + "\t Cesta pres: [");
        for (String misto : cesta) {
            vypis.append(misto).append(", ");
        }
        vypis.append("] \n Pocet kosu: ").append(pocetKosu).append("\t Index oazy: ").append(indexOazy).append("\t Cas doruceni: ").append(casDoruceni).append("\n Zastavky na piti: [ ");
        if (zastavky.isEmpty()) {
            vypis.append("-zadne zastavky na piti-");
        } else {
            for (int i = 0; i < zastavky.size(); i++) {
                vypis.append(zastavky.get(i)).append(" - ").append(casyZastavek.get(i)).append("[j], ");
            }
        }
        vypis.append("] \n Cas navratu: ").append(casNavratu);
        return vypis.toString();
    }


}
