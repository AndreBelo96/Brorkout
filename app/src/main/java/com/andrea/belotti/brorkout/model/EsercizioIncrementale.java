package com.andrea.belotti.brorkout.model;

import java.io.Serializable;

public class EsercizioIncrementale implements Esercizio, Serializable {

    private String type = "Incrementale";
    private String nomeEsercizio;
    private String tipoEsercizio;
    private String serie;
    private String recupero;
    private Boolean isVideo;
    private String indicazioniCoach;
    private String appuntiAtleta;
    private String inizio;
    private String picco;

    public EsercizioIncrementale(String nomeEsercizio,
                                 String tipoEsercizio,
                                 String serie,
                                 String recupero,
                                 Boolean isVideo,
                                 String indicazioniCoach,
                                 String appuntiAtleta,
                                 String inizio,
                                 String picco) {
        this.nomeEsercizio = nomeEsercizio;
        this.tipoEsercizio = tipoEsercizio;
        this.serie = serie;
        this.recupero = recupero;
        this.isVideo = isVideo;
        this.indicazioniCoach = indicazioniCoach;
        this.appuntiAtleta = appuntiAtleta;
        this.inizio = inizio;
        this.picco = picco;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getNomeEsercizio() {
        return nomeEsercizio;
    }

    @Override
    public void setNomeEsercizio(String nomeEsercizio) {
        this.nomeEsercizio = nomeEsercizio;
    }

    @Override
    public String getTipoEsercizio() {
        return tipoEsercizio;
    }

    @Override
    public void setTipoEsercizio(String tipoEsercizio) {
        this.tipoEsercizio = tipoEsercizio;
    }

    @Override
    public String getSerie() {
        return serie;
    }

    @Override
    public void setSerie(String serie) {
        this.serie = serie;
    }

    @Override

    public String getRecupero() {
        return recupero;
    }

    @Override
    public void setRecupero(String recupero) {
        this.recupero = recupero;
    }

    @Override
    public Boolean getVideo() {
        return isVideo;
    }

    @Override
    public void setVideo(Boolean video) {
        this.isVideo = video;
    }

    @Override
    public String getIndicazioniCoach() {
        return indicazioniCoach;
    }

    @Override
    public void setIndicazioniCoach(String indicazioniCoach) {
        this.indicazioniCoach = indicazioniCoach;
    }

    @Override
    public String getAppuntiAtleta() {
        return appuntiAtleta;
    }

    @Override
    public void setAppuntiAtleta(String appuntiAtleta) {
        this.appuntiAtleta = appuntiAtleta;
    }

    @Override
    public String getInizio() {
        return inizio;
    }

    @Override
    public void setInizio(String inizio) {
        this.inizio = inizio;
    }

    @Override
    public String getPicco() {
        return picco;
    }

    @Override
    public void setPicco(String picco) {
        this.picco = picco;
    }

    @Override
    public String getRecuperoSerie() {
        return null;
    }

    @Override
    public void setRecuperoSerie(String recuperoSerie) {

    }

    @Override
    public String getRipetizioni() {
        return null;
    }

    @Override
    public void setRipetizioni(String ripetizioni) {

    }

    @Override
    public String getTempoEsecuzione() {
        return null;
    }

    @Override
    public void setTempoEsecuzione(String tempoEsecuzione) {

    }

    @Override
    public String toString() {
        return "nomeEsercizio: " + nomeEsercizio +
                ", tipoEsercizio: " + tipoEsercizio +
                ", serie: " + serie +
                ", inizio: " + inizio +
                ", picco: " + picco +
                ", recupero: " + recupero;
    }

    @Override
    public String toStringUI() {
        return nomeEsercizio +
                ", " + serie +
                " x " + inizio +
                "-" + picco +
                ", rec: " + recupero + "\"";
    }

    @Override
    public String toStringResumeExe() {
        return " Nome esercizio: " + nomeEsercizio +
                "\n Numero serie: " + serie +
                "\n Ripetizioni: " + inizio + "-" + picco +
                "\n Recupero: " + recupero;
    }

    @Override
    public String getRipetizioneEsercizioString() {
        return "Ripetizioni: " + Integer.parseInt(this.getInizio());
    }
}
