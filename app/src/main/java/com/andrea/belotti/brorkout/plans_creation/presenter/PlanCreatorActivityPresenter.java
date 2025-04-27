package com.andrea.belotti.brorkout.plans_creation.presenter;

import android.content.Context;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.plans_creation.contract.PlanCreatorActivityContract;
import com.andrea.belotti.brorkout.plans_creation.view.CreationMenuFragmentView;
import com.andrea.belotti.brorkout.plans_creation.view.CreationPlanFragment;

import java.time.LocalDate;


public class PlanCreatorActivityPresenter implements PlanCreatorActivityContract.Presenter {

    private final PlanCreatorActivityContract.View view;
    private final Context context;

    public PlanCreatorActivityPresenter(PlanCreatorActivityContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onBackClick() {
        view.replaceWithStartingMenuActivity(context.getResources().getString(R.string.StartingMenuActivity));
    }

    @Override
    public void setFragmentActivityCreate(boolean isUpdate, Scheda chosenPlan) {
        if (isUpdate) {
            chosenPlan.setUpdateDate(LocalDate.now().toString());
            view.setFragmentInView(CreationPlanFragment.newInstance(chosenPlan));
        } else {
            view.setFragmentInView(new CreationMenuFragmentView());
        }
    }

}
