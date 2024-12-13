package com.andrea.belotti.brorkout.fragment.schedule_creator;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicButtonLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.ArrayList;
import java.util.List;

public class CreationMenuFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Boolean isNew = false;
    private Boolean isCopy = false;
    private Scheda selectedPlan = null;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_menu, container, false);

        // Take main activity and context
        ScheduleCreatorActivity activity = (ScheduleCreatorActivity) this.getActivity();
        Context context = getContext();

        // Retrieve data - TODO capire se serve tutto scriverlo meglio
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        List<Scheda> schedaList = ScheduleCreatingUtils.createListaSchede(sharedPreferences);

        // ------------------------ Initialize Variables ------------------------

        // Take Db switches
        Switch switchDb = view.findViewById(R.id.switchDb);
        Switch switchPrivate = view.findViewById(R.id.switchPrivate);

        // Title text
        EditText planTitle = view.findViewById(R.id.titoloScheda);

        // Take layout from xml
        LinearLayout newSchedule = view.findViewById(R.id.new_schedule_button);
        LinearLayout copyScheduleBtn = view.findViewById(R.id.copy_schedule_button);

        // New Schedule layout
        LinearLayout buttonDaysContainer = view.findViewById(R.id.dayNumberContainer);
        LinearLayout buttonDay1 = view.findViewById(R.id.buttonDay1);
        LinearLayout buttonDay2 = view.findViewById(R.id.buttonDay2);
        LinearLayout buttonDay3 = view.findViewById(R.id.buttonDay3);
        LinearLayout buttonDay4 = view.findViewById(R.id.buttonDay4);
        LinearLayout buttonDay5 = view.findViewById(R.id.buttonDay5);
        LinearLayout buttonDay6 = view.findViewById(R.id.buttonDay6);
        LinearLayout buttonDay7 = view.findViewById(R.id.buttonDay7);

        // Copy Schedule layout
        LinearLayout copyPlanContainer = view.findViewById(R.id.copy_plan_container);
        LinearLayout plansContainer = view.findViewById(R.id.list_plans_container);

        // Create Button
        LinearLayout createPlanButton = view.findViewById(R.id.confirm_button);


        // ------------------------ Set Variables ------------------------

        final String[] day = {""};
        List<LinearLayout> daysButtonList = new ArrayList<>();
        daysButtonList.add(buttonDay1);
        daysButtonList.add(buttonDay2);
        daysButtonList.add(buttonDay3);
        daysButtonList.add(buttonDay4);
        daysButtonList.add(buttonDay5);
        daysButtonList.add(buttonDay6);
        daysButtonList.add(buttonDay7);

        // TODO metti qui la scheda scelta non come globale
        List<LinearLayout> plansButtonList = new ArrayList<>();


        if (schedaList != null && !schedaList.isEmpty()) {
            createView(schedaList, plansContainer, plansButtonList, context);
        } else {
            Log.e(tag, "Lista schede vuota");
        }

        if (!switchDb.isChecked()) {
            switchPrivate.setVisibility(View.INVISIBLE);
        }


        // ---------------------- ClickListeners ----------------------

        switchDb.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                switchDb.setText(ExerciseConstants.DataBase.CLOUD);
                switchPrivate.setVisibility(View.VISIBLE);
                activity.setLocal(false);
            } else {
                switchDb.setText(ExerciseConstants.DataBase.LOCAL);
                switchPrivate.setVisibility(View.INVISIBLE);
                activity.setLocal(true);
            }

        });

        switchPrivate.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                switchPrivate.setText(ExerciseConstants.DataBase.PUBLIC);
            } else {
                switchPrivate.setText(ExerciseConstants.DataBase.PRIVATE);
            }

            //isPublic = isChecked;
        });

        // ---------------------- New Schedule Click Listeners ----------------------
        newSchedule.setOnClickListener(v -> {
            isNew = !isNew;
            isCopy = false;

            if (isNew) {
                newSchedule.setBackgroundResource(R.drawable.basic_button_pressed_bg);
                buttonDaysContainer.setVisibility(View.VISIBLE);
                createPlanButton.setVisibility(View.VISIBLE);
            } else {
                newSchedule.setBackgroundResource(R.drawable.blue_top_button); //TODO metodo
                buttonDaysContainer.setVisibility(View.GONE);
                createPlanButton.setVisibility(View.GONE);
                day[0] = "";
                ScheduleCreatingUtils.setBasicColor(daysButtonList);
            }

            copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
            copyPlanContainer.setVisibility(View.GONE);
            selectedPlan = null;
            ScheduleCreatingUtils.setBasicColor(plansButtonList);

        });

        daysButtonList.forEach(b -> b.setOnClickListener(v -> {
            day[0] = ((TextView) b.getChildAt(0)).getText().toString();
            ScheduleCreatingUtils.setBasicColor(daysButtonList);
            b.setBackgroundResource(R.drawable.basic_button_pressed_bg);
        }));

        // ---------------------- Copy Schedule Click Listeners ----------------------
        copyScheduleBtn.setOnClickListener(v -> {

            isCopy = !isCopy;
            isNew = false;

            if (isCopy) {
                copyScheduleBtn.setBackgroundResource(R.drawable.basic_button_pressed_bg);
                createPlanButton.setVisibility(View.VISIBLE);
                copyPlanContainer.setVisibility(View.VISIBLE);
            } else {
                copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
                createPlanButton.setVisibility(View.GONE);
                copyPlanContainer.setVisibility(View.GONE);
                ScheduleCreatingUtils.setBasicColor(plansButtonList);
                selectedPlan = null;
            }

            newSchedule.setBackgroundResource(R.drawable.blue_top_button);
            buttonDaysContainer.setVisibility(View.GONE);
            day[0] = "";
            ScheduleCreatingUtils.setBasicColor(daysButtonList);

            /*CopyScheduleFragment*/
        });

        // ---------------------- Create Click Listeners ----------------------

        createPlanButton.setOnClickListener(v -> {

            if (isNew) {
                String days = day[0];
                String scheduleName = planTitle.getText().toString();

                if (scheduleName.isEmpty()) {
                    Log.e(tag, "Titolo scheda vuoto");
                    Toast toast = Toast.makeText(context, "Titolo scheda vuoto", StringOutputConstants.shortDuration);
                    toast.show();
                    return;
                }
                if (days.isEmpty()) {
                    Log.e(tag, "Numero di giorni non selezionato");
                    Toast toast = Toast.makeText(context, "Numero di giorni non selezionato", StringOutputConstants.shortDuration);
                    toast.show();
                    return;
                }

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(scheduleName, Integer.parseInt(days)));
                fragmentTransaction.commit();
            }

            if (isCopy) {

                String scheduleName = planTitle.getText().toString();

                if (scheduleName.isEmpty()) {
                    Log.e(tag, "Titolo scheda vuoto");
                    Toast toast = Toast.makeText(context, "Titolo scheda vuoto", StringOutputConstants.shortDuration);
                    toast.show();
                    return;
                }

                if (selectedPlan == null) {
                    Log.e(tag, "Scheda da clonare non scelta");
                    Toast toast = Toast.makeText(context, "Scheda da clonare non scelta", StringOutputConstants.shortDuration);
                    toast.show();
                    return;
                }

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(scheduleName, selectedPlan.getGiornate().size(), selectedPlan));
                fragmentTransaction.commit();
            }

        });

        return view;
    }


    private void createView(List<Scheda> schedaList, LinearLayout plansContainer, List<LinearLayout> plansButtonList, Context context) {

        for (Scheda scheda : schedaList) {
            LinearLayout planButton = createBasicButtonLayout(context, scheda.getNome(), 16f);

            plansButtonList.add(planButton);
            plansContainer.addView(planButton);

            planButton.setOnClickListener(v -> {
                ScheduleCreatingUtils.setBasicColor(plansButtonList);
                planButton.setBackgroundResource(R.drawable.basic_button_pressed_bg);
                selectedPlan = scheda;
            });

        }
    }

}