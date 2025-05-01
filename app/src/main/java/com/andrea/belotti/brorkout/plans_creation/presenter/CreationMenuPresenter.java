package com.andrea.belotti.brorkout.plans_creation.presenter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.CreationCopyPlanAdapter;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.User;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_creation.CreateSingleton;
import com.andrea.belotti.brorkout.plans_creation.contract.CreationMenuContract;
import com.andrea.belotti.brorkout.plans_creation.view.CreationPlanFragment;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreationMenuPresenter implements CreationMenuContract.Presenter {

    private final CreationMenuContract.View view;
    private boolean isNew;
    private boolean isCopy;
    private String day = "";
    private Scheda selectedPlan = null;

    public CreationMenuPresenter(CreationMenuContract.View view) {
        this.view = view;
    }

    @Override
    public void onNewPlanClick(LinearLayout newSchedule,
                               LinearLayout buttonDaysContainer,
                               LinearLayout createPlanButton,
                               LinearLayout shareSelectionLayout,
                               LinearLayout copyScheduleBtn,
                               LinearLayout copyPlanContainer,
                               LinearLayout infoPlanContainer,
                               LinearLayout shareInfoContainer,
                               List<LinearLayout> daysButtonList,
                               CreationCopyPlanAdapter adapter) {
        isNew = !isNew;
        isCopy = false;

        if (isNew) {
            newSchedule.setBackgroundResource(R.drawable.basic_button_pressed_bg);
            buttonDaysContainer.setVisibility(View.VISIBLE);
            createPlanButton.setVisibility(View.VISIBLE);
            shareSelectionLayout.setVisibility(View.VISIBLE);
        } else {
            newSchedule.setBackgroundResource(R.drawable.blue_top_button);
            buttonDaysContainer.setVisibility(View.GONE);
            createPlanButton.setVisibility(View.GONE);
            shareSelectionLayout.setVisibility(View.GONE);
            day = "";
            ScheduleCreatingUtils.setBasicColor(daysButtonList);
        }

        copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
        copyPlanContainer.setVisibility(View.GONE);
        infoPlanContainer.setVisibility(View.GONE);
        shareInfoContainer.setVisibility(View.GONE);
        selectedPlan = null;
        ScheduleCreatingUtils.setCardViewBasicColor(adapter.getCardViewList());

    }

    @Override
    public void onCopyPlanClick(LinearLayout newSchedule,
                                LinearLayout buttonDaysContainer,
                                LinearLayout createPlanButton,
                                LinearLayout shareSelectionLayout,
                                LinearLayout copyScheduleBtn,
                                LinearLayout copyPlanContainer,
                                LinearLayout infoPlanContainer,
                                LinearLayout shareInfoContainer,
                                List<LinearLayout> daysButtonList,
                                CreationCopyPlanAdapter adapter) {
        isCopy = !isCopy;
        isNew = false;

        if (isCopy) {
            copyScheduleBtn.setBackgroundResource(R.drawable.basic_button_pressed_bg);
            createPlanButton.setVisibility(View.VISIBLE);
            shareSelectionLayout.setVisibility(View.VISIBLE);
            copyPlanContainer.setVisibility(View.VISIBLE);
            infoPlanContainer.setVisibility(View.GONE);
            shareInfoContainer.setVisibility(View.GONE);
        } else {
            copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
            createPlanButton.setVisibility(View.GONE);
            shareSelectionLayout.setVisibility(View.GONE);
            copyPlanContainer.setVisibility(View.GONE);
            infoPlanContainer.setVisibility(View.GONE);
            shareInfoContainer.setVisibility(View.GONE);
            ScheduleCreatingUtils.setCardViewBasicColor(adapter.getCardViewList());
            selectedPlan = null;
        }

        newSchedule.setBackgroundResource(R.drawable.blue_top_button);
        buttonDaysContainer.setVisibility(View.GONE);
        infoPlanContainer.setVisibility(View.GONE);
        shareInfoContainer.setVisibility(View.GONE);
        day = "";
        ScheduleCreatingUtils.setBasicColor(daysButtonList);
    }

    @Override
    public void onBackInfoClick(LinearLayout copyPlanContainer,
                                LinearLayout infoPlanContainer,
                                LinearLayout createPlanButton,
                                LinearLayout shareSelectionLayout) {
        infoPlanContainer.setVisibility(View.GONE);
        copyPlanContainer.setVisibility(View.VISIBLE);
        createPlanButton.setVisibility(View.VISIBLE);
        shareSelectionLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShareClick(LinearLayout createPlanButton,
                             LinearLayout shareSelectionLayout,
                             LinearLayout shareInfoContainer) {
        shareInfoContainer.setVisibility(View.VISIBLE);
        shareSelectionLayout.setVisibility(View.GONE);
        createPlanButton.setVisibility(View.GONE);
    }

    @Override
    public void onBackShareClick(LinearLayout createPlanButton,
                                 LinearLayout shareSelectionLayout,
                                 LinearLayout shareInfoContainer) {
        shareInfoContainer.setVisibility(View.GONE);
        shareSelectionLayout.setVisibility(View.VISIBLE);
        createPlanButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPersonalPlanClick(List<User> users,
                                    LinearLayout shareBtn,
                                    CheckBox isPersonalBtn,
                                    FirebaseUser currentFireBaseUser,
                                    ValueEventListener currentUserListener) {

        if (isPersonalBtn.isChecked()) {
            shareBtn.setVisibility(View.INVISIBLE);
            users.clear();
            UserRepository.getInstance().getById(currentFireBaseUser.getUid(), currentUserListener);
        } else {
            shareBtn.setVisibility(View.VISIBLE);
            users.clear();
        }
    }

    @Override
    public void onDayButtonClick(LinearLayout button, List<LinearLayout> daysButtonList) {
        day = ((TextView) button.getChildAt(0)).getText().toString();
        ScheduleCreatingUtils.setBasicColor(daysButtonList);
        button.setBackgroundResource(R.drawable.basic_button_pressed_bg);
    }

    @Override
    public void onCreatePlanClick(List<User> users, EditText planTitle) {

        if (isNew) {
            String scheduleName = planTitle.getText().toString();

            if (scheduleName.isEmpty()) {
                view.logTagOnScreen("Titolo scheda vuoto");
                return;
            }

            if (day.isEmpty()) {
                view.logTagOnScreen("Numero di giorni non selezionato");
                return;
            }

            // Save Users in singleton
            CreateSingleton.getInstance().setUsersToShare(users);

            String now = LocalDate.now().toString();
            int numberOfDays = Integer.parseInt(day);

            List<Giornata> giornateList = new ArrayList<>();
            for (int i = 1; i <= numberOfDays; i++) {
                Giornata g = new Giornata();
                List<Esercizio> exeList = new ArrayList<>();

                String date = LocalDate.now().toString();

                g.setExercises(exeList);
                g.setCreationDate(date);
                g.setUpdateDate(date);
                g.setUsed(false);
                g.setNumberOfDay(i);
                giornateList.add(g);
            }

            Scheda plan = new Scheda();
            plan.setNome(scheduleName);
            plan.setNumeroGiornate(numberOfDays);
            plan.setGiornate(giornateList);
            plan.setCreationDate(now);
            plan.setUpdateDate(now);

            view.replaceFragment(CreationPlanFragment.newInstance(plan));
        }

        if (isCopy) {

            String scheduleName = planTitle.getText().toString();

            if (scheduleName.isEmpty()) {
                view.logTagOnScreen("Titolo scheda vuoto");
                return;
            }

            if (selectedPlan == null) {
                view.logTagOnScreen("Scheda da clonare non scelta");
                return;
            }
            String now = LocalDate.now().toString();

            selectedPlan.setNome(scheduleName);
            selectedPlan.setCreationDate(now);
            selectedPlan.setUpdateDate(now);

            view.replaceFragment(CreationPlanFragment.newInstance(selectedPlan));
        }
    }
}
