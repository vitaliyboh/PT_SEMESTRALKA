import java.util.ArrayList;
import java.util.Queue;

public class Svet {
    Oaza[] oazy;
    Sklad[] sklady;
    DruhVelblouda[] druhyVelbloudu;
    Queue<Pozadavek> pozadavky;
    Graph1 mapa;

    public Svet(Oaza[] oazy, Sklad[] sklady, DruhVelblouda[] druhyVelbloudu, Queue<Pozadavek> pozadavky, Graph1 mapa) {
        this.oazy = oazy;
        this.sklady = sklady;
        this.druhyVelbloudu = druhyVelbloudu;
        this.pozadavky = pozadavky;
        this.mapa = mapa;
    }
}
