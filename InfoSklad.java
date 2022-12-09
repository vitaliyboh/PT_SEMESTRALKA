import java.util.ArrayList;
import java.util.List;

public class InfoSklad {
    int dobaDoplneni;
    int pocetKosuPredDoplnenim;
    int pocetKosuPoDoplneni;

    public int getDobyDoplneni() {
        return dobaDoplneni;
    }

    public InfoSklad(int dobaDoplneni, int pocetKosuPredDoplnenim, int pocetKosuPoDoplneni) {
        this.dobaDoplneni = dobaDoplneni;
        this.pocetKosuPredDoplnenim = pocetKosuPredDoplnenim;
        this.pocetKosuPoDoplneni = pocetKosuPoDoplneni;
    }

    public void setDobyDoplneni(int dobaDoplneni) {
        this.dobaDoplneni = dobaDoplneni;
    }

    public int getPocetKosuPredDoplnenim() {
        return pocetKosuPredDoplnenim;
    }

    public void setPocetKosuPredDoplnenim(int pocetKosuPredDoplnenim) {
        this.pocetKosuPredDoplnenim = pocetKosuPredDoplnenim;
    }

    public int getPocetKosuPoDoplneni() {
        return pocetKosuPoDoplneni;
    }

    public void setPocetKosuPoDoplneni(int pocetKosuPoDoplneni) {
        this.pocetKosuPoDoplneni = pocetKosuPoDoplneni;
    }

    @Override
    public String toString() {
        String vypis = "Cas doplneni: " + dobaDoplneni + "\t Pocet kosu pred doplnenim: " + pocetKosuPredDoplnenim +
                "\t Pocet kosu po doplneni: " + pocetKosuPoDoplneni + "\n";
        return vypis;
    }
}
