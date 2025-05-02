package com.andrea.belotti.brorkout.plans_archive.presenter;

import android.widget.TextView;

import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.contract.ExercisesContract;


public class ExercisesPresenter implements ExercisesContract.Presenter {


    @Override
    public void setTitle(TextView titleTV, int day) {

        StringBuilder exeTile = new StringBuilder("Scheda ")
                .append(ArchiveSingleton.getInstance().getPlanName())
                .append(" di ")
                .append(ArchiveSingleton.getInstance().getSelectedUser().getUsername())
                .append(" Giornata ")
                .append(day);

        titleTV.setText(exeTile);

    }


}
