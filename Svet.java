import java.util.Queue;
/**
 * Trida reprezentujici svet kde jsou vsechny potrebne informace o skladech, oazach, velbloudech a pozadavcich
 * @author Vitaliy Bohera, Martin Dobrovsky
 */
public class Svet {
    /** pole vsech oaz */
    Oaza[] oazy;
    /** pole vsech skladu */
    Sklad[] sklady;
    /** pole vsech druhu velbloudu */
    DruhVelblouda[] druhyVelbloudu;
    /** fronta prichozich pozadavku */
    Queue<Pozadavek> pozadavky;
    /** graf reprezentujici cesty mezi oazami a sklady */
    Graph mapa;

    /**
     * Konstruktor vytvori novou instanci sveta
     *
     * @param oazy           existujuiici oazy
     * @param sklady         existujici sklady
     * @param druhyVelbloudu vsechny druhy velbloudu
     * @param pozadavky      fronta pozadavku
     * @param mapa           graf cest mezi oazami a sklady
     */
    public Svet(Oaza[] oazy, Sklad[] sklady, DruhVelblouda[] druhyVelbloudu, Queue<Pozadavek> pozadavky, Graph mapa) {
        this.oazy = oazy;
        this.sklady = sklady;
        this.druhyVelbloudu = druhyVelbloudu;
        this.pozadavky = pozadavky;
        this.mapa = mapa;
    }
}
