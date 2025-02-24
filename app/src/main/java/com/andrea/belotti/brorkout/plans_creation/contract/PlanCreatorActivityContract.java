package com.andrea.belotti.brorkout.plans_creation.contract;


import androidx.fragment.app.Fragment;

import com.andrea.belotti.brorkout.entity.Scheda;

public interface PlanCreatorActivityContract {

    interface Presenter {
        void onBackClick();
        void setFragmentActivityCreate(boolean isUpdate, Scheda chosenPlan);
    }

    interface View {
        void replaceWithStartingMenuActivity(String message);
        void setFragmentInView(Fragment fragment);
    }
}
