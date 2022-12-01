import java.util.Queue;

public class Svet {
    Oaza[] oazy;
    Sklad[] sklady;
    DruhVelblouda[] druhyVelbloudu;
    Queue<Pozadavek> pozadavky;
    Graph mapa; // graf reprezentujici cesty mezi oazami a sklady

    public Svet(Oaza[] oazy, Sklad[] sklady, DruhVelblouda[] druhyVelbloudu, Queue<Pozadavek> pozadavky, Graph mapa) {
        this.oazy = oazy;
        this.sklady = sklady;
        this.druhyVelbloudu = druhyVelbloudu;
        this.pozadavky = pozadavky;
        this.mapa = mapa;
    }
}
