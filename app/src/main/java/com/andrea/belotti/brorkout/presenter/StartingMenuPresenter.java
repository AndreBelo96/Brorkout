package com.andrea.belotti.brorkout.presenter;

import android.content.Intent;
import android.view.View;

import com.andrea.belotti.brorkout.view.creation.ScheduleCreatorActivity;

public class StartingMenuPresenter {

    private View view;

    public StartingMenuPresenter(View  view) {
        this.view = view;
    }


    public void onCreatePlanClick() {
        Intent intent = new Intent(view.getContext(), ScheduleCreatorActivity.class);
        intent.putExtra("modifica", false);
        //startActivity(intent);

    }
}
