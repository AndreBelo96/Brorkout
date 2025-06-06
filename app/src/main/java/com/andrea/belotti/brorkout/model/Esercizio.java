package com.andrea.belotti.brorkout.model;

import androidx.annotation.Keep;

@Keep
public interface Esercizio {

    String getName();
    void setName(String name);

    String getTipoEsercizio();
    void setTipoEsercizio(String tipoEsercizio);

    String getSerie();
    void setSerie(String serie);

    int getSerieCompletate();
    void setSerieCompletate(int serieCompletate);

    String getRecupero();
    void setRecupero(String recupero);

    boolean getVideo();
    void setVideo(boolean video);

    String getIndicazioniCoach();
    void setIndicazioniCoach(String indicazioniCoach);

    String getAppuntiAtleta();
    void setAppuntiAtleta(String appuntiAtleta);

    String getInizio();
    void setInizio(String inizio);

    String getPicco();
    void setPicco(String picco);

    String getRecuperoSerie();
    void setRecuperoSerie(String recuperoSerie);

    String getRipetizioni();
    void setRipetizioni(String ripetizioni);

    String getTempoEsecuzione();
    void setTempoEsecuzione(String tempoEsecuzione);

    String getElastico();
    void setElastico(String elastico);

    String getZavorre();
    void setZavorre(String zavorre);

    String toString();

    Esercizio toObjectForDB(String jsonString);

    String getRepForExecution();

    String getSetForExecution();

    String getRecForExecution();

    void setNumeroRipetizioniDopoSerie();

    boolean isExeNotOk();

    CompleteState isExeComplete();

    boolean isExeTypeTenuta();
}
