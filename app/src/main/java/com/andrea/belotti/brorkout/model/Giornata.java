package com.andrea.belotti.brorkout.model;

import java.io.Serializable;
import java.util.List;

public class Giornata implements Serializable {

    private List<Esercizio> esercizi;

    public Giornata(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    public Giornata() {}

    public List<Esercizio> getEsercizi() {
        return esercizi;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    @Override
    public String toString() {
        return "Giornata{" +
                "esercizi=" + esercizi +
                '}';
    }

}
