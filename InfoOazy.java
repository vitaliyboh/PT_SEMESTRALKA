import java.util.ArrayList;
import java.util.List;

public class InfoOazy {
    int casPozadavku;
    int pocetKosu;
    int deadline;
    int casDoruceni = 0;
    List<Velbloud> listVelbloudu = new ArrayList<>();

    public void setCasPozadavku(int casPozadavku) {
        this.casPozadavku = casPozadavku;
    }

    public void setPocetKosu(int pocetKosu) {
        this.pocetKosu = pocetKosu;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setCasDoruceni(int casDoruceni) {
        this.casDoruceni = casDoruceni;
    }


    public void setListVelbloudu(List<Velbloud> listVelbloudu) {
        this.listVelbloudu = listVelbloudu;
    }

    public List<Velbloud> getListVelbloudu() {
        return listVelbloudu;
    }

    public int getCasPozadavku() {
        return casPozadavku;
    }

    public int getPocetKosu() {
        return pocetKosu;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getCasDoruceni() {
        return casDoruceni;
    }

    /**
     *
     */
    @Override
    public String toString() {
        String vypis = "Cas prichodu pozadavku: " + casPozadavku + "\t Pocet kosu: " + pocetKosu + "\t Deadline: " + deadline +
                "\t Cas doruceni: " + casDoruceni + "\t Velbloudi: [";
        for (Velbloud velbloud: listVelbloudu) {
            vypis += velbloud.getJmeno()+ "(S" + velbloud.getIndexSkladu() + "),";
        }
        vypis+= "]";
        return vypis;
    }
}
