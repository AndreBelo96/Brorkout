package com.andrea.belotti.brorkout.model;

import java.io.Serializable;
import java.util.List;

public class Giornata implements Serializable {

    private int numeroGiornata;

    private List<Esercizio> esercizi;

    public Giornata(int numeroGiornata, List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    public Giornata() {}

    public List<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    public int getNumeroGiornata() {
        return numeroGiornata;
    }

    public void setNumeroGiornata(int numeroGiornata) {
        this.numeroGiornata = numeroGiornata;
    }

    @Override
    public String toString() {
        return "Giornata{" +
                "numero giornata=" + numeroGiornata +
                "esercizi=" + esercizi +
                "}";
    }

}
