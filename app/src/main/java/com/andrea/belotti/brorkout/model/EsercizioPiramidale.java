package com.andrea.belotti.brorkout.model;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.ExeType.PIRAMIDALE;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class EsercizioPiramidale implements Esercizio, Serializable {

    private String type = PIRAMIDALE;
    private String nomeEsercizio;
    private String tipoEsercizio;
    private String serie;
    private int serieCompletate;
    private String recupero;
    private Boolean isVideo;
    private String indicazioniCoach;
    private String appuntiAtleta;
    private String inizio;
    private String ripetizioni;
    private String picco;
    private String recuperoSerie;
    private String elastico;
    private String zavorre;

    @Override
    public String getName() {
        return nomeEsercizio;
    }

    @Override
    public void setName(String name) {
        this.nomeEsercizio = name;
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
    public int getSerieCompletate() {
        return serieCompletate;
    }

    @Override
    public void setSerieCompletate(int serieCompletate) {
        this.serieCompletate = serieCompletate;
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
        return recuperoSerie;
    }

    @Override
    public void setRecuperoSerie(String recuperoSerie) {
        this.recuperoSerie = recuperoSerie;
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
    public String getElastico() {
        return elastico;
    }

    @Override
    public void setElastico(String elastico) {
        this.elastico = elastico;
    }

    @Override
    public String getZavorre() {
        return zavorre;
    }

    @Override
    public void setZavorre(String zavorre) {
        this.zavorre = zavorre;
    }


    @Override
    public String toString() {
        return "nomeEsercizio: " + nomeEsercizio +
                ", tipoEsercizio: " + tipoEsercizio +
                ", serie: " + serie +
                ", serieCompletate: " + serieCompletate +
                ", inizio: " + inizio +
                ", picco: " + picco +
                ", recuperoSerie: " + getRecuperoSerie() +
                ", recupero: " + recupero;
    }

    @Override
    public String toStringResumeEndSchedule() {
        return " Nome esercizio: " + nomeEsercizio +
                "\n Numero serie: " + serieCompletate + "/" + serie +
                "\n Ripetizioni: " + inizio + "-" + picco +
                "\n Commenti atleta: " + appuntiAtleta;
    }

    @Override
    public String getRipetizioneEsercizioString() {
        return "Ripetizioni: " + ripetizioni;
    }

    @Override
    public void setNumeroRipetizioniDopoSerie(){
        //int incrementoRipetizioni = (Integer.parseInt(picco) - Integer.parseInt(inizio)) / (Integer.parseInt(serie)-1);
        this.setRipetizioni((Integer.parseInt(this.getRipetizioni()) + 1) + "");
    }

    @Override
    public boolean isExeNotOk() {
        return StringUtils.isEmpty(this.getSerie()) ||
                StringUtils.isEmpty(this.getRecupero()) ||
                StringUtils.isEmpty(this.getName()) ||
                StringUtils.isEmpty(this.getInizio()) ||
                StringUtils.isEmpty(this.getPicco());
    }

}
