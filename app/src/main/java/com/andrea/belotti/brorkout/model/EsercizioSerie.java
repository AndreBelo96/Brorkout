package com.andrea.belotti.brorkout.model;

import java.io.Serializable;

public class EsercizioSerie implements Esercizio, Serializable {

    private String type = "Serie";
    private String nomeEsercizio;
    private String tipoEsercizio;
    private String serie;
    private String recupero;
    private Boolean isVideo;
    private String indicazioniCoach;
    private String appuntiAtleta;
    private String ripetizioni;

    public EsercizioSerie(){
        // non mi serve un costruttore per ora, creo l'oggetto vuoto e uso i setter
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
    public String getRecuperoSerie() {
        return null;
    }

    @Override
    public void setRecuperoSerie(String recuperoSerie) {
        // non esiste il recupero di serie negli esercizi di serie, perché corrisponde al recupero
    }

    @Override
    public String getInizio() {
        return null;
    }

    @Override
    public void setInizio(String inizio) {
        // non esiste l'inizio in una serie normale perché corrisponde alle ripetizioni
    }

    @Override
    public String getPicco() {
        return null;
    }

    @Override
    public void setPicco(String picco) {
        // non esiste il picco in una serie normale perché corrisponde alle ripetizioni
    }

    @Override
    public String getRipetizioni() {
        return ripetizioni;
    }

    @Override
    public void setRipetizioni(String ripetizioni) {
        this.ripetizioni = ripetizioni;
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
                ", ripetizioni: " + ripetizioni +
                ", recupero: " + recupero;
    }

    @Override
    public String toStringUI() {
        return nomeEsercizio +
                ", " + serie +
                " x " + ripetizioni +
                ", rec: " + recupero + "\"";
    }

    @Override
    public String toStringResumeExe() {
        return " Nome esercizio: " + nomeEsercizio +
                "\n Numero serie: " + serie +
                "\n Ripetizioni: " + ripetizioni +
                "\n Recupero: " + recupero;
    }

    @Override
    public String getRipetizioneEsercizioString() {
        return "Ripetizioni: " + ripetizioni;
    }

    @Override
    public void setNumeroRipetizioniDopoSerie(){
        // Non si incrementano le ripetizioni per un esercizio di tipo serie
    }

}
