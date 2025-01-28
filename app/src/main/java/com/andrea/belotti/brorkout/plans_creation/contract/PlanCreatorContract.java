package com.andrea.belotti.brorkout.plans_creation.contract;

import android.app.Activity;

public interface PlanCreatorContract {

    interface Presenter {
        void onBackClick();
    }

    interface View {
        void replaceWithStartingMenuActivity(String message);
    }
}
