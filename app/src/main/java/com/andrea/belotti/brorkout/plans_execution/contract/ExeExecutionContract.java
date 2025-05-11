package com.andrea.belotti.brorkout.plans_execution.contract;

import com.andrea.belotti.brorkout.model.Esercizio;

import java.util.List;

public interface ExeExecutionContract {

    interface Presenter {
        String setNextExerciseName(List<Esercizio> esercizioList, int actualExe);
        String setPreviousExerciseName(List<Esercizio> esercizioList, int actualExe);
        int setVideoVisibility(Esercizio actualExe);
    }

    interface View {

    }

}
