import java.util.ArrayList;
import java.util.List;

public class Trasa {
    int casOpusteni;
    List<String> cesta;
    int pocetKosu;
    int indexOazy;
    int casDoruceni;
    List<String> zastavky = new ArrayList<>();
    List<Integer> casyZastavek = new ArrayList<>();
    int casNavratu;

    public Trasa(int casOpusteni, List<String> cesta, int pocetKosu, int indexOazy) {
        this.casOpusteni = casOpusteni;
        this.cesta = cesta;
        this.pocetKosu = pocetKosu;
        this.indexOazy = indexOazy;
    }

    public int getCasOpusteni() {
        return casOpusteni;
    }

    public void setCasOpusteni(int casOpusteni) {
        this.casOpusteni = casOpusteni;
    }

    public List<String> getCesta() {
        return cesta;
    }

    public void setCesta(List<String> cesta) {
        this.cesta = cesta;
    }

    public int getPocetKosu() {
        return pocetKosu;
    }

    public void setPocetKosu(int pocetKosu) {
        this.pocetKosu = pocetKosu;
    }

    public int getIndexOazy() {
        return indexOazy;
    }

    public void setIndexOazy(int indexOazy) {
        this.indexOazy = indexOazy;
    }

    public int getCasDoruceni() {
        return casDoruceni;
    }

    public void setCasDoruceni(int casDoruceni) {
        this.casDoruceni = casDoruceni;
    }

    public List<String> getZastavky() {
        return zastavky;
    }

    public void setZastavky(List<String> zastavky) {
        this.zastavky = zastavky;
    }

    public List<Integer> getCasyZastavek() {
        return casyZastavek;
    }

    public void setCasyZastavek(List<Integer> casyZastavek) {
        this.casyZastavek = casyZastavek;
    }

    public int getCasNavratu() {
        return casNavratu;
    }

    public void setCasNavratu(int casNavratu) {
        this.casNavratu = casNavratu;
    }

    @Override
    public String toString() {
        String vypis = " <> Cas odchodu: " + casOpusteni + "\t Cesta pres: [";
        for (String misto: cesta) {
            vypis+= misto + ", ";
        }
        vypis += "] \n Pocet kosu: " + pocetKosu + "\t Index oazy: " + indexOazy + "\t Cas doruceni: " + casDoruceni + "\n Zastavky na piti: [ ";
        if (zastavky.isEmpty()){
            vypis += "-zadne zastavky na piti-";
        }
        else {
            for (int i = 0; i < zastavky.size(); i++) {
                vypis += zastavky.get(i) + " - " + casyZastavek.get(i) + "[j], ";
            }
        }
        vypis+="] \n Cas navratu: " + casNavratu;
        return vypis;
    }


}
