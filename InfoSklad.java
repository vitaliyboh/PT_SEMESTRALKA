

/**
 * Obalova trida pro zaznam doplneni skladu
 *
 * @author Vitaliy Bohera & Martin Dobrovsky
 */
public class InfoSklad {
    /** cas, kdy se sklad doplnil */
    private int dobaDoplneni;
    /** pocet kosu pred doplnenim */
    private int pocetKosuPredDoplnenim;
    /** pocet kosu po doplneni */
    private int pocetKosuPoDoplneni;

    /**
     * vrati cas kdy sse sklad doplnil
     *
     * @return doba doplneni
     */
    public int getDobyDoplneni() {
        return dobaDoplneni;
    }

    /**
     * Konstruktor pro info o skladu
     *
     * @param dobaDoplneni           doba doplneni
     * @param pocetKosuPredDoplnenim pocet kosu pred doplnenim
     * @param pocetKosuPoDoplneni    pocet kosu po doplneni
     */
    public InfoSklad(int dobaDoplneni, int pocetKosuPredDoplnenim, int pocetKosuPoDoplneni) {
        this.dobaDoplneni = dobaDoplneni;
        this.pocetKosuPredDoplnenim = pocetKosuPredDoplnenim;
        this.pocetKosuPoDoplneni = pocetKosuPoDoplneni;
    }

    /**
     * setter pro cas doplneni
     *
     * @param dobaDoplneni cas doplneni
     */
    public void setDobyDoplneni(int dobaDoplneni) {
        this.dobaDoplneni = dobaDoplneni;
    }

    /**
     * getter pro pocet kosu pred doplnenim
     *
     * @return pocet kosu pred doplnenim
     */
    public int getPocetKosuPredDoplnenim() {
        return pocetKosuPredDoplnenim;
    }

    /**
     * setter pro pocet kosu pred doplnenim
     *
     * @param pocetKosuPredDoplnenim pocet kosu pred doplnenim
     */
    public void setPocetKosuPredDoplnenim(int pocetKosuPredDoplnenim) {
        this.pocetKosuPredDoplnenim = pocetKosuPredDoplnenim;
    }

    /**
     * getter pro pocet kosu po doplneni
     *
     * @return pocet kosu po doplneni
     */
    public int getPocetKosuPoDoplneni() {
        return pocetKosuPoDoplneni;
    }

    /**
     * setter pro pocet kosu po doplneni
     *
     * @param pocetKosuPoDoplneni pocet kosu po doplneni
     */
    public void setPocetKosuPoDoplneni(int pocetKosuPoDoplneni) {
        this.pocetKosuPoDoplneni = pocetKosuPoDoplneni;
    }

    /**
     * Metoda pro potreby vypisu statistiky
     * Vrati retezec slozeny z atributu
     *
     * @return retezec pro vypis
     */
    @Override
    public String toString() {
        return "Cas doplneni: " + dobaDoplneni + "\t Pocet kosu pred doplnenim: " + pocetKosuPredDoplnenim +
                "\t Pocet kosu po doplneni: " + pocetKosuPoDoplneni + "\n";
    }
}
