package com.andrea.belotti.brorkout.model;

import java.io.Serializable;

public class EsercizioTenuta implements Esercizio, Serializable {

    private String type = "Tenuta";
    private String nomeEsercizio;
    private String tipoEsercizio;
    private String serie;
    private String recupero;
    private Boolean isVideo;
    private String indicazioniCoach;
    private String appuntiAtleta;
    private String ripetizioni;
    private String tempoEsecuzione;

    public EsercizioTenuta(){

    }

    public EsercizioTenuta(String nomeEsercizio,
                           String tipoEsercizio,
                           String serie,
                           String recupero,
                           Boolean isVideo,
                           String indicazioniCoach,
                           String appuntiAtleta,
                           String ripetizioni,
                           String tempoEsecuzione) {
        this.nomeEsercizio = nomeEsercizio;
        this.tipoEsercizio = tipoEsercizio;
        this.serie = serie;
        this.recupero = recupero;
        this.isVideo = isVideo;
        this.indicazioniCoach = indicazioniCoach;
        this.appuntiAtleta = appuntiAtleta;
        this.ripetizioni = ripetizioni;
        this.tempoEsecuzione = tempoEsecuzione;
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
    }

    @Override
    public String getInizio() {
        return null;
    }

    @Override
    public void setInizio(String inizio) {
    }

    @Override
    public String getPicco() {
        return null;
    }

    @Override
    public void setPicco(String picco) {
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
        return tempoEsecuzione;
    }

    @Override
    public void setTempoEsecuzione(String tempoEsecuzione) {
        this.tempoEsecuzione = tempoEsecuzione;
    }


    @Override
    public String toString() {
        return "nomeEsercizio: " + nomeEsercizio +
                ", tipoEsercizio: " + tipoEsercizio +
                ", serie: " + serie +
                ", tempoEsecuzione: " + tempoEsecuzione +
                ", recupero: " + recupero;
    }

    @Override
    public String toStringUI() {
        return  nomeEsercizio +
                ", " + serie +
                " x " + ripetizioni +
                ", rec: " + recupero + "\"";
    }

    @Override
    public String toStringResumeExe() {
        return " Nome esercizio: " + nomeEsercizio +
                "\n Numero serie: " + serie +
                "\n Ripetizioni: " + ripetizioni + " per " + tempoEsecuzione + "\"" +
                "\n Recupero: " + recupero;
    }

    @Override
    public String getRipetizioneEsercizioString() {
        return "Ripetizioni: " + Integer.parseInt(this.getRipetizioni());
    }

    @Override
    public void setNumeroRipetizioniDopoSerie(){
        // Non si incrementano le ripetizioni per un esercizio di tipo serie
    }
}
