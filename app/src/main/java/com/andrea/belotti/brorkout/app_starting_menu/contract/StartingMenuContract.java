package com.andrea.belotti.brorkout.app_starting_menu.contract;

import android.graphics.Bitmap;

public interface StartingMenuContract {

    interface Presenter {
        void onArchiveClick();
        void onCreatePlanClick();
        void onPersonalAreaClick();
        void onSelectionClick();
        void onOptionClick();
        void onLogoutClick();
        String getUsername();
        Bitmap getImage();
    }

    interface View {
        void replaceWithScheduleArchiveActivity(String message);
        void replaceWithScheduleCreatorActivity(String message);
        void replaceWithSelectionPlanActivity(String message);
        void replaceWithPersonalAreaActivity(String message);
        void replaceWithOptionActivity(String message);
        void replaceWithIntroActivity(String message);
    }

}
