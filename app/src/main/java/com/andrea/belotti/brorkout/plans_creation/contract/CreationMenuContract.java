package com.andrea.belotti.brorkout.plans_creation.contract;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.andrea.belotti.brorkout.adapter.CreationCopyPlanAdapter;
import com.andrea.belotti.brorkout.model.User;
import com.andrea.belotti.brorkout.plans_creation.view.CreationPlanFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public interface CreationMenuContract {

    interface Presenter {
        void onNewPlanClick(LinearLayout newSchedule,
                            LinearLayout buttonDaysContainer,
                            LinearLayout createPlanButton,
                            LinearLayout shareSelectionLayout,
                            LinearLayout copyScheduleBtn,
                            LinearLayout copyPlanContainer,
                            LinearLayout infoPlanContainer,
                            LinearLayout shareInfoContainer,
                            List<LinearLayout> daysButtonList,
                            CreationCopyPlanAdapter adapter);

        void onCopyPlanClick(LinearLayout newSchedule,
                             LinearLayout buttonDaysContainer,
                             LinearLayout createPlanButton,
                             LinearLayout shareSelectionLayout,
                             LinearLayout copyScheduleBtn,
                             LinearLayout copyPlanContainer,
                             LinearLayout infoPlanContainer,
                             LinearLayout shareInfoContainer,
                             List<LinearLayout> daysButtonList,
                             CreationCopyPlanAdapter adapter);

        void onBackInfoClick(LinearLayout copyPlanContainer,
                             LinearLayout infoPlanContainer,
                             LinearLayout createPlanButton,
                             LinearLayout shareSelectionLayout);

        void onShareClick(LinearLayout createPlanButton,
                          LinearLayout shareSelectionLayout,
                          LinearLayout shareInfoContainer);

        void onBackShareClick(LinearLayout createPlanButton,
                              LinearLayout shareSelectionLayout,
                              LinearLayout shareInfoContainer);

        void onPersonalPlanClick(List<User> users,
                                 LinearLayout shareBtn,
                                 CheckBox isPersonalBtn,
                                 FirebaseUser currentFireBaseUser,
                                 ValueEventListener currentUserListener);

        void onDayButtonClick(LinearLayout button, List<LinearLayout> daysButtonList);

        void onCreatePlanClick(List<User> users, EditText planTitle);
    }

    interface View {
        void logTagOnScreen(String message);

        void replaceFragment(CreationPlanFragment fragment);
    }

}
