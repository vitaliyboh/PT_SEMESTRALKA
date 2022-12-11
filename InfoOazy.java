import java.util.ArrayList;
import java.util.List;

/**
 * Obalova trida pro informace o dane oaze
 *
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class InfoOazy {
    /** cas pozadavku */
    private int casPozadavku;
    /** pocet kosu ktere oaza chce */
    private int pocetKosu;
    /** deadline pozadavku */
    private int deadline;
    /** cas kdy byly potrebne kose doruceny do oazy */
    private int casDoruceni = 0;
    /** seznam velbloudu obsluhujici dany pozadavek oazy */
    private List<Velbloud> listVelbloudu = new ArrayList<>();

    /**
     * Setter pro cas pozadavku
     *
     * @param casPozadavku cas pozadavku
     */
    public void setCasPozadavku(int casPozadavku) {
        this.casPozadavku = casPozadavku;
    }

    /**
     * Setter pro pocet kosu, ktere oaza chce
     *
     * @param pocetKosu pocet kosu
     */
    public void setPocetKosu(int pocetKosu) {
        this.pocetKosu = pocetKosu;
    }

    /**
     * Setter pro deadline
     *
     * @param deadline deadline
     */
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    /**
     * Setter pro cas doruceni
     *
     * @param casDoruceni cas doruceni
     */
    public void setCasDoruceni(int casDoruceni) {
        this.casDoruceni = casDoruceni;
    }

    /**
     * Setter pro list velbloudu
     *
     * @param listVelbloudu list velbloudu
     */
    public void setListVelbloudu(List<Velbloud> listVelbloudu) {
        this.listVelbloudu = listVelbloudu;
    }

    /**
     * getter pro list velbloudu
     *
     * @return list velbloudu
     */
    public List<Velbloud> getListVelbloudu() {
        return listVelbloudu;
    }

    /**
     * getter pro cas pozadavku
     *
     * @return cas pozadavku
     */
    public int getCasPozadavku() {
        return casPozadavku;
    }

    /**
     * Getter pro pocet kosu
     *
     * @return pocet kosu
     */
    public int getPocetKosu() {
        return pocetKosu;
    }

    /**
     * Getter pro deadline
     *
     * @return deadline
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     * Getter pro cas doruceni
     *
     * @return cas doruceni
     */
    public int getCasDoruceni() {
        return casDoruceni;
    }

    /**
     * Metoda vrati pozadovany retezec pro vypis do statistik
     *
     * @return retezec pro vypis
     */
    @Override
    public String toString() {
        StringBuilder vypis = new StringBuilder("Cas prichodu pozadavku: " + casPozadavku + "\t Pocet kosu: " + pocetKosu + "\t Deadline: " + deadline +
                "\t Cas doruceni: " + casDoruceni + "\t Velbloudi: [");
        for (Velbloud velbloud : listVelbloudu) {
            vypis.append(velbloud.getJmeno()).append("(S").append(velbloud.getIndexSkladu()).append("),");
        }
        vypis.append("]");
        return vypis.toString();
    }
}
