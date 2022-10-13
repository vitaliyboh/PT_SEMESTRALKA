import java.util.ArrayList;

public class Svet {
    Oaza[] oazy;
    Sklad[] sklady;
    DruhVelblouda[] druhyVelbloudu;
    Pozadavek[] pozadavky;
    Graph mapa;

    public Svet(Oaza[] oazy, Sklad[] sklady, DruhVelblouda[] druhyVelbloudu, Pozadavek[] pozadavky, Graph mapa) {
        this.oazy = oazy;
        this.sklady = sklady;
        this.druhyVelbloudu = druhyVelbloudu;
        this.pozadavky = pozadavky;
        this.mapa = mapa;
    }
}
