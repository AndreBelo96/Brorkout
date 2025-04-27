package com.andrea.belotti.brorkout.model;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.ExeType.SERIE;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class EsercizioSerie implements Esercizio, Serializable {

    private String type = SERIE;
    private String nomeEsercizio;
    private String tipoEsercizio;
    private String serie;
    private int serieCompletate;
    private String recupero;
    private Boolean isVideo;
    private String indicazioniCoach;
    private String appuntiAtleta;
    private String ripetizioni;
    private String elastico;
    private String zavorre;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
                ", ripetizioni: " + ripetizioni +
                ", recupero: " + recupero;
    }

    @Override
    public EsercizioSerie toObjectForDB(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(EsercizioIncrementale.class, new InterfaceAdapter());
        Gson gson = builder.create();
        return gson.fromJson(jsonString, EsercizioSerie.class);
    }

    @Override
    public String getRepForExecution() {
        return "Rep: " + ripetizioni;
    }

    @Override
    public String getSetForExecution() {
        return "Set: " + this.getSerieCompletate() + "/" + this.getSerie();
    }

    @Override
    public String getRecForExecution() {
        return "Rec: " + this.getRecupero() + "\"";
    }

    @Override
    public void setNumeroRipetizioniDopoSerie(){
        // Non si incrementano le ripetizioni per un esercizio di tipo serie
    }

    @Override
    public boolean isExeNotOk() {
        return StringUtils.isEmpty(this.getSerie()) ||
                StringUtils.isEmpty(this.getRecupero()) ||
                StringUtils.isEmpty(this.getName()) ||
                StringUtils.isEmpty(this.getRipetizioni());
    }

    @Override
    public CompleteState isExeComplete() {
        if (serieCompletate == 0) {
            return CompleteState.INCOMPLETE_KO;
        } else if (serieCompletate < Integer.valueOf(serie)) {
            return CompleteState.COMPLETE_OK;
        } else {
            return CompleteState.INCOMPLETE;
        }
    }

}
