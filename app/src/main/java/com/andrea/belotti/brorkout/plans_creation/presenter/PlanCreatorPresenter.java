package com.andrea.belotti.brorkout.plans_creation.presenter;

import android.content.Context;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.plans_creation.contract.PlanCreatorContract;

public class PlanCreatorPresenter implements PlanCreatorContract.Presenter {

    private final PlanCreatorContract.View view;
    private final Context context;

    public PlanCreatorPresenter(PlanCreatorContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onBackClick() {
        view.replaceWithStartingMenuActivity(context.getResources().getString(R.string.StartingMenuActivity));
    }

}
