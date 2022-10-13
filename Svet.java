import java.util.ArrayList;

public class Svet {
    Oaza[] oazy;
    Sklad[] sklady;
    DruhVelblouda[] druhyVelbloudu;
    Pozadavek[] pozadavky;

    public Svet(Oaza[] oazy, Sklad[] sklady, DruhVelblouda[] druhyVelbloudu, Pozadavek[] pozadavky) {
        this.oazy = oazy;
        this.sklady = sklady;
        this.druhyVelbloudu = druhyVelbloudu;
        this.pozadavky = pozadavky;
    }
}
