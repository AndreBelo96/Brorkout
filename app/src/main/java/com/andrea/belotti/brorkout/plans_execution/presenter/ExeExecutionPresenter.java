package com.andrea.belotti.brorkout.plans_execution.presenter;

import android.view.View;

import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_execution.contract.ExeExecutionContract;

import java.util.List;


public class ExeExecutionPresenter implements ExeExecutionContract.Presenter {


    @Override
    public String setNextExerciseName(List<Esercizio> esercizioList, int actualExe) {
        if (actualExe + 1 < esercizioList.size()) {
            return esercizioList.get(actualExe + 1).getName();
        } else {
            return "--";
        }
    }

    @Override
    public String setPreviousExerciseName(List<Esercizio> esercizioList, int actualExe) {
        if (actualExe <= 0) {
            return "--";
        } else {
            return esercizioList.get(actualExe - 1).getName();
        }
    }

    @Override
    public int setVideoVisibility(Esercizio actualExe) {
        if (actualExe.getSerieCompletate() >= Integer.parseInt(actualExe.getSerie()) - 1 && actualExe.getVideo()) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }


}
