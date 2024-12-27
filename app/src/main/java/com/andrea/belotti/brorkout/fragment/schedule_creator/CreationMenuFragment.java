package com.andrea.belotti.brorkout.fragment.schedule_creator;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createHorizotalLinearLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.adapter.CreationCopyPlanAdapter;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.ArrayList;
import java.util.List;

public class CreationMenuFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Boolean isNew = false;
    private Boolean isCopy = false;

    private Scheda selectedPlan = null;
    private String day = "";

    private LinearLayout treePlanContainer;
    private LinearLayout infoPlanContainer;
    private LinearLayout copyPlanContainer;
    private LinearLayout  createPlanButton;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_menu, container, false);

        // Take main activity and context
        ScheduleCreatorActivity activity = (ScheduleCreatorActivity) this.getActivity();
        Context context = getContext();

        List<Scheda> schedaList = new ArrayList<>();

        // Retrieve data
        if (activity != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("MySharedPref", MODE_PRIVATE);
            schedaList = ScheduleCreatingUtils.createListaSchede(sharedPreferences);
        }

        // ------------------------ Initialize Variables ------------------------

        // Initialize switches
        Switch switchDb = view.findViewById(R.id.switchDb);
        Switch switchPrivate = view.findViewById(R.id.switchPrivate);

        // Initialize Title text
        EditText planTitle = view.findViewById(R.id.titoloScheda);

        // Initialize New Schedule layout
        LinearLayout newSchedule = view.findViewById(R.id.new_schedule_button);
        LinearLayout buttonDaysContainer = view.findViewById(R.id.dayNumberContainer);
        LinearLayout buttonDay1 = view.findViewById(R.id.buttonDay1);
        LinearLayout buttonDay2 = view.findViewById(R.id.buttonDay2);
        LinearLayout buttonDay3 = view.findViewById(R.id.buttonDay3);
        LinearLayout buttonDay4 = view.findViewById(R.id.buttonDay4);
        LinearLayout buttonDay5 = view.findViewById(R.id.buttonDay5);
        LinearLayout buttonDay6 = view.findViewById(R.id.buttonDay6);
        LinearLayout buttonDay7 = view.findViewById(R.id.buttonDay7);

        // Initialize Copy Schedule layout
        LinearLayout copyScheduleBtn = view.findViewById(R.id.copy_schedule_button);
        copyPlanContainer = view.findViewById(R.id.copy_plan_container);
        RecyclerView plansContainer = view.findViewById(R.id.list_plans_container);

        // Initialize info-plan layout
        infoPlanContainer = view.findViewById(R.id.info_container);
        treePlanContainer = view.findViewById(R.id.tree_container);
        LinearLayout backInfoButton = view.findViewById(R.id.back_info_button);

        // Initialize Create Button
        createPlanButton = view.findViewById(R.id.confirm_button);


        // ------------------------ Set Variables ------------------------

        List<LinearLayout> daysButtonList = new ArrayList<>();
        daysButtonList.add(buttonDay1);
        daysButtonList.add(buttonDay2);
        daysButtonList.add(buttonDay3);
        daysButtonList.add(buttonDay4);
        daysButtonList.add(buttonDay5);
        daysButtonList.add(buttonDay6);
        daysButtonList.add(buttonDay7);

        CreationCopyPlanAdapter adapter;

        if (schedaList.isEmpty()) {
            adapter = null;
            // TODO potrei creare una textbox che dice che non ci sono liste
            Log.e(tag, "Lista schede vuota");
        } else {
            adapter = new CreationCopyPlanAdapter(view, context, schedaList.toArray(new Scheda[0]), this);
            plansContainer.setHasFixedSize(true);
            plansContainer.setLayoutManager(new LinearLayoutManager(context));
            plansContainer.setAdapter(adapter);
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
                day = "";
                ScheduleCreatingUtils.setBasicColor(daysButtonList);
            }

            copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
            copyPlanContainer.setVisibility(View.GONE);
            infoPlanContainer.setVisibility(View.GONE);
            selectedPlan = null;
            ScheduleCreatingUtils.setCardViewBasicColor(adapter.getCardViewList());

        });

        daysButtonList.forEach(b -> b.setOnClickListener(v -> {
            day = ((TextView) b.getChildAt(0)).getText().toString();
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
                infoPlanContainer.setVisibility(View.GONE);
            } else {
                copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
                createPlanButton.setVisibility(View.GONE);
                copyPlanContainer.setVisibility(View.GONE);
                infoPlanContainer.setVisibility(View.GONE);
                ScheduleCreatingUtils.setCardViewBasicColor(adapter.getCardViewList());
                selectedPlan = null;
            }

            newSchedule.setBackgroundResource(R.drawable.blue_top_button);
            buttonDaysContainer.setVisibility(View.GONE);
            infoPlanContainer.setVisibility(View.GONE);
            day = "";
            ScheduleCreatingUtils.setBasicColor(daysButtonList);

        });

        // ---------------------- Info Schedule Click Listeners ----------------------
        backInfoButton.setOnClickListener( v -> {
            infoPlanContainer.setVisibility(View.GONE);
            copyPlanContainer.setVisibility(View.VISIBLE);
            createPlanButton.setVisibility(View.VISIBLE);
        });

        // ---------------------- Create Click Listeners ----------------------
        createPlanButton.setOnClickListener(v -> {

            if (isNew) {
                String scheduleName = planTitle.getText().toString();

                if (scheduleName.isEmpty()) {
                    Log.e(tag, "Titolo scheda vuoto");
                    Toast toast = Toast.makeText(context, "Titolo scheda vuoto", StringOutputConstants.shortDuration);
                    toast.show();
                    return;
                }
                if (day.isEmpty()) {
                    Log.e(tag, "Numero di giorni non selezionato");
                    Toast toast = Toast.makeText(context, "Numero di giorni non selezionato", StringOutputConstants.shortDuration);
                    toast.show();
                    return;
                }

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(scheduleName, Integer.parseInt(day)));
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

    public void setInfoPlan(Scheda plan, View view, Context context) {
        TextView planTitle = view.findViewById(R.id.plan_title);
        planTitle.setText(plan.getNome());

        treePlanContainer.removeAllViews();

        for(Giornata day : plan.getGiornate()) {
            LinearLayout dayLayout = createHorizotalLinearLayout(context, 0, 0, 0, 0);
            dayLayout.setGravity(Gravity.CENTER);
            TextView dayName = createBasicTextView(context, "Giornata " + day.getNumeroGiornata(), 20f);

            ImageView dayImageView = new ImageView(context);
            dayImageView.setBackgroundResource(R.drawable.point_tree_view);
            dayImageView.setPadding(0,0,0,0);

            dayLayout.addView(dayImageView);
            dayLayout.addView(dayName);

            treePlanContainer.addView(dayLayout);

            for(Esercizio exe : day.getEsercizi()) {
                LinearLayout exeLayout = createHorizotalLinearLayout(context, 0, 40, 0, 0);
                TextView exeName = createBasicTextView(context, "- " + exe.getName(), 18f);

                exeLayout.addView(exeName);

                treePlanContainer.addView(exeLayout);
            }

        }


        copyPlanContainer.setVisibility(View.GONE);
        createPlanButton.setVisibility(View.GONE);
        infoPlanContainer.setVisibility(View.VISIBLE);

    }

    public void setSelectedPlan(Scheda selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

}