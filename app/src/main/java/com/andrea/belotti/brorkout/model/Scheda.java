package com.andrea.belotti.brorkout.model;

import java.io.Serializable;
import java.util.List;

public class Scheda implements Serializable {

    private List<Giornata> giornate;
    private String nome;

    public Scheda(List<Giornata> giornate) {
        this.giornate = giornate;
    }
    public Scheda() { }

    public List<Giornata> getGiornate() {
        return giornate;
    }

    public void setGiornate(List<Giornata> giornate) {
        this.giornate = giornate;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Scheda{" +
                "giornate=" + giornate +
                ", nome='" + nome + '\'' +
                '}';
    }
}
