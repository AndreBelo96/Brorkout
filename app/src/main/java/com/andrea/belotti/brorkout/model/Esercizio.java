package com.andrea.belotti.brorkout.model;

import androidx.annotation.Keep;

@Keep
public interface Esercizio {

    String getNomeEsercizio();
    void setNomeEsercizio(String nomeEsercizio);

    String getTipoEsercizio();
    void setTipoEsercizio(String tipoEsercizio);

    String getSerie();
    void setSerie(String serie);

    int getSerieCompletate();
    void setSerieCompletate(int serieCompletate);

    String getRecupero();
    void setRecupero(String recupero);

    Boolean getVideo();
    void setVideo(Boolean video);

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

    String toString();

    String toStringUI();

    String toStringResumeExe();

    String getRipetizioneEsercizioString();

    void setNumeroRipetizioniDopoSerie();

}
