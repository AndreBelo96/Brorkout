package com.andrea.belotti.brorkout.model;


public class EsercizioBaseModel {
    String nomeExeStr;
    String typeStr;
    String recoverStr;
    String serieStr;
    Boolean video;
    String indicazioniCoach;
    String appuntiAtleta;

    public String getNomeExeStr() {
        return nomeExeStr;
    }

    public void setNomeExeStr(String nomeExeStr) {
        this.nomeExeStr = nomeExeStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getRecoverStr() {
        return recoverStr;
    }

    public void setRecoverStr(String recoverStr) {
        this.recoverStr = recoverStr;
    }

    public String getSerieStr() {
        return serieStr;
    }

    public void setSerieStr(String serieStr) {
        this.serieStr = serieStr;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getIndicazioniCoach() {
        return indicazioniCoach;
    }

    public void setIndicazioniCoach(String indicazioniCoach) {
        this.indicazioniCoach = indicazioniCoach;
    }

    public String getAppuntiAtleta() {
        return appuntiAtleta;
    }

    public void setAppuntiAtleta(String appuntiAtleta) {
        this.appuntiAtleta = appuntiAtleta;
    }
}
